package com.example.AtividadeAssociacao.security;

import com.example.AtividadeAssociacao.model.Pessoa.Pessoa;
import com.example.AtividadeAssociacao.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Pessoa pessoa = pessoaRepository.findByEmail(username);
        if (pessoa == null) throw new UsernameNotFoundException("User not found");

        return new org.springframework.security.core.userdetails.User(
                pessoa.getEmail(),
                pessoa.getPassword(),
                pessoa.getRoles() // Role já é GrantedAuthority
        );
    }
}
