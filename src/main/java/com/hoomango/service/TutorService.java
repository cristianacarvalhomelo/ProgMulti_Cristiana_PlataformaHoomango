package com.hoomango.service;

import com.hoomango.model.Tutor;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TutorService {

    @PersistenceContext
    private EntityManager em;

    public void salvar(Tutor tutor) {
        em.persist(tutor);
    }

    public List<Tutor> listar() {
        return em.createQuery("SELECT t FROM Tutor t", Tutor.class).getResultList();
    }

    public Tutor buscarPorEmail(String email) {
        List<Tutor> resultado = em.createQuery(
                        "SELECT t FROM Tutor t WHERE t.email = :email", Tutor.class)
                .setParameter("email", email)
                .getResultList();

        return resultado.isEmpty() ? null : resultado.get(0);
    }
}
