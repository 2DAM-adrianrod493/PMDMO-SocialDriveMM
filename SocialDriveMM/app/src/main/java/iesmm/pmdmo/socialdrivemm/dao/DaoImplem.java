package iesmm.pmdmo.socialdrivemm.dao;

import java.sql.ResultSet;

public class DaoImplem implements DOA{
    @Override
    public void insert(String sql) {

    }

    @Override
    public void update(String sql) {

    }

    @Override
    public void delete(String sql) {

    }

    @Override
    public ResultSet select(String sql) {
        return null;
    }

    @Override
    public boolean getAccess(String useranme, String password, Conexion conn) {
        

        return false;
    }
}
