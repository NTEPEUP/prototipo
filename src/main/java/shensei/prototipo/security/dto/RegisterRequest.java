package shensei.prototipo.security.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private Integer idCliente;
    private String password;
    private String roleName; // ADMIN, CAJERO, SUPERVISOR, CLIENTE
    private String correo;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
}

