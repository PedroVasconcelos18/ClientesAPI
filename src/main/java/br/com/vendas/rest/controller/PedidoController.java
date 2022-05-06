package br.com.vendas.rest.controller;

        import br.com.vendas.domain.repository.PedidosRepository;
        import br.com.vendas.service.PedidoService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private PedidoService pedidoService;




}
