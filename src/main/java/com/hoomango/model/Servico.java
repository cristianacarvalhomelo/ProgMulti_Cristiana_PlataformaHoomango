package com.hoomango.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "servico_id")
    private Long id;

    @Size(min = 1, max = 200)
    private String descricao;

    private float preco;

    @ManyToOne
    @JoinColumn(name = "cuidador_id")
    private Cuidador cuidador;

    public Cuidador getCuidador() { return cuidador; }
    public void setCuidador(Cuidador cuidador) { this.cuidador = cuidador; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public float getPreco() { return preco; }
    public void setPreco(float preco) {this.preco = preco;}
}
