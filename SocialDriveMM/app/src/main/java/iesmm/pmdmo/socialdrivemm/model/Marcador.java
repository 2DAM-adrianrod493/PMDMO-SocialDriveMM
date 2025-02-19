package iesmm.pmdmo.socialdrivemm.model;

import java.sql.Timestamp;

public class Marcador {
    private int idMarcador;
    private double latitud;
    private double longitud;
    private String descripcion;
    private int tipoMarcadorId;
    private Timestamp fechaPublicacion;

    public Marcador(int idMarcador, double latitud, double longitud,
                    String descripcion, int tipoMarcadorId, Timestamp fechaPublicacion) {
        this.idMarcador = idMarcador;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.tipoMarcadorId = tipoMarcadorId;
        this.fechaPublicacion = fechaPublicacion;
    }

    // Getters y setters
    public int getIdMarcador() { return idMarcador; }
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
    public String getDescripcion() { return descripcion; }
    public int getTipoMarcadorId() { return tipoMarcadorId; }
    public Timestamp getFechaPublicacion() { return fechaPublicacion; }
}
