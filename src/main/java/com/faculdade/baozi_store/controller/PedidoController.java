package com.faculdade.baozi_store.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.faculdade.baozi_store.model.Pedido;
import com.faculdade.baozi_store.repository.ClienteRepository;
import com.faculdade.baozi_store.repository.PedidoRepository;
import com.faculdade.baozi_store.repository.ProdutoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .<ResponseEntity<Pedido>>map(
                        pedido -> ResponseEntity.ok(pedido))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pedido> cadastrar(@RequestBody Pedido pedido) {

        if (pedido.getClienteId() == null
                || pedido.getProdutoId() == null
                || pedido.getQuantidade() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe clienteId, produtoId e quantidade.");
        }

        if (pedido.getQuantidade() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A quantidade deve ser maior que zero.");
        }

        if (!clienteRepository.existsById(pedido.getClienteId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O cliente informado não existe.");
        }

        if (!produtoRepository.existsById(pedido.getProdutoId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O produto informado não existe.");
        }

        pedido.setId(null);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return ResponseEntity.status(201).body(pedidoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        pedidoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
