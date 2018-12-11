package cifprodolfoucha.com.listapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Loxica.Loxica_Lista;
import cifprodolfoucha.com.listapp.R;




public class Adaptador_MisListas extends ArrayAdapter {


    private ArrayList<Loxica_Lista> loxicaListas;
    private Context mContext;

    public Adaptador_MisListas(Context context, ArrayList<Loxica_Lista> loxicaListas) {
        super(context, R.layout.layout_elemento_mislistas);

        this.loxicaListas = loxicaListas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return loxicaListas.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }

    private static class ViewHolder {

        TextView tvNombreLista;
        TextView tvPrecioLista;
        TextView tvDiferenciaLista;
        ImageView ivImagen;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        Loxica_Lista loxicaLista = loxicaListas.get(position);

        Adaptador_MisListas.ViewHolder viewFila = new Adaptador_MisListas.ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_elemento_mislistas, parent, false);

            viewFila.tvNombreLista = (TextView) convertView.findViewById(R.id.tvNombre_MisListas);
            viewFila.tvPrecioLista = (TextView) convertView.findViewById(R.id.tvPrecio_MisListas);
            viewFila.tvDiferenciaLista = (TextView) convertView.findViewById(R.id.tvDiferencia_MisListas);
            viewFila.ivImagen= (ImageView) convertView.findViewById(R.id.ivImagenLleno_MisListas);

            convertView.setTag(viewFila);
        } else {
            viewFila = (Adaptador_MisListas.ViewHolder) convertView.getTag();
        }

        viewFila.tvNombreLista.setText(loxicaLista.getNombre());

        if(loxicaLista.precioChecked()!=0) {
            viewFila.tvPrecioLista.setText(loxicaLista.precioChecked() + "€");
        }else{
            viewFila.tvPrecioLista.setText("");
        }



        viewFila.tvDiferenciaLista.setText(loxicaLista.diferenciaArticulos());

        if(loxicaLista.TienesTodo()){
            //Bitmap bitmap2 = BitmapFactory.decodeFile("@android:drawable/btn_star_big_on");
            //viewFila.ivImagen.setImageBitmap(bitmap2);
            viewFila.ivImagen.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_star_big_on));
        } else{
            //Bitmap bitmap2 = BitmapFactory.decodeFile("@android:drawable/btn_star_big_off");
            //viewFila.ivImagen.setImageBitmap(bitmap2);
            viewFila.ivImagen.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_star_big_off));
        }





        return convertView;
    }
}
