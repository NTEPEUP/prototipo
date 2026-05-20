package shensei.prototipo.transacciones.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.transacciones.dto.TransaccionDTO;
import shensei.prototipo.transacciones.entity.Transaccion;
import shensei.prototipo.transacciones.repository.TransaccionRepository;
import shensei.prototipo.transacciones.service.TransaccionService;
import shensei.prototipo.transacciones.dto.TransferRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository repo;
    private final JdbcTemplate jdbcTemplate;

    public TransaccionServiceImpl(TransaccionRepository repo, JdbcTemplate jdbcTemplate) {
        this.repo = repo;
        this.jdbcTemplate = jdbcTemplate;
    }

    private TransaccionDTO toDto(Transaccion t) {
        if (t == null) return null;
        TransaccionDTO dto = new TransaccionDTO();
        dto.setIdTransaccion(t.getIdTransaccion());
        dto.setCuentaOrigen(t.getCuentaOrigen());
        dto.setCuentaDestino(t.getCuentaDestino());
        dto.setTipoTransaccion(t.getTipoTransaccion());
        dto.setMonto(t.getMonto());
        dto.setDescripcion(t.getDescripcion());
        dto.setFechaTransaccion(t.getFechaTransaccion());
        return dto;
    }

    private Transaccion toEntity(TransaccionDTO dto) {
        if (dto == null) return null;
        Transaccion t = new Transaccion();
        t.setIdTransaccion(dto.getIdTransaccion());
        t.setCuentaOrigen(dto.getCuentaOrigen());
        t.setCuentaDestino(dto.getCuentaDestino());
        t.setTipoTransaccion(dto.getTipoTransaccion());
        t.setMonto(dto.getMonto());
        t.setDescripcion(dto.getDescripcion());
        t.setFechaTransaccion(dto.getFechaTransaccion());
        return t;
    }

    @Override
    public List<TransaccionDTO> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<TransaccionDTO> findById(Integer id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public TransaccionDTO create(TransaccionDTO dto) {
        Transaccion t = toEntity(dto);
        Transaccion saved = repo.save(t);
        return toDto(saved);
    }

    @Override
    public Optional<TransaccionDTO> update(Integer id, TransaccionDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setCuentaOrigen(dto.getCuentaOrigen());
            existing.setCuentaDestino(dto.getCuentaDestino());
            existing.setTipoTransaccion(dto.getTipoTransaccion());
            existing.setMonto(dto.getMonto());
            existing.setDescripcion(dto.getDescripcion());
            existing.setFechaTransaccion(dto.getFechaTransaccion());
            Transaccion saved = repo.save(existing);
            return toDto(saved);
        });
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public void transfer(TransferRequest req) {
        if (jdbcTemplate == null) {
            throw new IllegalStateException("JdbcTemplate no está disponible");
        }

        try {
            jdbcTemplate.update("CALL transacciones.sp_transferencia(?, ?, ?, ?)",
                    req.getCuentaOrigen(),
                    req.getCuentaDestino(),
                    req.getMonto(),
                    req.getDescripcion());
        } catch (DataAccessException ex) {
            throw ex; // será manejado por controlador
        }
    }
}



