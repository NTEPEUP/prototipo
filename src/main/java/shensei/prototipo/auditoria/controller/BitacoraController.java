package shensei.prototipo.auditoria.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shensei.prototipo.auditoria.dto.BitacoraDTO;
import shensei.prototipo.auditoria.service.BitacoraService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
public class BitacoraController {

    private final BitacoraService service;

    public BitacoraController(BitacoraService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BitacoraDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BitacoraDTO> get(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BitacoraDTO> create(@RequestBody BitacoraDTO dto) {
        BitacoraDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/bitacora/" + created.getIdBitacora())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BitacoraDTO> update(@PathVariable Integer id, @RequestBody BitacoraDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

