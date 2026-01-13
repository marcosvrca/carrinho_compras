package com.example.AtividadeAssociacao.controler;

import com.example.AtividadeAssociacao.model.Carrinho;
import com.example.AtividadeAssociacao.model.ItemVenda;
import com.example.AtividadeAssociacao.model.Pessoa.Pessoa;
import com.example.AtividadeAssociacao.model.Produto;
import com.example.AtividadeAssociacao.model.Venda;
import com.example.AtividadeAssociacao.repository.PessoaRepository;
import com.example.AtividadeAssociacao.repository.ProdutoRepository;
import com.example.AtividadeAssociacao.repository.VendaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vendas")
@SessionAttributes("venda")
public class VendaController {

    private final VendaRepository vendaRepository;
    private final PessoaRepository pessoaRepository;
    private final ProdutoRepository produtoRepository;
    private final CarrinhoSession carrinhoSession;

    public VendaController(VendaRepository vendaRepository,
                           PessoaRepository pessoaRepository,
                           ProdutoRepository produtoRepository,
                           CarrinhoSession carrinhoSession) {
        this.vendaRepository = vendaRepository;
        this.pessoaRepository = pessoaRepository;
        this.produtoRepository = produtoRepository;
        this.carrinhoSession = carrinhoSession;
    }

    @ModelAttribute("venda") //iniciando a venda na sessao
    public Venda setupVenda() {
        Venda venda = new Venda();
        venda.setItens(new ArrayList<>()); // Certifique-se de que a lista de itens não seja nula
        // Não definir o cliente ou os dados aqui, pois eles serão selecionados/definidos posteriormente
        return venda;
    }

