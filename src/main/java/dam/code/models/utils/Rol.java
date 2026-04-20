package dam.code.models.utils;

public enum Rol {
    CAPITAN("Capitán"),
    OFICIAL_PUENTE("Oficial de Puente"),
    CONTRAMAESTRE("Contramaestre"),
    MARINERO("Marinero"),
    JEFE_MAQUINAS("Jefe de Máquinas"),
    COCINERO("Cocinero"),
    ADMINISTRADOR("Admin de Puerto"); // Rol especial para AdminView

    private final String descripcion;

    Rol(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}