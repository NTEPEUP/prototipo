package shensei.prototipo.transacciones.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shensei.prototipo.transacciones.dto.TransaccionDTO;
import shensei.prototipo.transacciones.service.TransaccionService;
import shensei.prototipo.transacciones.dto.ConfirmTransferRequest;
import shensei.prototipo.transacciones.dto.TransferRequest;
import shensei.prototipo.transacciones.dto.TransferTokenIssueDTO;
import shensei.prototipo.productos.dto.ValidacionCuentaDTO;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import shensei.prototipo.security.repository.UsuarioRepository;
import shensei.prototipo.productos.service.CuentaService;
import shensei.prototipo.transacciones.service.TransferTokenService;
import shensei.prototipo.mail.EmailService;
import shensei.prototipo.security.entity.Usuario;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final TransaccionService service;
    private final CuentaService cuentaService;
    private final UsuarioRepository usuarioRepository;
    private final TransferTokenService tokenService;
    private final EmailService emailService;

    public TransaccionController(TransaccionService service,
                                 CuentaService cuentaService,
                                 UsuarioRepository usuarioRepository,
                                 TransferTokenService tokenService,
                                 EmailService emailService) {
        this.service = service;
        this.cuentaService = cuentaService;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    private Usuario getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication == null ? null : authentication.getName();
        if (username == null || username.isBlank()) {
            return null;
        }
        return usuarioRepository.findByUsername(username).orElse(null);
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
            Usuario usuario = getAuthenticatedUser();
            if (usuario == null) {
                return ResponseEntity.status(401).body("Usuario no autenticado");
            }

            // Buscar cuentas
            var optOrigen = cuentaService.findByNumeroCuenta(req.getCuentaOrigen());
            var optDestino = cuentaService.findByNumeroCuenta(req.getCuentaDestino());

            if (optOrigen.isEmpty()) return ResponseEntity.badRequest().body("Cuenta origen no encontrada");
            if (optDestino.isEmpty()) return ResponseEntity.badRequest().body("Cuenta destino no encontrada");

            var cuentaOrigen = optOrigen.get();
            var cuentaDestino = optDestino.get();

            // Determinar si es transferencia ajena (destino diferente al propietario de origen)
            boolean esAjena = !cuentaOrigen.getIdCliente().equals(cuentaDestino.getIdCliente());

            if (esAjena) {
                if (req.getToken() != null && !req.getToken().isBlank()) {
                    return ResponseEntity.badRequest().body("Para confirmar transferencias ajenas usa POST /api/transacciones/confirmar");
                }

                if (usuario.getCorreo() == null || usuario.getCorreo().isBlank()) {
                    return ResponseEntity.badRequest().body("No se puede enviar token: email no disponible para el usuario autenticado");
                }

                TransferTokenIssueDTO issued = tokenService.createToken(req, usuario.getIdUsuario());
                ValidacionCuentaDTO validacionDestino = cuentaService.validarPorNumeroCuenta(req.getCuentaDestino()).orElse(null);
                String nombreBeneficiario = validacionDestino == null ? "No disponible" : validacionDestino.getNombreCliente();
                String tipoCuenta = validacionDestino == null ? "No disponible" : validacionDestino.getTipoCuenta();

                String subject = "Confirmación de transferencia bancaria";
                String html = "<html><body style='font-family:Arial,sans-serif;'>"
                        + "<h3>Confirmación de transferencia a cuenta ajena</h3>"
                        + "<p>Se detectó una transferencia a terceros y requiere validación.</p>"
                        + "<p><b>Cuenta destino:</b> " + req.getCuentaDestino() + "</p>"
                        + "<p><b>Beneficiario:</b> " + nombreBeneficiario + "</p>"
                        + "<p><b>Tipo de cuenta:</b> " + tipoCuenta + "</p>"
                        + "<p><b>Monto:</b> " + req.getMonto() + "</p>"
                        + "<p><b>Código:</b> <span style='font-size:22px;letter-spacing:2px;'>" + issued.getRawToken() + "</span></p>"
                        + "<p>Válido hasta: " + issued.getExpiresAt() + "</p>"
                        + "</body></html>";
                emailService.sendHtmlMessage(usuario.getCorreo(), subject, html);

                return ResponseEntity.accepted().body(new TransferInitResponse(
                        "Se envio un token al correo registrado. Confirma en /api/transacciones/confirmar",
                        issued.getTokenId(),
                        issued.getExpiresAt()
                ));
            }

            // Si no es ajena, ejecutar inmediatamente
            service.transfer(req);
            return ResponseEntity.ok().build();
        } catch (DataAccessException ex) {
            // devolver mensaje de error con causa
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarTransferencia(@RequestBody ConfirmTransferRequest req) {
        try {
            Usuario usuario = getAuthenticatedUser();
            if (usuario == null) {
                return ResponseEntity.status(401).body("Usuario no autenticado");
            }
            if (req.getTokenId() == null || req.getTokenId().isBlank() || req.getToken() == null || req.getToken().isBlank()) {
                return ResponseEntity.badRequest().body("tokenId y token son obligatorios");
            }

            var validated = tokenService.validateAndConsumeToken(req.getTokenId(), req.getToken(), usuario.getIdUsuario());
            if (validated.isEmpty()) {
                return ResponseEntity.status(403).body("Token invalido o expirado");
            }

            var t = validated.get();
            TransferRequest transferRequest = new TransferRequest();
            transferRequest.setCuentaOrigen(t.getCuentaOrigen());
            transferRequest.setCuentaDestino(t.getCuentaDestino());
            transferRequest.setMonto(t.getMonto());
            transferRequest.setDescripcion("Transferencia confirmada con token");
            service.transfer(transferRequest);

            return ResponseEntity.ok("Transferencia ejecutada correctamente");
        } catch (DataAccessException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    public static class TransferInitResponse {
        private String message;
        private String tokenId;
        private java.time.LocalDateTime expiresAt;

        public TransferInitResponse(String message, String tokenId, java.time.LocalDateTime expiresAt) {
            this.message = message;
            this.tokenId = tokenId;
            this.expiresAt = expiresAt;
        }

        public String getMessage() {
            return message;
        }

        public String getTokenId() {
            return tokenId;
        }

        public java.time.LocalDateTime getExpiresAt() {
            return expiresAt;
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


