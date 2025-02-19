package iesmm.pmdmo.socialdrivemm.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

import iesmm.pmdmo.socialdrivemm.R;
import iesmm.pmdmo.socialdrivemm.dao.DaoImplem;
import iesmm.pmdmo.socialdrivemm.model.Marcador;

public class ListaMarcadoresFragment extends Fragment {

    private ListView listViewMarcadores;
    private DaoImplem dao;  // Clase de acceso a datos

    public ListaMarcadoresFragment() {
        // Constructor público vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflar el layout del fragment
        View view = inflater.inflate(R.layout.fragment_lista_marcadores, container, false);

        // Referencia al ListView
        listViewMarcadores = view.findViewById(R.id.listViewMarcadores);

        // Inicializar el DAO
        dao = new DaoImplem();

        // 1) Recuperar el userId del Bundle
        int userId = 0;
        if (getArguments() != null) {
            userId = getArguments().getInt("userId", 0);
        }

        // 2) Obtener la lista de marcadores de ese usuario
        List<Marcador> marcadoresUsuario = dao.getMarcadoresUsuario(userId);

        // 3) Crear el adaptador y asignarlo al ListView
        MarcadorAdapter adapter = new MarcadorAdapter(requireContext(), marcadoresUsuario);
        listViewMarcadores.setAdapter(adapter);

        return view;
    }

}
