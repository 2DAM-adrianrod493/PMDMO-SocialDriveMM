package iesmm.pmdmo.socialdrivemm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Datos de la conexión
    private static final String URL = "jdbc:mysql://localhost:3306/socialdrivemm";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    // Método Obtener Conexión
    public static Connection getConexion() {
        if (connection != null) {
            return connection;
        }

        try {
            // Cargar Driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer Conexión
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión Exitosa");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al Cargar los Drivers de MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de Conexión con la BD: " + e.getMessage());
        }

        return connection;
    }

    // Método Cerrar Coneoxión
    public static void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión Terminada");
            } catch (SQLException e) {
                System.out.println("Error al Cerrar la BD: " + e.getMessage());
            }
        }
    }
}
