package br.com.vendas;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.domain.repository.ClientesRepository;
import br.com.vendas.domain.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClientesRepository clientes,
                                  @Autowired PedidosRepository pedidosRepository) {
        return args -> {
//            System.out.println("Salvando Clientes");
            Cliente monica = new Cliente();
            monica.setNome("monica");
            monica.setCpf("52461899999");
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
