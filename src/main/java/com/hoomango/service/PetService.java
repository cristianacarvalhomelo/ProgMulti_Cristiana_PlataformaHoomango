package com.hoomango.service;

import com.hoomango.model.Pet;
import com.hoomango.model.Tutor;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Stateless
public class PetService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public void salvar(Pet pet) { em.persist(pet); }

    public List<Pet> listar() { return em.createQuery("SELECT c FROM Pet c", Pet.class).getResultList(); }

    public void excluir(Pet pet) { Pet petGerenciado = em.merge(pet); em.remove(petGerenciado); }

    public void atualizar(Pet pet) { em.merge(pet); }

    public List<Pet> listarPorTutor(Tutor tutor) {
        if (tutor == null) {
            return Collections.emptyList();
        }
        TypedQuery<Pet> query = em.createQuery(
                "SELECT p FROM Pet p WHERE p.tutor = :tutor", Pet.class);
        query.setParameter("tutor", tutor);
        return query.getResultList();
    }
}