package shensei.prototipo.productos.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CuentaDTO {
    private Integer idCuenta;
    private String numeroCuenta;
    private Integer idCliente;
    private String tipoCuenta;
    private BigDecimal saldo;
    private String estado;
    private LocalDateTime fechaApertura;
}

