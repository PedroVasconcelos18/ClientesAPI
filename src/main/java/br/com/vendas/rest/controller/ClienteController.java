package br.com.vendas.rest.controller;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.domain.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Example.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    ClientesRepository clientesRepository;

    @GetMapping("{id}")
    public Cliente getClienteById( @PathVariable Integer id ) {
        return clientesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save( @RequestBody Cliente cliente) {
        return clientesRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ) {
       clientesRepository.findById(id)
               .map( cliente -> {
                   clientesRepository.delete(cliente);
                   return cliente;
               })
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id,
                                  @RequestBody Cliente cliente ) {

        clientesRepository
                .findById(id)
                .map( newCliente -> {
                    cliente.setId(newCliente.getId());
                    clientesRepository.save(cliente);
                    return newCliente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }

    @GetMapping
    public List<Cliente> find( Cliente filtro ) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING );
        Example example = Example.of(filtro, matcher);

        return clientesRepository.findAll(example);

    }


}
