package shensei.prototipo.transacciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransferTokenIssueDTO {
    private String tokenId;
    private String rawToken;
    private LocalDateTime expiresAt;
}

