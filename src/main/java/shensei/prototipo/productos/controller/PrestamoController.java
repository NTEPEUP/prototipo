package shensei.prototipo.productos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shensei.prototipo.productos.dto.PrestamoDTO;
import shensei.prototipo.productos.service.PrestamoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService service;

    public PrestamoController(PrestamoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PrestamoDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoDTO> get(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<PrestamoDTO>> findByCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(service.findByIdCliente(idCliente));
    }

    @PostMapping
    public ResponseEntity<PrestamoDTO> create(@RequestBody PrestamoDTO dto) {
        PrestamoDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/prestamos/" + created.getIdPrestamo())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestamoDTO> update(@PathVariable Integer id, @RequestBody PrestamoDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

