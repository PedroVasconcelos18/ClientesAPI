package br.com.vendas.service;

import br.com.vendas.domain.entity.Pedido;
import br.com.vendas.rest.dto.PedidoDTO;
import br.com.vendas.service.impl.PedidoServiceImpl;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

}