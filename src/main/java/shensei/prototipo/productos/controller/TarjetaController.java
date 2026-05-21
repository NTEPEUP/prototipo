package shensei.prototipo.productos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shensei.prototipo.productos.dto.TarjetaDTO;
import shensei.prototipo.productos.service.TarjetaService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tarjetas")
public class TarjetaController {

    private final TarjetaService service;

    public TarjetaController(TarjetaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TarjetaDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarjetaDTO> get(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<TarjetaDTO>> findByCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(service.findByIdCliente(idCliente));
    }

    @PostMapping
    public ResponseEntity<TarjetaDTO> create(@RequestBody TarjetaDTO dto) {
        TarjetaDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/tarjetas/" + created.getIdTarjeta())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarjetaDTO> update(@PathVariable Integer id, @RequestBody TarjetaDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

