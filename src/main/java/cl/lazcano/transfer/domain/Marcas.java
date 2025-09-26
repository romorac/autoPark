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
 * A Marcas.
 */
@Entity
@Table(name = "marcas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Marcas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "marcas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehiculos", "marcas" }, allowSetters = true)
    private Set<Modelos> modelos = new HashSet<>();

    @OneToMany(mappedBy = "marcas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "compra", "marcas", "modelos" }, allowSetters = true)
    private Set<Vehiculo> vehiculos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Marcas id(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Marcas nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Modelos> getModelos() {
        return this.modelos;
    }

    public Marcas modelos(Set<Modelos> modelos) {
        this.setModelos(modelos);
        return this;
    }

    public Marcas addModelos(Modelos modelos) {
        this.modelos.add(modelos);
        modelos.setMarcas(this);
        return this;
    }

    public Marcas removeModelos(Modelos modelos) {
        this.modelos.remove(modelos);
        modelos.setMarcas(null);
        return this;
    }

    public void setModelos(Set<Modelos> modelos) {
        if (this.modelos != null) {
            this.modelos.forEach(i -> i.setMarcas(null));
        }
        if (modelos != null) {
            modelos.forEach(i -> i.setMarcas(this));
        }
        this.modelos = modelos;
    }

    public Set<Vehiculo> getVehiculos() {
        return this.vehiculos;
    }

    public Marcas vehiculos(Set<Vehiculo> vehiculos) {
        this.setVehiculos(vehiculos);
        return this;
    }

    public Marcas addVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
        vehiculo.setMarcas(this);
        return this;
    }

    public Marcas removeVehiculo(Vehiculo vehiculo) {
        this.vehiculos.remove(vehiculo);
        vehiculo.setMarcas(null);
        return this;
    }

    public void setVehiculos(Set<Vehiculo> vehiculos) {
        if (this.vehiculos != null) {
            this.vehiculos.forEach(i -> i.setMarcas(null));
        }
        if (vehiculos != null) {
            vehiculos.forEach(i -> i.setMarcas(this));
        }
        this.vehiculos = vehiculos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Marcas)) {
            return false;
        }
        return id != null && id.equals(((Marcas) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Marcas{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
