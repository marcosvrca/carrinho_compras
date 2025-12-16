package com.example.AtividadeAssociacao.model.Pessoa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank; // Added import

@Entity
@Table(name = "tb_pessoa_juridica")
public class PessoaJuridica extends Pessoa {

    @Column(name = "razao_social")
    @NotBlank(message = "A Razão Social é obrigatória.")
    private String razaoSocial;

    @NotBlank(message = "O CNPJ é obrigatório.")
    private String cnpj;

    public String getRazaoSocial() {
        return razaoSocial;
    }
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    @Override
    public String getIdentificacao() {
        return razaoSocial;
    }

    @Override
    public String getNome() {
        return razaoSocial;
    }
}
