package shensei.prototipo.productos.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.productos.dto.TipoProductoDTO;
import shensei.prototipo.productos.entity.TipoProducto;
import shensei.prototipo.productos.repository.TipoProductoRepository;
import shensei.prototipo.productos.service.TipoProductoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoProductoServiceImpl implements TipoProductoService {

    private final TipoProductoRepository repo;

    public TipoProductoServiceImpl(TipoProductoRepository repo) {
        this.repo = repo;
    }

    private TipoProductoDTO toDto(TipoProducto t) {
        if (t == null) return null;
        TipoProductoDTO dto = new TipoProductoDTO();
        dto.setIdTipoProducto(t.getIdTipoProducto());
        dto.setNombre(t.getNombre());
        dto.setDescripcion(t.getDescripcion());
        return dto;
    }

    private TipoProducto toEntity(TipoProductoDTO dto) {
        if (dto == null) return null;
        TipoProducto t = new TipoProducto();
        t.setIdTipoProducto(dto.getIdTipoProducto());
        t.setNombre(dto.getNombre());
        t.setDescripcion(dto.getDescripcion());
        return t;
    }

    @Override
    public List<TipoProductoDTO> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<TipoProductoDTO> findById(Integer id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public TipoProductoDTO create(TipoProductoDTO dto) {
        TipoProducto t = toEntity(dto);
        TipoProducto saved = repo.save(t);
        return toDto(saved);
    }

    @Override
    public Optional<TipoProductoDTO> update(Integer id, TipoProductoDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setNombre(dto.getNombre());
            existing.setDescripcion(dto.getDescripcion());
            TipoProducto saved = repo.save(existing);
            return toDto(saved);
        });
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<TipoProductoDTO> findByNombre(String nombre) {
        return repo.findByNombre(nombre).map(this::toDto);
    }
}

