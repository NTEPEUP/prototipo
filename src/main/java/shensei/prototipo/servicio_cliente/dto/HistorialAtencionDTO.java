package shensei.prototipo.servicio_cliente.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistorialAtencionDTO {
    private Integer idHistorial;
    private Integer idCliente;
    private Integer idUsuario;
    private String comentario;
    private LocalDateTime fechaRegistro;
}

