package com.hoomango.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    @Size(min = 1, max = 30)
    private String nome;

    @Size(min = 1, max = 30)
    private String especie;

    private Date dataNascimento;

    @Size(min = 1, max = 30)
    private String raca;

    @Size(min = 1, max = 500)
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
