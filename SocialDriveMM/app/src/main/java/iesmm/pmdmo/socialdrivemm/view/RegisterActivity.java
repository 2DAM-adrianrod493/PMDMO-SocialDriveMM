package iesmm.pmdmo.socialdrivemm.view;

import static android.widget.Toast.LENGTH_LONG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import iesmm.pmdmo.socialdrivemm.R;
import iesmm.pmdmo.socialdrivemm.dao.DaoImplem;

public class RegisterActivity extends AppCompatActivity {

    private DaoImplem daoImplem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        daoImplem = new DaoImplem();

        EditText usernameTxt = findViewById(R.id.editTextUsername);
        EditText passwdTxt = findViewById(R.id.editTextPasswd);
        EditText emailTxt = findViewById(R.id.editTextEmail);

        Button register = findViewById(R.id.cirRegisterButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTxt.getText().toString();
                String passwd = passwdTxt.getText().toString();
                String mail = emailTxt.getText().toString();
                daoImplem.insertUsuario(username, passwd, mail);
                Snackbar.make(v, "Bienvenido a SocialDrive", Snackbar.LENGTH_LONG).show();
                
            }
        });

    }

    @SuppressLint("ObsoleteSdkInt")
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}