package cifprodolfoucha.com.listapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Adapatador_ListaDefault extends ArrayAdapter {


    private String[] articulos;
    private int[] cantidad;
    private double[] precio;
    private Context mContext;

    public Adapatador_ListaDefault(Context context, String[]articulos,int[]cantidad,double[]precio) {
        super(context, R.layout.layout_elemento_listadefault2);       // Enviamos o layout que imos utilizar

        this.articulos = articulos;
        this.cantidad = cantidad;
        this.precio = precio;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        //return super.getCount();
        return articulos.length;       // Poderíamos poñer tamén o número de imaxes.
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

        Adapatador_ListaDefault.ViewHolder viewFila = new Adapatador_ListaDefault.ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_elemento_listadefault2, parent, false);
            viewFila.mCheck = (CheckedTextView) convertView.findViewById(R.id.ctvNombreArticulo_ElementoListaDefault);
            viewFila.mCantidad = (TextView) convertView.findViewById(R.id.tvCantidadArticuloLista_ElementoListaDefault);
            viewFila.mPrecio = (TextView) convertView.findViewById(R.id.tvPrecioArticuloLista_ElementoListaDefault);
            convertView.setTag(viewFila);
        } else {
            viewFila = (Adapatador_ListaDefault.ViewHolder) convertView.getTag();
        }
        viewFila.mCheck.setText(articulos[position]);

        if(cantidad[position]!=0) {
            viewFila.mCantidad.setText(cantidad[position]+"");
        }else{
            viewFila.mCantidad.setText("1");
        }

        if(precio[position]!=0) {
            viewFila.mPrecio.setText(precio[position] + "€");
        }else{
            viewFila.mPrecio.setText("");
        }

        return convertView;
    }
}
