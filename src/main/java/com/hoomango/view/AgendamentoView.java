package com.hoomango.view;

import com.hoomango.LoginPage;
import com.hoomango.model.Agendamento;
import com.hoomango.model.Cuidador;
import com.hoomango.service.AgendamentoService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Named("agendamentoView")
@ViewScoped
public class AgendamentoView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Cuidador cuidador;
    private ScheduleModel eventModel;
    private String titulo;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    @Inject
    private AgendamentoService agendamentoService;

    @Inject
    private LoginPage loginPage;


    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        cuidador = loginPage.getCuidadorLogado();
        carregarEventos();
    }

    private void carregarEventos() {
        eventModel.clear();
        if (cuidador != null) {
            List<Agendamento> agendamentos = agendamentoService.listarPorCuidador(cuidador);
            for (Agendamento ag : agendamentos) {
                DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                        .title(ag.getTitulo())
                        .startDate(ag.getDataInicio())
                        .endDate(ag.getDataFim())
                        .build();
                eventModel.addEvent(event);
            }
        }
    }

    public void onDateSelect(SelectEvent<Date> event) {
        this.dataInicio = convertToLocalDateTime(event.getObject());
        this.dataFim = this.dataInicio;
        this.titulo = "";
    }

    public void prepararNovoAgendamento() {
        this.dataInicio = LocalDateTime.now();
        this.dataFim = LocalDateTime.now();
        this.titulo = "";
    }

    public void adicionarAgendamento() {
        if (cuidador == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro", "Usuário logado não encontrado."));
            return;
        }

        if (agendamentoService.temConflito(dataInicio, dataFim, cuidador)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Conflito de agendamento", "Já existe um serviço neste horário."));
            return;
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setTitulo(titulo);
        agendamento.setDataInicio(dataInicio);
        agendamento.setDataFim(dataFim);
        agendamento.setCuidador(cuidador);

        agendamentoService.salvar(agendamento);

        eventModel.addEvent(DefaultScheduleEvent.builder()
                .title(titulo)
                .startDate(dataInicio)
                .endDate(dataFim)
                .build());

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Agendamento salvo!", null));
    }

    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public ScheduleModel getEventModel() { return eventModel; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
}
