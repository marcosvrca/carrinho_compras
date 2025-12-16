package com.example.AtividadeAssociacao.repository;

import com.example.AtividadeAssociacao.model.Pessoa.Pessoa;
import com.example.AtividadeAssociacao.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager em;

    // SALVAR
    public void salvar(Produto produto) {
        em.merge(produto);
    }

    public List<Produto> findAll() {
        return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }

    // ATUALIZAR
    public Produto atualizar(Produto produto) {
        return em.merge(produto);
    }

    // BUSCAR POR ID
    public Produto buscarPorId(Long id) {
        return em.find(Produto.class, id);
    }

    // LISTAR TODOS
    public List<Produto> listarTodos() {
        return em.createQuery("SELECT p FROM Produto p", Produto.class)
                .getResultList();
    }

    @Transactional
    public void deleteAll() {
        em.createQuery("DELETE FROM Produto").executeUpdate();
    }

    // REMOVER POR ID
    public void remover(Long id) {
        Produto p = buscarPorId(id);
        if (p != null) {
            em.remove(p);
        }
    }

    public List<Produto> findByDescricaoContainingIgnoreCase(String descricao) {
        Query query = em.createQuery("SELECT p FROM Produto p WHERE LOWER(p.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))", Produto.class);
        query.setParameter("descricao", descricao);
        return query.getResultList();
    }
}
