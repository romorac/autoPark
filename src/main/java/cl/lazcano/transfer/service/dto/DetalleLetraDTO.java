package cl.lazcano.transfer.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetalleLetraDTO {

    private String numeroLetra;
    private String fechaVencimiento;
    private String monto;
    private String montoTexto;

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

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getMontoTexto() {
        return montoTexto;
    }

    public void setMontoTexto(String montoTexto) {
        this.montoTexto = montoTexto;
    }
}
