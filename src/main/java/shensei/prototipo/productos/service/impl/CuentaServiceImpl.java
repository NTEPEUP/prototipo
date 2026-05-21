package shensei.prototipo.productos.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.productos.dto.CuentaDTO;
import shensei.prototipo.productos.dto.ValidacionCuentaDTO;
import shensei.prototipo.productos.entity.Cuenta;
import shensei.prototipo.productos.repository.CuentaRepository;
import shensei.prototipo.productos.service.CuentaService;
import shensei.prototipo.cliente.entity.Cliente;
import shensei.prototipo.cliente.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository repo;
    private final ClienteRepository clienteRepository;

    public CuentaServiceImpl(CuentaRepository repo, ClienteRepository clienteRepository) {
        this.repo = repo;
        this.clienteRepository = clienteRepository;
    }

    private CuentaDTO toDto(Cuenta c) {
        if (c == null) return null;
        CuentaDTO dto = new CuentaDTO();
        dto.setIdCuenta(c.getIdCuenta());
        dto.setNumeroCuenta(c.getNumeroCuenta());
        dto.setIdCliente(c.getIdCliente());
        dto.setTipoCuenta(c.getTipoCuenta());
        dto.setSaldo(c.getSaldo());
        dto.setEstado(c.getEstado());
        dto.setFechaApertura(c.getFechaApertura());
        return dto;
    }

    private Cuenta toEntity(CuentaDTO dto) {
        if (dto == null) return null;
        Cuenta c = new Cuenta();
        c.setIdCuenta(dto.getIdCuenta());
        c.setNumeroCuenta(dto.getNumeroCuenta());
        c.setIdCliente(dto.getIdCliente());
        c.setTipoCuenta(dto.getTipoCuenta());
        c.setSaldo(dto.getSaldo());
        c.setEstado(dto.getEstado());
        c.setFechaApertura(dto.getFechaApertura());
        return c;
    }

    @Override
    public List<CuentaDTO> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<CuentaDTO> findById(Integer id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public CuentaDTO create(CuentaDTO dto) {
        Cuenta c = toEntity(dto);
        Cuenta saved = repo.save(c);
        return toDto(saved);
    }

    @Override
    public Optional<CuentaDTO> update(Integer id, CuentaDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setNumeroCuenta(dto.getNumeroCuenta());
            existing.setIdCliente(dto.getIdCliente());
            existing.setTipoCuenta(dto.getTipoCuenta());
            existing.setSaldo(dto.getSaldo());
            existing.setEstado(dto.getEstado());
            existing.setFechaApertura(dto.getFechaApertura());
            Cuenta saved = repo.save(existing);
            return toDto(saved);
        });
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<CuentaDTO> findByIdCliente(Integer idCliente) {
        return repo.findByIdCliente(idCliente).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<CuentaDTO> findByNumeroCuenta(String numeroCuenta) {
        return repo.findByNumeroCuenta(numeroCuenta).map(this::toDto);
    }

    @Override
    public Optional<ValidacionCuentaDTO> validarPorNumeroCuenta(String numeroCuenta) {
        return repo.findByNumeroCuenta(numeroCuenta).flatMap(cuenta ->
                clienteRepository.findById(cuenta.getIdCliente()).map(cliente -> {
                    ValidacionCuentaDTO dto = new ValidacionCuentaDTO();
                    dto.setNumeroCuenta(cuenta.getNumeroCuenta());
                    dto.setTipoCuenta(cuenta.getTipoCuenta());
                    dto.setIdCliente(cliente.getIdCliente());
                    dto.setNombreCliente((cliente.getNombres() != null ? cliente.getNombres() : "")
                            + ((cliente.getApellidos() != null && !cliente.getApellidos().isBlank()) ? " " + cliente.getApellidos() : ""));
                    return dto;
                })
        );
    }
}


