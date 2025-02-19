package iesmm.pmdmo.socialdrivemm.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import iesmm.pmdmo.socialdrivemm.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    // Guardaremos aquí el ID del usuario que llega desde el LoginActivity
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1) Recogemos el userId que nos ha llegado desde LoginActivity
        userId = getIntent().getIntExtra("userId", -1);

        // Vincular el Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Vincular el DrawerLayout
        drawerLayout = findViewById(R.id.main);

        // Vincular y configurar el NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Botón hamburguesa
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_nav,
                R.string.close_nav
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Mostrar por defecto el MapFragment si no hay estado previo
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MapFragment())
                    .commit();
            // Marcamos el ítem del mapa como seleccionado
            navigationView.setCheckedItem(R.id.nav_map);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Según la opción seleccionada en el menú lateral
        if (item.getItemId() == R.id.nav_map) {
            // Cargar el mapa
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MapFragment())
                    .commit();

        } else if (item.getItemId() == R.id.nav_lista) {
            // Cargar la lista de marcadores, pasándole el userId al fragment
            ListaMarcadoresFragment fragment = new ListaMarcadoresFragment();
            Bundle args = new Bundle();
            args.putInt("userId", userId);
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        } else if (item.getItemId() == R.id.nav_logout) {
            // Lógica para cerrar sesión o volver al Login
            Toast.makeText(this, "Saliendo...", Toast.LENGTH_SHORT).show();
            // Por ejemplo, podrías hacer: finish();
        }

        // Cerrar el menú tras la selección
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        // Si el menú está abierto, ciérralo antes de salir
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
