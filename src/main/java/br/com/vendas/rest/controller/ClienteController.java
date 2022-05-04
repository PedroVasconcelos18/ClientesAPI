package br.com.vendas.rest.controller;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.domain.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Example.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    ClientesRepository clientesRepository;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getClienteById( @PathVariable Integer id ) {
        Optional<Cliente> cliente = clientesRepository.findById(id);

        if(cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity save( @RequestBody Cliente cliente) {
        Cliente clienteSalvo = clientesRepository.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity delete( @PathVariable Integer id ) {
        Optional<Cliente> cliente = clientesRepository.findById(id);

        if(cliente.isPresent()) {
            clientesRepository.delete((cliente.get()));
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update( @PathVariable Integer id,
                                  @RequestBody Cliente cliente ) {

        return clientesRepository
                .findById(id)
                .map( newCliente -> {
                    cliente.setId(newCliente.getId());
                    clientesRepository.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () ->  ResponseEntity.notFound().build() );

    }

    @GetMapping
    public ResponseEntity find( Cliente filtro ) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING );
        Example example = Example.of(filtro, matcher);

        List<Cliente> clientes = clientesRepository.findAll(example);
        return ResponseEntity.ok(clientes);

    }


}
