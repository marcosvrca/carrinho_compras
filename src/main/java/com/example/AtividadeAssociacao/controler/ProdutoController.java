package com.example.AtividadeAssociacao.controler;

import com.example.AtividadeAssociacao.model.ItemVenda;
import com.example.AtividadeAssociacao.model.Produto;
import com.example.AtividadeAssociacao.model.Departamento;
import com.example.AtividadeAssociacao.repository.ProdutoRepository;
import com.example.AtividadeAssociacao.repository.DepartamentoRepository;
import jakarta.validation.Valid; // Added import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Added import
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity; // Added import
import org.springframework.http.HttpStatus; // Added import

import java.util.HashMap; // Added import
import java.util.List;
import java.util.Map; // Added import

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository; // Inject DepartamentoRepository

    @Autowired
    private CarrinhoSession carrinhoSession;

    // LISTAR TODOS E FILTRAR POR NOME
    @GetMapping
    public String listar(@RequestParam(required = false) String nome,
                         @RequestParam(required = false) Long departamentoId,
                         Model model) {
        List<Produto> produtos;
        if (departamentoId != null) {
            produtos = produtoRepository.findByDepartamentoId(departamentoId);
        } else if (nome != null && !nome.isEmpty()) {
            produtos = produtoRepository.findByDescricaoContainingIgnoreCase(nome, departamentoId); // Pass departamentoId here
        } else {
            produtos = produtoRepository.findAll();
        }
        model.addAttribute("produtos", produtos);
        model.addAttribute("carrinho", carrinhoSession.getCarrinho());
        model.addAttribute("nome", nome);
        model.addAttribute("departamentos", departamentoRepository.findAll()); // Add all departments to the model
        model.addAttribute("departamentoId", departamentoId); // Add selected department ID
        return "produto/list";
    }

    @GetMapping("/config")
    public String config(@RequestParam(required = false) String nome, Model model) {
        List<Produto> produtos;
        if (nome != null && !nome.isEmpty()) {
            produtos = produtoRepository.findByDescricaoContainingIgnoreCase(nome, null); // Use new method signature
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
        model.addAttribute("departamentos", departamentoRepository.findAll()); // Add all departments to the model
        return "produto/form"; // página form.html
    }

    // SALVAR
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Produto produto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("produto", produto); // Return the object with errors
            model.addAttribute("departamentos", departamentoRepository.findAll()); // Re-add departments on error
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
        model.addAttribute("departamentos", departamentoRepository.findAll()); // Add all departments to the model
        return "produto/form";
    }

    // EXCLUIR
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        produtoRepository.remover(id);
        return "redirect:/produtos/config";
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
}
