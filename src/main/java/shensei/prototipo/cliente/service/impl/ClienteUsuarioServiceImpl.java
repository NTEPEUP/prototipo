package shensei.prototipo.cliente.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.cliente.dto.ClienteUsuarioDTO;
import shensei.prototipo.cliente.entity.ClienteUsuario;
import shensei.prototipo.cliente.repository.ClienteUsuarioRepository;
import shensei.prototipo.cliente.service.ClienteUsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteUsuarioServiceImpl implements ClienteUsuarioService {

    private final ClienteUsuarioRepository repo;

    public ClienteUsuarioServiceImpl(ClienteUsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ClienteUsuarioDTO> findAll() {
        return repo.findAll().stream().map(cu -> {
            ClienteUsuarioDTO dto = new ClienteUsuarioDTO();
            dto.setIdCliente(cu.getId().getIdCliente());
            dto.setIdUsuario(cu.getId().getIdUsuario());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ClienteUsuarioDTO create(ClienteUsuarioDTO dto) {
        ClienteUsuario cu = new ClienteUsuario();
        ClienteUsuario.ClienteUsuarioId id = new ClienteUsuario.ClienteUsuarioId(dto.getIdCliente(), dto.getIdUsuario());
        cu.setId(id);
        ClienteUsuario saved = repo.save(cu);
        ClienteUsuarioDTO out = new ClienteUsuarioDTO();
        out.setIdCliente(saved.getId().getIdCliente());
        out.setIdUsuario(saved.getId().getIdUsuario());
        return out;
    }

    @Override
    public void delete(Integer idCliente, Integer idUsuario) {
        ClienteUsuario.ClienteUsuarioId id = new ClienteUsuario.ClienteUsuarioId(idCliente, idUsuario);
        repo.deleteById(id);
    }
}

