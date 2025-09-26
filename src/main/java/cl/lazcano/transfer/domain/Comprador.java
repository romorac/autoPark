package cl.lazcano.transfer.domain;

import cl.lazcano.transfer.domain.enumeration.EstadoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comprador.
 */
@Entity
@Table(name = "comprador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comprador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 50)
    @Column(name = "paterno", length = 50, nullable = false)
    private String paterno;

    @NotNull
    @Size(max = 50)
    @Column(name = "materno", length = 50, nullable = false)
    private String materno;

    @NotNull
    @Size(max = 20)
    @Column(name = "estado_civil", length = 20, nullable = false)
    private String estadoCivil;

    @NotNull
    @Size(max = 20)
    @Column(name = "nacionalidad", length = 20, nullable = false)
    private String nacionalidad;

    @NotNull
    @Size(max = 50)
    @Column(name = "profesion", length = 50, nullable = false)
    private String profesion;

    @NotNull
    @Size(max = 15)
    @Pattern(regexp = "^(((\\d{2})|(\\d{1}))\\.\\d{3}\\.\\d{3}-)([k|K|\\d]{1})$")
    @Column(name = "rut", length = 15, nullable = false)
    private String rut;

    @NotNull
    @Size(max = 150)
    @Column(name = "calle", length = 150, nullable = false)
    private String calle;

    @NotNull
    @Size(max = 10)
    @Column(name = "numero", length = 10, nullable = false)
    private String numero;

    @NotNull
    @Size(max = 50)
    @Column(name = "comuna", length = 50, nullable = false)
    private String comuna;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "celular")
    private String celular;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEnum estado;

    @JsonIgnoreProperties(value = { "comprador", "compra" }, allowSetters = true)
    @OneToOne(mappedBy = "comprador")
    private Venta venta;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comprador id(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Comprador nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return this.paterno;
    }

    public Comprador paterno(String paterno) {
        this.paterno = paterno;
        return this;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return this.materno;
    }

    public Comprador materno(String materno) {
        this.materno = materno;
        return this;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getEstadoCivil() {
        return this.estadoCivil;
    }

    public Comprador estadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
        return this;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNacionalidad() {
        return this.nacionalidad;
    }

    public Comprador nacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
        return this;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getProfesion() {
        return this.profesion;
    }

    public Comprador profesion(String profesion) {
        this.profesion = profesion;
        return this;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getRut() {
        return this.rut;
    }

    public Comprador rut(String rut) {
        this.rut = rut;
        return this;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getCalle() {
        return this.calle;
    }

    public Comprador calle(String calle) {
        this.calle = calle;
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return this.numero;
    }

    public Comprador numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComuna() {
        return this.comuna;
    }

    public Comprador comuna(String comuna) {
        this.comuna = comuna;
        return this;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Comprador telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return this.celular;
    }

    public Comprador celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public EstadoEnum getEstado() {
        return this.estado;
    }

    public Comprador estado(EstadoEnum estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public Venta getVenta() {
        return this.venta;
    }

    public Comprador venta(Venta venta) {
        this.setVenta(venta);
        return this;
    }

    public void setVenta(Venta venta) {
        if (this.venta != null) {
            this.venta.setComprador(null);
        }
        if (venta != null) {
            venta.setComprador(this);
        }
        this.venta = venta;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comprador)) {
            return false;
        }
        return id != null && id.equals(((Comprador) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comprador{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", paterno='" + getPaterno() + "'" +
            ", materno='" + getMaterno() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", nacionalidad='" + getNacionalidad() + "'" +
            ", profesion='" + getProfesion() + "'" +
            ", rut='" + getRut() + "'" +
            ", calle='" + getCalle() + "'" +
            ", numero='" + getNumero() + "'" +
            ", comuna='" + getComuna() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", celular='" + getCelular() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
