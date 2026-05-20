package shensei.prototipo.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shensei.prototipo.security.entity.UsuarioRol;
import shensei.prototipo.security.entity.UsuarioRol.UsuarioRolId;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> {

	java.util.List<UsuarioRol> findById_IdUsuario(Integer idUsuario);

	java.util.List<UsuarioRol> findById_IdRol(Integer idRol);

}

