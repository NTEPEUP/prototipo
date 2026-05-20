package shensei.prototipo.security.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shensei.prototipo.security.entity.Rol;
import shensei.prototipo.security.entity.Usuario;
import shensei.prototipo.security.entity.UsuarioRol;
import shensei.prototipo.security.repository.RolRepository;
import shensei.prototipo.security.repository.UsuarioRepository;
import shensei.prototipo.security.repository.UsuarioRolRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final RolRepository rolRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository, UsuarioRolRepository usuarioRolRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<UsuarioRol> urs = usuarioRolRepository.findById_IdUsuario(u.getIdUsuario());

        List<SimpleGrantedAuthority> authorities = urs.stream()
                .map(ur -> rolRepository.findById(ur.getId().getIdRol()))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .map(Rol::getNombre)
                .map(rn -> new SimpleGrantedAuthority("ROLE_" + rn))
                .collect(Collectors.toList());

        return User.builder()
                .username(u.getUsername())
                .password(u.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(u.getEstado() != null && !u.getEstado())
                .build();
    }
}

