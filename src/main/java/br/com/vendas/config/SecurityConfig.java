package br.com.vendas.config;

import br.com.vendas.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UsuarioServiceImpl usuarioService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService) // carregar os usuarios
                .passwordEncoder(passwordEncoder()); // comparar a senha dos usuarios
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http // o CSRF é uma configuração q permite que haja uma segurança entre uma aplicação web e o backend. vamos trabalhar no modo stateless ent n é necessário
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/clientes/**")
                .hasAnyRole("USER")
                .antMatchers("/api/produtos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/pedidos/**")
                .hasRole("USER")
                .and()
                .httpBasic();
    }

}
