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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import iesmm.pmdmo.socialdrivemm.R;
import iesmm.pmdmo.socialdrivemm.dao.DaoImplem;
import iesmm.pmdmo.socialdrivemm.model.Marcador;
import iesmm.pmdmo.socialdrivemm.model.TipoMarcador;

public class MapFragment extends Fragment {

    private GoogleMap map;
    private DaoImplem dao;
    private int userId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        dao = new DaoImplem();
        if (getActivity() != null) {
            userId = getActivity().getIntent().getIntExtra("userId", -1);
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this::initMap);
        }
    }

    private void initMap(GoogleMap googleMap) {
        map = googleMap;
        cargarMarcadores();
        map.setOnMapLongClickListener(this::handleMapLongClick);
    }

    // Carga los marcadores desde la BD y asignamos un icono según su tipo
    private void cargarMarcadores() {
        List<Marcador> todos = dao.getAllMarcadores();
        if (!todos.isEmpty()) {
            LatLng center = new LatLng(todos.get(0).getLatitud(), todos.get(0).getLongitud());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 8f));
        }
        for (Marcador m : todos) {
            int tipoId = m.getTipoMarcadorId();
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(m.getLatitud(), m.getLongitud()))
                    .title(m.getDescripcion())
                    .icon(BitmapDescriptorFactory.fromResource(getIconForTipoMarcador(tipoId))));
        }
    }

    // Manejamos el click largo en el mapa para añadir un nuevo marcador
    private void handleMapLongClick(LatLng latLng) {
        String direccion = getAddressFromLatLng(latLng.latitude, latLng.longitude);
        showAddMarkerDialog(latLng, direccion);
    }

    // Convierte las coordenadas en una dirección legible
    private String getAddressFromLatLng(double lat, double lng) {
        try {
            List<Address> addresses = new Geocoder(requireContext(), Locale.getDefault())
                    .getFromLocation(lat, lng, 1);
            return (addresses != null && !addresses.isEmpty())
                    ? addresses.get(0).getAddressLine(0)
                    : "No hay dirección";
        } catch (IOException e) {
            return "No hay dirección";
        }
    }

    // Mostramos el diálogo para agregar un marcador, con selección de tipo obtenida desde la BD
    private void showAddMarkerDialog(LatLng latLng, String direccion) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_marker, null);
        EditText etDescripcion = dialogView.findViewById(R.id.etDescripcion);
        Spinner spinnerTipo = dialogView.findViewById(R.id.spinnerTipo);

        etDescripcion.setText(direccion);

        // Se obtiene la lista de tipos desde la BD mediante DaoImplem
        List<TipoMarcador> listaTipo = dao.getTipoMarcadores();
        ArrayAdapter<TipoMarcador> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, listaTipo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        new AlertDialog.Builder(requireContext())
                .setTitle("Añadir Marcador")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String desc = etDescripcion.getText().toString().trim();
                    TipoMarcador tipoSeleccionado = (TipoMarcador) spinnerTipo.getSelectedItem();
                    int tipoMarcadorId = tipoSeleccionado.getId();
                    dao.insertarMarcador(userId, latLng.latitude, latLng.longitude, desc, tipoMarcadorId);
                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(desc)
                            .icon(BitmapDescriptorFactory.fromResource(getIconForTipoMarcador(tipoMarcadorId))));
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // Devuelve el recurso de imagen para cada tipo de marcador
    private int getIconForTipoMarcador(int tipoMarcadorId) {
        switch (tipoMarcadorId) {
            case 1:
                return R.drawable.speed_radar; // Radar Móvil
            case 2:
                return R.drawable.control_alcohol; // Control Alcoholemia
            case 3:
                return R.drawable.policia_multa; // Multa
            case 4:
                return R.drawable.accidente; // Accidente
            case 5:
                return R.drawable.atasco; // Atasco
            default:
                return R.drawable.icono_marcador;
        }
    }
}