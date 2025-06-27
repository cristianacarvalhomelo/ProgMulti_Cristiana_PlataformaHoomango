package com.hoomango.service;

import com.hoomango.model.Mensagem;
import com.hoomango.model.Cuidador;
import com.hoomango.model.Tutor;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ChatService {

    @PersistenceContext
    private EntityManager em;

    public void enviar(Mensagem msg) {
        em.persist(msg);
    }

    public List<Mensagem> buscarMensagens(Cuidador cuidador, Tutor tutor) {
        return em.createQuery(
                        "SELECT m FROM Mensagem m WHERE m.cuidador = :cuidador AND m.tutor = :tutor ORDER BY m.dataHora",
                        Mensagem.class)
                .setParameter("cuidador", cuidador)
                .setParameter("tutor", tutor)
                .getResultList();
    }

    public List<Mensagem> buscarMensagensTutor(Tutor tutor, Cuidador cuidador) {
        return em.createQuery(
                        "SELECT m FROM Mensagem m WHERE m.tutor = :tutor AND m.cuidador = :cuidador ORDER BY m.dataHora",
                        Mensagem.class)
                .setParameter("tutor", tutor)
                .setParameter("cuidador", cuidador)
                .getResultList();
    }

    public List<Tutor> listarTutoresPorCuidador(Cuidador cuidador) {
        return em.createQuery(
                        "SELECT DISTINCT m.tutor FROM Mensagem m WHERE m.cuidador = :cuidador",
                        Tutor.class)
                .setParameter("cuidador", cuidador)
                .getResultList();
    }

    public List<Cuidador> listarCuidadoresPorTutor(Tutor tutor) {
        return em.createQuery(
                        "SELECT DISTINCT m.cuidador FROM Mensagem m WHERE m.tutor = :tutor",
                        Cuidador.class)
                .setParameter("tutor", tutor)
                .getResultList();
    }

}
