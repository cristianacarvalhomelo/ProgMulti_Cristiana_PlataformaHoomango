package com.hoomango.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "cuidador")
public class Cuidador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuidador_id")
    private Long id;

    @Size(min = 1, max = 30)
    private String nome;

    @Column(unique = true)
    @Size(min = 2, max = 50)
    private String email;

    private String senha;

    private String telefone;

    @OneToMany(mappedBy = "cuidador", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Servico> servicos = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public List<Servico> getServicos() { return servicos; }
    public void setServicos(List<Servico> servicos) { this.servicos = servicos; }

    public String getServicosAsString() {
        if (servicos == null || servicos.isEmpty()) {
            return "Nenhum";
        }
        return servicos.stream()
                .map(Servico::getDescricao)
                .collect(Collectors.joining(", "));
    }

    @Transient
    public String getServicosDescricao() {
        if (servicos == null || servicos.isEmpty()) {
            return "";
        }
        return servicos.stream()
                .map(Servico::getDescricao)
                .reduce("", (a, b) -> a + ", " + b);
    }


}
