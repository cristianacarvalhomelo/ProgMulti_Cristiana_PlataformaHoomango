package com.hoomango.view;

import com.hoomango.service.RecuperacaoSenhaService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("resetSenhaView")
@ViewScoped
public class ResetSenhaView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;
    private String novaSenha;

    @Inject
    private RecuperacaoSenhaService recuperacaoSenhaService;

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        token = ec.getRequestParameterMap().get("t");
        System.out.println("Token recebido: " + token);
        if (token == null || token.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Token inválido.", "Nenhum token de redefinição foi informado."));
        }
    }

    public void redefinirSenha() {
        try {
            recuperacaoSenhaService.redefinirSenha(token, novaSenha);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Senha alterada!", "Você já pode fazer login com a nova senha."));
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro", e.getMessage()));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro ao redefinir senha.", "Tente novamente."));
            e.printStackTrace();
        }
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) { this.token = token; }

    public String getNovaSenha() {
        return novaSenha;
    }
    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
