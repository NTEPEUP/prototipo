package shensei.prototipo.transacciones.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transfer_token", schema = "transacciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferToken {

    @Id
    @Column(name = "id_token", length = 36)
    private String idToken = UUID.randomUUID().toString();

    @Column(name = "token_hash", length = 64, nullable = false)
    private String tokenHash;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "cuenta_origen", length = 30)
    private String cuentaOrigen;

    @Column(name = "cuenta_destino", length = 30)
    private String cuentaDestino;

    @Column(name = "monto", precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "used")
    private Boolean used = false;
}


