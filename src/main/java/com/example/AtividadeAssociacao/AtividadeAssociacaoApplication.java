package com.example.AtividadeAssociacao;

import com.example.AtividadeAssociacao.model.Pessoa.PessoaFisica;
import com.example.AtividadeAssociacao.model.Produto;
import com.example.AtividadeAssociacao.repository.ItemVendaRepository;
import com.example.AtividadeAssociacao.repository.PessoaRepository;
import com.example.AtividadeAssociacao.repository.ProdutoRepository;
import com.example.AtividadeAssociacao.repository.VendaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class AtividadeAssociacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtividadeAssociacaoApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder, ProdutoRepository produtoRepository, VendaRepository vendaRepository, ItemVendaRepository itemVendaRepository) {
		return args -> {
			itemVendaRepository.deleteAll();
			vendaRepository.deleteAll();
			pessoaRepository.deleteAll();
			produtoRepository.deleteAll();

			if (pessoaRepository.findByEmail("admin@admin.com") == null) {
				PessoaFisica admin = new PessoaFisica();
				admin.setNome("Admin");
				admin.setEmail("admin@admin.com");
				admin.setTelefone("11999998888");
				admin.setCpf("12345678900"); // Added CPF
				admin.setPassword(passwordEncoder.encode("password"));
				admin.setRole("ROLE_USER");
				pessoaRepository.save(admin);
			}
			if (pessoaRepository.findByEmail("user@user.com") == null) {
				PessoaFisica user = new PessoaFisica();
				user.setNome("user");
				user.setEmail("user@user.com");
				user.setTelefone("11988887777");
				user.setCpf("00987654321"); // Added CPF
				user.setPassword(passwordEncoder.encode("password"));
				user.setRole("ROLE_ADMIN");
				pessoaRepository.save(user);
			}
			if (produtoRepository.findAll().isEmpty()) {
				for (int i = 1; i <= 50; i++) {
					Produto produto = new Produto();
					produto.setDescricao("Produto " + i);
					produto.setValor(new BigDecimal(10.00 * i));
					produto.setImageUrl("https://picsum.photos/seed/" + i + "/200/300");
					produtoRepository.salvar(produto);
				}
			}
		};
	}

}
