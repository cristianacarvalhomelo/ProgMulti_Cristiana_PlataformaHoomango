package com.hoomango.view;

import com.hoomango.model.Pet;
import com.hoomango.model.Tutor;
import com.hoomango.service.PetService;
import com.hoomango.service.TutorService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named("tutorPerfilView")
@RequestScoped
public class TutorPerfilView {

    private Long id;
    private Tutor tutor;
    private List<Pet> pets;

    @Inject
    private TutorService tutorService;

    @Inject
    private PetService petService;

    @PostConstruct
    public void init() {
        if (id != null) {
            tutor = tutorService.buscarPorId(id);
            pets = petService.listarPorTutor(tutor);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }

    public List<Pet> getPets() { return pets; }
    public void setPets(List<Pet> pets) { this.pets = pets; }
}

