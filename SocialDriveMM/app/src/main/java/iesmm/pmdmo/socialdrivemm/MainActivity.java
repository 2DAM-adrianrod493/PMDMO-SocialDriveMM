package iesmm.pmdmo.socialdrivemm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import iesmm.pmdmo.socialdrivemm.dao.Conexion;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Llamada asincrónica para conectarse a la base de datos
//        new ConexionTask().execute();
    }

    // AsyncTask para conectar a la base de datos y obtener datos
//    private class ConexionTask extends AsyncTask<Void, Void, String> {
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            Connection conexion = Conexion.getConexion();
//            if (conexion != null) {
//                try {
//                    Statement statement = conexion.createStatement();
//                    String query = "SELECT * FROM usuarios";  // Reemplaza esto con la consulta que necesites
//                    ResultSet resultSet = statement.executeQuery(query);
//                    StringBuilder result = new StringBuilder();
//                    while (resultSet.next()) {
//                        result.append("Usuario: ").append(resultSet.getString("nombre")).append("\n");
//                    }
//                    return result.toString();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return "Error en la consulta: " + e.getMessage();
//                } finally {
//                    Conexion.cerrarConexion();
//                }
//            }
//            return "Error de conexión";
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            // Muestra el resultado o error en un Toast
//            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
//        }
//    }
}
