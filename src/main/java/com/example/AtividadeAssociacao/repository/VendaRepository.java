package com.example.AtividadeAssociacao.repository;

import com.example.AtividadeAssociacao.model.Pessoa.Pessoa;
import com.example.AtividadeAssociacao.model.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VendaRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Venda venda) {
        em.persist(venda);
    }

    public Venda findById(Long id) {
        return em.find(Venda.class, id);
    }

    public List<Venda> findAll() {
        Query query = em.createQuery("FROM Venda");
        return query.getResultList();
    }

    @Transactional
    public void deleteAll() {
        em.createQuery("DELETE FROM Venda").executeUpdate();
    }

    @Transactional
    public void remove(Long id) {
        Venda venda = em.find(Venda.class, id);
        if (venda != null) {
            em.remove(venda);
        }
    }
    @Transactional
    public void update(Venda venda) {
        em.merge(venda);
    }

    public List<Venda> findByData(LocalDateTime dataInicio, LocalDateTime dataFim) {
        Query query = em.createQuery("from Venda v where v.data between :dataInicio and :dataFim order by v.data desc");
        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataFim", dataFim);
        return query.getResultList();
    }

        public List<Venda> findByClientes(List<?> clientes) {

            if (clientes == null || clientes.isEmpty()) {

                return List.of();

            }

    

            String jpql = "SELECT v FROM Venda v WHERE v.cliente IN :clientes ORDER BY v.data DESC";

            Query query = em.createQuery(jpql);

            query.setParameter("clientes", clientes);

            return query.getResultList();

        }

    

        public List<Venda> findByClienteAndData(List<Pessoa> clientes, LocalDateTime dataInicio, LocalDateTime dataFim) {

            if (clientes == null || clientes.isEmpty()) return List.of();

            Query query = em.createQuery(

                    "SELECT v FROM Venda v WHERE v.cliente IN :clientes AND v.data BETWEEN :dataInicio AND :dataFim ORDER BY v.data DESC"

            );

            query.setParameter("clientes", clientes);

            query.setParameter("dataInicio", dataInicio);

            query.setParameter("dataFim", dataFim);

            return query.getResultList();

        }

    

        public List<Venda> findByCliente(Pessoa cliente) {

            Query query = em.createQuery("SELECT v FROM Venda v WHERE v.cliente = :cliente ORDER BY v.data DESC");

            query.setParameter("cliente", cliente);

            return query.getResultList();

        }

    public List<Venda> findByClienteAndData(Pessoa cliente, LocalDateTime dataInicio, LocalDateTime dataFim) {
        Query query = em.createQuery(
                "SELECT v FROM Venda v WHERE v.cliente = :cliente AND v.data BETWEEN :dataInicio AND :dataFim ORDER BY v.data DESC"
        );
        query.setParameter("cliente", cliente);
        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataFim", dataFim);
        return query.getResultList();
    }




}
