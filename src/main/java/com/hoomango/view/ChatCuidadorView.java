package com.hoomango.view;

import com.hoomango.LoginPage;
import com.hoomango.model.Mensagem;
import com.hoomango.model.Tutor;
import com.hoomango.service.ChatService;
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

@Named("chatCuidadorView")
@ViewScoped
public class ChatCuidadorView implements Serializable {

    private Long tutorId;
    private List<Tutor> tutores;
    private List<Mensagem> mensagens;
    private String novaMensagem;
    private Tutor tutorSelecionado;
    private String participanteAtual;
    private int lastMessageId = 0;

    @Inject private ChatService chatService;
    @Inject private LoginPage loginPage;

    @PostConstruct
    public void init() {
        tutores = chatService.listarTutoresPorCuidador(loginPage.getCuidadorLogado());
    }

    public void selecionarTutor(Tutor tutor) {
        this.tutorSelecionado = tutor;
        this.participanteAtual = tutor.getNome();
        mensagens = chatService.buscarMensagens(loginPage.getCuidadorLogado(), tutor);
    }

    public void enviar() {
        if (novaMensagem == null || tutorSelecionado == null) return;
        Mensagem msg = new Mensagem();
        msg.setTexto(novaMensagem);
        msg.setDataHora(LocalDateTime.now());
        msg.setTutor(tutorSelecionado);
        msg.setCuidador(loginPage.getCuidadorLogado());
        msg.setEnviadoPorTutor(false);
        chatService.enviar(msg);
        mensagens.add(msg);
        novaMensagem = "";
    }

    public void atualizarMensagens() {
        if (tutorSelecionado == null) {
            return;
        }

        List<Mensagem> novasMensagens = chatService.buscarMensagens(loginPage.getCuidadorLogado(), tutorSelecionado);

        if (!novasMensagens.isEmpty()) {
            Mensagem ultimaMensagem = novasMensagens.get(novasMensagens.size() - 1);

            if (ultimaMensagem.getId() > lastMessageId && ultimaMensagem.isEnviadoPorTutor()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Nova mensagem de " + tutorSelecionado.getNome(), null));

                PrimeFaces.current().executeScript(
                        "document.getElementById('audioNotificacao').play();" +
                                "notifyUser('Nova mensagem de " + tutorSelecionado.getNome() + "', '" +
                                ultimaMensagem.getTexto().replace("'", "\\'") + "');"
                );

                lastMessageId = ultimaMensagem.getId();
            }

            mensagens = novasMensagens;
        }
    }



    public List<Tutor> getTutores() {
        return tutores;
    }

    public void setTutores(List<Tutor> tutores) {
        this.tutores = tutores;
    }

    public String getParticipanteAtual() {
        return participanteAtual;
    }

    public void setParticipanteAtual(String participanteAtual) {
        this.participanteAtual = participanteAtual;
    }


    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public String getNovaMensagem() {
        return novaMensagem;
    }

    public void setNovaMensagem(String novaMensagem) {
        this.novaMensagem = novaMensagem;
    }

    public Tutor getTutorSelecionado() {
        return tutorSelecionado;
    }

    public void setTutorSelecionado(Tutor tutorSelecionado) {
        this.tutorSelecionado = tutorSelecionado;
    }

    public int getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(int lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

}
