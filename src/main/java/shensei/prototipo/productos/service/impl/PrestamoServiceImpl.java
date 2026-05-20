package shensei.prototipo.productos.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.productos.dto.PrestamoDTO;
import shensei.prototipo.productos.entity.Prestamo;
import shensei.prototipo.productos.repository.PrestamoRepository;
import shensei.prototipo.productos.service.PrestamoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository repo;

    public PrestamoServiceImpl(PrestamoRepository repo) {
        this.repo = repo;
    }

    private PrestamoDTO toDto(Prestamo p) {
        if (p == null) return null;
        PrestamoDTO dto = new PrestamoDTO();
        dto.setIdPrestamo(p.getIdPrestamo());
        dto.setIdCliente(p.getIdCliente());
        dto.setMonto(p.getMonto());
        dto.setSaldoPendiente(p.getSaldoPendiente());
        dto.setTasaInteres(p.getTasaInteres());
        dto.setPlazoMeses(p.getPlazoMeses());
        dto.setEstado(p.getEstado());
        dto.setFechaCreacion(p.getFechaCreacion());
        return dto;
    }

    private Prestamo toEntity(PrestamoDTO dto) {
        if (dto == null) return null;
        Prestamo p = new Prestamo();
        p.setIdPrestamo(dto.getIdPrestamo());
        p.setIdCliente(dto.getIdCliente());
        p.setMonto(dto.getMonto());
        p.setSaldoPendiente(dto.getSaldoPendiente());
        p.setTasaInteres(dto.getTasaInteres());
        p.setPlazoMeses(dto.getPlazoMeses());
        p.setEstado(dto.getEstado());
        p.setFechaCreacion(dto.getFechaCreacion());
        return p;
    }

    @Override
    public List<PrestamoDTO> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<PrestamoDTO> findById(Integer id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public PrestamoDTO create(PrestamoDTO dto) {
        Prestamo p = toEntity(dto);
        Prestamo saved = repo.save(p);
        return toDto(saved);
    }

    @Override
    public Optional<PrestamoDTO> update(Integer id, PrestamoDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setIdCliente(dto.getIdCliente());
            existing.setMonto(dto.getMonto());
            existing.setSaldoPendiente(dto.getSaldoPendiente());
            existing.setTasaInteres(dto.getTasaInteres());
            existing.setPlazoMeses(dto.getPlazoMeses());
            existing.setEstado(dto.getEstado());
            existing.setFechaCreacion(dto.getFechaCreacion());
            Prestamo saved = repo.save(existing);
            return toDto(saved);
        });
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}

