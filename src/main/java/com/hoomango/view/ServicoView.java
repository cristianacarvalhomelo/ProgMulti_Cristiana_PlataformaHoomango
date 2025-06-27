package com.hoomango.view;

import com.hoomango.LoginPage;
import com.hoomango.model.Cuidador;
import com.hoomango.model.Servico;
import com.hoomango.service.ServicoService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("servicoView")
@ViewScoped
public class ServicoView implements Serializable {

    private Servico servico;
    private List<Servico> listaServicos;
    private Servico servicoSelecionado;

    @Inject
    private ServicoService servicoService;

    @Inject
    private LoginPage loginPage;

    @PostConstruct
    public void init() {
        Cuidador cuidadorLogado = loginPage.getCuidadorLogado();
        listaServicos = servicoService.listarPorCuidador(cuidadorLogado);
        servico = new Servico();
    }

    public void listarServicos() {
        listaServicos = servicoService.listar();
    }

    public void salvarOuAtualizar() {
        try {
            Cuidador cuidadorLogado = loginPage.getCuidadorLogado();
            servico.setCuidador(cuidadorLogado);
            if (servico.getId() == null) {
                servicoService.salvar(servico);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Serviço cadastrado com sucesso!", null));
            } else {
                servicoService.atualizar(servico);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Serviço atualizado com sucesso!", null));
            }
            listaServicos = servicoService.listarPorCuidador(cuidadorLogado);
            servico = new Servico();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar serviço.", null));
        }
    }

    public void excluir() {
        System.out.println("Chamou método excluir");
        try {
            if (servicoSelecionado != null) {
                servicoService.excluir(servicoSelecionado);
                Cuidador cuidadorLogado = loginPage.getCuidadorLogado();
                listaServicos = servicoService.listarPorCuidador(cuidadorLogado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Serviço excluído com sucesso!", null));
                servicoSelecionado = null;
            } else { System.out.println("servicoSelecionado está null!");}
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir serviço.", null));
        }
    }

    public void prepararEdicao(Servico servicoSelecionado) {
        this.servico = servicoSelecionado;
    }

    public Servico getServico() { return servico; }
    public void setServico(Servico servico) {this.servico = servico; }

    public List<Servico> getListaServicos() { return listaServicos; }
    public void setListaServicos(List<Servico> listaServicos) {this.listaServicos = listaServicos; }

    public Servico getServicoSelecionado() { return servicoSelecionado; }
    public void setServicoSelecionado(Servico servicoSelecionado) {
        System.out.println("Servico selecionado para exclusão: " + servicoSelecionado.getDescricao());
        this.servicoSelecionado = servicoSelecionado;
    }
}
