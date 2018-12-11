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


    private ArrayList<Loxica_Categoria> loxicaCategorias;
    private Context mContext;

    public Adaptador_Categorias(Context context, ArrayList<Loxica_Categoria> loxicaCategorias) {
        super(context, R.layout.layout_elemento_categoria);       // Enviamos o layout que imos utilizar

        this.loxicaCategorias = loxicaCategorias;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        //return super.getCount();
        if(loxicaCategorias !=null) {
            return loxicaCategorias.size();       // Poderíamos poñer tamén o número de imaxes.
        }
        return 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }

    private static class ViewHolder {

        TextView tvNombreCategoria;
        //ImageView ivImagenCategoria;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        Loxica_Categoria loxicaCategoria = loxicaCategorias.get(position);

        Adaptador_Categorias.ViewHolder viewFila = new Adaptador_Categorias.ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_elemento_categoria, parent, false);

            viewFila.tvNombreCategoria = (TextView) convertView.findViewById(R.id.tvNombre_Categoria);
           // viewFila.ivImagenCategoria= (ImageView) convertView.findViewById(R.id.tvImagen_GestionCategorias);

            convertView.setTag(viewFila);
        } else {
            viewFila = (Adaptador_Categorias.ViewHolder) convertView.getTag();
        }

        viewFila.tvNombreCategoria.setText(loxicaCategoria.getNombre());
        Loxica_Categoria c= loxicaCategoria;

        //Bitmap bitmap = BitmapFactory.decodeFile(loxicaCategoria.getImagen());
        //viewFila.ivImagenCategoria.setImageBitmap(bitmap);

        return convertView;
    }
}
