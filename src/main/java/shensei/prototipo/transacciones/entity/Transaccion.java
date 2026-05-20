package shensei.prototipo.transacciones.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion", schema = "transacciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @Column(name = "cuenta_origen")
    private Integer cuentaOrigen;

    @Column(name = "cuenta_destino")
    private Integer cuentaDestino;

    @Column(name = "tipo_transaccion", length = 30, nullable = false)
    private String tipoTransaccion;

    @Column(name = "monto", precision = 15, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_transaccion")
    private LocalDateTime fechaTransaccion;

}

