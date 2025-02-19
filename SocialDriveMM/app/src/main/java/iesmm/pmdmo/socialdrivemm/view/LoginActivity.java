package iesmm.pmdmo.socialdrivemm.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView logIn = findViewById(R.id.loginTxtView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView imgmas = findViewById(R.id.imgRegisterbtn);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            }
        });

        imgmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            }
        });

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
