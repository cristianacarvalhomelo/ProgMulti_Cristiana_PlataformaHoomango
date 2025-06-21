package com.hoomango.view;


import com.hoomango.model.Agendamento;
import com.hoomango.model.Cuidador;
import com.hoomango.service.AgendamentoService;
import com.hoomango.service.CuidadorService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

@Named("cuidadorPerfilView")
@ViewScoped
public class CuidadorPerfilView implements Serializable {

    private Long id;
    private Cuidador cuidador;

    @Inject
    private CuidadorService cuidadorService;

    @Inject
    private AgendamentoService agendamentoService;

    private ScheduleModel eventModel;

    @PostConstruct
    public void init() {
        if (id != null) {
            cuidador = cuidadorService.buscarPorId(id);
            if (cuidador != null) {
                carregarEventos();
            }
        }
    }

    private void carregarEventos() {
        eventModel = new DefaultScheduleModel();
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

    public ScheduleModel getEventModel() {
        return eventModel;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cuidador getCuidador() { return cuidador; }
    public void  setCuidador(Cuidador cuidador) { this.cuidador = cuidador; }
}

