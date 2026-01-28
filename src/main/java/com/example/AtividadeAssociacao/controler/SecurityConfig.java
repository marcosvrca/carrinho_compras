package com.example.AtividadeAssociacao.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        return mailSender;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        /* ================= ROTAS PÚBLICAS ================= */
                        .requestMatchers(
                                "/", "/home", "/login",
                                "/css/**", "/js/**", "/imagens/**",
                                "/forgot-password", "/reset-password",

                                /* cadastro público */
                                "/clientes/novo",
                                "/clientes/salvar"
                        ).permitAll()

                        /* ================= PRODUTOS (PÚBLICO) ================= */
                        .requestMatchers(
                                "/produtos",
                                "/produtos/adicionar-ao-carrinho",
                                "/produtos/carrinho",
                                "/produtos/detail/**",
                                "/api/cart/item-count",
                                "/produtos/atualizar-carrinho",
                                "/produtos/remover-do-carrinho/**"
                        ).permitAll()

                        /* ================= ADMIN ================= */
                        .requestMatchers(
                                "/produtos/novo",
                                "/produtos/editar/**",
                                "/produtos/excluir/**",
                                "/produtos/config",
                                "/departamentos",
                                "/departamentos/**"
                        ).hasAuthority("ROLE_ADMIN")

                        /* ================= VENDAS ================= */
                        .requestMatchers(
                                "/vendas/finalizar",
                                "/vendas/minhas-compras"
                        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                        .requestMatchers(
                                "/vendas/novo",
                                "/vendas/addItem",
                                "/vendas/removeItem",
                                "/vendas/salvar"
                        ).hasAuthority("ROLE_ADMIN")

                        /* ================= CLIENTES (ADMIN) ================= */
                        .requestMatchers(
                                "/clientes",
                                "/clientes/editar/**",
                                "/clientes/remover/**"
                        ).hasAuthority("ROLE_ADMIN")

                        /* ================= QUALQUER OUTRA ================= */
                        .anyRequest().authenticated()
                )

                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/produtos", true)
                        .permitAll()
                )

                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
}
