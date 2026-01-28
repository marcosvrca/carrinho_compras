package com.example.AtividadeAssociacao.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da role:
     * ROLE_USER
     * ROLE_ADMIN
     */
    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToMany(mappedBy = "roles")
    private List<com.example.AtividadeAssociacao.model.Pessoa.Pessoa> pessoas = new ArrayList<>();

    @Override
    public String getAuthority() {
        return nome;
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<com.example.AtividadeAssociacao.model.Pessoa.Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<com.example.AtividadeAssociacao.model.Pessoa.Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
}
