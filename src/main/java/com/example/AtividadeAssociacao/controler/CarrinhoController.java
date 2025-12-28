package com.example.AtividadeAssociacao.controler;

import com.example.AtividadeAssociacao.model.Carrinho;
import com.example.AtividadeAssociacao.model.Produto;
import com.example.AtividadeAssociacao.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class CarrinhoController {

    @Autowired
    private CarrinhoSession carrinhoSession;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping("/adicionar-ao-carrinho")
    public Map<String, Object> adicionarAoCarrinho(
            @RequestParam Long produtoId,
            @RequestParam int quantidade) {

        Map<String, Object> response = new HashMap<>();

        Produto produto = produtoRepository.buscarPorId(produtoId);

        if (produto == null) {
            response.put("success", false);
            response.put("message", "Produto não encontrado");
            return response;
        }

        Carrinho carrinho = carrinhoSession.getCarrinho();
        carrinho.adicionarProduto(produto, quantidade);

        response.put("success", true);
        response.put("message", "Produto adicionado ao carrinho!");
        return response;
    }
}

