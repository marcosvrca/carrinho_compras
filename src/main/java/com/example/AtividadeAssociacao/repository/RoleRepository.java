package com.example.AtividadeAssociacao.repository;

import com.example.AtividadeAssociacao.security.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RoleRepository {

    @PersistenceContext
    private EntityManager em;

    public Role findById(Long id) {
        return em.find(Role.class, id);
    }

    public Role findByNome(String nome) {
        try {
            return em.createQuery(
                            "SELECT r FROM Role r WHERE r.nome = :nome", Role.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r ORDER BY r.nome", Role.class)
                .getResultList();
    }

    @Transactional
    public Role save(Role role) {
        if (role.getId() == null) {
            em.persist(role);
            return role;
        }
        return em.merge(role);
    }
}
