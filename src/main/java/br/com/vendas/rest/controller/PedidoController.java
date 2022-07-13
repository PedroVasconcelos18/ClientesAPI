package br.com.vendas.rest.controller;

import br.com.vendas.domain.entity.ItemPedido;
import br.com.vendas.domain.entity.Pedido;
import br.com.vendas.domain.enums.StatusPedido;
import br.com.vendas.domain.repository.PedidosRepository;
import br.com.vendas.rest.dto.AtualizacaoStatusPedidoDTO;
import br.com.vendas.rest.dto.InformacaoItemPedidoDTO;
import br.com.vendas.rest.dto.InformacoesPedidoDTO;
import br.com.vendas.rest.dto.PedidoDTO;
import br.com.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id) {
        return pedidoService.obterPedidoCompleto(id)
                .map(this::parseDTOtoEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    private InformacoesPedidoDTO parseDTOtoEntity(Pedido pedido) {
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().toString())
                .items(converterItemPedidoDTOToInformacaoItemPedidoDTO(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converterItemPedidoDTOToInformacaoItemPedidoDTO(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }
        return itens.stream().map(
                        item -> InformacaoItemPedidoDTO
                                .builder()
                                .descricaoProduto(item.getProduto().getDescricao())
                                .quatidade(item.getQuantidade())
                                .precoUnitario(item.getProduto().getPreco())
                                .build())
                //pega a strem que retornamos e transforma em uma list
                .collect(Collectors.toList());
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(
            @PathVariable Integer id,
            @RequestBody AtualizacaoStatusPedidoDTO dto) {

        pedidoService.atualizaStatus(id, StatusPedido.valueOf(dto.getNovoStatus()));

    }


}
