package cl.lazcano.transfer.domain;

import cl.lazcano.transfer.domain.enumeration.EstadoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Compra.
 */
@Entity
@Table(name = "compra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_recepcion", nullable = false)
    private LocalDate fechaRecepcion;

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

    @Size(max = 1000)
    @Column(name = "forma_pago", length = 1000)
    private String formaPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEnum estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "en_proceso_venta")
    private EstadoEnum enProcesoVenta;

    @JsonIgnoreProperties(value = { "compra", "marcas", "modelos" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Vehiculo vehiculo;

    @JsonIgnoreProperties(value = { "comprador", "compra" }, allowSetters = true)
    @OneToOne(mappedBy = "compra")
    private Venta venta;

    @ManyToOne
    @JsonIgnoreProperties(value = { "compras" }, allowSetters = true)
    private Vendedor vendedor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "compras" }, allowSetters = true)
    private Comerciante comerciante;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Compra id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getFechaRecepcion() {
        return this.fechaRecepcion;
    }

    public Compra fechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
        return this;
    }

    public void setFechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public Compra ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Long getMonto() {
        return this.monto;
    }

    public Compra monto(Long monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public String getMontoTexto() {
        return this.montoTexto;
    }

    public Compra montoTexto(String montoTexto) {
        this.montoTexto = montoTexto;
        return this;
    }

    public void setMontoTexto(String montoTexto) {
        this.montoTexto = montoTexto;
    }

    public String getFormaPago() {
        return this.formaPago;
    }

    public Compra formaPago(String formaPago) {
        this.formaPago = formaPago;
        return this;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public EstadoEnum getEstado() {
        return this.estado;
    }

    public Compra estado(EstadoEnum estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public EstadoEnum getEnProcesoVenta() {
        return this.enProcesoVenta;
    }

    public Compra enProcesoVenta(EstadoEnum enProcesoVenta) {
        this.enProcesoVenta = enProcesoVenta;
        return this;
    }

    public void setEnProcesoVenta(EstadoEnum enProcesoVenta) {
        this.enProcesoVenta = enProcesoVenta;
    }

    public Vehiculo getVehiculo() {
        return this.vehiculo;
    }

    public Compra vehiculo(Vehiculo vehiculo) {
        this.setVehiculo(vehiculo);
        return this;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Venta getVenta() {
        return this.venta;
    }

    public Compra venta(Venta venta) {
        this.setVenta(venta);
        return this;
    }

    public void setVenta(Venta venta) {
        if (this.venta != null) {
            this.venta.setCompra(null);
        }
        if (venta != null) {
            venta.setCompra(this);
        }
        this.venta = venta;
    }

    public Vendedor getVendedor() {
        return this.vendedor;
    }

    public Compra vendedor(Vendedor vendedor) {
        this.setVendedor(vendedor);
        return this;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Comerciante getComerciante() {
        return this.comerciante;
    }

    public Compra comerciante(Comerciante comerciante) {
        this.setComerciante(comerciante);
        return this;
    }

    public void setComerciante(Comerciante comerciante) {
        this.comerciante = comerciante;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compra)) {
            return false;
        }
        return id != null && id.equals(((Compra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Compra{" +
            "id=" + getId() +
            ", fechaRecepcion='" + getFechaRecepcion() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", monto=" + getMonto() +
            ", montoTexto='" + getMontoTexto() + "'" +
            ", formaPago='" + getFormaPago() + "'" +
            ", estado='" + getEstado() + "'" +
            ", enProcesoVenta='" + getEnProcesoVenta() + "'" +
            "}";
    }
}
