package shensei.prototipo.productos.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuenta", schema = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta")
    private Integer idCuenta;

    @Column(name = "numero_cuenta", length = 30, unique = true, nullable = false, insertable = false,
            updatable = false)
    private String numeroCuenta;

    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @Column(name = "tipo_cuenta", length = 30, nullable = false)
    private String tipoCuenta;

    @Column(name = "saldo", precision = 15, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "estado", length = 20)
    private String estado = "ACTIVA";

    @Column(name = "fecha_apertura", insertable = false,
    updatable = false)
    private LocalDateTime fechaApertura;

}

