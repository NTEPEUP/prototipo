package shensei.prototipo.security.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.security.dto.UsuarioDTO;
import shensei.prototipo.security.entity.Usuario;
import shensei.prototipo.security.repository.UsuarioRepository;
import shensei.prototipo.security.service.UsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private UsuarioDTO toDto(Usuario u) {
        if (u == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(u.getIdUsuario());
        dto.setUsername(u.getUsername());
        dto.setPassword(u.getPassword());
        dto.setNombres(u.getNombres());
        dto.setApellidos(u.getApellidos());
        dto.setTelefono(u.getTelefono());
        dto.setDireccion(u.getDireccion());
        dto.setCorreo(u.getCorreo());
        dto.setEstado(u.getEstado());
        dto.setFechaCreacion(u.getFechaCreacion());
        return dto;
    }

    private Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        Usuario u = new Usuario();
        u.setIdUsuario(dto.getIdUsuario());
        u.setUsername(dto.getUsername());
        u.setPassword(dto.getPassword());
        u.setNombres(dto.getNombres());
        u.setApellidos(dto.getApellidos());
        u.setTelefono(dto.getTelefono());
        u.setDireccion(dto.getDireccion());
        u.setCorreo(dto.getCorreo());
        u.setEstado(dto.getEstado());
        u.setFechaCreacion(dto.getFechaCreacion());
        return u;
    }

    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioDTO> findById(Integer id) {
        return usuarioRepository.findById(id).map(this::toDto);
    }

    @Override
    public UsuarioDTO create(UsuarioDTO dto) {
        Usuario u = toEntity(dto);
        Usuario saved = usuarioRepository.save(u);
        return toDto(saved);
    }

    @Override
    public Optional<UsuarioDTO> update(Integer id, UsuarioDTO dto) {
        return usuarioRepository.findById(id).map(existing -> {
            existing.setUsername(dto.getUsername());
            existing.setPassword(dto.getPassword());
            existing.setNombres(dto.getNombres());
            existing.setApellidos(dto.getApellidos());
            existing.setTelefono(dto.getTelefono());
            existing.setDireccion(dto.getDireccion());
            existing.setCorreo(dto.getCorreo());
            existing.setEstado(dto.getEstado());
            existing.setFechaCreacion(dto.getFechaCreacion());
            Usuario saved = usuarioRepository.save(existing);
            return toDto(saved);
        });
    }

    @Override
    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<UsuarioDTO> findByUsername(String username) {
        return usuarioRepository.findByUsername(username).map(this::toDto);
    }
}

