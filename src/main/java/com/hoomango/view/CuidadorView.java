package com.hoomango.view;

import com.hoomango.model.Cuidador;
import com.hoomango.service.CuidadorService;
import jakarta.enterprise.context.RequestScoped;
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

    public Cuidador getCuidador() {
        return cuidador;
    }

    public void setCuidador(Cuidador cuidador) {
        this.cuidador = cuidador;
    }
}
