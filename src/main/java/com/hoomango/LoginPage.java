package com.hoomango;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("loginPage")
@SessionScoped
public class LoginPage implements Serializable {

    private String email;
    private String senha;
    private boolean logado = false;

    public String login() {
        if ("admin@hoomango.com".equals(email) && "1234".equals(senha)) {
            logado = true;
            return "home.xhtml?faces-redirect=true";
        } else {
            jakarta.faces.context.FacesContext.getCurrentInstance()
                    .addMessage(null, new jakarta.faces.application.FacesMessage("Login inválido"));
            return null;
        }
    }

    public String logout() {
        jakarta.faces.context.FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true";
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public boolean isLogado() { return logado; }
}

