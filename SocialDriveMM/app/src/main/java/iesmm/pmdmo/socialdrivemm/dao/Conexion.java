package iesmm.pmdmo.socialdrivemm.dao;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    protected static String db = "socialdrivers";
    protected static String ip = "192.168.1.23";
    protected static String port = "3306";
    protected static String username = "social";
    protected static String password = "P@blo1234";

    public Connection CONN() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + db;
            conn = DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return conn;
    }
}
