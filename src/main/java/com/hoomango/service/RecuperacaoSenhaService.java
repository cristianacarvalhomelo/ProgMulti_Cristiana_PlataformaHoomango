package com.hoomango.service;

import com.hoomango.model.Cuidador;
import com.hoomango.model.PasswordResetToken;
import com.hoomango.model.Tutor;
import com.hoomango.util.PasswordUtil;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.UUID;

@Stateless
public class RecuperacaoSenhaService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private PasswordResetTokenService tokenService;

    @Inject
    private TutorService tutorService;

    @Inject
    private CuidadorService cuidadorService;

    @Inject
    private EmailService emailService;

    public void gerarTokenRecuperacao(String email) {
        Tutor tutor = tutorService.buscarPorEmail(email);
        Cuidador cuidador = cuidadorService.buscarPorEmail(email);

        if (tutor != null) {
            PasswordResetToken token = new PasswordResetToken();
            token.setToken(UUID.randomUUID().toString());
            token.setExpiration(LocalDateTime.now().plusHours(1));
            token.setTutor(tutor);
            tokenService.salvar(token);

            emailService.enviarEmail(tutor.getEmail(), "Recuperação de Senha",
                    "Clique para redefinir: http://localhost:8080/hoomango-1.0-SNAPSHOT/resetSenha.xhtml?t=" + token.getToken());

        } else if (cuidador != null) {
            PasswordResetToken token = new PasswordResetToken();
            token.setToken(UUID.randomUUID().toString());
            token.setExpiration(LocalDateTime.now().plusHours(1));
            token.setCuidador(cuidador);
            tokenService.salvar(token);

            emailService.enviarEmail(cuidador.getEmail(), "Recuperação de Senha",
                    "Clique para redefinir: http://localhost:8080/hoomango-1.0-SNAPSHOT/resetSenha.xhtml?t=" + token.getToken());

        } else {
            throw new IllegalArgumentException("E-mail não encontrado.");
        }
    }

    public void redefinirSenha(String tokenStr, String novaSenha) {
        PasswordResetToken token = tokenService.buscarPorToken(tokenStr);

        if (token == null || !tokenService.isTokenValido(token)) {
            throw new IllegalArgumentException("Token inválido ou expirado.");
        }

        if (token.getTutor() != null) {
            Tutor tutor = token.getTutor();
            tutor.setSenha(PasswordUtil.hashPassword(novaSenha));
            tutorService.atualizar(tutor);
        } else if (token.getCuidador() != null) {
            Cuidador cuidador = token.getCuidador();
            cuidador.setSenha(PasswordUtil.hashPassword(novaSenha));
            cuidadorService.atualizar(cuidador);
        }

        tokenService.remover(token);
    }
}

