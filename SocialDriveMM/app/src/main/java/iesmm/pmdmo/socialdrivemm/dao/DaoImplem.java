package iesmm.pmdmo.socialdrivemm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DaoImplem implements DOA {
    private Conexion conexion;
    private Connection conn;

    public DaoImplem() {
        conexion = new Conexion();
        connect();
    }

    @Override
    public void connect() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                conn = conexion.CONN();
                if (conn == null) {
                    System.out.println("Error en la conexión a la base de datos");
                } else {
                    System.out.println("Conectado a la base de datos");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Connection getConnection() {
        return conn;
    }

    public boolean getAccess(String username, String password) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Boolean> task = () -> {
            try {
                if (conn == null) {
                    connect(); // Si no hay conexión, intenta conectar
                }
                String query = "SELECT username, password FROM usuario WHERE username = ?";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String name = resultSet.getString("username");
                    String passwd = resultSet.getString("password");
                    return username.equals(name) && password.equals(passwd);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return false;
        };

        Future<Boolean> futuro = executor.submit(task);
        try {
            return futuro.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(String sql) {
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {

        }


    }

    @Override
    public void update(String sql) {
    }

    @Override
    public void delete(String sql) {
    }

    @Override
    public HashMap<Integer, ArrayList<String>> obtenerMarcadoresTodoUsuarios(String sql) {
        return null;
    }

    @Override
    public Boolean executeProcedimiento(String nombreProcedimiento) {
        return null;
    }


}
