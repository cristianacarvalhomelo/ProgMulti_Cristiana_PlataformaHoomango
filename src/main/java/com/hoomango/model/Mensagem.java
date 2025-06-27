package com.hoomango.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Mensagem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String texto;

    private LocalDateTime dataHora;

    @ManyToOne
    private Tutor tutor;

    @ManyToOne
    private Cuidador cuidador;

    private boolean enviadoPorTutor;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }

    public Cuidador getCuidador() { return cuidador; }
    public void setCuidador(Cuidador cuidador) { this.cuidador = cuidador; }

    public boolean isEnviadoPorTutor() { return enviadoPorTutor; }
    public void setEnviadoPorTutor(boolean enviadoPorTutor) { this.enviadoPorTutor = enviadoPorTutor; }

}

