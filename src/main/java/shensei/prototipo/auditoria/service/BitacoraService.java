package shensei.prototipo.auditoria.service;

import shensei.prototipo.auditoria.dto.BitacoraDTO;

import java.util.List;
import java.util.Optional;

public interface BitacoraService {
    List<BitacoraDTO> findAll();
    Optional<BitacoraDTO> findById(Integer id);
    BitacoraDTO create(BitacoraDTO dto);
    Optional<BitacoraDTO> update(Integer id, BitacoraDTO dto);
    void deleteById(Integer id);
}

