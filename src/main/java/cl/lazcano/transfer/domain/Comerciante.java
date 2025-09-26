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
 * A Comerciante.
 */
@Entity
@Table(name = "comerciante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comerciante implements Serializable {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEnum estado;

    @OneToMany(mappedBy = "comerciante")
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

    public Comerciante id(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Comerciante nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return this.paterno;
    }

    public Comerciante paterno(String paterno) {
        this.paterno = paterno;
        return this;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return this.materno;
    }

    public Comerciante materno(String materno) {
        this.materno = materno;
        return this;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getRut() {
        return this.rut;
    }

    public Comerciante rut(String rut) {
        this.rut = rut;
        return this;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public EstadoEnum getEstado() {
        return this.estado;
    }

    public Comerciante estado(EstadoEnum estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public Set<Compra> getCompras() {
        return this.compras;
    }

    public Comerciante compras(Set<Compra> compras) {
        this.setCompras(compras);
        return this;
    }

    public Comerciante addCompra(Compra compra) {
        this.compras.add(compra);
        compra.setComerciante(this);
        return this;
    }

    public Comerciante removeCompra(Compra compra) {
        this.compras.remove(compra);
        compra.setComerciante(null);
        return this;
    }

    public void setCompras(Set<Compra> compras) {
        if (this.compras != null) {
            this.compras.forEach(i -> i.setComerciante(null));
        }
        if (compras != null) {
            compras.forEach(i -> i.setComerciante(this));
        }
        this.compras = compras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comerciante)) {
            return false;
        }
        return id != null && id.equals(((Comerciante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comerciante{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", paterno='" + getPaterno() + "'" +
            ", materno='" + getMaterno() + "'" +
            ", rut='" + getRut() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
