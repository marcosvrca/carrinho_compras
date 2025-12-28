package com.example.AtividadeAssociacao.controler;

import com.example.AtividadeAssociacao.model.Departamento;
import com.example.AtividadeAssociacao.repository.DepartamentoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @GetMapping
    public String listar(@RequestParam(required = false) String nome, Model model) {
        List<Departamento> departamentos;
        if (nome != null && !nome.isEmpty()) {
            departamentos = departamentoRepository.findByNomeContainingIgnoreCase(nome);
        } else {
            departamentos = departamentoRepository.findAll();
        }
        model.addAttribute("departamentos", departamentos);
        model.addAttribute("nome", nome);
        return "departamento/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("departamento", new Departamento());
        return "departamento/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Departamento departamento, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departamento", departamento);
            return "departamento/form";
        }

        if (departamento.getId() == null) {
            departamentoRepository.save(departamento);
        } else {
            departamentoRepository.save(departamento); // JpaRepository save handles update if ID exists
        }

        return "redirect:/departamentos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departamento inválido id:" + id));
        model.addAttribute("departamento", departamento);
        return "departamento/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        departamentoRepository.deleteById(id);
        return "redirect:/departamentos";
    }
}
