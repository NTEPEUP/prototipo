package shensei.prototipo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import shensei.prototipo.security.entity.Rol;
import shensei.prototipo.security.entity.Usuario;
import shensei.prototipo.security.entity.UsuarioRol;
import shensei.prototipo.security.repository.RolRepository;
import shensei.prototipo.security.repository.UsuarioRepository;
import shensei.prototipo.security.repository.UsuarioRolRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepository, UsuarioRepository usuarioRepository, UsuarioRolRepository usuarioRolRepository, PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        createRoleIfNotExists("ADMIN", "Administrador del sistema");
        createRoleIfNotExists("CAJERO", "Usuario cajero");
        createRoleIfNotExists("SUPERVISOR", "Supervisor bancario");
        createRoleIfNotExists("CLIENTE", "Cliente de banca virtual");

        // Crear usuario admin si no existe
        Optional<Usuario> opt = usuarioRepository.findByUsername("admin");
        Usuario admin;
        if (opt.isPresent()) {
            admin = opt.get();
        } else {
            admin = new Usuario();
            admin.setUsername("admin");
            // contraseña por defecto admin123 -> encriptada
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEstado(true);
            admin.setFechaCreacion(LocalDateTime.now());
            admin = usuarioRepository.save(admin);
        }

        // Asociar admin con rol ADMIN
        Integer adminId = admin.getIdUsuario();
        rolRepository.findByNombre("ADMIN").ifPresent(rol -> {
            UsuarioRol.UsuarioRolId id = new UsuarioRol.UsuarioRolId(adminId, rol.getIdRol());
            if (!usuarioRolRepository.existsById(id)) {
                UsuarioRol ur = new UsuarioRol();
                ur.setId(id);
                usuarioRolRepository.save(ur);
            }
        });
    }

    private void createRoleIfNotExists(String nombre, String descripcion) {
        if (!rolRepository.findByNombre(nombre).isPresent()) {
            Rol r = new Rol();
            r.setNombre(nombre);
            r.setDescripcion(descripcion);
            rolRepository.save(r);
        }
    }
}

