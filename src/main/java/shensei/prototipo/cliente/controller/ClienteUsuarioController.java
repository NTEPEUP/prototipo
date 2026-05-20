package shensei.prototipo.cliente.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shensei.prototipo.cliente.dto.ClienteUsuarioDTO;
import shensei.prototipo.cliente.service.ClienteUsuarioService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cliente-usuarios")
public class ClienteUsuarioController {

    private final ClienteUsuarioService service;

    public ClienteUsuarioController(ClienteUsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClienteUsuarioDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<ClienteUsuarioDTO> create(@RequestBody ClienteUsuarioDTO dto) {
        ClienteUsuarioDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/cliente-usuarios/" )).body(created);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Integer idCliente, @RequestParam Integer idUsuario) {
        service.delete(idCliente, idUsuario);
        return ResponseEntity.noContent().build();
    }
}

