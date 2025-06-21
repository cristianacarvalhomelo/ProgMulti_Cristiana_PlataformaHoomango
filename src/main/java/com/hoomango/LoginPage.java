package com.hoomango;

import com.hoomango.model.Cuidador;
import com.hoomango.model.Tutor;
import com.hoomango.service.CuidadorService;
import com.hoomango.service.EmailService;
import com.hoomango.service.TutorService;
import com.hoomango.util.SessionUtil;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.UUID;

@Named("loginPage")
@SessionScoped
public class LoginPage implements Serializable {

    private String email;
    private String senha;
    private boolean logado = false;
    private Tutor tutorLogado;
    private Cuidador cuidadorLogado;

    @Inject
    private CuidadorService cuidadorService;

    @Inject
    private TutorService tutorService;

    public String login() {
        Tutor tutor = tutorService.autenticar(email, senha);
        if (tutor != null) {
            logado = true;
            tutorLogado = tutor;
            SessionUtil.setAtributo("emailUsuarioLogado", tutor.getEmail());
            return "tutor/homeTutor.xhtml?faces-redirect=true";
        }

        Cuidador cuidador = cuidadorService.autenticar(email, senha);
        if (cuidador != null) {
            logado = true;
            cuidadorLogado = cuidador;
            SessionUtil.setAtributo("emailUsuarioLogado", cuidador.getEmail());
            return "cuidador/homeCuidador.xhtml?faces-redirect=true";
        }


        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail ou senha inválidos.", null));
        return null;
    }

    public String logout() {
        logado = false;
        tutorLogado = null;
        cuidadorLogado = null;
        SessionUtil.invalidarSessao();
        return "/home.xhtml?faces-redirect=true";
    }

    public String excluirConta() {
        if (tutorLogado != null) {
            tutorService.excluir(tutorLogado);
        } else if (cuidadorLogado != null) {
            cuidadorService.excluir(cuidadorLogado);
        }
        SessionUtil.invalidarSessao();
        return "/home.xhtml?faces-redirect=true";
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public Tutor getTutorLogado() { return tutorLogado; }
    public void setTutorLogado(Tutor tutorLogado) { this.tutorLogado = tutorLogado; }

    public Cuidador getCuidadorLogado() { return cuidadorLogado; }
    public void setCuidadorLogado(Cuidador cuidadorLogado) { this.cuidadorLogado = cuidadorLogado; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}

