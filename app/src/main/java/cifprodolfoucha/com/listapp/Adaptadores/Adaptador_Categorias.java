package cifprodolfoucha.com.listapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Loxica.Loxica_Categoria;
import cifprodolfoucha.com.listapp.R;




public class Adaptador_Categorias extends ArrayAdapter {


    private ArrayList<Loxica_Categoria> categorias;
    private Context mContext;

    public Adaptador_Categorias(Context context, ArrayList<Loxica_Categoria> categorias) {
        super(context, R.layout.layout_elemento_categoria);

        this.categorias = categorias;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if(categorias !=null) {
            return categorias.size();
        }
        return 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }

    private static class ViewHolder {
        TextView tvNombreCategoria;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        Loxica_Categoria categoria = categorias.get(position);

        Adaptador_Categorias.ViewHolder viewFila = new Adaptador_Categorias.ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_elemento_categoria, parent, false);

            viewFila.tvNombreCategoria = (TextView) convertView.findViewById(R.id.tvNombre_Categoria);

            convertView.setTag(viewFila);
        } else {
            viewFila = (Adaptador_Categorias.ViewHolder) convertView.getTag();
        }

        viewFila.tvNombreCategoria.setText(categoria.getNombre());
        Loxica_Categoria c= categoria;

        return convertView;
    }
}
