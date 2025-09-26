package cl.lazcano.transfer.service;

import cl.lazcano.transfer.service.dto.PagoLetrasDTO;

public interface PagoLetrasService {
    Object generarPagoLetrasPDF(PagoLetrasDTO pagoletrasDTO);
}
