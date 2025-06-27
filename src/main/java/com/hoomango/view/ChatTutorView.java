package com.hoomango.view;

import com.hoomango.LoginPage;
import com.hoomango.model.Cuidador;
import com.hoomango.model.Mensagem;
import com.hoomango.model.Tutor;
import com.hoomango.service.ChatService;
import com.hoomango.service.CuidadorService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named("chatTutorView")
@ViewScoped
public class ChatTutorView implements Serializable {

    private Long cuidadorId;
    private List<Cuidador> cuidadores;
    private List<Mensagem> mensagens;
    private String novaMensagem;
    private Cuidador cuidadorSelecionado;
    private Tutor tutorSelecionado;
    private String participanteAtual;
    private int lastMessageId = 0;

    @Inject
    private CuidadorService cuidadorService;

    @Inject
    private ChatService chatService;

    @Inject
    private LoginPage loginPage;

    @PostConstruct
    public void init() {
        cuidadores = chatService.listarCuidadoresPorTutor(loginPage.getTutorLogado());
    }

    public void iniciarChat() {
        if (cuidadorId != null) {
            cuidadorSelecionado = cuidadorService.buscarPorId(cuidadorId);
            mensagens = chatService.buscarMensagensTutor(tutorSelecionado, loginPage.getCuidadorLogado());
        }
    }

    public void selecionarCuidador(Cuidador cuidador) {
        this.cuidadorSelecionado = cuidador;
        this.participanteAtual = cuidador.getNome();
        mensagens = chatService.buscarMensagensTutor(loginPage.getTutorLogado(), cuidador);
    }

    public void enviar() {
        if (novaMensagem == null || cuidadorSelecionado == null) return;
        Mensagem msg = new Mensagem();
        msg.setTexto(novaMensagem);
        msg.setDataHora(LocalDateTime.now());
        msg.setCuidador(cuidadorSelecionado);
        msg.setTutor(loginPage.getTutorLogado());
        msg.setEnviadoPorTutor(true);
        chatService.enviar(msg);
        mensagens.add(msg);
        novaMensagem = "";
    }

    public void atualizarMensagens() {
        if (cuidadorSelecionado == null) {
            return;
        }

        List<Mensagem> novasMensagens = chatService.buscarMensagensTutor(loginPage.getTutorLogado(), cuidadorSelecionado);

        if (!novasMensagens.isEmpty()) {
            Mensagem ultimaMensagem = novasMensagens.get(novasMensagens.size() - 1);

            if (ultimaMensagem.getId() > lastMessageId && !ultimaMensagem.isEnviadoPorTutor()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Nova mensagem de " + cuidadorSelecionado.getNome(), null));

                PrimeFaces.current().executeScript(
                        "document.getElementById('audioNotificacao').play();" +
                                "notifyUser('Nova mensagem de " + cuidadorSelecionado.getNome() + "', '" +
                                ultimaMensagem.getTexto().replace("'", "\\'") + "');"
                );

                lastMessageId = ultimaMensagem.getId();
            }

            mensagens = novasMensagens;
        }
    }

    public List<Cuidador> getCuidadores() { return cuidadores; }
    public void setCuidadores(List<Cuidador> cuidadores) { this.cuidadores = cuidadores; }

    public List<Mensagem> getMensagens() { return mensagens; }
    public void setMensagens(List<Mensagem> mensagens) { this.mensagens = mensagens; }

    public String getNovaMensagem() { return novaMensagem; }
    public void setNovaMensagem(String novaMensagem) { this.novaMensagem = novaMensagem; }

    public Cuidador getCuidadorSelecionado() { return cuidadorSelecionado; }
    public void setCuidadorSelecionado(Cuidador cuidadorSelecionado) { this.cuidadorSelecionado = cuidadorSelecionado; }

    public String getParticipanteAtual() { return participanteAtual; }
    public void setParticipanteAtual(String participanteAtual) { this.participanteAtual = participanteAtual; }

    public Long getCuidadorId() { return cuidadorId; }
    public void setCuidadorId(Long cuidadorId) { this.cuidadorId = cuidadorId; }

    public void setTutorSelecionado(Tutor tutorSelecionado) { this.tutorSelecionado = tutorSelecionado; }
}

