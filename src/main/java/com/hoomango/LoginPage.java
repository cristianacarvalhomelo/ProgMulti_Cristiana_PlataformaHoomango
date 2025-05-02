package com.hoomango;

import com.hoomango.model.Cuidador;
import com.hoomango.model.Tutor;
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

    // getters e setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public boolean isLogado() { return logado; }

    public Tutor getTutorLogado() { return tutorLogado; }
    public Cuidador getCuidadorLogado() { return cuidadorLogado; }
}
