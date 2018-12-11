package cifprodolfoucha.com.listapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;
import cifprodolfoucha.com.listapp.R;

public class Adapatador_Lista extends ArrayAdapter {


    private ArrayList<Loxica_Articulo> loxicaArticulos;
    private Context mContext;

    public Adapatador_Lista(Context context, ArrayList<Loxica_Articulo> loxicaArticulos) {
        super(context, R.layout.layout_elemento_lista);       // Enviamos o layout que imos utilizar

        this.loxicaArticulos = loxicaArticulos;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        //return super.getCount();
        return loxicaArticulos.size();       // Poderíamos poñer tamén o número de imaxes.
    }

    @Override
    public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
        return getView(position,convertView,parent);
    }

    private static class ViewHolder {
        CheckedTextView mCheck;
        TextView mCantidad;
        TextView mPrecio;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        Loxica_Articulo loxicaArticulo = loxicaArticulos.get(position);

        Adapatador_Lista.ViewHolder viewFila = new Adapatador_Lista.ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_elemento_lista, parent, false);
            viewFila.mCheck = (CheckedTextView) convertView.findViewById(R.id.ctvNombreArticulo_ElementoLista);
            viewFila.mCantidad = (TextView) convertView.findViewById(R.id.tvCantidadArticuloLista_ElementoLista);
            viewFila.mPrecio = (TextView) convertView.findViewById(R.id.tvPrecioArticuloLista_ElementoLista);
            convertView.setTag(viewFila);
        } else {
            viewFila = (Adapatador_Lista.ViewHolder) convertView.getTag();
        }

        viewFila.mCheck.setText(loxicaArticulo.getNombre());

        if(loxicaArticulo.getCantidad()!=0) {
            viewFila.mCantidad.setText(loxicaArticulo.getCantidad()+"");
        }else{
            viewFila.mCantidad.setText("1");
        }

        if(loxicaArticulo.getPrecio()!=0) {
            viewFila.mPrecio.setText(loxicaArticulo.getPrecio() + "€");
        }else{
            viewFila.mPrecio.setText("");
        }

        if(loxicaArticulo.getNotas().length()!=0){

        }else{

        }

        if(loxicaArticulo.isSeleccionado()){
            viewFila.mCheck.setChecked(true);
        }else{
            viewFila.mCheck.setChecked(false);
        }



        return convertView;
    }
}
