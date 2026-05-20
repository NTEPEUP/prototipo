package shensei.prototipo.transacciones.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String cuentaOrigen;
    private String cuentaDestino;
    private BigDecimal monto;
    private String descripcion;
}

