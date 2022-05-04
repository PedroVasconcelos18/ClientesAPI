package br.com.vendas.domain.repository;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PedidosRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente );
}
