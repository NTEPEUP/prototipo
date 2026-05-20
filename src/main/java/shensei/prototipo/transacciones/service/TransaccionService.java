package shensei.prototipo.transacciones.service;

import shensei.prototipo.transacciones.dto.TransaccionDTO;
import shensei.prototipo.transacciones.dto.TransferRequest;

import java.util.List;
import java.util.Optional;

public interface TransaccionService {
    List<TransaccionDTO> findAll();
    Optional<TransaccionDTO> findById(Integer id);
    TransaccionDTO create(TransaccionDTO dto);
    Optional<TransaccionDTO> update(Integer id, TransaccionDTO dto);
    void deleteById(Integer id);

    void transfer(TransferRequest req);
}



