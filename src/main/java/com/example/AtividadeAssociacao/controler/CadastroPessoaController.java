package com.example.AtividadeAssociacao.controler;

import com.example.AtividadeAssociacao.model.Pessoa.Pessoa;
import com.example.AtividadeAssociacao.model.Pessoa.PessoaFisica;
import com.example.AtividadeAssociacao.model.Pessoa.PessoaJuridica;
import com.example.AtividadeAssociacao.repository.PessoaRepository;
import com.example.AtividadeAssociacao.repository.RoleRepository;
import com.example.AtividadeAssociacao.security.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class CadastroPessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", pessoaRepository.findAll());
        return "clientes/list";
    }

    @GetMapping("/novo")
    public String novoCliente(
            @RequestParam(required = false, defaultValue = "FISICA") String tipo,
            Model model) {

        String tipoUpper = tipo.toUpperCase();
        model.addAttribute("tipo", tipoUpper);

        if ("JURIDICA".equals(tipoUpper)) {
            model.addAttribute("pessoa", new PessoaJuridica());
        } else {
            model.addAttribute("pessoa", new PessoaFisica());
        }

        return "clientes/form";
    }

    @PostMapping(value = "/salvar", params = "tipo=FISICA")
    public String salvarFisica(
            @Valid @ModelAttribute("pessoa") PessoaFisica pessoa,
            BindingResult bindingResult,
            Model model) {

        // senha obrigatória
        if (pessoa.getPassword() == null || pessoa.getPassword().isBlank()) {
            bindingResult.rejectValue("password", "error.password", "A senha é obrigatória.");
        }

        // role padrão = ROLE_USER
        Role roleUser = roleRepository.findByNome("ROLE_USER");
        if (roleUser == null) {
            bindingResult.reject("role", "Role ROLE_USER não encontrada no banco. Verifique o data.sql.");
        } else {
            pessoa.getRoles().clear();
            pessoa.getRoles().add(roleUser);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("tipo", "FISICA");
            return "clientes/form";
        }

        pessoa.setPassword(passwordEncoder.encode(pessoa.getPassword()));
        pessoaRepository.save(pessoa);
        return "redirect:/login";
    }


    @PostMapping(value = "/salvar", params = "tipo=JURIDICA")
    public String salvarJuridica(
            @Valid @ModelAttribute("pessoa") PessoaJuridica pessoa,
            BindingResult bindingResult,
            Model model) {

        // senha obrigatória
        if (pessoa.getPassword() == null || pessoa.getPassword().isBlank()) {
            bindingResult.rejectValue("password", "error.password", "A senha é obrigatória.");
        }

        // role padrão = ROLE_USER
        Role roleUser = roleRepository.findByNome("ROLE_USER");
        if (roleUser == null) {
            bindingResult.reject("role", "Role ROLE_USER não encontrada no banco. Verifique o data.sql.");
        } else {
            pessoa.getRoles().clear();
            pessoa.getRoles().add(roleUser);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("tipo", "JURIDICA");
            return "clientes/form";
        }

        pessoa.setPassword(passwordEncoder.encode(pessoa.getPassword()));
        pessoaRepository.save(pessoa);
        return "redirect:/login";
    }



    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Pessoa pessoa = pessoaRepository.findById(id);
        if (pessoa == null) return "redirect:/clientes";

        if (pessoa instanceof PessoaFisica pf) {
            model.addAttribute("tipo", "FISICA");
            model.addAttribute("pessoa", pf);
        } else if (pessoa instanceof PessoaJuridica pj) {
            model.addAttribute("tipo", "JURIDICA");
            model.addAttribute("pessoa", pj);
        } else {
            return "redirect:/clientes";
        }

        return "clientes/form";
    }


    @PostMapping(value = "/atualizar", params = "tipo=FISICA")
    public String atualizarFisica(
            @Valid @ModelAttribute("pessoa") PessoaFisica pessoa,
            BindingResult bindingResult,
            Model model) {

        return atualizarPessoa(pessoa, "FISICA", bindingResult, model);
    }

    @PostMapping(value = "/atualizar", params = "tipo=JURIDICA")
    public String atualizarJuridica(
            @Valid @ModelAttribute("pessoa") PessoaJuridica pessoa,
            BindingResult bindingResult,
            Model model) {

        return atualizarPessoa(pessoa, "JURIDICA", bindingResult, model);
    }

    private String atualizarPessoa(
            Pessoa pessoa,
            String tipo,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("tipo", tipo);
            return "clientes/form";
        }

        Pessoa pessoaBanco = pessoaRepository.findById(pessoa.getId());

        // mantém a senha atual
        pessoa.setPassword(pessoaBanco.getPassword());

        // mantém as roles atuais
        pessoa.setRoles(pessoaBanco.getRoles());

        pessoaRepository.update(pessoa);
        return "redirect:/clientes";
    }


    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id) {
        pessoaRepository.remove(id);
        return "redirect:/clientes";
    }
}
