package cl.lazcano.transfer.service.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Getter
//@Setter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PagoLetrasDTO {

    private String ciudadGiro;

    private String fechaGiro;
    private String nombrePagador;
    private String nombreBeneficiario;
    private String domicilioBeneficiario;
    private String ciudadBeneficiario;
    private String comunaBeneficiario;
    private String rutBeneficiario;
    private List<DetalleLetraDTO> lstDetalleLetra;

    public List<DetalleLetraDTO> getLstDetalleLetra() {
        if (null == lstDetalleLetra) {
            lstDetalleLetra = new ArrayList<>();
        }
        return lstDetalleLetra;
    }

    public String getCiudadGiro() {
        return ciudadGiro;
    }

    public void setCiudadGiro(String ciudadGiro) {
        this.ciudadGiro = ciudadGiro;
    }

    public String getFechaGiro() {
        return fechaGiro;
    }

    public void setFechaGiro(String fechaGiro) {
        this.fechaGiro = fechaGiro;
    }

    public String getNombrePagador() {
        return nombrePagador;
    }

    public void setNombrePagador(String nombrePagador) {
        this.nombrePagador = nombrePagador;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public String getDomicilioBeneficiario() {
        return domicilioBeneficiario;
    }

    public void setDomicilioBeneficiario(String domicilioBeneficiario) {
        this.domicilioBeneficiario = domicilioBeneficiario;
    }

    public String getCiudadBeneficiario() {
        return ciudadBeneficiario;
    }

    public void setCiudadBeneficiario(String ciudadBeneficiario) {
        this.ciudadBeneficiario = ciudadBeneficiario;
    }

    public String getComunaBeneficiario() {
        return comunaBeneficiario;
    }

    public void setComunaBeneficiario(String comunaBeneficiario) {
        this.comunaBeneficiario = comunaBeneficiario;
    }

    public String getRutBeneficiario() {
        return rutBeneficiario;
    }

    public void setRutBeneficiario(String rutBeneficiario) {
        this.rutBeneficiario = rutBeneficiario;
    }

    public void setLstDetalleLetra(List<DetalleLetraDTO> lstDetalleLetra) {
        this.lstDetalleLetra = lstDetalleLetra;
    }
}
