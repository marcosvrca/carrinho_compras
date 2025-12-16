package com.example.AtividadeAssociacao.model.Pessoa;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank; // Added import

@Entity
@Table(name = "tb_pessoa_fisica")
public class PessoaFisica extends Pessoa {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;
    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    @Override
    public String getIdentificacao() {
        return nome;
    }
}
