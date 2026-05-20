package shensei.prototipo.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shensei.prototipo.productos.entity.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

	java.util.List<Prestamo> findByIdCliente(Integer idCliente);

}

