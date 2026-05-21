package shensei.prototipo.transacciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shensei.prototipo.transacciones.entity.TransferToken;

@Repository
public interface TransferTokenRepository extends JpaRepository<TransferToken, String> {
}


