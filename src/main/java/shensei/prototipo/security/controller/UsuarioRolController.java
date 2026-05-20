package shensei.prototipo.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shensei.prototipo.security.dto.UsuarioRolDTO;
import shensei.prototipo.security.service.UsuarioRolService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuario-roles")
public class UsuarioRolController {

    private final UsuarioRolService service;

    public UsuarioRolController(UsuarioRolService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRolDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<UsuarioRolDTO> create(@RequestBody UsuarioRolDTO dto) {
        UsuarioRolDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/usuario-roles/" )).body(created);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Integer idUsuario, @RequestParam Integer idRol) {
        service.delete(idUsuario, idRol);
        return ResponseEntity.noContent().build();
    }
}

