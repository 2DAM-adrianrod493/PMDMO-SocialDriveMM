package iesmm.pmdmo.socialdrivemm.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import iesmm.pmdmo.socialdrivemm.model.Marcador;
import iesmm.pmdmo.socialdrivemm.R;

public class MarcadorAdapter extends ArrayAdapter<Marcador> {

    public MarcadorAdapter(@NonNull Context context, @NonNull List<Marcador> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_marcador, parent, false);
        }

        Marcador marcador = getItem(position);

        TextView tvDescripcion = convertView.findViewById(R.id.tvDescripcion);
        TextView tvFecha = convertView.findViewById(R.id.tvFecha);

        if (marcador != null) {
            tvDescripcion.setText(marcador.getDescripcion());
            // Si quieres mostrar solo fecha, formatea el Timestamp a tu gusto
            tvFecha.setText(marcador.getFechaPublicacion().toString());
        }

        return convertView;
    }
}
