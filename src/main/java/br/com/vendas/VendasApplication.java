package br.com.vendas;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.domain.entity.Pedido;
import br.com.vendas.domain.repository.ClientesRepository;
import br.com.vendas.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClientesRepository clientes,
                                  @Autowired Pedidos pedidosRepository) {
        return args -> {
//            System.out.println("Salvando Clientes");
            Cliente monica = new Cliente("Monica");
            clientes.save(monica);
//            clientes.save(new Cliente("Serena"));
//            clientes.save(new Cliente("Pedro"));

//            Pedido p = new Pedido();
//            p.setCliente(monica);
//            p.setDataPedido(LocalDate.now());
//            p.setTotal(BigDecimal.valueOf(100));

//            pedidosRepository.save(p);

//            List<Cliente> result = clientes.encontrarPorId(1);
//            for (Cliente cliente : result) {
//                System.out.print("O nome do cliente encontrado é : " + cliente.getNome());
//                System.out.print("\nE seu ID é : " + cliente.getId());
//            }

//            Cliente cliente = clientes.findClienteFetchPedidos(monica.getId());
//            System.out.println(cliente);
//            System.out.println(cliente.getPedidos());
//
//            pedidosRepository.findByCliente(monica).forEach(System.out::println);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }

}
