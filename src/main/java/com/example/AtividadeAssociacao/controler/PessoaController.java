package com.example.AtividadeAssociacao.controler;

import com.example.AtividadeAssociacao.model.Pessoa.Pessoa;
import com.example.AtividadeAssociacao.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping
    public String listarPessoas(@RequestParam(required = false) String nome, Model model) {
        List<Pessoa> pessoas;

        if (nome != null && !nome.trim().isEmpty()) {
            pessoas = pessoaRepository.findByNomeOuRazaoSocial(nome);
        } else {
            pessoas = pessoaRepository.findAll();
        }

        model.addAttribute("pessoas", pessoas);
        model.addAttribute("nome", nome);
        return "/list";
    }
}
