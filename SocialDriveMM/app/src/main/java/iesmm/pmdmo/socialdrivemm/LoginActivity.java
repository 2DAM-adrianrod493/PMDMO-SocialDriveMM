package iesmm.pmdmo.socialdrivemm;

import static android.graphics.ColorSpace.connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.concurrent.Executors;
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
                if (daoImplem.getAccess(username.getText().toString(), password.getText().toString(), conexion)) {
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




}
