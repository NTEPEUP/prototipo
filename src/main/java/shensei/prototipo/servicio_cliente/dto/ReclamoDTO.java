package shensei.prototipo.servicio_cliente.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReclamoDTO {
    private Integer idReclamo;
    private String codigoReclamo;
    private Integer idCliente;
    private String tipoCaso;
    private String categoria;
    private String descripcion;
    private String estado;
    private LocalDateTime fechaCreacion;
}

