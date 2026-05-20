package shensei.prototipo.productos.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TarjetaDTO {
    private Integer idTarjeta;
    private Integer idCliente;
    private String numeroTarjeta;
    private String tipoTarjeta;
    private BigDecimal limiteCredito;
    private BigDecimal saldoUtilizado;
    private String estado;
    private LocalDateTime fechaCreacion;
}

