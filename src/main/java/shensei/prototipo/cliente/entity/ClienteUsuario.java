package shensei.prototipo.cliente.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "cliente_usuario", schema = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteUsuario {

    @EmbeddedId
    private ClienteUsuarioId id;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClienteUsuarioId implements Serializable {
        @Column(name = "id_cliente")
        private Integer idCliente;

        @Column(name = "id_usuario")
        private Integer idUsuario;
    }

}

