package com.example.AtividadeAssociacao.model.Pessoa;

import com.example.AtividadeAssociacao.model.Venda;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email; // Added import
import jakarta.validation.constraints.NotBlank; // Added import
import jakarta.validation.constraints.NotNull; // Added import

@Entity
@Table(name = "tb_pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    //Cada classe da hierarquia (Pessoa, PessoaFisica, PessoaJuridica) gera uma tabela própria no banco.
    //As tabelas de subclasses (tb_pessoa_fisica, tb_pessoa_juridica) possuem uma chave estrangeira (id) que referencia a tabela tb_pessoa.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    private String email;

    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @NotBlank(message = "A senha é obrigatória.")
    private String password;

    @NotNull(message = "A role é obrigatória.")
    private String role;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "token_creation_date")
    private java.time.LocalDateTime tokenCreationDate;

    // Um cliente pode ter várias vendas
    @OneToMany(mappedBy = "cliente")
    private java.util.List<Venda> vendas;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public java.time.LocalDateTime getTokenCreationDate() {
        return tokenCreationDate;
    }

    public void setTokenCreationDate(java.time.LocalDateTime tokenCreationDate) {
        this.tokenCreationDate = tokenCreationDate;
    }

    public java.util.List<Venda> getVendas() {
        return vendas;
    }
    public void setVendas(java.util.List<Venda> vendas) {
        this.vendas = vendas;
    }

    public String getIdentificacao() {
        if (this instanceof PessoaFisica) {
            return ((PessoaFisica) this).getNome();
        } else if (this instanceof PessoaJuridica) {
            return ((PessoaJuridica) this).getRazaoSocial();
        }
        return "";
    }

    public abstract String getNome();


}
