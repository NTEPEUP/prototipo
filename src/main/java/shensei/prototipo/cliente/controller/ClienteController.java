package shensei.prototipo.cliente.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shensei.prototipo.cliente.entity.Cliente;
import shensei.prototipo.cliente.service.ClienteService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> list() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> get(@PathVariable Integer id) {
        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigoCliente}")
    public ResponseEntity<Cliente> getByCodigoCliente(@PathVariable String codigoCliente) {
        if (codigoCliente == null || codigoCliente.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return clienteService.findByCodigoCliente(codigoCliente.trim())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/dpi/{dpi}")
    public ResponseEntity<Cliente> getByDpi(@PathVariable String dpi) {
        if (dpi == null || dpi.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return clienteService.findByDpi(dpi.trim())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        Cliente saved = clienteService.save(cliente);
        return ResponseEntity.created(URI.create("/api/clientes/" + saved.getIdCliente())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        return clienteService.findById(id).map(existing -> {
            cliente.setIdCliente(id);
            Cliente updated = clienteService.save(cliente);
            return ResponseEntity.ok(updated);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return clienteService.findById(id).map(existing -> {
            clienteService.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}

