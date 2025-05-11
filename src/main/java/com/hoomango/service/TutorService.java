package com.hoomango.service;

import com.hoomango.model.Tutor;
import jakarta.ejb.Stateless;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Stateless
public class TutorService implements Serializable {

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

    public void atualizar(Tutor tutor) {
        em.merge(tutor);
    }

    public Tutor buscarTutorLogado() {
        // Exemplo simples — você pode ajustar para pegar o usuário da sessão, etc.
        String emailLogado = obterEmailUsuarioLogado();
        return em.createQuery("SELECT t FROM Tutor t WHERE t.email = :email", Tutor.class)
                .setParameter("email", emailLogado)
                .getSingleResult();
    }

    private String obterEmailUsuarioLogado() {
        // Pega da sessão (ajuste conforme seu login)
        return (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("emailUsuarioLogado");
    }

}
