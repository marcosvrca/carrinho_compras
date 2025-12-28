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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // Added import
import org.springframework.mail.javamail.JavaMailSender; // Added import
import org.springframework.mail.javamail.JavaMailSenderImpl; // Added import

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
        // Configure mailSender properties (host, port, username, password, etc.)
        // These can be loaded from application.properties or application.yml
        return mailSender;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        // Abertos ao público
                        .requestMatchers("/", "/home", "/login", "/css/**", "/js/**", "/images/**",
                                "/forgot-password", "/reset-password", "/produtos",
                                "/produtos/adicionar-ao-carrinho", "/produtos/carrinho",
                                "/clientes/form", "/clientes/novo", "/clientes/salvar")
                        .permitAll()

                        // ADMIN para produtos sensíveis
                        .requestMatchers("/produtos/novo", "/produtos/editar/**",
                                "/produtos/excluir/**", "/produtos/config")
                        .hasAuthority("ROLE_ADMIN")

                        // Usuário comum pode finalizar compra
                        .requestMatchers("/vendas/finalizar", "/vendas/minhas-compras")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                        // Vendas que são administrativas
                        .requestMatchers("/vendas/novo", "/vendas/addItem",
                                "/vendas/removeItem", "/vendas/salvar")
                        .hasAuthority("ROLE_ADMIN")

                        // Todo o resto de /clientes/** é só admin
                        .requestMatchers("/clientes/**").hasAuthority("ROLE_ADMIN")

                        // Qualquer outro endpoint
                        .anyRequest().authenticated()
                )

                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/produtos", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // Allow GET for /logout
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
