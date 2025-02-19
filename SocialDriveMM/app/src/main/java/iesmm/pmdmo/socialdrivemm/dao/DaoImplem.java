package iesmm.pmdmo.socialdrivemm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import iesmm.pmdmo.socialdrivemm.model.Marcador;

public class DaoImplem implements DOA {
    private Conexion conexion;
    private Connection conn;

    public DaoImplem() {
        conexion = new Conexion();
        connect(); // Llamada para iniciar la conexión
    }

    @Override
    public void connect() {
        // Hacemos la conexión de forma síncrona
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
    }

    public Connection getConnection() {
        return conn;
    }

    // Verificar acceso de usuario
    public boolean getAccess(String username, String password) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Boolean> task = () -> {
            try {
                if (conn == null) {
                    connect();
                }
                // IMPORTANTE: la tabla se llama "Usuario" (mayúscula), no "usuario"
                String query = "SELECT username, password FROM Usuario WHERE username = ?";
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
            return futuro.get(); // Bloquea hasta obtener el resultado
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }
    }

    @Override
    public void insert(String sql) {
        // ...
    }

    @Override
    public void update(String sql) {
        // ...
    }

    @Override
    public void delete(String sql) {
        // ...
    }

    @Override
    public HashMap<Integer, ArrayList<String>> obtenerMarcadoresTodoUsuarios(String sql) {
        return null;
    }

    @Override
    public Boolean executeProcedimiento(String nombreProcedimiento) {
        return null;
    }

    // Obtener marcadores de un usuario
    public List<Marcador> getMarcadoresUsuario(int userId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<List<Marcador>> task = () -> {
            List<Marcador> lista = new ArrayList<>();
            if (conn == null) {
                connect();
            }

            String query = "SELECT id_marcador, latitud, longitud, descripcion, tipo_marcador_id, fecha_publicacion "
                    + "FROM Marcador "
                    + "WHERE usuario_id = ? "
                    + "ORDER BY fecha_publicacion DESC";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idMarcador = rs.getInt("id_marcador");
                double lat = rs.getDouble("latitud");
                double lon = rs.getDouble("longitud");
                String desc = rs.getString("descripcion");
                int tipo = rs.getInt("tipo_marcador_id");
                java.sql.Timestamp fecha = rs.getTimestamp("fecha_publicacion");

                Marcador m = new Marcador(idMarcador, lat, lon, desc, tipo, fecha);
                lista.add(m);
            }
            return lista;
        };

        Future<List<Marcador>> future = executor.submit(task);
        try {
            return future.get(); // Bloquea hasta que acabe la consulta
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            executor.shutdown();
        }
    }

    // Obtener id_usuario a partir del username
    public int getUserId(String username) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> task = () -> {
            if (conn == null) {
                connect();
            }
            // IMPORTANTE: "Usuario" con mayúscula
            String query = "SELECT id_usuario FROM Usuario WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_usuario");
            }
            return -1;
        };

        Future<Integer> futuro = executor.submit(task);
        try {
            return futuro.get();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            executor.shutdown();
        }
    }
}
