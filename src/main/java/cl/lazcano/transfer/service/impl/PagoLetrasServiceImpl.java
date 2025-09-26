package cl.lazcano.transfer.service.impl;

import cl.lazcano.transfer.service.PagoLetrasService;
import cl.lazcano.transfer.service.dto.DetalleLetraDTO;
import cl.lazcano.transfer.service.dto.PagoLetrasDTO;
import cl.lazcano.transfer.service.dto.ReporteDetallePagoLetrasDTO;
import cl.lazcano.transfer.service.dto.ReportePagoLetrasDTO;
import cl.lazcano.transfer.service.util.NumeroLetra;
import cl.lazcano.transfer.service.util.PagoLetrasUtil;
import cl.lazcano.transfer.service.vo.FilePdfResponseVO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PagoLetrasServiceImpl implements PagoLetrasService {

    private final Logger log = LoggerFactory.getLogger(PagoLetrasServiceImpl.class);

    @Autowired
    private PagoLetrasUtil srvPagoLetras;

    @Autowired
    private NumeroLetra numToLetra;

    public PagoLetrasServiceImpl(PagoLetrasUtil srvPagoLetras, NumeroLetra numToLetra) {
        this.srvPagoLetras = srvPagoLetras;
        this.numToLetra = numToLetra;
    }

    private ReportePagoLetrasDTO getDataPagoLetras(PagoLetrasDTO pagoletrasDTO) {
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        ReportePagoLetrasDTO salida = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        if (!pagoletrasDTO.getLstDetalleLetra().isEmpty()) {
            salida = new ReportePagoLetrasDTO();
            for (DetalleLetraDTO it : pagoletrasDTO.getLstDetalleLetra()) {
                ReporteDetallePagoLetrasDTO ca = new ReporteDetallePagoLetrasDTO(
                    pagoletrasDTO.getNombrePagador(),
                    pagoletrasDTO.getCiudadGiro(),
                    pagoletrasDTO.getNombreBeneficiario(),
                    pagoletrasDTO.getDomicilioBeneficiario(),
                    pagoletrasDTO.getCiudadBeneficiario(),
                    pagoletrasDTO.getComunaBeneficiario(),
                    pagoletrasDTO.getRutBeneficiario(),
                    it.getNumeroLetra(),
                    formatDate(it.getFechaVencimiento()),
                    formatDate(pagoletrasDTO.getFechaGiro()),
                    formatDate(it.getFechaVencimiento()),
                    formatea.format(Integer.parseInt(it.getMonto())).toString(),
                    numToLetra.Convertir(it.getMonto(), true),
                    pagoletrasDTO.getLstDetalleLetra().size() + ".-"
                );
                salida.getLista().add(ca);
            }
        }

        return salida;
    }

    /**
     * @since 06-09-2021
     * @author rmora
     * @param request <?>
     * @return <?> response <b>historial de cambios</b>
     *         <ul>
     *         <li>{0.0.0}[06-09-2021][rmora] inicial</li>
     *         </ul>
     **/
    private static String encodeFileToBase64(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new IllegalStateException("could not read file " + file, e);
        }
    }

    /**
     * @since 21-01-2021
     * @author rmora
     * @param request <?>
     * @return <?> response
     *         <ul>
     *         <li>[rmora]-{0.0.0} inicial</li>
     *         </ul>
     **/
    private void removePdf(File file) {
        if (file.delete()) log.info("El fichero ha sido borrado satisfactoriamente"); else log.info("El fichero no puede ser borrado");
    }

    @Override
    public Object generarPagoLetrasPDF(PagoLetrasDTO pagoletrasDTO) {
        log.info("[generarPagoLetrasPDF][ini]");
        FilePdfResponseVO salida = new FilePdfResponseVO();

        String nombreArchivoSalida = "PagoLetrasPDF_" + new java.util.Date().getTime() + ".pdf";
        File file = new File(nombreArchivoSalida);
        ReportePagoLetrasDTO datosPagoLetras = getDataPagoLetras(pagoletrasDTO);
        log.info("datos letra" + datosPagoLetras);

        try {
            srvPagoLetras.createPdf(nombreArchivoSalida, datosPagoLetras);
        } catch (JRException e) {
            log.error(e.getMessage());
        }
        salida.setBase64(encodeFileToBase64(file));
        salida.setNombreArchivo(nombreArchivoSalida);
        removePdf(file);
        log.info("[generarPagoLetrasPDF][fin]");
        return salida;
    }

    public static String formatDate(String inputDate) {
        try {
            // Definir el formato de entrada
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            // Definir el formato de salida
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

            if (inputDate.matches("\\d*")) {
                return outputFormat.format(new Date(Long.parseLong(inputDate)));
            } else {
                // Parsear la fecha de entrada
                Date date = inputFormat.parse(inputDate);
                // Formatear la fecha a la salida deseada
                return outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
