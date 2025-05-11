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

    @Inject
    private EmailService emailService;

    private Tutor tutorLogado;
    private Cuidador cuidadorLogado;

    public String login() {
        Tutor tutor = usuarioService.buscarTutorPorEmailESenha(email, senha);
        if (tutor != null) {
            logado = true;
            return "tutor/homeTutor.xhtml?faces-redirect=true";
        }

        Cuidador cuidador = usuarioService.buscarCuidadorPorEmailESenha(email, senha);
        if (cuidador != null) {
            logado = true;
            return "cuidador/homeCuidador.xhtml?faces-redirect=true";
        }

        jakarta.faces.context.FacesContext.getCurrentInstance()
                .addMessage(null, new jakarta.faces.application.FacesMessage("E-mail ou senha inválidos."));
        return null;
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
        Tutor tutor = usuarioService.buscarTutorPorEmail(email);
        Cuidador cuidador = usuarioService.buscarCuidadorPorEmail(email);

        if (tutor != null) {
            emailService.enviarEmail(email, "Recuperação de Senha",
                    "Sua senha cadastrada é: " + tutor.getSenha());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Senha enviada para seu e-mail."));
        } else if (cuidador != null) {
            emailService.enviarEmail(email, "Recuperação de Senha",
                    "Sua senha cadastrada é: " + cuidador.getSenha());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Senha enviada para seu e-mail."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail não encontrado", null));
        }
    }
}
