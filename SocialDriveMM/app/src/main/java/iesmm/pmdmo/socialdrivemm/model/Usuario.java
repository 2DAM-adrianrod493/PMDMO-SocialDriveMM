package iesmm.pmdmo.socialdrivemm.model;

public class Usuario {
    private int id;
    private String username;
    private String password;
    private String email;
    private Rol rol;

    public Usuario(int id, String username, String password, String email, Rol rol) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public Rol getRol() { return rol; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setRol(Rol rol) { this.rol = rol; }
}
