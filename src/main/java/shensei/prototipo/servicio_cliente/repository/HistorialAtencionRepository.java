package shensei.prototipo.servicio_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shensei.prototipo.servicio_cliente.entity.HistorialAtencion;

@Repository
public interface HistorialAtencionRepository extends JpaRepository<HistorialAtencion, Integer> {

}

