package iesmm.pmdmo.socialdrivemm.dao;

import java.sql.ResultSet;

public interface DOA {
    public void insert(String sql);
    public void update(String sql);
    public void delete(String sql);
    public ResultSet select(String sql);
    public boolean getAccess(String useranme, String password, Conexion conn);

}
