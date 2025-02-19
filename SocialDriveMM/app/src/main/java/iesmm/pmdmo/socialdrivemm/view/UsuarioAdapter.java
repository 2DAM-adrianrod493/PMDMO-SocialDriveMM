package iesmm.pmdmo.socialdrivemm.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import iesmm.pmdmo.socialdrivemm.R;
import iesmm.pmdmo.socialdrivemm.dao.DaoImplem;
import iesmm.pmdmo.socialdrivemm.model.Usuario;

public class UsuarioAdapter extends BaseAdapter {
    private Context context;
    private List<Usuario> usuarios;
    private DaoImplem dao;

    public UsuarioAdapter(Context context, List<Usuario> usuarios, DaoImplem dao) {
        this.context = context;
        this.usuarios = usuarios;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return usuarios.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_usuario, parent, false);
        }

        TextView tvUsername = convertView.findViewById(R.id.tvUsername);
        Button btnDelete = convertView.findViewById(R.id.btnDeleteUser);

        Usuario u = usuarios.get(position);
        tvUsername.setText(u.getUsername());

        btnDelete.setOnClickListener(v -> {
            dao.deleteUsuario(u.getId());
            usuarios.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }
}
