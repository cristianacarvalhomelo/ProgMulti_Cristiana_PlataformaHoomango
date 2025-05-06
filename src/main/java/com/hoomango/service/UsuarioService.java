package com.hoomango.service;

import com.hoomango.model.Cuidador;
import com.hoomango.model.Tutor;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;

@Stateless
public class UsuarioService implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public Tutor autenticarTutor(String email, String senha) {
        try {
            return em.createQuery("SELECT t FROM Tutor t WHERE t.email = :email AND t.senha = :senha", Tutor.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

    public Cuidador autenticarCuidador(String email, String senha) {
        try {
            return em.createQuery("SELECT c FROM Cuidador c WHERE c.email = :email AND c.senha = :senha", Cuidador.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }


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

    public Tutor buscarTutorPorEmail(String email) {
        try {
            return em.createQuery("SELECT t FROM Tutor t WHERE t.email = :email", Tutor.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

    public Cuidador buscarCuidadorPorEmail(String email) {
        try {
            return em.createQuery("SELECT c FROM Cuidador c WHERE c.email = :email", Cuidador.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

}

