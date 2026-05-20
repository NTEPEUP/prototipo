package shensei.prototipo.auditoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shensei.prototipo.auditoria.entity.Bitacora;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Integer> {

}

