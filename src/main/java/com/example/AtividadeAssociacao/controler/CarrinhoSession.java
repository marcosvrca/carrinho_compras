package com.example.AtividadeAssociacao.controler;

import com.example.AtividadeAssociacao.model.Carrinho;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CarrinhoSession {

    private Carrinho carrinho = new Carrinho();

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }
}
