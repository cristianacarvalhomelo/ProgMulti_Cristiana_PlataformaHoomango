package com.hoomango.view;

import com.hoomango.LoginPage;
import com.hoomango.model.Cuidador;
import com.hoomango.model.Tutor;
import com.hoomango.service.CuidadorService;
import com.hoomango.service.TutorService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("cuidadorView")
@RequestScoped
public class CuidadorView {

    private Cuidador cuidador;

    @Inject
    private CuidadorService cuidadorService;

    @Inject
    private TutorService tutorService;

    @Inject
    private LoginPage loginPage;

    @PostConstruct
    public void init() {
        cuidador = loginPage.getCuidadorLogado();
        if (cuidador == null) {
            cuidador = new Cuidador();
        }
    }

    public String salvar() {
        try {
            Cuidador existenteCuidador = cuidadorService.buscarPorEmail(cuidador.getEmail());
            Tutor existenteTutor = tutorService.buscarPorEmail(cuidador.getEmail());

            if (existenteCuidador != null || existenteTutor != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail já cadastrado em outra conta!", null));
                return null;
            }

            cuidadorService.salvar(cuidador);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso!", null));

            return "login.xhtml?faces-redirect=true";

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao realizar cadastro.", null));
            return null;
        }
    }


    public void atualizarPerfil() {
        try {
            cuidadorService.atualizar(cuidador);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil atualizado!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar perfil.", null));
        }
    }

    public Cuidador getCuidador() {
        return cuidador;
    }

    public void setCuidador(Cuidador cuidador) {
        this.cuidador = cuidador;
    }
}
