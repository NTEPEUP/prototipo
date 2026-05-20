package shensei.prototipo.productos.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.productos.dto.TarjetaDTO;
import shensei.prototipo.productos.entity.Tarjeta;
import shensei.prototipo.productos.repository.TarjetaRepository;
import shensei.prototipo.productos.service.TarjetaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    private final TarjetaRepository repo;

    public TarjetaServiceImpl(TarjetaRepository repo) {
        this.repo = repo;
    }

    private TarjetaDTO toDto(Tarjeta t) {
        if (t == null) return null;
        TarjetaDTO dto = new TarjetaDTO();
        dto.setIdTarjeta(t.getIdTarjeta());
        dto.setIdCliente(t.getIdCliente());
        dto.setNumeroTarjeta(t.getNumeroTarjeta());
        dto.setTipoTarjeta(t.getTipoTarjeta());
        dto.setLimiteCredito(t.getLimiteCredito());
        dto.setSaldoUtilizado(t.getSaldoUtilizado());
        dto.setEstado(t.getEstado());
        dto.setFechaCreacion(t.getFechaCreacion());
        return dto;
    }

    private Tarjeta toEntity(TarjetaDTO dto) {
        if (dto == null) return null;
        Tarjeta t = new Tarjeta();
        t.setIdTarjeta(dto.getIdTarjeta());
        t.setIdCliente(dto.getIdCliente());
        t.setNumeroTarjeta(dto.getNumeroTarjeta());
        t.setTipoTarjeta(dto.getTipoTarjeta());
        t.setLimiteCredito(dto.getLimiteCredito());
        t.setSaldoUtilizado(dto.getSaldoUtilizado());
        t.setEstado(dto.getEstado());
        t.setFechaCreacion(dto.getFechaCreacion());
        return t;
    }

    @Override
    public List<TarjetaDTO> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<TarjetaDTO> findById(Integer id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public TarjetaDTO create(TarjetaDTO dto) {
        Tarjeta t = toEntity(dto);
        Tarjeta saved = repo.save(t);
        return toDto(saved);
    }

    @Override
    public Optional<TarjetaDTO> update(Integer id, TarjetaDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setIdCliente(dto.getIdCliente());
            existing.setNumeroTarjeta(dto.getNumeroTarjeta());
            existing.setTipoTarjeta(dto.getTipoTarjeta());
            existing.setLimiteCredito(dto.getLimiteCredito());
            existing.setSaldoUtilizado(dto.getSaldoUtilizado());
            existing.setEstado(dto.getEstado());
            existing.setFechaCreacion(dto.getFechaCreacion());
            Tarjeta saved = repo.save(existing);
            return toDto(saved);
        });
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<TarjetaDTO> findByNumeroTarjeta(String numeroTarjeta) {
        return repo.findByNumeroTarjeta(numeroTarjeta).map(this::toDto);
    }
}

