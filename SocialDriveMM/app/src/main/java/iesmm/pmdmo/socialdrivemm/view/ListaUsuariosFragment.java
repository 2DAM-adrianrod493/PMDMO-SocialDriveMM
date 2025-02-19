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
import iesmm.pmdmo.socialdrivemm.model.Usuario;

public class ListaUsuariosFragment extends Fragment {

    private ListView listViewUsuarios;
    private DaoImplem dao;

    public ListaUsuariosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_usuarios, container, false);

        listViewUsuarios = view.findViewById(R.id.listViewUsuarios);
        dao = new DaoImplem();

        // Obtenemos la lista de todos los usuarios
        List<Usuario> lista = dao.getAllUsuarios();

        UsuarioAdapter adapter = new UsuarioAdapter(requireContext(), lista, dao);
        listViewUsuarios.setAdapter(adapter);

        return view;
    }
}
