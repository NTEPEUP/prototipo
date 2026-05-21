package shensei.prototipo.transacciones.dto;

import lombok.Data;

@Data
public class ConfirmTransferRequest {
    private String tokenId;
    private String token;
}

