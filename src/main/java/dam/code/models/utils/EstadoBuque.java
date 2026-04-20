package dam.code.models.utils;

public enum EstadoBuque {
    EN_PUERTO("En Puerto"),
    NAVEGANDO("Navegando"),
    FONDEADO("Fondeado"),
    EN_REPARACION("En Reparación"),
    DESARMADO("Fuera de Servicio");

    private final String mensaje;

    EstadoBuque(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}