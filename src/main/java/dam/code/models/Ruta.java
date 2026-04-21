package dam.code.models;

public class Ruta {
    private int idRuta;
    private String origen;
    private String destino;
    private int distanciaKm;

    public Ruta() {}

    public Ruta(int idRuta, String origen, String destino, int distanciaKm) {
        this.idRuta = idRuta;
        this.origen = origen;
        this.destino = destino;
        this.distanciaKm = distanciaKm;
    }

    // Getters y Setters
    public int getIdRuta() { return idRuta; }
    public void setIdRuta(int idRuta) { this.idRuta = idRuta; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public int getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(int distanciaKm) { this.distanciaKm = distanciaKm; }

    @Override
    public String toString() {
        return origen + " -> " + destino + " (" + distanciaKm + " km)";
    }
}