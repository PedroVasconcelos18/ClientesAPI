package br.com.vendas.rest.controller;

        import br.com.vendas.domain.entity.Pedido;
        import br.com.vendas.domain.repository.PedidosRepository;
        import br.com.vendas.rest.dto.PedidoDTO;
        import br.com.vendas.service.PedidoService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }




}
