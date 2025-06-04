package com.hoomango.view;

import com.hoomango.LoginPage;
import com.hoomango.model.Cuidador;
import com.hoomango.model.Servico;
import com.hoomango.model.Tutor;
import com.hoomango.service.CuidadorService;
import com.hoomango.service.ServicoService;
import com.hoomango.service.TutorService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named("cuidadorView")
@RequestScoped
public class CuidadorView {

    private Cuidador cuidador;
    private List<Cuidador> listaCuidadores;
    private List<Servico> listaServicos;
    private Cuidador cuidadorComServicos;

    @Inject
    private CuidadorService cuidadorService;

    @Inject
    private TutorService tutorService;

    @Inject
    private LoginPage loginPage;

    @Inject
    private ServicoService servicoService;

    @PostConstruct
    public void init() {
        try {
            if (loginPage != null && loginPage.getCuidadorLogado() != null) {
                cuidador = loginPage.getCuidadorLogado();
                cuidadorComServicos = cuidadorService.buscarComServicos(cuidador.getId());
            } else {
                cuidador = new Cuidador();
                cuidadorComServicos = null;
            }

            listaCuidadores = cuidadorService.listar();
            listaServicos = servicoService.listar();

        } catch (Exception e) {
            e.printStackTrace();
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

    public List<Cuidador> getListaCuidadores() {
        return listaCuidadores;
    }

    public void setListaCuidadores(List<Cuidador> listaCuidadores) {
        this.listaCuidadores = listaCuidadores;
    }

    public List<Servico> getListaServicos() {
        return listaServicos;
    }

    public void setListaServicos(List<Servico> listaServicos) {
        this.listaServicos = listaServicos;
    }

    public Cuidador getCuidadorComServicos() {
        return cuidadorComServicos;
    }

    public void setCuidadorComServicos(Cuidador cuidadorComServicos) {
        this.cuidadorComServicos = cuidadorComServicos;
    }
}
