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
import org.springframework.web.bind.support.SessionStatus; // Import SessionStatus
import org.springframework.format.annotation.DateTimeFormat; // Added import for DateTimeFormat

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vendas")
@SessionAttributes("venda") // Add this annotation
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

    @ModelAttribute("venda") // Initialize venda in session
    public Venda setupVenda() {
        Venda venda = new Venda();
        venda.setItens(new ArrayList<>()); // Ensure items list is not null
        // Do not set cliente or data here, as they are selected/set later
        return venda;
    }

    // ------------------------- LISTAR -------------------------
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("vendas", vendaRepository.findAll());
        return "list";
    }

    // ------------------------- NOVA VENDA -------------------------
    @GetMapping("/novo")
    public String novo(Model model, @ModelAttribute("venda") Venda venda) {
        // The @ModelAttribute("venda") setupVenda() method already ensures a fresh Venda
        // Just provide necessary data for dropdowns
        model.addAttribute("clientes", pessoaRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        return "venda/form";
    }

    // ------------------------- ADICIONAR ITEM -------------------------
    @PostMapping("/addItem")
    public String addItem(@RequestParam Long produtoId,
                          @RequestParam Integer quantidade,
                          @ModelAttribute("venda") Venda venda, // This 'venda' is from session
                          Model model) {

        Produto produto = produtoRepository.buscarPorId(produtoId);

        if (produto != null) {
            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setVenda(venda); // Associate item with the session-managed venda
            venda.getItens().add(item);
        }

        model.addAttribute("clientes", pessoaRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());

        return "venda/form";
    }

    // ------------------------- REMOVER ITEM -------------------------
    @GetMapping("/removeItem")
    public String removeItem(@RequestParam int index,
                             @ModelAttribute("venda") Venda venda, // This 'venda' is from session
                             Model model) {

        if (index >= 0 && index < venda.getItens().size()) {
            venda.getItens().remove(index);
        }

        model.addAttribute("clientes", pessoaRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());

        return "venda/form";
    }

    // ------------------------- SALVAR VENDA -------------------------
    @PostMapping("/salvar")
    public String salvar(@RequestParam Long clienteId,
                         @ModelAttribute("venda") Venda venda, // This 'venda' is from session
                         SessionStatus status) { // Inject SessionStatus

        Pessoa cliente = pessoaRepository.findById(clienteId);
        venda.setCliente(cliente);
        // venda.setData(LocalDateTime.now()); // Removed - Date now comes from the form

        // Ensure ItemVenda's venda field is set for persistence
        for (ItemVenda item : venda.getItens()) {
            item.setVenda(venda);
        }

        vendaRepository.save(venda);
        status.setComplete(); // Clear session attributes

        return "redirect:/vendas";
    }

    // --------------------------- DETALHES ---------------------------
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
            return "redirect:/clientes"; // Or handle error appropriately
        }

        List<Venda> vendas;
        if (inicio != null && fim != null) {
            LocalDateTime dataInicio = inicio.atStartOfDay();
            LocalDateTime dataFim = fim.atTime(LocalTime.MAX);
            vendas = vendaRepository.findByClienteAndData(cliente, dataInicio, dataFim); // Corrected
        } else {
            vendas = vendaRepository.findByCliente(cliente); // Corrected
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
        String username = authentication.getName(); // Get the username (email) of the logged-in user

        Pessoa cliente = pessoaRepository.findByEmail(username);
        if (cliente == null) {
            // This should ideally not happen if user is authenticated and registered
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
            // Re-fetch the product to ensure it's a managed entity
            Produto managedProduto = produtoRepository.buscarPorId(carrinhoItem.getProduto().getId());

            ItemVenda newItemVenda = new ItemVenda();
            newItemVenda.setId(null);
            newItemVenda.setProduto(managedProduto); // Set the managed product
            newItemVenda.setQuantidade(carrinhoItem.getQuantidade());
            newItemVenda.setVenda(venda); // Associate with the new venda
            itensVenda.add(newItemVenda);
        }
        venda.setItens(itensVenda);
        
        vendaRepository.save(venda);
        carrinho.limpar();

                return "venda/sucesso"; // Redirect to success page
            }
        }
