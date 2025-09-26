package cl.lazcano.transfer.domain;

import cl.lazcano.transfer.domain.enumeration.EstadoEnum;
import cl.lazcano.transfer.domain.enumeration.TipoVentaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_venta", nullable = false)
    private LocalDate fechaVenta;

    @NotNull
    @Size(max = 50)
    @Column(name = "ciudad", length = 50, nullable = false)
    private String ciudad;

    @NotNull
    @Min(value = 0L)
    @Column(name = "monto", nullable = false)
    private Long monto;

    @NotNull
    @Size(max = 100)
    @Column(name = "monto_texto", length = 100, nullable = false)
    private String montoTexto;

    @NotNull
    @Min(value = 0L)
    @Column(name = "monto_declaracion", nullable = false)
    private Long montoDeclaracion;

    @NotNull
    @Size(max = 100)
    @Column(name = "monto_texto_declaracion", length = 100, nullable = false)
    private String montoTextoDeclaracion;

    @NotNull
    @Min(value = 0L)
    @Column(name = "monto_contrato", nullable = false)
    private Long montoContrato;

    @NotNull
    @Size(max = 100)
    @Column(name = "monto_contrato_texto", length = 100, nullable = false)
    private String montoContratoTexto;

    @NotNull
    @Column(name = "fecha_del_contrato_venta", nullable = false)
    private LocalDate fechaDelContratoVenta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_venta", nullable = false)
    private TipoVentaEnum tipoVenta;

    @Size(max = 100)
    @Column(name = "nombre_venta_para", length = 100)
    private String nombreVentaPara;

    @Size(max = 15)
    @Pattern(regexp = "^(((\\d{2})|(\\d{1}))\\.\\d{3}\\.\\d{3}-)([k|K|\\d]{1})$")
    @Column(name = "rut_venta_para", length = 15)
    private String rutVentaPara;

    @Size(max = 1000)
    @Column(name = "forma_pago", length = 1000)
    private String formaPago;

    @Size(max = 1000)
    @Column(name = "forma_pago_declaracion", length = 1000)
    private String formaPagoDeclaracion;

    @Size(max = 400)
    @Column(name = "observacion", length = 400)
    private String observacion;

    @Size(max = 35)
    @Column(name = "numero_causa", length = 35)
    private String numeroCausa;

    @Size(max = 15)
    @Column(name = "numero_solicitud", length = 15)
    private String numeroSolicitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEnum estado;

    @JsonIgnoreProperties(value = { "venta" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Comprador comprador;

    @JsonIgnoreProperties(value = { "vehiculo", "venta", "vendedor", "comerciante" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Compra compra;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venta id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getFechaVenta() {
        return this.fechaVenta;
    }

    public Venta fechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
        return this;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public Venta ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Long getMonto() {
        return this.monto;
    }

    public Venta monto(Long monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public String getMontoTexto() {
        return this.montoTexto;
    }

    public Venta montoTexto(String montoTexto) {
        this.montoTexto = montoTexto;
        return this;
    }

    public void setMontoTexto(String montoTexto) {
        this.montoTexto = montoTexto;
    }

    public Long getMontoDeclaracion() {
        return this.montoDeclaracion;
    }

    public Venta montoDeclaracion(Long montoDeclaracion) {
        this.montoDeclaracion = montoDeclaracion;
        return this;
    }

    public void setMontoDeclaracion(Long montoDeclaracion) {
        this.montoDeclaracion = montoDeclaracion;
    }

    public String getMontoTextoDeclaracion() {
        return this.montoTextoDeclaracion;
    }

    public Venta montoTextoDeclaracion(String montoTextoDeclaracion) {
        this.montoTextoDeclaracion = montoTextoDeclaracion;
        return this;
    }

    public void setMontoTextoDeclaracion(String montoTextoDeclaracion) {
        this.montoTextoDeclaracion = montoTextoDeclaracion;
    }

    public Long getMontoContrato() {
        return this.montoContrato;
    }

    public Venta montoContrato(Long montoContrato) {
        this.montoContrato = montoContrato;
        return this;
    }

    public void setMontoContrato(Long montoContrato) {
        this.montoContrato = montoContrato;
    }

    public String getMontoContratoTexto() {
        return this.montoContratoTexto;
    }

    public Venta montoContratoTexto(String montoContratoTexto) {
        this.montoContratoTexto = montoContratoTexto;
        return this;
    }

    public void setMontoContratoTexto(String montoContratoTexto) {
        this.montoContratoTexto = montoContratoTexto;
    }

    public LocalDate getFechaDelContratoVenta() {
        return this.fechaDelContratoVenta;
    }

    public Venta fechaDelContratoVenta(LocalDate fechaDelContratoVenta) {
        this.fechaDelContratoVenta = fechaDelContratoVenta;
        return this;
    }

    public void setFechaDelContratoVenta(LocalDate fechaDelContratoVenta) {
        this.fechaDelContratoVenta = fechaDelContratoVenta;
    }

    public TipoVentaEnum getTipoVenta() {
        return this.tipoVenta;
    }

    public Venta tipoVenta(TipoVentaEnum tipoVenta) {
        this.tipoVenta = tipoVenta;
        return this;
    }

    public void setTipoVenta(TipoVentaEnum tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getNombreVentaPara() {
        return this.nombreVentaPara;
    }

    public Venta nombreVentaPara(String nombreVentaPara) {
        this.nombreVentaPara = nombreVentaPara;
        return this;
    }

    public void setNombreVentaPara(String nombreVentaPara) {
        this.nombreVentaPara = nombreVentaPara;
    }

    public String getRutVentaPara() {
        return this.rutVentaPara;
    }

    public Venta rutVentaPara(String rutVentaPara) {
        this.rutVentaPara = rutVentaPara;
        return this;
    }

    public void setRutVentaPara(String rutVentaPara) {
        this.rutVentaPara = rutVentaPara;
    }

    public String getFormaPago() {
        return this.formaPago;
    }

    public Venta formaPago(String formaPago) {
        this.formaPago = formaPago;
        return this;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getFormaPagoDeclaracion() {
        return this.formaPagoDeclaracion;
    }

    public Venta formaPagoDeclaracion(String formaPagoDeclaracion) {
        this.formaPagoDeclaracion = formaPagoDeclaracion;
        return this;
    }

    public void setFormaPagoDeclaracion(String formaPagoDeclaracion) {
        this.formaPagoDeclaracion = formaPagoDeclaracion;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public Venta observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNumeroCausa() {
        return this.numeroCausa;
    }

    public Venta numeroCausa(String numeroCausa) {
        this.numeroCausa = numeroCausa;
        return this;
    }

    public void setNumeroCausa(String numeroCausa) {
        this.numeroCausa = numeroCausa;
    }

    public String getNumeroSolicitud() {
        return this.numeroSolicitud;
    }

    public Venta numeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
        return this;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public EstadoEnum getEstado() {
        return this.estado;
    }

    public Venta estado(EstadoEnum estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public Comprador getComprador() {
        return this.comprador;
    }

    public Venta comprador(Comprador comprador) {
        this.setComprador(comprador);
        return this;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public Compra getCompra() {
        return this.compra;
    }

    public Venta compra(Compra compra) {
        this.setCompra(compra);
        return this;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return id != null && id.equals(((Venta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
	@Override
	public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", monto=" + getMonto() +
            ", montoTexto='" + getMontoTexto() + "'" +
            ", montoDeclaracion=" + getMontoDeclaracion() +
            ", montoTextoDeclaracion='" + getMontoTextoDeclaracion() + "'" +
            ", montoContrato=" + getMontoContrato() +
            ", montoContratoTexto='" + getMontoContratoTexto() + "'" +
            ", fechaDelContratoVenta='" + getFechaDelContratoVenta() + "'" +
            ", tipoVenta='" + getTipoVenta() + "'" +
            ", nombreVentaPara='" + getNombreVentaPara() + "'" +
            ", rutVentaPara='" + getRutVentaPara() + "'" +
            ", formaPago='" + getFormaPago() + "'" +
            ", formaPagoDeclaracion='" + getFormaPagoDeclaracion() + "'" +
            ", observacion='" + getObservacion() + "'" +
            ", numeroCausa='" + getNumeroCausa() + "'" +
            ", numeroSolicitud='" + getNumeroSolicitud() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
	}
}
