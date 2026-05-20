package shensei.prototipo.transacciones.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shensei.prototipo.transacciones.dto.TransaccionDTO;
import shensei.prototipo.transacciones.service.TransaccionService;
import shensei.prototipo.transacciones.dto.TransferRequest;

import org.springframework.dao.DataAccessException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final TransaccionService service;

    public TransaccionController(TransaccionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TransaccionDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDTO> get(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransaccionDTO> create(@RequestBody TransaccionDTO dto) {
        TransaccionDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/transacciones/" + created.getIdTransaccion())).body(created);
    }

    @PostMapping("/transferencia")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest req) {
        try {
            service.transfer(req);
            return ResponseEntity.ok().build();
        } catch (DataAccessException ex) {
            // devolver mensaje de error con causa
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransaccionDTO> update(@PathVariable Integer id, @RequestBody TransaccionDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}


