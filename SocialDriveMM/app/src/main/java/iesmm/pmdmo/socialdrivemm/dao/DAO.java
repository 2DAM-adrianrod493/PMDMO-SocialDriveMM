package iesmm.pmdmo.socialdrivemm.dao;

import java.sql.Connection;
import java.util.List;

import iesmm.pmdmo.socialdrivemm.model.Marcador;
import iesmm.pmdmo.socialdrivemm.model.TipoMarcador;
import iesmm.pmdmo.socialdrivemm.model.Usuario;


public interface DAO {
    void connect();
    Connection getConnection();
    boolean getAccess(String username, String password);
    String getRolUsuario(String username);
    int getUserId(String username);
    List<TipoMarcador> getTipoMarcadores();
    List<Marcador> getMarcadoresUsuario(int userId);
    List<Marcador> getAllMarcadores();
    void insertarMarcador(int userId, double lat, double lon, String descripcion, int tipoMarcadorId);
    List<Usuario> getAllUsuarios();
    void deleteUsuario(int userId);
    void insertUsuario(String username, String password, String email);
}