package shensei.prototipo.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shensei.prototipo.productos.entity.Tarjeta;

import java.util.Optional;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Integer> {
    java.util.List<Tarjeta> findByIdCliente(Integer idCliente);
    Optional<Tarjeta> findByNumeroTarjeta(String numeroTarjeta);
}

