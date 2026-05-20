package shensei.prototipo.servicio_cliente.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shensei.prototipo.servicio_cliente.dto.HistorialAtencionDTO;
import shensei.prototipo.servicio_cliente.service.HistorialAtencionService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/historial-atencion")
public class HistorialAtencionController {

    private final HistorialAtencionService service;

    public HistorialAtencionController(HistorialAtencionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<HistorialAtencionDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialAtencionDTO> get(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<HistorialAtencionDTO>> getByCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(service.findByIdCliente(idCliente));
    }

    @PostMapping
    public ResponseEntity<HistorialAtencionDTO> create(@RequestBody HistorialAtencionDTO dto) {
        HistorialAtencionDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/historial-atencion/" + created.getIdHistorial())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialAtencionDTO> update(@PathVariable Integer id, @RequestBody HistorialAtencionDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

