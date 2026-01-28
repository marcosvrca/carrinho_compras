package com.example.AtividadeAssociacao.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_item_venda")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    // pertence a um produto
    @ManyToOne(fetch = FetchType.EAGER, optional = false) //  um ItemVenda precisa ter Produto e Venda
    @JoinColumn(name = "id_produto")
    private Produto produto;

    // pertence a uma venda
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_venda")
    private Venda venda;

    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ItemVenda() {

    }

    public BigDecimal total() {
        if (produto == null || produto.getValor() == null)
            return BigDecimal.ZERO;
        return produto.getValor().multiply(BigDecimal.valueOf(quantidade));
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }
    public void setVenda(Venda venda) {
        this.venda = venda;
    }
}
