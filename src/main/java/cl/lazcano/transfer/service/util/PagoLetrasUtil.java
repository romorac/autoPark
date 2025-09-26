package cl.lazcano.transfer.service.util;

import cl.lazcano.transfer.service.dto.ReporteDetallePagoLetrasDTO;
import cl.lazcano.transfer.service.dto.ReportePagoLetrasDTO;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;

@Controller
public class PagoLetrasUtil {

    private static final String JASPERFILE = "/jasper/LetraCambio.jrxml";

    public void createPdf(String nombreArchivoSalida, ReportePagoLetrasDTO datosPagoLetras) throws JRException {
        System.out.println("[createPdf]");
        // Fetching the .jrxml file from the resources folder.
        final InputStream stream = this.getClass().getResourceAsStream(JASPERFILE);
        // Compile the Jasper report from .jrxml to .japser
        final JasperReport report = JasperCompileManager.compileReport(stream);
        Collection<ReporteDetallePagoLetrasDTO> test = new ArrayList<ReporteDetallePagoLetrasDTO>();
        for (ReporteDetallePagoLetrasDTO it : datosPagoLetras.getLista()) {
            test.add(it);
        }
        // Fetching the employees from the data source.
        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(test);
        // Adding the additional parameters to the pdf.
        final Map<String, Object> parameters = new HashMap<>();
        // parameters.put("nombreVendedor", datosPagoLetras.getNombre());
        final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
        // Export the report to a PDF file.
        JasperExportManager.exportReportToPdfFile(print, nombreArchivoSalida);
    }
}
