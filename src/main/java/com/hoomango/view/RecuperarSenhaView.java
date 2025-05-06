package com.hoomango.view;


import com.hoomango.model.Tutor;
import com.hoomango.service.UsuarioService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("recuperacaoSenhaView")
@ViewScoped
public class RecuperarSenhaView implements Serializable {

    private String email;

    @Inject
    private UsuarioService usuarioService;

    public void enviarSenha() {
        Tutor tutor = usuarioService.buscarTutorPorEmail(email);
        if (tutor != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Senha enviada para o e-mail!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail não encontrado", null));
        }
    }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
