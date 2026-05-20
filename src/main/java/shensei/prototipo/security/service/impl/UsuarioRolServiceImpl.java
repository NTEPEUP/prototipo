package shensei.prototipo.security.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.security.dto.UsuarioRolDTO;
import shensei.prototipo.security.entity.UsuarioRol;
import shensei.prototipo.security.repository.UsuarioRolRepository;
import shensei.prototipo.security.service.UsuarioRolService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioRolServiceImpl implements UsuarioRolService {

    private final UsuarioRolRepository repo;

    public UsuarioRolServiceImpl(UsuarioRolRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<UsuarioRolDTO> findAll() {
        return repo.findAll().stream().map(ur -> {
            UsuarioRolDTO dto = new UsuarioRolDTO();
            dto.setIdUsuario(ur.getId().getIdUsuario());
            dto.setIdRol(ur.getId().getIdRol());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public UsuarioRolDTO create(UsuarioRolDTO dto) {
        UsuarioRol ur = new UsuarioRol();
        UsuarioRol.UsuarioRolId id = new UsuarioRol.UsuarioRolId(dto.getIdUsuario(), dto.getIdRol());
        ur.setId(id);
        UsuarioRol saved = repo.save(ur);
        UsuarioRolDTO out = new UsuarioRolDTO();
        out.setIdUsuario(saved.getId().getIdUsuario());
        out.setIdRol(saved.getId().getIdRol());
        return out;
    }

    @Override
    public void delete(Integer idUsuario, Integer idRol) {
        UsuarioRol.UsuarioRolId id = new UsuarioRol.UsuarioRolId(idUsuario, idRol);
        repo.deleteById(id);
    }
}

