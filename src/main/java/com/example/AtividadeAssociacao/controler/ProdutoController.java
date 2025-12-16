package com.example.AtividadeAssociacao.controler;

import com.example.AtividadeAssociacao.model.ItemVenda;
import com.example.AtividadeAssociacao.model.Produto;
import com.example.AtividadeAssociacao.repository.ProdutoRepository;
import jakarta.validation.Valid; // Added import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Added import
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CarrinhoSession carrinhoSession;

    // LISTAR TODOS E FILTRAR POR NOME
    @GetMapping
    public String listar(@RequestParam(required = false) String nome, Model model) {
        List<Produto> produtos;
        if (nome != null && !nome.isEmpty()) {
            produtos = produtoRepository.findByDescricaoContainingIgnoreCase(nome);
        } else {
            produtos = produtoRepository.findAll();
        }
        model.addAttribute("produtos", produtos);
        model.addAttribute("carrinho", carrinhoSession.getCarrinho());
        model.addAttribute("nome", nome);
        return "produto/list";
    }

    @GetMapping("/config")
    public String config(@RequestParam(required = false) String nome, Model model) {
        List<Produto> produtos;
        if (nome != null && !nome.isEmpty()) {
            produtos = produtoRepository.findByDescricaoContainingIgnoreCase(nome);
        } else {
            produtos = produtoRepository.findAll();
        }
        model.addAttribute("produtos", produtos);
        model.addAttribute("nome", nome);
        return "produto/config";
    }

    // FORM NOVO
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/form"; // página form.html
    }

    // SALVAR
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Produto produto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("produto", produto); // Return the object with errors
            return "produto/form";
        }

        if (produto.getId() == null) {
            produtoRepository.salvar(produto);
        } else {
            produtoRepository.atualizar(produto);
        }

        return "redirect:/produtos/config";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.buscarPorId(id);
        model.addAttribute("produto", produto);
        return "produto/form";
    }

    // EXCLUIR
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        produtoRepository.remover(id);
        return "redirect:/produtos/config";
    }

    @PostMapping("/adicionar-ao-carrinho")
    public String adicionarAoCarrinho(@RequestParam("produtoId") Long produtoId, @RequestParam("quantidade") int quantidade) {
        Produto produto = produtoRepository.buscarPorId(produtoId);
        if (produto != null) {
            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            carrinhoSession.getCarrinho().adicionarItem(item);
        }
        return "redirect:/produtos";
    }

    @GetMapping("/carrinho")
    public String verCarrinho(Model model) {
        model.addAttribute("carrinho", carrinhoSession.getCarrinho());
        return "carrinho";
    }

    @GetMapping("/remover-do-carrinho/{produtoId}")
    public String removerDoCarrinho(@PathVariable Long produtoId) {
        Produto produto = produtoRepository.buscarPorId(produtoId);
        if (produto != null) {
            carrinhoSession.getCarrinho().removerItem(produto);
        }
        return "redirect:/produtos/carrinho";
    }

    @PostMapping("/atualizar-carrinho")
    public String atualizarCarrinho(@RequestParam("produtoId") Long produtoId, @RequestParam("quantidade") int quantidade) {
        Produto produto = produtoRepository.buscarPorId(produtoId);
        if (produto != null) {
            carrinhoSession.getCarrinho().atualizarItem(produto, quantidade);
        }
        return "redirect:/produtos/carrinho";
    }
}
