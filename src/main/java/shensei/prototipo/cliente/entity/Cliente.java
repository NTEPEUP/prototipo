package shensei.prototipo.cliente.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cliente", schema = "clientes")
public class Cliente {

	@Id
	@Column(name = "id_cliente")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCliente;

	@Column(name = "codigo_cliente", length = 20, unique = true)
	private String codigoCliente;

	@Column(name = "dpi", length = 20, unique = true, nullable = false)
	private String dpi;

	@Column(name = "nombres", length = 100, nullable = false)
	private String nombres;

	@Column(name = "apellidos", length = 100, nullable = false)
	private String apellidos;

	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;

	@Column(name = "correo", length = 100)
	private String correo;

	@Column(name = "telefono", length = 20)
	private String telefono;

	@Column(name = "direccion", columnDefinition = "TEXT")
	private String direccion;

	@Column(name = "estado_cliente", length = 30)
	private String estadoCliente = "ACTIVO";

	@Column(name = "fecha_registro")
	private LocalDateTime fechaRegistro;

	public Cliente() {
	}

	// Getters and setters

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getDpi() {
		return dpi;
	}

	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEstadoCliente() {
		return estadoCliente;
	}

	public void setEstadoCliente(String estadoCliente) {
		this.estadoCliente = estadoCliente;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cliente cliente = (Cliente) o;
		return Objects.equals(idCliente, cliente.idCliente);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCliente);
	}

	@Override
	public String toString() {
		return "Cliente{" +
				"idCliente=" + idCliente +
				", codigoCliente='" + codigoCliente + '\'' +
				", dpi='" + dpi + '\'' +
				", nombres='" + nombres + '\'' +
				", apellidos='" + apellidos + '\'' +
				'}';
	}

}
