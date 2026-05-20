package shensei.prototipo.servicio_cliente.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.servicio_cliente.dto.HistorialAtencionDTO;
import shensei.prototipo.servicio_cliente.entity.HistorialAtencion;
import shensei.prototipo.servicio_cliente.repository.HistorialAtencionRepository;
import shensei.prototipo.servicio_cliente.service.HistorialAtencionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistorialAtencionServiceImpl implements HistorialAtencionService {

    private final HistorialAtencionRepository repo;

    public HistorialAtencionServiceImpl(HistorialAtencionRepository repo) {
        this.repo = repo;
    }

    private HistorialAtencionDTO toDto(HistorialAtencion h) {
        if (h == null) return null;
        HistorialAtencionDTO dto = new HistorialAtencionDTO();
        dto.setIdHistorial(h.getIdHistorial());
        dto.setIdCliente(h.getIdCliente());
        dto.setIdUsuario(h.getIdUsuario());
        dto.setComentario(h.getComentario());
        dto.setFechaRegistro(h.getFechaRegistro());
        return dto;
    }

    private HistorialAtencion toEntity(HistorialAtencionDTO dto) {
        if (dto == null) return null;
        HistorialAtencion h = new HistorialAtencion();
        h.setIdHistorial(dto.getIdHistorial());
        h.setIdCliente(dto.getIdCliente());
        h.setIdUsuario(dto.getIdUsuario());
        h.setComentario(dto.getComentario());
        h.setFechaRegistro(dto.getFechaRegistro());
        return h;
    }

    @Override
    public List<HistorialAtencionDTO> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<HistorialAtencionDTO> findById(Integer id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public HistorialAtencionDTO create(HistorialAtencionDTO dto) {
        HistorialAtencion h = toEntity(dto);
        HistorialAtencion saved = repo.save(h);
        return toDto(saved);
    }

    @Override
    public Optional<HistorialAtencionDTO> update(Integer id, HistorialAtencionDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setIdCliente(dto.getIdCliente());
            existing.setIdUsuario(dto.getIdUsuario());
            existing.setComentario(dto.getComentario());
            existing.setFechaRegistro(dto.getFechaRegistro());
            HistorialAtencion saved = repo.save(existing);
            return toDto(saved);
        });
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}

