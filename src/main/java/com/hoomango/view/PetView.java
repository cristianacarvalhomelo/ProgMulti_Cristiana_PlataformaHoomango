package com.hoomango.view;

import com.hoomango.LoginPage;
import com.hoomango.model.Pet;
import com.hoomango.model.Tutor;
import com.hoomango.service.PetService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@Named("petView")
@ViewScoped
public class PetView implements Serializable {

    private Pet pet;
    private List<Pet> listaPets;
    private Pet petSelecionado;

    @Inject
    private PetService petService;

    @Inject
    private LoginPage loginPage;

    @PostConstruct
    public void init() {
        Tutor tutorLogado = loginPage.getTutorLogado();
        listaPets = petService.listarPorTutor(tutorLogado);
        pet = new Pet();
    }

    public void listarPets() {
        listaPets = petService.listar();
    }

    @Transactional
    public void salvarOuAtualizar() {
        try {
            Tutor tutorLogado = loginPage.getTutorLogado();
            pet.setTutor(tutorLogado);
            if (pet.getId() == null) {
                petService.salvar(pet);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Pet cadastrado com sucesso!", null));
            } else {
                petService.atualizar(pet);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Pet atualizado com sucesso!", null));
            }
            listaPets = petService.listarPorTutor(tutorLogado);
            pet = new Pet();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar pet.", null));
        }
    }

    public void excluir(Pet petSelecionado) {
        try {
            Tutor tutorLogado = loginPage.getTutorLogado();
            pet.setTutor(tutorLogado);
            petService.excluir(petSelecionado);
            listaPets = petService.listarPorTutor(tutorLogado);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Pet excluído com sucesso!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir pet.", null));
        }
    }

    public void prepararEdicao(Pet petSelecionado) {
        this.pet = petSelecionado;
    }

    public void cancelar() {
        this.pet = new Pet();
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public List<Pet> getListaPets() {
        return listaPets;
    }

    public void setListaPets(List<Pet> listaPets) {
        this.listaPets = listaPets;
    }

    public Pet getPetSelecionado() {
        return petSelecionado;
    }

    public void setPetSelecionado(Pet petSelecionado) {
        this.petSelecionado = petSelecionado;
    }
}
