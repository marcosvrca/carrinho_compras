package com.example.AtividadeAssociacao.service;


import com.example.AtividadeAssociacao.model.Pessoa.Pessoa;
import com.example.AtividadeAssociacao.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void generateResetPasswordToken(Pessoa pessoa) {
        String token = UUID.randomUUID().toString();
        pessoa.setResetPasswordToken(token);
        pessoa.setTokenCreationDate(LocalDateTime.now());
        pessoaRepository.update(pessoa);
    }

    public Pessoa findByResetPasswordToken(String token) {
        //recebendo o produto para garantir que seja uma entidade gerenciada
        return pessoaRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(Pessoa pessoa, String newPassword) {
        pessoa.setPassword(passwordEncoder.encode(newPassword));
        pessoa.setResetPasswordToken(null);
        pessoa.setTokenCreationDate(null);
        pessoaRepository.update(pessoa);
    }
}
