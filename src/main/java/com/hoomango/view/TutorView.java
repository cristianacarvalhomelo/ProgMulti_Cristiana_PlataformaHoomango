package com.hoomango.view;

import com.hoomango.LoginPage;
import com.hoomango.model.Cuidador;
import com.hoomango.model.Tutor;
import com.hoomango.service.CuidadorService;
import com.hoomango.service.TutorService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("tutorView")
@RequestScoped
public class TutorView {

    private Tutor tutor;
    private String confirmarSenha;

    @Inject
    private TutorService tutorService;

    @Inject
    private CuidadorService cuidadorService;

    @Inject
    private LoginPage loginPage;

    @PostConstruct
    public void init() {
        tutor = loginPage.getTutorLogado();
        if (tutor == null) {
            tutor = new Tutor();
        }
    }

    public String salvar() {
        try {

            if (!tutor.getSenha().equals(confirmarSenha)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "As senhas não coincidem.", null));
                return null;
            }

            Tutor existenteTutor = tutorService.buscarPorEmail(tutor.getEmail());
            Cuidador existenteCuidador = cuidadorService.buscarPorEmail(tutor.getEmail());

            if (existenteTutor != null || existenteCuidador != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail já cadastrado em outra conta!", null));
                return null;
            }
            tutorService.salvar(tutor);
            tutor = new Tutor();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso!", null));

            return "login.xhtml?faces-redirect=true";

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao realizar cadastro.", null));
            return null;
        }
    }


    public String atualizarPerfil() {
        try {
            tutorService.atualizar(tutor);
            loginPage.setTutorLogado(tutor);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil atualizado com sucesso!", null));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar perfil: " + e.getMessage(), null));
        }

        return null;
    }

    public Tutor getTutor() { return tutor; }

    public void setTutor(Tutor tutor) { this.tutor = tutor; }

    public String getConfirmarSenha() { return confirmarSenha; }

    public void setConfirmarSenha(String confirmarSenha) { this.confirmarSenha = confirmarSenha; }

}
