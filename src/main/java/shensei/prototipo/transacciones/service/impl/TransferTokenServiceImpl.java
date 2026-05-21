package shensei.prototipo.transacciones.service.impl;

import org.springframework.stereotype.Service;
import shensei.prototipo.transacciones.entity.TransferToken;
import shensei.prototipo.transacciones.repository.TransferTokenRepository;
import shensei.prototipo.transacciones.dto.TransferRequest;
import shensei.prototipo.transacciones.dto.TransferTokenIssueDTO;
import shensei.prototipo.transacciones.service.TransferTokenService;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class TransferTokenServiceImpl implements TransferTokenService {

    private final TransferTokenRepository repo;
    private final Random random = new Random();
    private final long ttlSeconds;

    public TransferTokenServiceImpl(TransferTokenRepository repo, @Value("${transfer.token.ttl-seconds:300}") long ttlSeconds) {
        this.repo = repo;
        this.ttlSeconds = ttlSeconds;
    }

    private String generateNumericToken() {
        int digits = 6;
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        int val = random.nextInt(max - min + 1) + min;
        return Integer.toString(val);
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no disponible", e);
        }
    }

    @Override
    public TransferTokenIssueDTO createToken(TransferRequest req, Integer idUsuario) {
        String rawToken = generateNumericToken();
        TransferToken t = new TransferToken();
        t.setTokenHash(sha256(rawToken));
        t.setIdUsuario(idUsuario);
        t.setCuentaOrigen(req.getCuentaOrigen());
        t.setCuentaDestino(req.getCuentaDestino());
        t.setMonto(req.getMonto());
        t.setCreatedAt(LocalDateTime.now());
        t.setExpiresAt(LocalDateTime.now().plusSeconds(ttlSeconds));
        t.setUsed(false);
        TransferToken saved = repo.save(t);
        return new TransferTokenIssueDTO(saved.getIdToken(), rawToken, saved.getExpiresAt());
    }

    @Override
    public Optional<TransferToken> validateAndConsumeToken(String tokenId, String token, Integer idUsuario) {
        Optional<TransferToken> opt = repo.findById(tokenId);
        if (opt.isPresent()) {
            TransferToken t = opt.get();
            if (t.getUsed() != null && t.getUsed()) return Optional.empty();
            if (t.getIdUsuario() == null || idUsuario == null || !t.getIdUsuario().equals(idUsuario)) return Optional.empty();
            if (t.getExpiresAt().isBefore(LocalDateTime.now())) return Optional.empty();
            if (!t.getTokenHash().equals(sha256(token))) return Optional.empty();
            t.setUsed(true);
            repo.save(t);
            return Optional.of(t);
        }
        return Optional.empty();
    }
}


