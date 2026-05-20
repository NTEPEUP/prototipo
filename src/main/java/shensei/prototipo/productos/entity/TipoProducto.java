package shensei.prototipo.productos.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "tipo_producto", schema = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_producto")
    private Integer idTipoProducto;

    @Column(name = "nombre", length = 50, unique = true, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

}

