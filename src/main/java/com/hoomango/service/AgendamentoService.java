package com.hoomango.service;

import com.hoomango.model.Agendamento;
import com.hoomango.model.Cuidador;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class AgendamentoService {

    @PersistenceContext
    private EntityManager em;

    public boolean temConflito(LocalDateTime inicio, LocalDateTime fim, Cuidador cuidador) {
        String jpql = "SELECT COUNT(a) FROM Agendamento a "
                + "WHERE a.cuidador = :cuidador "
                + "AND ( (a.dataInicio < :fim AND a.dataFim > :inicio) )";

        Long count = em.createQuery(jpql, Long.class)
                .setParameter("cuidador", cuidador)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getSingleResult();

        return count > 0;
    }

    public void salvar(Agendamento agendamento) {
        if (agendamento.getId() == null) {
            em.persist(agendamento);
        } else {
            em.merge(agendamento);
        }
    }

    public List<Agendamento> listarPorCuidador(Cuidador cuidador) {
        String jpql = "SELECT a FROM Agendamento a WHERE a.cuidador = :cuidador";
        return em.createQuery(jpql, Agendamento.class)
                .setParameter("cuidador", cuidador)
                .getResultList();
    }

    public void excluir(Long id) {
        Agendamento agendamento = em.find(Agendamento.class, id);
        if (agendamento != null) {
            em.remove(agendamento);
        }
    }
}

