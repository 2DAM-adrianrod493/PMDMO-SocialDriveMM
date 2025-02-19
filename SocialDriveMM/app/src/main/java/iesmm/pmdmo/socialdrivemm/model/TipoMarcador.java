package iesmm.pmdmo.socialdrivemm.model;

public class TipoMarcador {
    private int id;
    private String nombre;

    // Constructor
    public TipoMarcador(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Este método se usa para que, al cargar el objeto en un Spinner, se muestre el nombre
    @Override
    public String toString() {
        return nombre;
    }
}
