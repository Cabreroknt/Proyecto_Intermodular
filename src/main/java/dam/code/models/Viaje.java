package dam.code.models;

import java.time.LocalDate;

public class Viaje {
    private int idAsignacion;
    private int idBuque;
    private int idRuta;
    private LocalDate fechaSalida;

    public Viaje() {}

    public Viaje(int idAsignacion, int idBuque, int idRuta, LocalDate fechaSalida) {
        this.idAsignacion = idAsignacion;
        this.idBuque = idBuque;
        this.idRuta = idRuta;
        this.fechaSalida = fechaSalida;
    }

    // Getters y Setters
    public int getIdAsignacion() { return idAsignacion; }
    public void setIdAsignacion(int idAsignacion) { this.idAsignacion = idAsignacion; }
    public int getIdBuque() { return idBuque; }
    public void setIdBuque(int idBuque) { this.idBuque = idBuque; }
    public int getIdRuta() { return idRuta; }
    public void setIdRuta(int idRuta) { this.idRuta = idRuta; }
    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }
}