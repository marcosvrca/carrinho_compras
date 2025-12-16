package com.example.AtividadeAssociacao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrinho implements Serializable {

    private List<ItemVenda> itens = new ArrayList<>();

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemVenda item) {
        // Verifica se o produto já está no carrinho
        for (ItemVenda itemVenda : itens) {
            if (itemVenda.getProduto().equals(item.getProduto())) {
                itemVenda.setQuantidade(itemVenda.getQuantidade() + item.getQuantidade());
                return;
            }
        }
        // Se não estiver, adiciona um novo item
        itens.add(item);
    }

    public void removerItem(Produto produto) {
        itens.removeIf(item -> item.getProduto().equals(produto));
    }

    public void atualizarItem(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            removerItem(produto);
            return;
        }

        for (ItemVenda itemVenda : itens) {
            if (itemVenda.getProduto().equals(produto)) {
                itemVenda.setQuantidade(quantidade);
                return;
            }
        }
    }

    public double getValorTotal() {
        return itens.stream()
                .mapToDouble(item -> item.getProduto().getValor().doubleValue() * item.getQuantidade())
                .sum();
    }

    public void limpar() {
        itens.clear();
    }
}
