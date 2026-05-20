package shensei.prototipo.productos.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarjeta", schema = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Integer idTarjeta;

    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @Column(name = "numero_tarjeta", length = 20, unique = true, nullable = false)
    private String numeroTarjeta;

    @Column(name = "tipo_tarjeta", length = 30)
    private String tipoTarjeta;

    @Column(name = "limite_credito", precision = 15, scale = 2)
    private BigDecimal limiteCredito;

    @Column(name = "saldo_utilizado", precision = 15, scale = 2)
    private BigDecimal saldoUtilizado = BigDecimal.ZERO;

    @Column(name = "estado", length = 20)
    private String estado = "ACTIVA";

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

}

