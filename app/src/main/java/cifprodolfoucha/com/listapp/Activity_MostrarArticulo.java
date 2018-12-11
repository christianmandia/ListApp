package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;

public class Activity_MostrarArticulo extends Activity {

    private Loxica_Articulo articulo =null;

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

        tvNombreArticulo.setText(articulo.getNombre());
        if(articulo.getPrecio()!=0) {
            tvNombreArticulo.setText(articulo.getPrecio()+ "");
        }
        tvCantidadArticulo.setText(articulo.getCantidad()+"");
        tvNotasArticulo.setText(articulo.getNotas());
        if(!articulo.getRutaImagen().equals("")){
            Bitmap bitmap = BitmapFactory.decodeFile(articulo.getRutaImagen());
            ivFoto.setImageBitmap(bitmap);
        }else{
            ivFoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_report_image));
        }
    }

    private static ConstraintLayout constraintLayout;

    private void aplicarPreferencias() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Boolean fondo= preferencias.getBoolean("preferencia_idFondo", false);
        if(fondo){
            setTheme(R.style.Nocturno);
            constraintLayout.setBackgroundColor(Color.BLACK);
        }else{
            setTheme(R.style.Diurno);
            constraintLayout.setBackgroundColor(Color.WHITE);


        }
        //nome.setText(valorNome);


    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicarPreferencias();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mostrararticulo);
        articulo =(Loxica_Articulo) getIntent().getSerializableExtra("articulo");
        constraintLayout = (ConstraintLayout) findViewById(R.id.bgFondo_MostrarArticulo);
        setTitle("Artículo: "+ articulo.getNombre());

        xestionarEventos();
        cargarArticulo();
    }
}
