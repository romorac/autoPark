package cl.lazcano.transfer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Modelos.
 */
@Entity
@Table(name = "modelos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Modelos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "modelos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "compra", "marcas", "modelos" }, allowSetters = true)
    private Set<Vehiculo> vehiculos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "modelos", "vehiculos" }, allowSetters = true)
    private Marcas marcas;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Modelos id(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Modelos nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Vehiculo> getVehiculos() {
        return this.vehiculos;
    }

    public Modelos vehiculos(Set<Vehiculo> vehiculos) {
        this.setVehiculos(vehiculos);
        return this;
    }

    public Modelos addVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
        vehiculo.setModelos(this);
        return this;
    }

    public Modelos removeVehiculo(Vehiculo vehiculo) {
        this.vehiculos.remove(vehiculo);
        vehiculo.setModelos(null);
        return this;
    }

    public void setVehiculos(Set<Vehiculo> vehiculos) {
        if (this.vehiculos != null) {
            this.vehiculos.forEach(i -> i.setModelos(null));
        }
        if (vehiculos != null) {
            vehiculos.forEach(i -> i.setModelos(this));
        }
        this.vehiculos = vehiculos;
    }

    public Marcas getMarcas() {
        return this.marcas;
    }

    public Modelos marcas(Marcas marcas) {
        this.setMarcas(marcas);
        return this;
    }

    public void setMarcas(Marcas marcas) {
        this.marcas = marcas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Modelos)) {
            return false;
        }
        return id != null && id.equals(((Modelos) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Modelos{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
