package com.hoomango.service;

import com.hoomango.model.Servico;
import com.hoomango.model.Cuidador;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Stateless
public class ServicoService implements Serializable {
    
    @PersistenceContext
    private EntityManager em;
    
    public void salvar(Servico servico) { em.persist(servico); }

    public List<Servico> listar() { return em.createQuery("SELECT c FROM Servico c", Servico.class).getResultList(); }

    public void excluir(Servico servico) { Servico servicoGerenciado = em.merge(servico); em.remove(servicoGerenciado); }
    
    public void atualizar(Servico servico) { em.merge(servico); }

    public List<Servico> listarPorCuidador(Cuidador cuidador) {
        if (cuidador == null) {
            return Collections.emptyList();
        }
        TypedQuery<Servico> query = em.createQuery(
                "SELECT p FROM Servico p WHERE p.cuidador = :cuidador", Servico.class);
        query.setParameter("cuidador", cuidador);
        return query.getResultList();
    }
    
    
}
