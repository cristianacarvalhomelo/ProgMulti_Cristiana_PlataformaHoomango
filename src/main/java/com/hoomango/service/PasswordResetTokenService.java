package com.hoomango.service;

import com.hoomango.model.PasswordResetToken;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;

@Stateless
public class PasswordResetTokenService {

    @PersistenceContext
    private EntityManager em;

    public void salvar(PasswordResetToken token) {
        if (token.getTutor() != null) {
            PasswordResetToken existente = em.createQuery(
                            "SELECT p FROM PasswordResetToken p WHERE p.tutor = :tutor", PasswordResetToken.class)
                    .setParameter("tutor", token.getTutor())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existente != null) {
                em.remove(existente);
                em.flush();
            }
        }

        if (token.getCuidador() != null) {
            PasswordResetToken existente = em.createQuery(
                            "SELECT p FROM PasswordResetToken p WHERE p.cuidador = :cuidador", PasswordResetToken.class)
                    .setParameter("cuidador", token.getCuidador())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existente != null) {
                em.remove(existente);
                em.flush();
            }
        }

        em.persist(token);
    }

    public PasswordResetToken buscarPorToken(String tokenStr) {
        try {
            return em.createQuery("SELECT t FROM PasswordResetToken t WHERE t.token = :token", PasswordResetToken.class)
                    .setParameter("token", tokenStr)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void remover(PasswordResetToken token) {
        PasswordResetToken tokenGerenciado = em.merge(token);
        em.remove(tokenGerenciado);
    }

    public boolean isTokenValido(PasswordResetToken token) {
        return token != null && token.getExpiration().isAfter(LocalDateTime.now());
    }
}

