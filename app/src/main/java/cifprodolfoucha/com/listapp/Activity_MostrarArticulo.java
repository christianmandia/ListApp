package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;

public class Activity_MostrarArticulo extends Activity {

    private Loxica_Articulo loxicaArticulo =null;

    private void xestionarEventos(){
        ImageButton ibtn_Volver=findViewById(R.id.ibtn_VolverMostrarArticulo);

        ibtn_Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void cargarArticulo(){
        TextView tvNombreArticulo=findViewById(R.id.tvNombreArticuloMostrado_MostrarArticulo);
        TextView tvPrecioArticulo=findViewById(R.id.tvPrecioArticulo_ModificarArticulo);
        TextView tvCantidadArticulo=findViewById(R.id.tvCantidadArticuloMostrado_MostrarArticulo);
        TextView tvNotasArticulo=findViewById(R.id.tvNotasArticuloMostrado_MostrarArticulo);

        ImageView ivFoto=findViewById(R.id.ivImagenArticulo_MostrarArticulo);

        tvNombreArticulo.setText(loxicaArticulo.getNombre());
        if(loxicaArticulo.getPrecio()!=0) {
            tvNombreArticulo.setText(loxicaArticulo.getPrecio()+ "");
        }
        tvCantidadArticulo.setText(loxicaArticulo.getCantidad()+"");
        tvNotasArticulo.setText(loxicaArticulo.getNotas());
        if(!loxicaArticulo.getRutaImagen().equals("")){
            Bitmap bitmap = BitmapFactory.decodeFile(loxicaArticulo.getRutaImagen());
            ivFoto.setImageBitmap(bitmap);
        }else{
            Bitmap bitmap = BitmapFactory.decodeFile(null);
            ivFoto.setImageBitmap(bitmap);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mostrararticulo);
        loxicaArticulo =(Loxica_Articulo) getIntent().getSerializableExtra("loxicaArticulo");
        setTitle("Artículo: "+ loxicaArticulo.getNombre());

        xestionarEventos();
        cargarArticulo();
    }
}
