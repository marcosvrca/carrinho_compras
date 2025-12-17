package com.example.AtividadeAssociacao;

import com.example.AtividadeAssociacao.model.Pessoa.PessoaFisica;
import com.example.AtividadeAssociacao.model.Pessoa.PessoaJuridica;
import com.example.AtividadeAssociacao.model.Produto;
import com.example.AtividadeAssociacao.repository.ItemVendaRepository;
import com.example.AtividadeAssociacao.repository.PessoaRepository;
import com.example.AtividadeAssociacao.repository.ProdutoRepository;
import com.example.AtividadeAssociacao.repository.VendaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class AtividadeAssociacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtividadeAssociacaoApplication.class, args);
	}
}
