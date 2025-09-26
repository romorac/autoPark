package cl.lazcano.transfer.domain;

import cl.lazcano.transfer.domain.enumeration.EstadoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Vendedor.
 */
@Entity
@Table(name = "vendedor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vendedor implements Serializable {

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
    @Size(max = 15)
    @Pattern(regexp = "^(((\\d{2})|(\\d{1}))\\.\\d{3}\\.\\d{3}-)([k|K|\\d]{1})$")
    @Column(name = "rut", length = 15, nullable = false)
    private String rut;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "celular")
    private String celular;

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

    @Size(max = 60)
    @Column(name = "nombre_representante", length = 60)
    private String nombreRepresentante;

    @Pattern(regexp = "^()|((((\\d{2})|(\\d{1}))\\.\\d{3}\\.\\d{3}-)([k|K|\\d]{1}))$")
    @Column(name = "rut_representante")
    private String rutRepresentante;

    @NotNull
    @Column(name = "tiene_representante", nullable = false)
    private Boolean tieneRepresentante;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEnum estado;

    @OneToMany(mappedBy = "vendedor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehiculo", "venta", "vendedor", "comerciante" }, allowSetters = true)
    private Set<Compra> compras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vendedor id(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Vendedor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return this.paterno;
    }

    public Vendedor paterno(String paterno) {
        this.paterno = paterno;
        return this;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return this.materno;
    }

    public Vendedor materno(String materno) {
        this.materno = materno;
        return this;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getRut() {
        return this.rut;
    }

    public Vendedor rut(String rut) {
        this.rut = rut;
        return this;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Vendedor telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return this.celular;
    }

    public Vendedor celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCalle() {
        return this.calle;
    }

    public Vendedor calle(String calle) {
        this.calle = calle;
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return this.numero;
    }

    public Vendedor numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComuna() {
        return this.comuna;
    }

    public Vendedor comuna(String comuna) {
        this.comuna = comuna;
        return this;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getEstadoCivil() {
        return this.estadoCivil;
    }

    public Vendedor estadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
        return this;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNacionalidad() {
        return this.nacionalidad;
    }

    public Vendedor nacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
        return this;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getProfesion() {
        return this.profesion;
    }

    public Vendedor profesion(String profesion) {
        this.profesion = profesion;
        return this;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getNombreRepresentante() {
        return this.nombreRepresentante;
    }

    public Vendedor nombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
        return this;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getRutRepresentante() {
        return this.rutRepresentante;
    }

    public Vendedor rutRepresentante(String rutRepresentante) {
        this.rutRepresentante = rutRepresentante;
        return this;
    }

    public void setRutRepresentante(String rutRepresentante) {
        this.rutRepresentante = rutRepresentante;
    }

    public Boolean getTieneRepresentante() {
        return this.tieneRepresentante;
    }

    public Vendedor tieneRepresentante(Boolean tieneRepresentante) {
        this.tieneRepresentante = tieneRepresentante;
        return this;
    }

    public void setTieneRepresentante(Boolean tieneRepresentante) {
        this.tieneRepresentante = tieneRepresentante;
    }

    public EstadoEnum getEstado() {
        return this.estado;
    }

    public Vendedor estado(EstadoEnum estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public Set<Compra> getCompras() {
        return this.compras;
    }

    public Vendedor compras(Set<Compra> compras) {
        this.setCompras(compras);
        return this;
    }

    public Vendedor addCompra(Compra compra) {
        this.compras.add(compra);
        compra.setVendedor(this);
        return this;
    }

    public Vendedor removeCompra(Compra compra) {
        this.compras.remove(compra);
        compra.setVendedor(null);
        return this;
    }

    public void setCompras(Set<Compra> compras) {
        if (this.compras != null) {
            this.compras.forEach(i -> i.setVendedor(null));
        }
        if (compras != null) {
            compras.forEach(i -> i.setVendedor(this));
        }
        this.compras = compras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vendedor)) {
            return false;
        }
        return id != null && id.equals(((Vendedor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vendedor{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", paterno='" + getPaterno() + "'" +
            ", materno='" + getMaterno() + "'" +
            ", rut='" + getRut() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", celular='" + getCelular() + "'" +
            ", calle='" + getCalle() + "'" +
            ", numero='" + getNumero() + "'" +
            ", comuna='" + getComuna() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", nacionalidad='" + getNacionalidad() + "'" +
            ", profesion='" + getProfesion() + "'" +
            ", nombreRepresentante='" + getNombreRepresentante() + "'" +
            ", rutRepresentante='" + getRutRepresentante() + "'" +
            ", tieneRepresentante='" + getTieneRepresentante() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
