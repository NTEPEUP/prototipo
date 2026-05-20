package shensei.prototipo.cliente.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.cliente.entity.Cliente;
import shensei.prototipo.cliente.repository.ClienteRepository;
import shensei.prototipo.cliente.service.ClienteService;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void deleteById(Integer id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Optional<Cliente> findByDpi(String dpi) {
        return clienteRepository.findByDpi(dpi);
    }
}

