package shensei.prototipo.productos.dto;

import lombok.Data;

@Data
public class ValidacionCuentaDTO {
    private String numeroCuenta;
    private String tipoCuenta;
    private Integer idCliente;
    private String nombreCliente;
}

