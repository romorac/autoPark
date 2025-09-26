package cl.lazcano.transfer.domain.enumeration;

public enum VariableEnum {
    COMISION(1L),
    GASTOS(2L),
    DIRECCION(3L),
    NOTARIO(4L),
    V6_CREDITO_VALOR(5L),
    V6_VALOR(6L);

    private Long codigo;

    private VariableEnum(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigo() {
        return codigo;
    }
}
