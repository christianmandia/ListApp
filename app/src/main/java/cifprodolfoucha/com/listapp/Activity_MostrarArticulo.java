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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;

public class Activity_MostrarArticulo extends Activity {

    private Loxica_Articulo articulo =null;

    private static ConstraintLayout constraintLayout;

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
        TextView tvPrecioArticulo=findViewById(R.id.tvPrecioArticuloMostrado_MostrarArticulo);
        TextView tvCantidadArticulo=findViewById(R.id.tvCantidadArticuloMostrado_MostrarArticulo);
        TextView tvNotasArticulo=findViewById(R.id.tvNotasArticuloMostrado_MostrarArticulo);

        ImageView ivFoto=findViewById(R.id.ivImagenArticulo_MostrarArticulo);

        tvNombreArticulo.setText(articulo.getNombre());
        if(articulo.getPrecio()!=0) {
            tvPrecioArticulo.setText(articulo.getPrecio()+ "");
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

    /**
     * Aplica a preferencia do Modo Nocturno e obten a preferencia onde se garda a versión do XMl.
     **/
    private void aplicarPreferencias() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        TextView tvNombre=(TextView) findViewById(R.id.tvArticulo_MostrarAticulo);
        TextView tvNotas=(TextView) findViewById(R.id.tvNotasArticulo_MostrarArticulo);
        TextView tvCantidad=(TextView) findViewById(R.id.tvCantidadArticulo_MostrarArticulo);
        TextView tvPrecio=(TextView) findViewById(R.id.tvPrecioArticulo_MostrarArticulo);
        TextView tvNombreM=(TextView) findViewById(R.id.tvNombreArticuloMostrado_MostrarArticulo);
        TextView tvNotasM=(TextView) findViewById(R.id.tvNotasArticuloMostrado_MostrarArticulo);
        TextView tvCantidadM=(TextView) findViewById(R.id.tvCantidadArticuloMostrado_MostrarArticulo);
        TextView tvPrecioM=(TextView) findViewById(R.id.tvPrecioArticuloMostrado_MostrarArticulo);


        Boolean fondo= preferencias.getBoolean("preferencia_idFondo", false);
        if(fondo){
            setTheme(R.style.Nocturno);
            constraintLayout.setBackgroundColor(Color.BLACK);

            tvNombre.setTextColor(getResources().getColor(R.color.white));
            tvNotas.setTextColor(getResources().getColor(R.color.white));
            tvPrecio.setTextColor(getResources().getColor(R.color.white));
            tvCantidad.setTextColor(getResources().getColor(R.color.white));
            tvNombreM.setTextColor(getResources().getColor(R.color.yellow));
            tvNotasM.setTextColor(getResources().getColor(R.color.yellow));
            tvPrecioM.setTextColor(getResources().getColor(R.color.yellow));
            tvCantidadM.setTextColor(getResources().getColor(R.color.yellow));

        }else{
            setTheme(R.style.Diurno);
            constraintLayout.setBackgroundColor(Color.WHITE);


        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicarPreferencias();
    }

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        guardaEstado.putSerializable("articulo",articulo);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        articulo=(Loxica_Articulo) recuperaEstado.getSerializable("articulo");
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
