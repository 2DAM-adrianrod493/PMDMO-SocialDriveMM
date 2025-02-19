package iesmm.pmdmo.socialdrivemm.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public interface DOA {
    void insert(String sql);
    void update(String sql);
    void delete(String sql);
    HashMap<Integer, ArrayList<String>> obtenerMarcadoresTodoUsuarios(String sql);
    Boolean executeProcedimiento(String nombreProcedimiento);

    // Métodos
    void connect();
    Connection getConnection();
    boolean getAccess(String username, String password);
}
