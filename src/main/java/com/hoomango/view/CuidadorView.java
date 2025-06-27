package com.hoomango.view;

import com.hoomango.LoginPage;
import com.hoomango.model.Cuidador;
import com.hoomango.model.Servico;
import com.hoomango.model.Tutor;
import com.hoomango.service.ChatService;
import com.hoomango.service.CuidadorService;
import com.hoomango.service.ServicoService;
import com.hoomango.service.TutorService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.util.LangUtils;

import java.util.List;
import java.util.Locale;

import static java.lang.Integer.getInteger;

@Named("cuidadorView")
@RequestScoped
public class CuidadorView {

    private Cuidador cuidador;
    private List<Cuidador> listaCuidadores;
    private List<Servico> listaServicos;
    private Cuidador cuidadorComServicos;
    private String confirmarSenha;
    private List<Cuidador> listaCuidadoresFiltrados;

    @Inject
    private CuidadorService cuidadorService;

    @Inject
    private TutorService tutorService;

    @Inject
    private ChatService chatService;

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
            listaCuidadoresFiltrados = cuidadorService.listar();
            listaServicos = servicoService.listar();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String salvar() {
        try {

            if (!cuidador.getSenha().equals(confirmarSenha)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "As senhas não coincidem.", null));
                return null;
            }

            Cuidador existenteCuidador = cuidadorService.buscarPorEmail(cuidador.getEmail());
            Tutor existenteTutor = tutorService.buscarPorEmail(cuidador.getEmail());

            if (existenteCuidador != null || existenteTutor != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail já cadastrado em outra conta!", null));
                return null;
            }

            cuidadorService.salvar(cuidador);
            cuidador = new Cuidador();
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

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }

        Cuidador c = (Cuidador) value;
        return (c.getNome() != null && c.getNome().toLowerCase().contains(filterText))
                || (c.getCidade() != null && c.getCidade().toLowerCase().contains(filterText))
                || (c.getEstado() != null && c.getEstado().toLowerCase().contains(filterText))
                || (c.getTelefone() != null && c.getTelefone().toLowerCase().contains(filterText))
                || c.getServicos().stream()
                .anyMatch(s -> (s.getDescricao() + " " + s.getPreco()).toLowerCase().contains(filterText));
    }

    public List<Tutor> getTutoresComQuemConversou() {
        return chatService.listarTutoresPorCuidador(loginPage.getCuidadorLogado());
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

    public String verPerfil(Long id) {
        return "perfilCuidador.xhtml?faces-redirect=true&id=" + cuidador.getId();
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

    public String getConfirmarSenha() { return confirmarSenha; }

    public void setConfirmarSenha(String confirmarSenha) { this.confirmarSenha = confirmarSenha; }

    public List<Cuidador> getListaCuidadoresFiltrados() {
        return listaCuidadoresFiltrados;
    }

    public void setListaCuidadoresFiltrados(List<Cuidador> listaCuidadoresFiltrados) {
        this.listaCuidadoresFiltrados = listaCuidadoresFiltrados;
    }

}