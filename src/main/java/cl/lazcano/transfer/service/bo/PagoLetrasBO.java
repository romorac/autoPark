package cl.lazcano.transfer.service.bo;

import java.util.ArrayList;
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
public class PagoLetrasBO {

    private List<DetalleLetraBO> lstDetalleLetra;

    public List<DetalleLetraBO> getLstDetalleLetra() {
        if (null == lstDetalleLetra) {
            lstDetalleLetra = new ArrayList<>();
        }
        return lstDetalleLetra;
    }
}
