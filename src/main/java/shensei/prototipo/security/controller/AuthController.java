package shensei.prototipo.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import shensei.prototipo.security.jwt.JwtUtil;
import shensei.prototipo.security.dto.RegisterRequest;
import shensei.prototipo.security.entity.Rol;
import shensei.prototipo.security.entity.Usuario;
import shensei.prototipo.security.entity.UsuarioRol;
import shensei.prototipo.security.repository.RolRepository;
import shensei.prototipo.security.repository.UsuarioRepository;
import shensei.prototipo.security.repository.UsuarioRolRepository;
import shensei.prototipo.cliente.repository.ClienteUsuarioRepository;
import shensei.prototipo.cliente.repository.ClienteRepository;
import shensei.prototipo.cliente.entity.ClienteUsuario;
import shensei.prototipo.cliente.entity.Cliente;

import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final ClienteUsuarioRepository clienteUsuarioRepository;
    private final ClienteRepository clienteRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                         PasswordEncoder passwordEncoder, RolRepository rolRepository, UsuarioRepository usuarioRepository,
                         UsuarioRolRepository usuarioRolRepository,
                         ClienteUsuarioRepository clienteUsuarioRepository,
                         ClienteRepository clienteRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.clienteUsuarioRepository = clienteUsuarioRepository;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(s -> s.replace("ROLE_", ""))
                .collect(Collectors.toList());
        String roleName = roles.isEmpty() ? null : roles.get(0);

        String token = jwtUtil.generateToken(req.getUsername(), roles);

        String nombres = null;
        String apellidos = null;
        Integer idCliente = null;

        Usuario usuario = usuarioRepository.findByUsername(req.getUsername()).orElse(null);
        if (usuario != null) {
            // Preferir nombres/apellidos almacenados en security.usuario si existen
            if (usuario.getNombres() != null && !usuario.getNombres().trim().isEmpty()) {
                nombres = usuario.getNombres();
            }
            if (usuario.getApellidos() != null && !usuario.getApellidos().trim().isEmpty()) {
                apellidos = usuario.getApellidos();
            }

            // Resolver relación cliente-usuario sin depender de nombres/apellidos.
            java.util.Optional<ClienteUsuario> optCu = clienteUsuarioRepository.findById_IdUsuario(usuario.getIdUsuario());
            if (optCu.isPresent()) {
                Integer linkedIdCliente = optCu.get().getId().getIdCliente();

                if (roles.stream().anyMatch(r -> "CLIENTE".equalsIgnoreCase(r))) {
                    idCliente = linkedIdCliente;
                }

                // Si faltan nombres/apellidos, intentar obtenerlos desde cliente.
                if (nombres == null || apellidos == null) {
                    Cliente cliente = clienteRepository.findById(linkedIdCliente).orElse(null);
                    if (cliente != null) {
                        if (nombres == null) nombres = cliente.getNombres();
                        if (apellidos == null) apellidos = cliente.getApellidos();
                    }
                }
            }
        }

        return ResponseEntity.ok(new AuthResponse(token, roleName, nombres, apellidos, idCliente));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        // Validar que el usuario no exista
        if (usuarioRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }

        // Validar que el rol exista
        Rol rol = rolRepository.findByNombre(req.getRoleName()).orElse(null);
        if (rol == null) {
            return ResponseEntity.badRequest().body("Rol no encontrado: " + req.getRoleName());
        }

        // Crear usuario con contraseña encriptada en BCrypt
        Usuario usuario = new Usuario();
        usuario.setUsername(req.getUsername());
        usuario.setPassword(passwordEncoder.encode(req.getPassword())); // BCrypt automático
        usuario.setEstado(true);
        usuario.setFechaCreacion(LocalDateTime.now());

        Usuario saved = usuarioRepository.save(usuario);

        // Asignar rol
        UsuarioRol.UsuarioRolId id = new UsuarioRol.UsuarioRolId(saved.getIdUsuario(), rol.getIdRol());
        UsuarioRol ur = new UsuarioRol();
        ur.setId(id);
        usuarioRolRepository.save(ur);

        return ResponseEntity.ok().body(new RegisterResponse(saved.getIdUsuario(), req.getUsername(), req.getRoleName()));
    }

    public static class AuthRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {
        private String token;
        private String roleName;
        private String nombres;
        private String apellidos;
        private Integer idCliente;

        public AuthResponse(String token, String roleName, String nombres, String apellidos, Integer idCliente) {
            this.token = token;
            this.roleName = roleName;
            this.nombres = nombres;
            this.apellidos = apellidos;
            this.idCliente = idCliente;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getRoleName() { return roleName; }
        public void setRoleName(String roleName) { this.roleName = roleName; }
        public String getNombres() { return nombres; }
        public void setNombres(String nombres) { this.nombres = nombres; }
        public String getApellidos() { return apellidos; }
        public void setApellidos(String apellidos) { this.apellidos = apellidos; }
        public Integer getIdCliente() { return idCliente; }
        public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
    }

    public static class RegisterResponse {
        private Integer idUsuario;
        private String username;
        private String roleName;

        public RegisterResponse(Integer idUsuario, String username, String roleName) {
            this.idUsuario = idUsuario;
            this.username = username;
            this.roleName = roleName;
        }

        public Integer getIdUsuario() { return idUsuario; }
        public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getRoleName() { return roleName; }
        public void setRoleName(String roleName) { this.roleName = roleName; }
    }

}







