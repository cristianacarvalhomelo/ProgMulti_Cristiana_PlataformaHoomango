package com.hoomango.view;

import com.hoomango.LoginPage;
import com.hoomango.model.Agendamento;
import com.hoomango.model.Cuidador;
import com.hoomango.model.Tutor;
import com.hoomango.service.AgendamentoService;
import com.hoomango.service.ChatService;
import com.hoomango.service.TutorService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Named("agendamentoView")
@ViewScoped
public class AgendamentoView implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Tutor> tutoresDisponiveis;
    private Long tutorIdSelecionadoStr;
    private Cuidador cuidador;
    private DefaultScheduleModel eventModel;
    private String titulo;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Date dataMinima;
    private Agendamento agendamentoSelecionado;

    @Inject
    private TutorService tutorService;

    @Inject
    private ChatService chatService;

    @Inject
    private AgendamentoService agendamentoService;

    @Inject
    private LoginPage loginPage;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        cuidador = loginPage.getCuidadorLogado();
        carregarEventos();
        dataMinima = new Date();
        tutoresDisponiveis = chatService.listarTutoresPorCuidador(cuidador);
        for (Tutor t : tutoresDisponiveis) {
            System.out.println("Tutor: " + t.getNome() + " - ID: " + t.getId());
        }
    }

    public void carregarEventos() {
        eventModel.clear();
        if (cuidador != null) {
            List<Agendamento> agendamentos = agendamentoService.listarPorCuidador(cuidador);
            for (Agendamento ag : agendamentos) {
                DefaultScheduleEvent<Agendamento> event = DefaultScheduleEvent.<Agendamento>builder()
                        .title(ag.getTitulo())
                        .startDate(ag.getDataInicio())
                        .endDate(ag.getDataFim())
                        .data(ag)
                        .build();
                eventModel.addEvent(event);
            }

        }
    }

    public void onDateSelect(SelectEvent<LocalDateTime> event) {
        this.dataInicio = event.getObject();
        this.dataFim = event.getObject();
        this.titulo = "";
        this.tutorIdSelecionadoStr = null;
    }

    public void onEventSelect(SelectEvent<DefaultScheduleEvent<Agendamento>> event) {
        this.agendamentoSelecionado = event.getObject().getData();
    }

    public void prepararNovoAgendamento() {
        this.dataInicio = LocalDateTime.now();
        this.dataFim = LocalDateTime.now();
        this.titulo = "";
        this.tutorIdSelecionadoStr = null;
    }

    public void adicionarAgendamento() {
        if (cuidador == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuário logado não encontrado."));
            return;
        }

        if (tutorIdSelecionadoStr == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Selecione um tutor."));
            return;
        }

        Tutor tutorBanco = tutorService.buscarPorId(tutorIdSelecionadoStr);

        if (agendamentoService.temConflito(dataInicio, dataFim, cuidador)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conflito de agendamento", "Já existe um serviço neste horário."));
            return;
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setTitulo(titulo);
        agendamento.setDataInicio(dataInicio);
        agendamento.setDataFim(dataFim);
        agendamento.setCuidador(cuidador);
        agendamento.setTutor(tutorBanco);

        agendamentoService.salvar(agendamento);

        eventModel.addEvent(DefaultScheduleEvent.builder()
                .title(titulo)
                .startDate(dataInicio)
                .endDate(dataFim)
                .build());

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Agendamento salvo!", null));
    }

    public void cancelarAgendamento() {
        if (agendamentoSelecionado != null) {
            agendamentoService.excluir(agendamentoSelecionado.getId());
            carregarEventos();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Agendamento cancelado com sucesso!", null));
        }
    }

    public List<Tutor> getTutoresDisponiveis() { return tutoresDisponiveis; }

    public Long getTutorIdSelecionadoStr() {
        return tutorIdSelecionadoStr; }
    public void setTutorIdSelecionadoStr(Long tutorIdSelecionadoStr) {
        this.tutorIdSelecionadoStr = tutorIdSelecionadoStr; }

    public DefaultScheduleModel getEventModel() { return eventModel; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }

    public Date getDataMinima() { return dataMinima; }
    public void setDataMinima(Date dataMinima) { this.dataMinima = dataMinima; }

    public Agendamento getAgendamentoSelecionado() {
        return agendamentoSelecionado; }
    public void setAgendamentoSelecionado(Agendamento agendamentoSelecionado) {
        this.agendamentoSelecionado = agendamentoSelecionado; }

}