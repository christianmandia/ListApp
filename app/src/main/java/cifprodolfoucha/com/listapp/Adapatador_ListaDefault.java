package cifprodolfoucha.com.listapp;

import android.content.Context;
import android.widget.ArrayAdapter;

public class Adapatador_ListaDefault extends ArrayAdapter {


    String[] articulos;
    int[] cantidad;
    double[] precio;
    Context mContext;

    public Adapatador_ListaDefault(Context context, String[]articulos,int[]cantidad,double[]precio) {
        super(context, R.layout.layout_elemento_listadefault2);       // Enviamos o layout que imos utilizar

        this.articulos = articulos;
        this.cantidad = cantidad;
        this.precio = precio;
        this.mContext = context;
    }
}
