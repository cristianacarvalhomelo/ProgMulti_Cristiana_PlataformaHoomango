package com.hoomango.view;

import com.hoomango.service.RecuperacaoSenhaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("recuperarSenhaView")
@RequestScoped
public class RecuperarSenhaView {

    private String email;

    @Inject
    private RecuperacaoSenhaService recuperacaoSenhaService;

    public void recuperarSenha() {
        try {
            recuperacaoSenhaService.gerarTokenRecuperacao(email);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Enviado!", "Verifique seu e-mail para redefinir a senha."));
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro", e.getMessage()));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro", "Erro ao tentar enviar o e-mail. Tente novamente."));
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

