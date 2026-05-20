package shensei.prototipo.productos.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PrestamoDTO {
    private Integer idPrestamo;
    private Integer idCliente;
    private BigDecimal monto;
    private BigDecimal saldoPendiente;
    private BigDecimal tasaInteres;
    private Integer plazoMeses;
    private String estado;
    private LocalDateTime fechaCreacion;
}

