package shensei.prototipo.transacciones.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransaccionDTO {
    private Integer idTransaccion;
    private Integer cuentaOrigen;
    private Integer cuentaDestino;
    private String tipoTransaccion;
    private BigDecimal monto;
    private String descripcion;
    private LocalDateTime fechaTransaccion;
}

