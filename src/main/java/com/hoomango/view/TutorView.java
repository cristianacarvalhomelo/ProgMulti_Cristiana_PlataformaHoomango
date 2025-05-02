package com.hoomango.view;

import com.hoomango.model.Tutor;
import com.hoomango.service.TutorService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

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

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
