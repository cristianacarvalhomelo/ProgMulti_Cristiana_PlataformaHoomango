package com.hoomango.service;

import com.hoomango.model.Cuidador;
import com.hoomango.model.Tutor;
import jakarta.ejb.Stateless;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Stateless
public class CuidadorService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public void salvar(Cuidador cuidador) {
        em.persist(cuidador);
    }

    public List<Cuidador> listar() {
        return em.createQuery("SELECT c FROM Cuidador c", Cuidador.class).getResultList();
    }

    public Cuidador buscarPorEmail(String email) {
        List<Cuidador> resultado = em.createQuery(
                        "SELECT c FROM Cuidador c WHERE c.email = :email", Cuidador.class)
                .setParameter("email", email)
                .getResultList();

        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public void atualizar(Cuidador cuidador) {
        em.merge(cuidador);
    }

    public Cuidador buscarCuidadorLogado() {
        String emailLogado = obterEmailUsuarioLogado();
        return em.createQuery("SELECT c FROM Cuidador c WHERE c.email = :email", Cuidador.class)
                .setParameter("email", emailLogado)
                .getSingleResult();
    }

    private String obterEmailUsuarioLogado() {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("emailUsuarioLogado");
    }
}