    // listar
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("vendas", vendaRepository.findAll());
        return "list";
    }

    // nova venda
    @GetMapping("/novo")
    public String novo(Model model, @ModelAttribute("venda") Venda venda) {
        // O método setupVenda() do atributo @ModelAttribute("venda") já garante uma Venda atualizada
        // Basta fornecer os dados necessários para os menus suspensos.
        model.addAttribute("clientes", pessoaRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll(0, Integer.MAX_VALUE)); // Get all products for selection
        return "venda/form";
    }

    // Adicionar Item
    @PostMapping("/addItem")
    public String addItem(@RequestParam Long produtoId,
                          @RequestParam Integer quantidade,
                          @ModelAttribute("venda") Venda venda, // Esta 'venda' é da sessão
                          Model model) {

        Produto produto = produtoRepository.buscarPorId(produtoId);

        if (produto != null) {
            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setVenda(venda); // associa o item a session-maneged por sessao
            venda.getItens().add(item);
        }

        model.addAttribute("clientes", pessoaRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll(0, Integer.MAX_VALUE)); // Corrected
        return "venda/form";
    }

    //Remover item
    @GetMapping("/removeItem")
    public String removeItem(@RequestParam int index,
                             @ModelAttribute("venda") Venda venda, // Esta 'venda' é da sessão
                             Model model) {

        if (index >= 0 && index < venda.getItens().size()) {
            venda.getItens().remove(index);
        }

        model.addAttribute("clientes", pessoaRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll(0, Integer.MAX_VALUE));

        return "venda/form";
    }

    // ------------------------- SALVAR VENDA -------------------------
    @PostMapping("/salvar")
    public String salvar(@RequestParam Long clienteId,
                         @ModelAttribute("venda") Venda venda, // Esta 'venda' é da sessão
                         SessionStatus status) { // Injetar SessionStatus

        Pessoa cliente = pessoaRepository.findById(clienteId);
        venda.setCliente(cliente);
        // venda.setData(LocalDateTime.now()); // Removido - A data agora vem do formulário

        // certificando de que o campo "venda" do ItemVenda esteja configurado para persistência
        for (ItemVenda item : venda.getItens()) {
            item.setVenda(venda);
        }

        vendaRepository.save(venda);
        status.setComplete(); // Limpa atributos da sessao

        return "redirect:/vendas";
    }

    //Detalhes
    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        Venda venda = vendaRepository.findById(id);
        if (venda == null) {
            return "redirect:/vendas";
        }
        model.addAttribute("venda", venda);
        return "detail";
    }

    @GetMapping("/filtrar")
    public String filtrar(
            @RequestParam(required = false) String nomeCliente,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            Model model) {

        List<Venda> vendas;

        if (nomeCliente != null && !nomeCliente.trim().isEmpty() && inicio != null && fim != null) {
            List<Pessoa> clientes = pessoaRepository.findByNomeOuRazaoSocial(nomeCliente);
            LocalDateTime dataInicio = inicio.atStartOfDay();
            LocalDateTime dataFim = fim.atTime(LocalTime.MAX);
            vendas = vendaRepository.findByClienteAndData(clientes, dataInicio, dataFim);

        } else if (nomeCliente != null && !nomeCliente.trim().isEmpty()) {
            List<Pessoa> clientes = pessoaRepository.findByNomeOuRazaoSocial(nomeCliente);
            vendas = vendaRepository.findByClientes(clientes);

        } else if (inicio != null && fim != null) {
            LocalDateTime dataInicio = inicio.atStartOfDay();
            LocalDateTime dataFim = fim.atTime(LocalTime.MAX);
            vendas = vendaRepository.findByData(dataInicio, dataFim);

        } else {
            vendas = vendaRepository.findAll();
        }

        model.addAttribute("vendas", vendas);
        model.addAttribute("nomeCliente", nomeCliente);
        model.addAttribute("inicio", inicio);
        model.addAttribute("fim", fim);
        model.addAttribute("filtroAtivo", true);
        model.addAttribute("clientes", pessoaRepository.findAll());

        return "list";
    }

    @GetMapping("/cliente/{clientId}")
    public String vendasPorCliente(@PathVariable Long clientId,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
                                   Model model) {
        Pessoa cliente = pessoaRepository.findById(clientId);
        if (cliente == null) {
            return "redirect:/clientes";
        }

        List<Venda> vendas;
        if (inicio != null && fim != null) {
            LocalDateTime dataInicio = inicio.atStartOfDay();
            LocalDateTime dataFim = fim.atTime(LocalTime.MAX);
            vendas = vendaRepository.findByClienteAndData(cliente, dataInicio, dataFim);
        } else {
            vendas = vendaRepository.findByCliente(cliente);
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("vendas", vendas);
        model.addAttribute("inicio", inicio);
        model.addAttribute("fim", fim);

        return "venda/vendas-por-cliente";
    }

    @GetMapping("/minhas-compras")
    public String minhasCompras(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Pega o nome de usuário (e-mail) do usuário conectado

        Pessoa cliente = pessoaRepository.findByEmail(username);
        if (cliente == null) {
            return "redirect:/login?error=clientNotFound";
        }

        List<Venda> minhasVendas = vendaRepository.findByCliente(cliente);
        model.addAttribute("minhasVendas", minhasVendas);

        return "venda/minhas-compras";
    }

    @GetMapping("/finalizar")
    public String finalizar() {
        Carrinho carrinho = carrinhoSession.getCarrinho();
        if (carrinho.getItens().isEmpty()) {
            return "redirect:/produtos";
        }

        Venda venda = new Venda();
        venda.setData(LocalDateTime.now());
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        Pessoa cliente = pessoaRepository.findByEmail(username);
        if (cliente == null) {
            return "redirect:/login?error=clientNotFound";
        }
        venda.setCliente(cliente);

        List<ItemVenda> itensVenda = new ArrayList<>();
        for (ItemVenda carrinhoItem : carrinho.getItens()) {
            // Recupere o produto para garantir que seja uma entidade gerenciada
            Produto managedProduto = produtoRepository.buscarPorId(carrinhoItem.getProduto().getId());

            ItemVenda newItemVenda = new ItemVenda();
            newItemVenda.setId(null);
            newItemVenda.setProduto(managedProduto);
            newItemVenda.setQuantidade(carrinhoItem.getQuantidade());
            newItemVenda.setVenda(venda);
            itensVenda.add(newItemVenda);
        }
        venda.setItens(itensVenda);
        
        vendaRepository.save(venda);
        carrinho.limpar();

                return "venda/sucesso";
            }
        }
