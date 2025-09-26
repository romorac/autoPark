package cl.lazcano.transfer.domain.enumeration;

/**
 * The TipoVehiculoEnum enumeration.
 */
public enum TipoVehiculoEnum {
    AUTOMOVIL("AUTOMOVIL"),
    TODOTERRENO("TODOTERRENO"),
    FURGON("FURGON"),
    CAMIONETA("CAMIONETA"),
    SUV("SUV"),
    SEDAN("SEDAN"),
    CITYCAR("CITYCAR"),
    MOTOCICLETA("MOTOCICLETA"),
    SCOOTER("SCOOTER"),
    OTRO("OTRO"),
    STATION_WAGON("STATION WAGON"),
    MOTO("MOTO"),
    CAMION("CAMION"),
    MINIBUS("MINIBUS"),
    JEEP("JEEP"),
    AMBULANCIA("AMBULANCIA"),
    TRANCTOCAMION("TRANCTOCAMION"),
    CUATRIMOTO("CUATRIMOTO"),
    CHASIS_CABINA("CHASIS CABINA"),
    CHASIS_CABINADO("CHASIS CABINADO"),
    SEMIREMOLQUE("SEMIREMOLQUE"),
    BUS("BUS"),
    TRACTOR("TRACTOR"),
    MAQUINARIAINDUSTRIAL("MAQUINA INDUSTRIAL");

    private String desc;

    private TipoVehiculoEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static TipoVehiculoEnum fromNombre(String det) {
        for (TipoVehiculoEnum b : TipoVehiculoEnum.values()) {
            if (b.desc.equals(det)) {
                return b;
            }
        }
        return null;
    }
}
