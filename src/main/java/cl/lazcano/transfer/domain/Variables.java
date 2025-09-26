package cl.lazcano.transfer.domain;

import cl.lazcano.transfer.domain.enumeration.EstadoEnum;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Variables.
 */
@Entity
@Table(name = "variables")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Variables implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "nombre", length = 30, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 30)
    @Column(name = "valor", length = 30, nullable = false)
    private String valor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEnum estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Variables id(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Variables nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return this.valor;
    }

    public Variables valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public EstadoEnum getEstado() {
        return this.estado;
    }

    public Variables estado(EstadoEnum estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Variables)) {
            return false;
        }
        return id != null && id.equals(((Variables) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Variables{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", valor='" + getValor() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
