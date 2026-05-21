package shensei.prototipo.transacciones.service;

import shensei.prototipo.transacciones.entity.TransferToken;
import shensei.prototipo.transacciones.dto.TransferTokenIssueDTO;
import shensei.prototipo.transacciones.dto.TransferRequest;

import java.util.Optional;

public interface TransferTokenService {
    TransferTokenIssueDTO createToken(TransferRequest req, Integer idUsuario);
    Optional<TransferToken> validateAndConsumeToken(String tokenId, String token, Integer idUsuario);
}


