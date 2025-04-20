package com.hoomango;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class TutorBean implements Serializable {

    private Tutor tutor = new Tutor();

    @Inject
    private UsuarioService usuarioService;

    public void cadastrar() {
        usuarioService.salvarTutor(tutor);
        tutor = new Tutor();
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
