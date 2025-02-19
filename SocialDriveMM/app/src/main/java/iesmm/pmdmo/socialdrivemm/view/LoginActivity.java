package iesmm.pmdmo.socialdrivemm.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import iesmm.pmdmo.socialdrivemm.R;
import iesmm.pmdmo.socialdrivemm.dao.DaoImplem;

public class LoginActivity extends AppCompatActivity {
    private DaoImplem daoImplem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        daoImplem = new DaoImplem();

        EditText username = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        Button iniciarSesion = findViewById(R.id.cirLoginButton);

        iniciarSesion.setOnClickListener(view -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (daoImplem.getAccess(user, pass)) {
                int userId = daoImplem.getUserId(user);
                String rol = daoImplem.getRolUsuario(user);

                if ("Admin".equalsIgnoreCase(rol)) {
                    // Rol administrador
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                } else {
                    // Rol usuario
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                }
            } else {
                Snackbar.make(view, "Usuario o contraseña incorrectos", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
