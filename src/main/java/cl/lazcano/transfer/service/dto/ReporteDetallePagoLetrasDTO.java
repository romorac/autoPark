package cl.lazcano.transfer.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.ToString;

@JsonPOJOBuilder
@ToString
public class ReporteDetallePagoLetrasDTO {

    private String pagadorNombre;
    private String pagadorCiudad;
    private String beneficiarioNombre;
    private String beneficiarioDomicilio;
    private String beneficiarioCiudad;
    private String beneficiarioComuna;
    private String beneficiarioRut;
    private String numeroLetra;
    private String fechaVencimiento;
    private String fechaGiro;
    private String fechaPago;
    private String montoNumerico;
    private String montoTexto;
    private String cantidadLetras;

    public ReporteDetallePagoLetrasDTO(
        String pagadorNombre,
        String pagadorCiudad,
        String beneficiarioNombre,
        String beneficiarioDomicilio,
        String beneficiarioCiudad,
        String beneficiarioComuna,
        String beneficiarioRut,
        String numeroLetra,
        String fechaVencimiento,
        String fechaGiro,
        String fechaPago,
        String montoNumerico,
        String montoTexto,
        String cantidadLetras
    ) {
        super();
        this.pagadorNombre = pagadorNombre;
        this.pagadorCiudad = pagadorCiudad;
        this.beneficiarioNombre = beneficiarioNombre;
        this.beneficiarioDomicilio = beneficiarioDomicilio;
        this.beneficiarioCiudad = beneficiarioCiudad;
        this.beneficiarioComuna = beneficiarioComuna;
        this.beneficiarioRut = beneficiarioRut;
        this.numeroLetra = numeroLetra;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaGiro = fechaGiro;
        this.fechaPago = fechaPago;
        this.montoNumerico = montoNumerico;
        this.montoTexto = montoTexto;
        this.cantidadLetras = cantidadLetras;
    }

    public String getPagadorNombre() {
        return pagadorNombre;
    }

    public void setPagadorNombre(String pagadorNombre) {
        this.pagadorNombre = pagadorNombre;
    }

    public String getPagadorCiudad() {
        return pagadorCiudad;
    }

    public void setPagadorCiudad(String pagadorCiudad) {
        this.pagadorCiudad = pagadorCiudad;
    }

    public String getBeneficiarioNombre() {
        return beneficiarioNombre;
    }

    public void setBeneficiarioNombre(String beneficiarioNombre) {
        this.beneficiarioNombre = beneficiarioNombre;
    }

    public String getBeneficiarioDomicilio() {
        return beneficiarioDomicilio;
    }

    public void setBeneficiarioDomicilio(String beneficiarioDomicilio) {
        this.beneficiarioDomicilio = beneficiarioDomicilio;
    }

    public String getBeneficiarioCiudad() {
        return beneficiarioCiudad;
    }

    public void setBeneficiarioCiudad(String beneficiarioCiudad) {
        this.beneficiarioCiudad = beneficiarioCiudad;
    }

    public String getBeneficiarioComuna() {
        return beneficiarioComuna;
    }

    public void setBeneficiarioComuna(String beneficiarioComuna) {
        this.beneficiarioComuna = beneficiarioComuna;
    }

    public String getBeneficiarioRut() {
        return beneficiarioRut;
    }

    public void setBeneficiarioRut(String beneficiarioRut) {
        this.beneficiarioRut = beneficiarioRut;
    }

    public String getNumeroLetra() {
        return numeroLetra;
    }

    public void setNumeroLetra(String numeroLetra) {
        this.numeroLetra = numeroLetra;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaGiro() {
        return fechaGiro;
    }

    public void setFechaGiro(String fechaGiro) {
        this.fechaGiro = fechaGiro;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMontoNumerico() {
        return montoNumerico;
    }

    public void setMontoNumerico(String montoNumerico) {
        this.montoNumerico = montoNumerico;
    }

    public String getMontoTexto() {
        return montoTexto;
    }

    public void setMontoTexto(String montoTexto) {
        this.montoTexto = montoTexto;
    }

    public String getCantidadLetras() {
        return cantidadLetras;
    }

    public void setCantidadLetras(String cantidadLetras) {
        this.cantidadLetras = cantidadLetras;
    }
}
