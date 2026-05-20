package shensei.prototipo.servicio_cliente.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shensei.prototipo.servicio_cliente.dto.ReclamoDTO;
import shensei.prototipo.servicio_cliente.service.ReclamoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reclamos")
public class ReclamoController {

    private final ReclamoService service;

    public ReclamoController(ReclamoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ReclamoDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReclamoDTO> get(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReclamoDTO> create(@RequestBody ReclamoDTO dto) {
        ReclamoDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/reclamos/" + created.getIdReclamo())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReclamoDTO> update(@PathVariable Integer id, @RequestBody ReclamoDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

