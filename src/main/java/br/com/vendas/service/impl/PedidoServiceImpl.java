package br.com.vendas.service.impl;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.domain.entity.ItemPedido;
import br.com.vendas.domain.entity.Pedido;
import br.com.vendas.domain.entity.Produto;
import br.com.vendas.domain.repository.ClientesRepository;
import br.com.vendas.domain.repository.ItensPedidoRepository;
import br.com.vendas.domain.repository.PedidosRepository;
import br.com.vendas.domain.repository.ProdutosRepository;
import br.com.vendas.exception.RegraNegocioException;
import br.com.vendas.rest.dto.ItemPedidoDTO;
import br.com.vendas.rest.dto.PedidoDTO;
import br.com.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Faz gerar um construtor com todos os argumentos obrigatorios ( aqueles que tem o final )
public class PedidoServiceImpl implements PedidoService {


    private final PedidosRepository pedidosRepository;
    private final ClientesRepository clientesRepository;
    private final ProdutosRepository produtosRepository;
    private final ItensPedidoRepository itensPedidoRepository;

    @Autowired


    @Override
    @Transactional // ou ele salva tudo ou se acontecer algum erro ele dá um rollback em tudo oq ocorreu no método ( garante a integridade )
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.
                findById(idCliente)
                .orElseThrow( () -> new RegraNegocioException("Código de cliente inválido"));


        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemPedidos = converterItems(pedido, dto.getItems());

        pedidosRepository.save(pedido);
        itensPedidoRepository.saveAll(itemPedidos);

        pedido.setItens(itemPedidos);

        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if(items.isEmpty()) {
            throw  new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items.stream().map( dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtosRepository
                    .findById(idProduto)
                    .orElseThrow( () -> new RegraNegocioException("Código de produto inválido: " + idProduto));


            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);

            return itemPedido;
        }).collect(Collectors.toList());
    }
}
