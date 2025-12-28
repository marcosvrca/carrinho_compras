package com.example.AtividadeAssociacao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin; // Added import
import jakarta.validation.constraints.NotBlank; // Added import
import jakarta.validation.constraints.NotNull; // Added import
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "A descrição do produto é obrigatória.") // Added validation
    private String descricao;

    @NotNull(message = "O valor do produto é obrigatório.") // Added validation
    @DecimalMin(value = "0.01", message = "O valor do produto deve ser maior que zero.") // Added validation
    private BigDecimal valor;

    @Column(length = 512) // Increased length for image URL
    @NotBlank(message = "A URL da imagem é obrigatória.") // Added validation
    private String imageUrl;

    // um pra muitos (itemvenda)
    @OneToMany(mappedBy = "produto")
    private List<ItemVenda> itens;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }
    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return java.util.Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
