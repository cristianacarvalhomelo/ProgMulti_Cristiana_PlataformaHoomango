package com.hoomango.service;

import com.hoomango.model.Cuidador;
import com.hoomango.model.Tutor;
import com.hoomango.util.PasswordUtil;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Stateless
public class CuidadorService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public void salvar(Cuidador cuidador) {
        cuidador.setSenha(PasswordUtil.hashPassword(cuidador.getSenha()));
        em.persist(cuidador);
    }

    public Cuidador autenticar(String email, String senha) {
        try {
            Cuidador cuidador = em.createQuery("SELECT c FROM Cuidador c WHERE c.email = :email", Cuidador.class)
                    .setParameter("email", email)
                    .getSingleResult();

            if (PasswordUtil.checkPassword(senha, cuidador.getSenha())) {
                return cuidador;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Cuidador> listar() {
        return em.createQuery("SELECT c FROM Cuidador c", Cuidador.class).getResultList();
    }

    public void excluir(Cuidador cuidador) {
        Cuidador cuidadorGerenciado = em.merge(cuidador);
        em.remove(cuidadorGerenciado);
    }

    public Cuidador buscarPorEmail(String email) {
        try {
            return em.createQuery("SELECT c FROM Cuidador c WHERE c.email = :email", Cuidador.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void atualizar(Cuidador cuidador) {
        em.merge(cuidador);
    }

}
