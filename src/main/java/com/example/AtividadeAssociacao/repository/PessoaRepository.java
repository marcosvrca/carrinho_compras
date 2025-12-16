package com.example.AtividadeAssociacao.repository;

import com.example.AtividadeAssociacao.model.Pessoa.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PessoaRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Pessoa pessoa) {
        em.persist(pessoa);
    }

    public Pessoa findById(Long id) {
        return em.find(Pessoa.class, id);
    }

    public List<Pessoa> findAll() {
        Query query = em.createQuery("FROM Pessoa");
        return query.getResultList();
    }

    @Transactional
    public void remove(Long id) {
        Pessoa pessoa = em.find(Pessoa.class, id);
        if (pessoa != null) {
            em.remove(pessoa);
        }
    }

    @Transactional
    public void update(Pessoa pessoa) {
        em.merge(pessoa);
    }

    @Transactional
    public void deleteAll() {
        em.createQuery("DELETE FROM Pessoa").executeUpdate();
    }

    public List<Pessoa> findByNomeOuRazaoSocial(String nome) {
        List<Pessoa> resultado = new ArrayList<>();

        Query queryFisica = em.createQuery(
                "SELECT p FROM PessoaFisica p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))"
        );
        queryFisica.setParameter("nome", nome);
        resultado.addAll(queryFisica.getResultList());

        Query queryJuridica = em.createQuery(
                "SELECT p FROM PessoaJuridica p WHERE LOWER(p.razaoSocial) LIKE LOWER(CONCAT('%', :nome, '%'))"
        );
        queryJuridica.setParameter("nome", nome);
        resultado.addAll(queryJuridica.getResultList());

        return resultado;
    }

    public Pessoa findByEmail(String email) {
        try {
            return em.createQuery("SELECT p FROM Pessoa p WHERE LOWER(p.email) = LOWER(:email)", Pessoa.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

    public Pessoa findByResetPasswordToken(String token) {
        try {
            return em.createQuery("SELECT p FROM Pessoa p WHERE p.resetPasswordToken = :token", Pessoa.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

}
