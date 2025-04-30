package com.hoomango.service;

import com.hoomango.view.Cuidador;
import com.hoomango.view.Tutor;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UsuarioService {

    @PersistenceContext
    private EntityManager em;

    public void cadastrarTutor(String nome, String email, String senha) {
        Tutor tutor = new Tutor();
        tutor.setNome(nome);
        tutor.setEmail(email);
        tutor.setSenha(senha);
        em.persist(tutor);
    }

    public void cadastrarCuidador(String nome, String email, String senha) {
        Cuidador cuidador = new Cuidador();
        cuidador.setNome(nome);
        cuidador.setEmail(email);
        cuidador.setSenha(senha);
        em.persist(cuidador);
    }

    public void salvarTutor(Tutor tutor) {
        em.persist(tutor);
    }

    public void salvarCuidador(Cuidador cuidador) {
        em.persist(cuidador);
    }
}
