package com.hoomango.view;

import com.hoomango.model.Cuidador;
import com.hoomango.service.CuidadorService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("cuidadorView")
@RequestScoped
public class CuidadorView {

    private Cuidador cuidador = new Cuidador();

    @Inject
    private CuidadorService cuidadorService;

    public String salvar() {
        cuidadorService.salvar(cuidador);
        return "login.xhtml?faces-redirect=true";
    }

    public void atualizarPerfil() {
        try {
            cuidadorService.atualizar(cuidador);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil atualizado!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar perfil.", null));
        }
    }

    public Cuidador getCuidador() { return cuidador; }

    public void setCuidador(Cuidador cuidador) { this.cuidador = cuidador; }
}
