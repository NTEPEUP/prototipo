package shensei.prototipo.auditoria.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BitacoraDTO {
    private Integer idBitacora;
    private Integer idUsuario;
    private String accion;
    private String tablaAfectada;
    private String descripcion;
    private LocalDateTime fechaRegistro;
}

