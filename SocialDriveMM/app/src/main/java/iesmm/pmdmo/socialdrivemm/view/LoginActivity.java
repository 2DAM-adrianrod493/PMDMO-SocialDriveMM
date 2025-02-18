package iesmm.pmdmo.socialdrivemm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iesmm.pmdmo.socialdrivemm.R;
import iesmm.pmdmo.socialdrivemm.dao.DaoImplem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    private DaoImplem daoImplem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        daoImplem = new DaoImplem(); // Se inicializa DAO

        EditText username = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        Button iniciarSesion = findViewById(R.id.cirLoginButton);

        iniciarSesion.setOnClickListener(view -> {
            if (daoImplem.getAccess(username.getText().toString(), password.getText().toString())) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Snackbar.make(view, "Usuario o contraseña incorrectos", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
