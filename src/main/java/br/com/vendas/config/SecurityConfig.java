package br.com.vendas.config;

import br.com.vendas.security.jwt.JwtAuthFilter;
import br.com.vendas.security.jwt.JwtService;
import br.com.vendas.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UsuarioServiceImpl usuarioService;

    @Autowired
    public JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService);
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
                    .antMatchers(HttpMethod.POST, "/api/usuarios/**")
                        .permitAll()
                    .anyRequest().authenticated() // caso tenha esquecido de mapear alguma URL, esse trecho de código garante que pelo menos esteja logado para realizar a request
                .and()
                    .sessionManagement()// definir que n vai mais criar sessões, cada requisição vai conter todos os elementos necessários para ela ocorrer
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        
    }

}
