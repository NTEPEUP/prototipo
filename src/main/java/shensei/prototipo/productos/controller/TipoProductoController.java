package shensei.prototipo.productos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shensei.prototipo.productos.dto.TipoProductoDTO;
import shensei.prototipo.productos.service.TipoProductoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-producto")
public class TipoProductoController {

    private final TipoProductoService service;

    public TipoProductoController(TipoProductoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoProductoDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProductoDTO> get(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoProductoDTO> create(@RequestBody TipoProductoDTO dto) {
        TipoProductoDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/tipos-producto/" + created.getIdTipoProducto())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProductoDTO> update(@PathVariable Integer id, @RequestBody TipoProductoDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

