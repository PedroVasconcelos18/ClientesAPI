package br.com.vendas.rest.dto;

import java.math.BigDecimal;
import java.util.List;

public class pedidoDTO {

    private Integer cliente;
    private BigDecimal total;
    private List<itemPedidoDTO> items;
}
