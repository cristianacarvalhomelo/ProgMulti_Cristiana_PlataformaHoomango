package com.hoomango.view;

import com.hoomango.service.UsuarioService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;

@Named
@RequestScoped
public class CadastroUsuarioView {

    private String nome;
    private String email;
    private String senha;

    @Inject
    private UsuarioService usuarioService;

    public void cadastrarTutor() {
        usuarioService.cadastrarTutor(nome, email, senha);
    }

    public void cadastrarCuidador() {
        usuarioService.cadastrarCuidador(nome, email, senha);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
