package shensei.prototipo.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shensei.prototipo.productos.entity.TipoProducto;

import java.util.Optional;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto, Integer> {
    Optional<TipoProducto> findByNombre(String nombre);
}

