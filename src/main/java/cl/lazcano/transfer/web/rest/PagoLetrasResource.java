package cl.lazcano.transfer.web.rest;

import cl.lazcano.transfer.service.PagoLetrasService;
import cl.lazcano.transfer.service.dto.PagoLetrasDTO;
import cl.lazcano.transfer.service.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.lazcano.transfer.service.PagoLetrasService;
import cl.lazcano.transfer.service.dto.PagoLetrasDTO;
import cl.lazcano.transfer.service.vo.ResponseVO;

@RestController
@RequestMapping("/api")
public class PagoLetrasResource {

    private final PagoLetrasService pagoLetraService;
    private final Logger log = LoggerFactory.getLogger(PagoLetrasResource.class);

    public PagoLetrasResource(PagoLetrasService pagoLetraService) {
        this.pagoLetraService = pagoLetraService;
    }

    @PostMapping("/letras/generaPdf")
    public ResponseVO pagoLetrasPdf(@RequestBody PagoLetrasDTO pagoletrasDTO) {
        log.info("[pagoLetrasPdf][ini]");
        ResponseVO salida = new ResponseVO(
            HttpStatus.OK.value(),
            HttpStatus.OK.toString(),
            pagoLetraService.generarPagoLetrasPDF(pagoletrasDTO)
        );
        log.info("[pagoLetrasPdf][fin]");
        return salida;
    }
}
