package br.com.vendas.domain.repository;

import br.com.vendas.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
