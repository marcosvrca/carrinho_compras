package com.example.AtividadeAssociacao.repository;

import com.example.AtividadeAssociacao.model.ItemVenda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ItemVendaRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void deleteAll() {
        em.createQuery("DELETE FROM ItemVenda").executeUpdate();
    }
}
