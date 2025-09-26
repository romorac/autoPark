package cl.lazcano.transfer.service.bo;

import cl.lazcano.transfer.service.dto.DetalleLetraDTO;
import cl.lazcano.transfer.service.dto.PagoLetrasDTO;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonPOJOBuilder
public class DetalleLetraBO {

    private String numeroLetra;
    private String fechaVencimiento;
    private String monto;
    private String montoTexto;
}
