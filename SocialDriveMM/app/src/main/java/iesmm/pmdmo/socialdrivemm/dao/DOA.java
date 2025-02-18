package iesmm.pmdmo.socialdrivemm.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public interface DOA {
    void insert(String sql);
    void update(String sql);
    void delete(String sql);
    HashMap<Integer, ArrayList<String>> obtenerMarcadoresTodoUsuarios(String sql);
    Boolean executeProcedimiento(String nombreProcedimiento);

    // Métodos agregados
    void connect(); // Metodo para establecer la conexión
    Connection getConnection(); // Obtener la conexión actual
    boolean getAccess(String username, String password); // Verificar acceso de usuario
}
