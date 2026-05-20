package shensei.prototipo.security.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String roleName; // ADMIN, CAJERO, SUPERVISOR, CLIENTE
}

