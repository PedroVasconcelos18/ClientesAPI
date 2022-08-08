package br.com.vendas.rest.controller;

import br.com.vendas.domain.entity.Usuario;
import br.com.vendas.exception.SenhaInvalidaException;
import br.com.vendas.rest.dto.CredenciaisDTO;
import br.com.vendas.rest.dto.TokenDTO;
import br.com.vendas.security.jwt.JwtService;
import br.com.vendas.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor // gera o consturtor com as propriedades que tem o final
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO) {
        try {
            Usuario usuario = Usuario.builder()
                    .login(credenciaisDTO.getLogin())
                    .senha(credenciaisDTO.getSenha())
                    .build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);

            String token = jwtService.gerarToken(usuario);

            return new TokenDTO(usuario.getLogin(), token);

        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
