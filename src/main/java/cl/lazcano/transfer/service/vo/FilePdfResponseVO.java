package cl.lazcano.transfer.service.vo;

import java.io.Serializable;

/**
 * <h1>FilePdfResponseVO.java</h1><br>
 *
 * @author rmora
 * @since  28-01-2021</br>
 *         <b>historial de cambios</b>
 *         <ul>
 *         <li>[rmora]inicial</li>
 *         </ul>
 **/

public class FilePdfResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String base64;
    private String nombreArchivo;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FilePdfResponseVO [base64=");
        builder.append(base64);
        builder.append(", nombreArchivo=");
        builder.append(nombreArchivo);
        builder.append("]");
        return builder.toString();
    }
}
