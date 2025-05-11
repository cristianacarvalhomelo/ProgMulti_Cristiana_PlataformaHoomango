package com.hoomango.view;

import com.hoomango.model.Tutor;
import com.hoomango.service.TutorService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Named("tutorView")
@RequestScoped
public class TutorView {

    private Tutor tutor = new Tutor();

    @Inject
    private TutorService tutorService;

    public String salvar() {
        tutorService.salvar(tutor);
        return "login.xhtml?faces-redirect=true";
    }

    public void atualizarPerfil() {
        try {
            tutorService.atualizar(tutor);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil atualizado!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar perfil.", null));
        }
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
