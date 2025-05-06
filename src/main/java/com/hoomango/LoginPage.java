package com.hoomango;

import com.hoomango.model.Cuidador;
import com.hoomango.model.Tutor;
import com.hoomango.service.EmailService;
import com.hoomango.service.UsuarioService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("loginPage")
@SessionScoped
public class LoginPage implements Serializable {

    private String email;
    private String senha;
    private boolean logado = false;

    @Inject
    private UsuarioService usuarioService;
    private EmailService emailService;
    private String emailRecuperacao;

    private Tutor tutorLogado;
    private Cuidador cuidadorLogado;

    public String login() {
        tutorLogado = usuarioService.autenticarTutor(email, senha);
        cuidadorLogado = usuarioService.autenticarCuidador(email, senha);

        if (tutorLogado != null) {
            logado = true;
            return "home.xhtml?faces-redirect=true";
        } else if (cuidadorLogado != null) {
            logado = true;
            return "home.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Login inválido"));
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true";
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public boolean isLogado() { return logado; }

    public Tutor getTutorLogado() { return tutorLogado; }
    public Cuidador getCuidadorLogado() { return cuidadorLogado; }

    public void recuperarSenha() {
        Tutor tutor = usuarioService.buscarTutorPorEmail(emailRecuperacao);
        Cuidador cuidador = usuarioService.buscarCuidadorPorEmail(emailRecuperacao);

        if (tutor != null) {
            emailService.enviarEmail(emailRecuperacao, "Recuperação de Senha",
                    "Sua senha cadastrada é: " + tutor.getSenha());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Senha enviada para seu e-mail."));
        } else if (cuidador != null) {
            emailService.enviarEmail(emailRecuperacao, "Recuperação de Senha",
                    "Sua senha cadastrada é: " + cuidador.getSenha());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Senha enviada para seu e-mail."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail não encontrado", null));
        }
    }
}
