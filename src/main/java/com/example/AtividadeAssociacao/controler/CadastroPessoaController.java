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


    @PostMapping("/salvar")
    public String salvar(@RequestParam String tipo,
                         @RequestParam String nome,
                         @RequestParam String cpfCnpj,
                         @RequestParam String email,
                         @RequestParam String telefone,
                         @RequestParam String password,
                         Model model) {

        Pessoa pessoaToValidate;

        if (tipo.equalsIgnoreCase("FISICA")) {
            PessoaFisica pf = new PessoaFisica();
            pf.setNome(nome);
            pf.setCpf(cpfCnpj);
            pf.setEmail(email);
            pf.setTelefone(telefone);
            pf.setPassword(passwordEncoder.encode(password));
            pf.setRole("ROLE_USER");
            pessoaToValidate = pf;
        } else { // JURIDICA
            PessoaJuridica pj = new PessoaJuridica();
            pj.setRazaoSocial(nome);
            pj.setCnpj(cpfCnpj);
            pj.setEmail(email);
            pj.setTelefone(telefone);
            pj.setPassword(passwordEncoder.encode(password));
            pj.setRole("ROLE_USER");
            pessoaToValidate = pj;
        }

        BindingResult bindingResult = new BeanPropertyBindingResult(pessoaToValidate, "pessoa");

        validator.validate(pessoaToValidate, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("pessoa", pessoaToValidate);
            model.addAttribute("tipo", tipo.toUpperCase());
            model.addAllAttributes(bindingResult.getModel());
            return "clientes/form";
        }

        if (pessoaToValidate instanceof PessoaFisica) {
            pessoaRepository.save((PessoaFisica) pessoaToValidate);
        } else {
            pessoaRepository.save((PessoaJuridica) pessoaToValidate);
        }

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

    @PostMapping("/atualizar")
    public String atualizar(@RequestParam Long id,
                            @RequestParam String tipo,
                            @RequestParam String nome,
                            @RequestParam String cpfCnpj,
                            @RequestParam String email,
                            @RequestParam String telefone,
                            Model model) {

        Pessoa pessoa = pessoaRepository.findById(id);
        if (pessoa == null) {
            return "redirect:/clientes";
        }

        if (tipo.equals("FISICA") && pessoa instanceof PessoaFisica pf) {
            pf.setNome(nome);
            pf.setCpf(cpfCnpj);
            pf.setEmail(email);
            pf.setTelefone(telefone);

            BindingResult bindingResult = new BeanPropertyBindingResult(pf, "pessoa"); // Correct instantiation
            validator.validate(pf, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("pessoa", pf);
                model.addAttribute("tipo", "FISICA");
                model.addAllAttributes(bindingResult.getModel());
                return "clientes/form";
            }
            pessoaRepository.update(pf);
        } else if (tipo.equals("JURIDICA") && pessoa instanceof PessoaJuridica pj) {
            pj.setRazaoSocial(nome);
            pj.setCnpj(cpfCnpj);
            pj.setEmail(email);
            pj.setTelefone(telefone);

            BindingResult bindingResult = new BeanPropertyBindingResult(pj, "pessoa"); // Correct instantiation
            validator.validate(pj, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("pessoa", pj);
                model.addAttribute("tipo", "JURIDICA");
                model.addAllAttributes(bindingResult.getModel());
                return "clientes/form";
            }
            pessoaRepository.update(pj);
        }

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
