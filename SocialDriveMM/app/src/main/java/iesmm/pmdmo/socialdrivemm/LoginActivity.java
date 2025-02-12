package iesmm.pmdmo.socialdrivemm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import iesmm.pmdmo.socialdrivemm.dao.Conexion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;

public class LoginActivity extends AppCompatActivity {
    Conexion conexion;
    Connection conn;
    ResultSet rs;
    String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
//        Button boton = findViewById(R.id.loguear);
//        boton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //accion al realizar click
//                String usuario = ((EditText) findViewById(R.id.inputUser)) ;
//                String contrasena = ((EditText) findViewById(R.id.inputPassword));
//                if(getAcces(usuario,contrasena)){
//                    Snackbar.make(v,"Bienvenido",Snackbar.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }else{
//                    Snackbar.make(v,"Usuario o contraseña incorrectos",Snackbar.LENGTH_SHORT).show();
//                }
//            }
//
//
//        });


    }
//    public void connect(){
//        ExecutorService
//    }

    private boolean getAcces(String usuario, String contrasenia) {
        boolean encontrado = false;

        try {
            // Abrir fichero de res/raw
            // InputStream is = getResources().openRawResource(R.raw.users);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linea;

            // Read each line of the CSV file
            while ((linea = br.readLine()) != null && !encontrado) {
                String[] partes = linea.split(":");
                String user = partes[0]; //coger username de fichero
                String password = partes[1]; //coger password de fichero
                String email = partes[2]; //coger email de fichero
                //Comprobar si el usuario introduce su usuario o correo con lo que da acceso con los dos
                if ((usuario.equals(user)) && (contrasenia.equals(password))) {

                    encontrado = true;
                }
            }
            br.close(); // Close the BufferedReader
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encontrado;
    }
}
