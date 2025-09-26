package cl.lazcano.transfer.service.vo;

import java.io.Serializable;

/**
 * <h1>ResponseVO.java</h1><br>
 *
 * @author     rmora
 * @param  <T>
 * @param  <T>
 * @since      29-12-2020</br>
 *             <b>historial de cambios</b>
 *             <ul>
 *             <li>[rmora]inicial</li>
 *             </ul>
 **/

public class ResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer status;
    private String message;
    private Object result;

    public ResponseVO(Integer status, String message, Object result) {
        super();
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public ResponseVO(Integer status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResponseVO [status=");
        builder.append(status);
        builder.append(", message=");
        builder.append(message);
        builder.append(", result=");
        builder.append(result);
        builder.append("]");
        return builder.toString();
    }
}
