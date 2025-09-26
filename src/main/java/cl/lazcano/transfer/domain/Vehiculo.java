package cl.lazcano.transfer.domain;

import cl.lazcano.transfer.domain.enumeration.EstadoEnum;
import cl.lazcano.transfer.domain.enumeration.TipoVehiculoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Vehiculo.
 */
@Entity
@Table(name = "vehiculo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoVehiculoEnum tipo;

    @NotNull
    @Min(value = 1950L)
    @Max(value = 2040L)
    @Column(name = "anio", nullable = false)
    private Long anio;

    @NotNull
    @Size(max = 10)
    @Column(name = "patente", length = 10, nullable = false)
    private String patente;

    @NotNull
    @Size(max = 30)
    @Column(name = "nro_motor", length = 30, nullable = false)
    private String nroMotor;

    @NotNull
    @Size(max = 30)
    @Column(name = "nro_chasis", length = 30, nullable = false)
    private String nroChasis;

    @NotNull
    @Size(max = 40)
    @Column(name = "color", length = 40, nullable = false)
    private String color;

    @NotNull
    @Size(max = 20)
    @Column(name = "nro_inscripcion", length = 20, nullable = false)
    private String nroInscripcion;

    @NotNull
    @Min(value = 0)
    @Max(value = 99999999)
    @Column(name = "avaluo_fiscal", nullable = false)
    private Integer avaluoFiscal;

    @Column(name = "fecha_recepcion")
    private LocalDate fechaRecepcion;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @NotNull
    @Column(name = "comuna_ultimo_permiso", nullable = false)
    private String comunaUltimoPermiso;

    @Min(value = 1980)
    @Max(value = 2040)
    @Column(name = "anio_ultimo_permiso")
    private Integer anioUltimoPermiso;

    @Size(max = 14)
    @Column(name = "codigo_sii", length = 14)
    private String codigoSii;

    @Column(name = "valor_transferencia")
    private Integer valorTransferencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEnum estado;

    @JsonIgnoreProperties(value = { "vehiculo", "venta", "vendedor", "comerciante" }, allowSetters = true)
    @OneToOne(mappedBy = "vehiculo")
    private Compra compra;

    @ManyToOne
    @JsonIgnoreProperties(value = { "modelos", "vehiculos" }, allowSetters = true)
    private Marcas marcas;

    @ManyToOne
    @JsonIgnoreProperties(value = { "vehiculos", "marcas" }, allowSetters = true)
    private Modelos modelos;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehiculo id(Long id) {
        this.id = id;
        return this;
    }

    public TipoVehiculoEnum getTipo() {
        return this.tipo;
    }

    public Vehiculo tipo(TipoVehiculoEnum tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoVehiculoEnum tipo) {
        this.tipo = tipo;
    }

    public Long getAnio() {
        return this.anio;
    }

    public Vehiculo anio(Long anio) {
        this.anio = anio;
        return this;
    }

    public void setAnio(Long anio) {
        this.anio = anio;
    }

    public String getPatente() {
        return this.patente;
    }

    public Vehiculo patente(String patente) {
        this.patente = patente;
        return this;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getNroMotor() {
        return this.nroMotor;
    }

    public Vehiculo nroMotor(String nroMotor) {
        this.nroMotor = nroMotor;
        return this;
    }

    public void setNroMotor(String nroMotor) {
        this.nroMotor = nroMotor;
    }

    public String getNroChasis() {
        return this.nroChasis;
    }

    public Vehiculo nroChasis(String nroChasis) {
        this.nroChasis = nroChasis;
        return this;
    }

    public void setNroChasis(String nroChasis) {
        this.nroChasis = nroChasis;
    }

    public String getColor() {
        return this.color;
    }

    public Vehiculo color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNroInscripcion() {
        return this.nroInscripcion;
    }

    public Vehiculo nroInscripcion(String nroInscripcion) {
        this.nroInscripcion = nroInscripcion;
        return this;
    }

    public void setNroInscripcion(String nroInscripcion) {
        this.nroInscripcion = nroInscripcion;
    }

    public Integer getAvaluoFiscal() {
        return this.avaluoFiscal;
    }

    public Vehiculo avaluoFiscal(Integer avaluoFiscal) {
        this.avaluoFiscal = avaluoFiscal;
        return this;
    }

    public void setAvaluoFiscal(Integer avaluoFiscal) {
        this.avaluoFiscal = avaluoFiscal;
    }

    public LocalDate getFechaRecepcion() {
        return this.fechaRecepcion;
    }

    public Vehiculo fechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
        return this;
    }

    public void setFechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public LocalDate getFechaVenta() {
        return this.fechaVenta;
    }

    public Vehiculo fechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
        return this;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getComunaUltimoPermiso() {
        return this.comunaUltimoPermiso;
    }

    public Vehiculo comunaUltimoPermiso(String comunaUltimoPermiso) {
        this.comunaUltimoPermiso = comunaUltimoPermiso;
        return this;
    }

    public void setComunaUltimoPermiso(String comunaUltimoPermiso) {
        this.comunaUltimoPermiso = comunaUltimoPermiso;
    }

    public Integer getAnioUltimoPermiso() {
        return this.anioUltimoPermiso;
    }

    public Vehiculo anioUltimoPermiso(Integer anioUltimoPermiso) {
        this.anioUltimoPermiso = anioUltimoPermiso;
        return this;
    }

    public void setAnioUltimoPermiso(Integer anioUltimoPermiso) {
        this.anioUltimoPermiso = anioUltimoPermiso;
    }

    public String getCodigoSii() {
        return this.codigoSii;
    }

    public Vehiculo codigoSii(String codigoSii) {
        this.codigoSii = codigoSii;
        return this;
    }

    public void setCodigoSii(String codigoSii) {
        this.codigoSii = codigoSii;
    }

    public Integer getValorTransferencia() {
        return this.valorTransferencia;
    }

    public Vehiculo valorTransferencia(Integer valorTransferencia) {
        this.valorTransferencia = valorTransferencia;
        return this;
    }

    public void setValorTransferencia(Integer valorTransferencia) {
        this.valorTransferencia = valorTransferencia;
    }

    public EstadoEnum getEstado() {
        return this.estado;
    }

    public Vehiculo estado(EstadoEnum estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public Compra getCompra() {
        return this.compra;
    }

    public Vehiculo compra(Compra compra) {
        this.setCompra(compra);
        return this;
    }

    public void setCompra(Compra compra) {
        if (this.compra != null) {
            this.compra.setVehiculo(null);
        }
        if (compra != null) {
            compra.setVehiculo(this);
        }
        this.compra = compra;
    }

    public Marcas getMarcas() {
        return this.marcas;
    }

    public Vehiculo marcas(Marcas marcas) {
        this.setMarcas(marcas);
        return this;
    }

    public void setMarcas(Marcas marcas) {
        this.marcas = marcas;
    }

    public Modelos getModelos() {
        return this.modelos;
    }

    public Vehiculo modelos(Modelos modelos) {
        this.setModelos(modelos);
        return this;
    }

    public void setModelos(Modelos modelos) {
        this.modelos = modelos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehiculo)) {
            return false;
        }
        return id != null && id.equals(((Vehiculo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehiculo{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", anio=" + getAnio() +
            ", patente='" + getPatente() + "'" +
            ", nroMotor='" + getNroMotor() + "'" +
            ", nroChasis='" + getNroChasis() + "'" +
            ", color='" + getColor() + "'" +
            ", nroInscripcion='" + getNroInscripcion() + "'" +
            ", avaluoFiscal=" + getAvaluoFiscal() +
            ", fechaRecepcion='" + getFechaRecepcion() + "'" +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", comunaUltimoPermiso='" + getComunaUltimoPermiso() + "'" +
            ", anioUltimoPermiso=" + getAnioUltimoPermiso() +
            ", codigoSii='" + getCodigoSii() + "'" +
            ", valorTransferencia=" + getValorTransferencia() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
