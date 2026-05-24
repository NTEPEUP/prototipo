package shensei.prototipo.servicio_cliente.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "reclamo", schema = "servicio_cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reclamo")
    private Integer idReclamo;

    @Column(name = "codigo_reclamo", length = 30, unique = true, insertable = false,
            updatable = false)
    private String codigoReclamo;

    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @Column(name = "tipo_caso", length = 100)
    private String tipoCaso;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado", length = 30)
    private String estado = "ABIERTO";

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

}

