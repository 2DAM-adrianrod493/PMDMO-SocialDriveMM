package iesmm.pmdmo.socialdrivemm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import iesmm.pmdmo.socialdrivemm.model.Marcador;
import iesmm.pmdmo.socialdrivemm.model.Rol;
import iesmm.pmdmo.socialdrivemm.model.Usuario;

public class DaoImplem implements DOA {
    private Conexion conexion;
    private Connection conn;

    public DaoImplem() {
        conexion = new Conexion();
        connect();
    }

    @Override
    public void connect() {
        try {
            conn = conexion.CONN();
            if (conn == null) {
                System.out.println("ERROR de Conexión con la Base de Datos");
            } else {
                System.out.println("Conectado con Éxito a la Base de Datos");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() {
        return conn;
    }

    // Validamos Credenciales
    @Override
    public boolean getAccess(String username, String password) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Boolean> task = () -> {
            try {
                if (conn == null) {
                    connect();
                }
                String query = "SELECT username, password FROM Usuario WHERE username = ?";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, username);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("username");
                    String passwd = rs.getString("password");
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
        } finally {
            executor.shutdown();
        }
    }

    // Obtenemos el rol con la función rolnombre
    public String getRolUsuario(String username) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> task = () -> {
            if (conn == null) {
                connect();
            }
            String query = "SELECT rolnombre(?) AS rol";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("rol");
            }
            return null;
        };

        Future<String> futuro = executor.submit(task);
        try {
            return futuro.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            executor.shutdown();
        }
    }

    // Obtenemos el id_usuario dado el username
    public int getUserId(String username) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> task = () -> {
            if (conn == null) {
                connect();
            }
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

    // Obtenemos los marcadores de un usuario
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
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            executor.shutdown();
        }
    }

    // Obtenemos todos los marcadores
    public List<Marcador> getAllMarcadores() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<List<Marcador>> task = () -> {
            List<Marcador> lista = new ArrayList<>();
            if (conn == null) {
                connect();
            }
            String query = "SELECT id_marcador, latitud, longitud, descripcion, tipo_marcador_id, fecha_publicacion "
                    + "FROM Marcador "
                    + "ORDER BY fecha_publicacion DESC";
            PreparedStatement ps = conn.prepareStatement(query);
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
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            executor.shutdown();
        }
    }

    // Agregar nuevo marcador
    public void insertarMarcador(int userId, double lat, double lon, String descripcion, int tipoMarcadorId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                if (conn == null) {
                    connect();
                }
                String sql = "INSERT INTO Marcador (usuario_id, latitud, longitud, descripcion, tipo_marcador_id) "
                        + "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setInt(1, userId);
                st.setDouble(2, lat);
                st.setDouble(3, lon);
                st.setString(4, descripcion);
                st.setInt(5, tipoMarcadorId);
                st.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Obtenemos todos los usuarios (para Admin)
    public List<Usuario> getAllUsuarios() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Usuario>> task = () -> {
            if (conn == null) {
                connect();
            }
            String query = "SELECT id_usuario, username, password, email, rol_id FROM Usuario";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<Usuario> lista = new ArrayList<>();
            while (rs.next()) {
                int idU = rs.getInt("id_usuario");
                String name = rs.getString("username");
                String pass = rs.getString("password");
                String email = rs.getString("email");
                int rolId = rs.getInt("rol_id");

                Rol rol = (rolId == 1) ? Rol.Administrador : Rol.Usuario;
                Usuario u = new Usuario(idU, name, pass, email, rol);
                lista.add(u);
            }
            return lista;
        };

        Future<List<Usuario>> futuro = executor.submit(task);
        try {
            return futuro.get();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            executor.shutdown();
        }
    }

    // Borramos usuario y sus marcadores
    public void deleteUsuario(int userId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                if (conn == null) {
                    connect();
                }
                // Borrar primero sus marcadores
                PreparedStatement st1 = conn.prepareStatement("DELETE FROM Marcador WHERE usuario_id=?");
                st1.setInt(1, userId);
                st1.executeUpdate();

                // Borrar el usuario
                PreparedStatement st2 = conn.prepareStatement("DELETE FROM Usuario WHERE id_usuario=?");
                st2.setInt(1, userId);
                st2.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void insert(String sql) { }
    @Override
    public void update(String sql) { }
    @Override
    public void delete(String sql) { }
    @Override
    public HashMap<Integer, ArrayList<String>> obtenerMarcadoresTodoUsuarios(String sql) {
        return null;
    }
    @Override
    public Boolean executeProcedimiento(String nombreProcedimiento) {
        return null;
    }
}
