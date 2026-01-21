package com.example.AtividadeAssociacao.controler;

import com.example.AtividadeAssociacao.model.Pessoa.Pessoa;
import com.example.AtividadeAssociacao.model.Pessoa.PessoaFisica;
import com.example.AtividadeAssociacao.model.Pessoa.PessoaJuridica;
import com.example.AtividadeAssociacao.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Added import
import org.springframework.validation.BeanPropertyBindingResult; // Added import
import org.springframework.validation.Validator; // Changed import
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class CadastroPessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", pessoaRepository.findAll());
        return "clientes/list";
    }

    @GetMapping("/novo")
    public String novoCliente(@RequestParam(required = false, defaultValue = "FISICA") String tipo, Model model) {
        String tipoUpper = tipo.toUpperCase();
        model.addAttribute("tipo", tipoUpper); // Adiciona o tipo ao modelo

        if ("JURIDICA".equals(tipoUpper)) {
            model.addAttribute("pessoa", new PessoaJuridica());
        } else {
            model.addAttribute("pessoa", new PessoaFisica());
        }
        return "clientes/form";
    }


    @PostMapping(value = "/salvar", params = "tipo=FISICA")
    public String salvarFisica(@ModelAttribute PessoaFisica pessoa, BindingResult bindingResult, Model model) {
        return salvarPessoa(pessoa, "FISICA", bindingResult, model);
    }

    @PostMapping(value = "/salvar", params = "tipo=JURIDICA")
    public String salvarJuridica(@ModelAttribute PessoaJuridica pessoa, BindingResult bindingResult, Model model) {
        return salvarPessoa(pessoa, "JURIDICA", bindingResult, model);
    }

    private String salvarPessoa(Pessoa pessoa, String tipo, BindingResult bindingResult, Model model) {
        validator.validate(pessoa, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("pessoa", pessoa);
            model.addAttribute("tipo", tipo);
            model.addAllAttributes(bindingResult.getModel());
            return "clientes/form";
        }

        pessoa.setPassword(passwordEncoder.encode(pessoa.getPassword()));
        pessoa.setRole("ROLE_USER");
        pessoaRepository.save(pessoa);

        return "redirect:/login";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Pessoa pessoa = pessoaRepository.findById(id);
        if (pessoa == null) {
            return "redirect:/clientes";
        }

        String tipo = (pessoa instanceof PessoaFisica) ? "FISICA" : "JURIDICA";
        model.addAttribute("tipo", tipo);
        model.addAttribute("pessoa", pessoa);

        return "clientes/form";
    }

    @PostMapping(value = "/atualizar", params = "tipo=FISICA")
    public String atualizarFisica(@ModelAttribute PessoaFisica pessoa, BindingResult bindingResult, Model model) {
        return atualizarPessoa(pessoa, "FISICA", bindingResult, model);
    }

    @PostMapping(value = "/atualizar", params = "tipo=JURIDICA")
    public String atualizarJuridica(@ModelAttribute PessoaJuridica pessoa, BindingResult bindingResult, Model model) {
        return atualizarPessoa(pessoa, "JURIDICA", bindingResult, model);
    }

    private String atualizarPessoa(Pessoa pessoa, String tipo, BindingResult bindingResult, Model model) {
        validator.validate(pessoa, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("pessoa", pessoa);
            model.addAttribute("tipo", tipo);
            model.addAllAttributes(bindingResult.getModel());
            return "clientes/form";
        }

        pessoaRepository.update(pessoa);
        return "redirect:/clientes";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id) {
        pessoaRepository.remove(id);
        return "redirect:/clientes";
    }

    @GetMapping("/teste")
    @ResponseBody
    public String teste() {
        return "ok";
    }

}
