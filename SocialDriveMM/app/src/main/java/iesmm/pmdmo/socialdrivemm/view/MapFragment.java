package iesmm.pmdmo.socialdrivemm.view;

import android.app.AlertDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import iesmm.pmdmo.socialdrivemm.R;
import iesmm.pmdmo.socialdrivemm.dao.DaoImplem;
import iesmm.pmdmo.socialdrivemm.model.Marcador;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    private GoogleMap map;
    private DaoImplem dao;
    private int userId = -1;

    private OnMapReadyCallback callback = googleMap -> {
        map = googleMap;

        // Cargamos los marcadores de la base de datos
        List<Marcador> todos = dao.getAllMarcadores();
        for (Marcador m : todos) {
            LatLng pos = new LatLng(m.getLatitud(), m.getLongitud());
            map.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(m.getDescripcion()));
        }

        // Centramos la pantalla en el primer marcador
        if (!todos.isEmpty()) {
            Marcador primero = todos.get(0);
            LatLng center = new LatLng(primero.getLatitud(), primero.getLongitud());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 8f));
        }

        // Escuchamos clicks para añadir un nuevo marcador
        map.setOnMapLongClickListener(latLng -> {
            // obtener dirección
            String direccion = getAddressFromLatLng(latLng.latitude, latLng.longitude);

            // MostraMOS ventana para añadir los datos
            showAddMarkerDialog(latLng, direccion);
        });
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        dao = new DaoImplem();

        if (getActivity() != null && getActivity().getIntent() != null) {
            userId = getActivity().getIntent().getIntExtra("userId", -1);
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private String getAddressFromLatLng(double lat, double lng) {
        // obtenemos la dirección
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (!addresses.isEmpty()) {
                return addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "No hay dirección";
    }

    private void showAddMarkerDialog(LatLng latLng, String direccion) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_marker, null);
        EditText etDescripcion = dialogView.findViewById(R.id.etDescripcion);
        Spinner spinnerTipo = dialogView.findViewById(R.id.spinnerTipo);

        etDescripcion.setText(direccion);

        new AlertDialog.Builder(requireContext())
                .setTitle("Añadir Marcador")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String desc = etDescripcion.getText().toString().trim();
                    int tipoMarcadorId = 1;

                    // Insertamos en la base de datos
                    dao.insertarMarcador(userId, latLng.latitude, latLng.longitude, desc, tipoMarcadorId);

                    // Añadimos la chincheta al mapa
                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(desc));
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
