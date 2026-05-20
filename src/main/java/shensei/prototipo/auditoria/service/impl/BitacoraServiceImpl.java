package shensei.prototipo.auditoria.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.auditoria.dto.BitacoraDTO;
import shensei.prototipo.auditoria.entity.Bitacora;
import shensei.prototipo.auditoria.repository.BitacoraRepository;
import shensei.prototipo.auditoria.service.BitacoraService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BitacoraServiceImpl implements BitacoraService {

    private final BitacoraRepository repo;

    public BitacoraServiceImpl(BitacoraRepository repo) {
        this.repo = repo;
    }

    private BitacoraDTO toDto(Bitacora b) {
        if (b == null) return null;
        BitacoraDTO dto = new BitacoraDTO();
        dto.setIdBitacora(b.getIdBitacora());
        dto.setIdUsuario(b.getIdUsuario());
        dto.setAccion(b.getAccion());
        dto.setTablaAfectada(b.getTablaAfectada());
        dto.setDescripcion(b.getDescripcion());
        dto.setFechaRegistro(b.getFechaRegistro());
        return dto;
    }

    private Bitacora toEntity(BitacoraDTO dto) {
        if (dto == null) return null;
        Bitacora b = new Bitacora();
        b.setIdBitacora(dto.getIdBitacora());
        b.setIdUsuario(dto.getIdUsuario());
        b.setAccion(dto.getAccion());
        b.setTablaAfectada(dto.getTablaAfectada());
        b.setDescripcion(dto.getDescripcion());
        b.setFechaRegistro(dto.getFechaRegistro());
        return b;
    }

    @Override
    public List<BitacoraDTO> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<BitacoraDTO> findById(Integer id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public BitacoraDTO create(BitacoraDTO dto) {
        Bitacora b = toEntity(dto);
        Bitacora saved = repo.save(b);
        return toDto(saved);
    }

    @Override
    public Optional<BitacoraDTO> update(Integer id, BitacoraDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setIdUsuario(dto.getIdUsuario());
            existing.setAccion(dto.getAccion());
            existing.setTablaAfectada(dto.getTablaAfectada());
            existing.setDescripcion(dto.getDescripcion());
            existing.setFechaRegistro(dto.getFechaRegistro());
            Bitacora saved = repo.save(existing);
            return toDto(saved);
        });
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}

