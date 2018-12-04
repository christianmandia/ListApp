package cifprodolfoucha.com.listapp.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Modelos.Lista;
import cifprodolfoucha.com.listapp.R;




public class Adaptador_MisListas extends ArrayAdapter {


    private ArrayList<Lista> listas;
    private Context mContext;

    public Adaptador_MisListas(Context context, ArrayList<Lista>listas) {
        super(context, R.layout.layout_elemento_mislistas);

        this.listas = listas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return listas.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }

    private static class ViewHolder {

        TextView tvNombreLista;
        TextView tvPrecioLista;
        TextView tvDiferenciaLista;
        ImageView ivCategoria;
        ImageView ivImagen;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        Lista lista=listas.get(position);

        Adaptador_MisListas.ViewHolder viewFila = new Adaptador_MisListas.ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_elemento_mislistas, parent, false);

            viewFila.tvNombreLista = (TextView) convertView.findViewById(R.id.tvNombre_MisListas);
            viewFila.tvPrecioLista = (TextView) convertView.findViewById(R.id.tvPrecio_MisListas);
            viewFila.tvDiferenciaLista = (TextView) convertView.findViewById(R.id.tvDiferencia_MisListas);
            viewFila.ivCategoria= (ImageView)  convertView.findViewById(R.id.ivImagenCategoria_MisListas);
            viewFila.ivImagen= (ImageView) convertView.findViewById(R.id.ivImagenLleno_MisListas);

            convertView.setTag(viewFila);
        } else {
            viewFila = (Adaptador_MisListas.ViewHolder) convertView.getTag();
        }

        viewFila.tvNombreLista.setText(lista.getNombre());

        viewFila.tvPrecioLista.setText(lista.precioChecked()+"");


        viewFila.tvDiferenciaLista.setText(lista.diferenciaArticulos());
/*
        Bitmap bitmap = BitmapFactory.decodeFile(lista.getCategoria().getImagen());
        viewFila.ivImagen.setImageBitmap(bitmap);
*/

        // No esta claro si la ruta funciona de esta manera
        if(lista.TienesTodo()){
            Bitmap bitmap2 = BitmapFactory.decodeFile("@android:drawable/btn_star_big_on");
            viewFila.ivImagen.setImageBitmap(bitmap2);
        } else{
            Bitmap bitmap2 = BitmapFactory.decodeFile("@android:drawable/btn_star_big_off");
            viewFila.ivImagen.setImageBitmap(bitmap2);
        }







        return convertView;
    }
}
