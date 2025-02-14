package iesmm.pmdmo.socialdrivemm.view;

import static android.graphics.ColorSpace.connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iesmm.pmdmo.socialdrivemm.R;
import iesmm.pmdmo.socialdrivemm.dao.Conexion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import iesmm.pmdmo.socialdrivemm.dao.DaoImplem;

public class LoginActivity extends AppCompatActivity {
    Conexion conexion;
    Connection conn;
    ResultSet rs;
    String name, str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        conexion = new Conexion();
        connect();
        EditText username = (EditText) findViewById(R.id.editTextEmail);
        EditText password = (EditText) findViewById(R.id.editTextPassword);
        Button iniciarSesion = (Button) findViewById(R.id.cirLoginButton);
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaoImplem daoImplem = new DaoImplem();
                if (getAccess(username.getText().toString(), password.getText().toString(), conexion)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(view, "Usuario o contraseña incorrectos", Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

    public void connect() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                conn = conexion.CONN();
                if (conn == null) {
                    str = "Error in connecting to database";
                } else {
                    str = "Connected to database";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            runOnUiThread(() -> {
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });
        });
    }


    public boolean getAccess(String username, String password, Conexion conn) {
        boolean salida=false;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Boolean> task = () -> {
            try {
                Connection conexion = conn.CONN();

                String query = "SELECT username, password FROM usuario WHERE username = ?";
                PreparedStatement statement = conexion.prepareStatement(query);
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                //Verficacion de existencia de usuario
                if(resultSet.next()){
                    String name = resultSet.getString("username");
                    String passwd = resultSet.getString("password");

                    if(username.equals(name) && password.equals(passwd)){
                        return true;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return false;
        };

        Future<Boolean> futuro = executor.submit(task);
        try{
            boolean acceso = futuro.get();
            if(!acceso){
                runOnUiThread(() -> {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                });
            }
            return acceso;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




}
