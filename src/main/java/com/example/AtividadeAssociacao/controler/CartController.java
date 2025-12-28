package com.example.AtividadeAssociacao.controler;

import com.example.AtividadeAssociacao.model.Carrinho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CarrinhoSession carrinhoSession;

    @GetMapping("/item-count")
    public Map<String, Integer> getItemCount() {
        int count = carrinhoSession.getCarrinho().getItens().stream()
                        .mapToInt(item -> item.getQuantidade())
                        .sum();
        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);
        return response;
    }
}