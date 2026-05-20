package shensei.prototipo.servicio_cliente.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.servicio_cliente.dto.ReclamoDTO;
import shensei.prototipo.servicio_cliente.entity.Reclamo;
import shensei.prototipo.servicio_cliente.repository.ReclamoRepository;
import shensei.prototipo.servicio_cliente.service.ReclamoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReclamoServiceImpl implements ReclamoService {

    private final ReclamoRepository repo;

    public ReclamoServiceImpl(ReclamoRepository repo) {
        this.repo = repo;
    }

    private ReclamoDTO toDto(Reclamo r) {
        if (r == null) return null;
        ReclamoDTO dto = new ReclamoDTO();
        dto.setIdReclamo(r.getIdReclamo());
        dto.setIdCliente(r.getIdCliente());
        dto.setTipoReclamo(r.getTipoReclamo());
        dto.setDescripcion(r.getDescripcion());
        dto.setEstado(r.getEstado());
        dto.setFechaCreacion(r.getFechaCreacion());
        return dto;
    }

    private Reclamo toEntity(ReclamoDTO dto) {
        if (dto == null) return null;
        Reclamo r = new Reclamo();
        r.setIdReclamo(dto.getIdReclamo());
        r.setIdCliente(dto.getIdCliente());
        r.setTipoReclamo(dto.getTipoReclamo());
        r.setDescripcion(dto.getDescripcion());
        r.setEstado(dto.getEstado());
        r.setFechaCreacion(dto.getFechaCreacion());
        return r;
    }

    @Override
    public List<ReclamoDTO> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ReclamoDTO> findById(Integer id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public ReclamoDTO create(ReclamoDTO dto) {
        Reclamo r = toEntity(dto);
        Reclamo saved = repo.save(r);
        return toDto(saved);
    }

    @Override
    public Optional<ReclamoDTO> update(Integer id, ReclamoDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setIdCliente(dto.getIdCliente());
            existing.setTipoReclamo(dto.getTipoReclamo());
            existing.setDescripcion(dto.getDescripcion());
            existing.setEstado(dto.getEstado());
            existing.setFechaCreacion(dto.getFechaCreacion());
            Reclamo saved = repo.save(existing);
            return toDto(saved);
        });
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<ReclamoDTO> findByIdCliente(Integer idCliente) {
        return repo.findByIdCliente(idCliente).stream().map(this::toDto).collect(Collectors.toList());
    }
}

