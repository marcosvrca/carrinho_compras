package com.example.AtividadeAssociacao.controler;

import com.example.AtividadeAssociacao.model.Departamento;
import com.example.AtividadeAssociacao.model.Produto;
import com.example.AtividadeAssociacao.repository.DepartamentoRepository;
import com.example.AtividadeAssociacao.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public String listar(@RequestParam(required = false) String nome, Model model) {
        List<Departamento> departamentos =
                (nome != null && !nome.isEmpty())
                        ? departamentoRepository.findByNomeContainingIgnoreCase(nome)
                        : departamentoRepository.findAll();

        model.addAttribute("departamentos", departamentos);
        model.addAttribute("nome", nome);
        return "departamento/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("departamento", new Departamento());
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("produtoIdsSelecionados", List.of());
        return "departamento/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departamento inválido id:" + id));

        model.addAttribute("departamento", departamento);
        model.addAttribute("produtos", produtoRepository.findAll());

        // IDs dos produtos que já estão nesse departamento
        List<Long> selecionados = produtoRepository.findByDepartamentoId(id, 0, Integer.MAX_VALUE)
                .stream()
                .map(Produto::getId)
                .toList();

        model.addAttribute("produtoIdsSelecionados", selecionados);

        return "departamento/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Departamento departamento,
                         BindingResult result,
                         @RequestParam(required = false) List<Long> produtoIds,
                         Model model) {

        if (produtoIds == null) produtoIds = List.of();

        if (result.hasErrors()) {
            model.addAttribute("produtos", produtoRepository.findAll());
            model.addAttribute("produtoIdsSelecionados", produtoIds);
            return "departamento/form";
        }

        Departamento deptoSalvo = departamentoRepository.save(departamento);
        List<Produto> atuais = produtoRepository.findByDepartamentoId(deptoSalvo.getId(), 0, Integer.MAX_VALUE);
        for (Produto p : atuais) {
            p.setDepartamento(null);
        }
        produtoRepository.saveAll(atuais);

        if (!produtoIds.isEmpty()) {
            List<Produto> selecionados = produtoRepository.findAllById(produtoIds);
            for (Produto p : selecionados) {
                p.setDepartamento(deptoSalvo);
            }
            produtoRepository.saveAll(selecionados);
        }

        return "redirect:/departamentos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {

        // Desvincula produtos antes de excluir
        produtoRepository.desvincularDepartamento(id);

        departamentoRepository.deleteById(id);

        ra.addFlashAttribute("sucesso", "Departamento excluído e produtos desvinculados.");
        return "redirect:/departamentos";
    }
}
