package shensei.prototipo.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shensei.prototipo.cliente.entity.ClienteUsuario;
import shensei.prototipo.cliente.entity.ClienteUsuario.ClienteUsuarioId;

@Repository
public interface ClienteUsuarioRepository extends JpaRepository<ClienteUsuario, ClienteUsuarioId> {

	java.util.Optional<ClienteUsuario> findById_IdUsuario(Integer idUsuario);

}

