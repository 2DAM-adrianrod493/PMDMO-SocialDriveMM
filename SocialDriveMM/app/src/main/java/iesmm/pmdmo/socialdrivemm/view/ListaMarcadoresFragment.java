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
    private DaoImplem dao;

    public ListaMarcadoresFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_marcadores, container, false);

        listViewMarcadores = view.findViewById(R.id.listViewMarcadores);
        dao = new DaoImplem();

        // id usuario
        int userId = 0;
        if (getArguments() != null) {
            userId = getArguments().getInt("userId", 0);
        }

        // Marcadores del usuario
        List<Marcador> marcadoresUsuario = dao.getMarcadoresUsuario(userId);

        MarcadorAdapter adapter = new MarcadorAdapter(requireContext(), marcadoresUsuario);
        listViewMarcadores.setAdapter(adapter);

        return view;
    }
}
