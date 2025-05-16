package com.hoomango.service;

import com.hoomango.model.Tutor;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Stateless
public class TutorService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public void salvar(Tutor tutor) { em.persist(tutor); }

    public void atualizar(Tutor tutor) { em.merge(tutor); }

    public List<Tutor> listar() {
        return em.createQuery("SELECT t FROM Tutor t", Tutor.class).getResultList();
    }

    public void excluir(Tutor tutor) { Tutor tutorGerenciado = em.merge(tutor); em.remove(tutorGerenciado); }

    public Tutor buscarPorEmail(String email) {
        try {
            return em.createQuery("SELECT t FROM Tutor t WHERE t.email = :email", Tutor.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Tutor autenticar(String email, String senha) {
        try {
            return em.createQuery("SELECT t FROM Tutor t WHERE t.email = :email AND t.senha = :senha", Tutor.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
