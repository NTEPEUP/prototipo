package shensei.prototipo.security.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private Integer idUsuario;
    private String username;
    private String password;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String direccion;
    private String correo;
    private Boolean estado;
    private LocalDateTime fechaCreacion;
}

