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
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private CarrinhoSession carrinhoSession;

    // LISTAR TODOS E FILTRAR POR NOME
    @GetMapping
    public String listar(@RequestParam(required = false) String nome,
                         @RequestParam(required = false) Long departamentoId,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "20") int size,
                         Model model) {
        List<Produto> produtos;
        long totalElements;

        if (departamentoId != null) {
            produtos = produtoRepository.findByDepartamentoId(departamentoId, page, size);
            totalElements = produtoRepository.countByDepartamentoId(departamentoId);
        } else if (nome != null && !nome.isEmpty()) {
            produtos = produtoRepository.findByDescricaoContainingIgnoreCase(nome, null, page, size);
            totalElements = produtoRepository.countByDescricaoContainingIgnoreCase(nome, null);
        } else {
            produtos = produtoRepository.findAll(page, size);
            totalElements = produtoRepository.countAll();
        }

        int totalPages = (int) Math.ceil((double) totalElements / size);

        model.addAttribute("produtos", produtos);
        model.addAttribute("carrinho", carrinhoSession.getCarrinho());
        model.addAttribute("nome", nome);
        model.addAttribute("departamentos", departamentoRepository.findAll());
        model.addAttribute("departamentoId", departamentoId);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size); // Passar o tamanho para mantê-lo nos links de paginação.
        return "produto/list";
    }

    @GetMapping("/config")
    public String config(@RequestParam(required = false) String nome, Model model) {
        List<Produto> produtos;
        // Para fins de configuração, geralmente queremos visualizar todos os produtos relevantes sem paginação para fins de gerenciamento.
        if (nome != null && !nome.isEmpty()) {
            produtos = produtoRepository.findByDescricaoContainingIgnoreCase(nome, null, 0, Integer.MAX_VALUE); //Pega todos os resultados.
        } else {
            produtos = produtoRepository.findAll(0, Integer.MAX_VALUE); // pega todos
        }
        model.addAttribute("produtos", produtos);
        model.addAttribute("nome", nome);
        return "produto/config";
    }

    // FORM NOVO
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("departamentos", departamentoRepository.findAll()); // Adiciona todos os departamentos ao modelo.
        return "produto/form"; // página form.html
    }

    // SALVAR
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Produto produto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("produto", produto); //Retorna o objeto com erros.
            model.addAttribute("departamentos", departamentoRepository.findAll()); // Adiciona novamente os departamentos em caso de erro
            return "produto/form";
        }

        produtoRepository.salvar(produto); // Use o método salvar

        return "redirect:/produtos/config";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.buscarPorId(id);
        model.addAttribute("produto", produto);
        model.addAttribute("departamentos", departamentoRepository.findAll()); // Adiciona todos os departamentos ao modelo.
        return "produto/form";
    }

    // EXCLUIR
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        produtoRepository.remover(id); // Use metodo remover
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
