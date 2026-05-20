package shensei.prototipo.security.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.security.dto.RolDTO;
import shensei.prototipo.security.entity.Rol;
import shensei.prototipo.security.repository.RolRepository;
import shensei.prototipo.security.service.RolService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    private RolDTO toDto(Rol r) {
        if (r == null) return null;
        RolDTO dto = new RolDTO();
        dto.setIdRol(r.getIdRol());
        dto.setNombre(r.getNombre());
        dto.setDescripcion(r.getDescripcion());
        return dto;
    }

    private Rol toEntity(RolDTO dto) {
        if (dto == null) return null;
        Rol r = new Rol();
        r.setIdRol(dto.getIdRol());
        r.setNombre(dto.getNombre());
        r.setDescripcion(dto.getDescripcion());
        return r;
    }

    @Override
    public List<RolDTO> findAll() {
        return rolRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<RolDTO> findById(Integer id) {
        return rolRepository.findById(id).map(this::toDto);
    }

    @Override
    public RolDTO create(RolDTO dto) {
        Rol r = toEntity(dto);
        Rol saved = rolRepository.save(r);
        return toDto(saved);
    }

    @Override
    public Optional<RolDTO> update(Integer id, RolDTO dto) {
        return rolRepository.findById(id).map(existing -> {
            existing.setNombre(dto.getNombre());
            existing.setDescripcion(dto.getDescripcion());
            Rol saved = rolRepository.save(existing);
            return toDto(saved);
        });
    }

    @Override
    public void deleteById(Integer id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Optional<RolDTO> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre).map(this::toDto);
    }
}

