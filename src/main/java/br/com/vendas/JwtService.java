package br.com.vendas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}") // pegando ao application.properties
    private String expiracao;
    private String chaveAssinatura;

}
