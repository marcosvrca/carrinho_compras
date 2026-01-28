package com.example.AtividadeAssociacao.model;

import com.example.AtividadeAssociacao.model.Pessoa.Pessoa;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    // Uma/varias venda(s) está associada a apenas um cliente
    //Cria a chave estrangeira id_cliente na tabela tb_venda.
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Pessoa cliente;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItemVenda> itens;

    public Double total() {
        if (itens == null) return 0.0;
        double soma = 0.0;
        for (ItemVenda i : itens) {
            soma += i.total().doubleValue();
        }
        return soma;
    }
    @Transient
    public Double getValor() {
        return total();
    }

    public Pessoa getCliente() {
        return cliente;
    }
    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }
    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }
    @Transient
    private ItemVenda itemTemp = new ItemVenda();

    public ItemVenda getItemTemp() {
        return itemTemp;
    }

    public void setItemTemp(ItemVenda itemTemp) {
        this.itemTemp = itemTemp;
    }

}
