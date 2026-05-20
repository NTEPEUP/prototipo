package shensei.prototipo.cliente.service;

import shensei.prototipo.cliente.dto.ClienteUsuarioDTO;

import java.util.List;

public interface ClienteUsuarioService {
    List<ClienteUsuarioDTO> findAll();
    ClienteUsuarioDTO create(ClienteUsuarioDTO dto);
    void delete(Integer idCliente, Integer idUsuario);
}

