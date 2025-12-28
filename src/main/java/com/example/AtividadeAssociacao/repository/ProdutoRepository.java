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

    // BUSCAR POR ID
    public Produto buscarPorId(Long id) {
        return em.find(Produto.class, id);
    }

    // REMOVER POR ID
    public void remover(Long id) {
        Produto p = buscarPorId(id);
        if (p != null) {
            em.remove(p);
        }
    }

    public List<Produto> findAll(int page, int size) {
        return em.createQuery("SELECT p FROM Produto p", Produto.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    public long countAll() {
        return em.createQuery("SELECT COUNT(p) FROM Produto p", Long.class).getSingleResult();
    }

    public List<Produto> findByDescricaoContainingIgnoreCase(String descricao, Long departamentoId, int page, int size) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Produto p WHERE 1=1");
        if (descricao != null && !descricao.isEmpty()) {
            jpql.append(" AND LOWER(p.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))");
        }
        if (departamentoId != null) {
            jpql.append(" AND p.departamento.id = :departamentoId");
        }
        Query query = em.createQuery(jpql.toString(), Produto.class);
        if (descricao != null && !descricao.isEmpty()) {
            query.setParameter("descricao", descricao);
        }
        if (departamentoId != null) {
            query.setParameter("departamentoId", departamentoId);
        }
        return query.setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    public long countByDescricaoContainingIgnoreCase(String descricao, Long departamentoId) {
        StringBuilder jpql = new StringBuilder("SELECT COUNT(p) FROM Produto p WHERE 1=1");
        if (descricao != null && !descricao.isEmpty()) {
            jpql.append(" AND LOWER(p.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))");
        }
        if (departamentoId != null) {
            jpql.append(" AND p.departamento.id = :departamentoId");
        }
        Query query = em.createQuery(jpql.toString(), Long.class);
        if (descricao != null && !descricao.isEmpty()) {
            query.setParameter("descricao", descricao);
        }
        if (departamentoId != null) {
            query.setParameter("departamentoId", departamentoId);
        }
        return (Long) query.getSingleResult();
    }

    public List<Produto> findByDepartamentoId(Long departamentoId, int page, int size) {
        return em.createQuery("SELECT p FROM Produto p WHERE p.departamento.id = :departamentoId", Produto.class)
                .setParameter("departamentoId", departamentoId)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    public long countByDepartamentoId(Long departamentoId) {
        return em.createQuery("SELECT COUNT(p) FROM Produto p WHERE p.departamento.id = :departamentoId", Long.class)
                .setParameter("departamentoId", departamentoId)
                .getSingleResult();
    }
}
