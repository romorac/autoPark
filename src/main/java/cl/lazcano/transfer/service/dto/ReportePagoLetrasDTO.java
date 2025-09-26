package cl.lazcano.transfer.service.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportePagoLetrasDTO {

    private List<ReporteDetallePagoLetrasDTO> lista;

    public List<ReporteDetallePagoLetrasDTO> getLista() {
        if (lista == null) {
            lista = new ArrayList<>();
        }
        return lista;
    }
}
