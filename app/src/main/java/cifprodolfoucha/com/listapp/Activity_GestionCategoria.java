package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_Categorias;
import cifprodolfoucha.com.listapp.Modelos.Categoria;

public class Activity_GestionCategoria extends Activity {
    ArrayList<Categoria> cat;

    private BaseDatos baseDatos;

    private void xestionarEventos(){
        ImageButton ibtnAdd=(ImageButton)findViewById(R.id.ibtn_AddCat);
        ibtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseDatos.engadirCategoria("CAT","");


                baseDatos.obterCategoria("CAT","");

                Categoria c=new Categoria();


                cat.add(c);
                Intent datos = new Intent();
                datos.putExtra(Activity_MisListas.CATEGORIAS, cat);
                setResult(RESULT_OK, datos);
                finish();
            }
        });


    }

    private void cargarCategorias(){
        Spinner categorias=findViewById(R.id.spnCategorias_mislistas);
        /*
        ArrayList<Categoria> cat=

        Adaptador_Categorias miAdaptador=new Adaptador_Categorias(this,categorias1);
        categorias.setAdapter(miAdaptador);
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (baseDatos==null) {   // Abrimos a base de datos para escritura
            baseDatos = baseDatos.getInstance(getApplicationContext());
            baseDatos.abrirBD();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (baseDatos!=null){    // Pechamos a base de datos.
            baseDatos.pecharBD();
            baseDatos=null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__gestion_categoria);

        cat=(ArrayList<Categoria>)getIntent().getSerializableExtra("categorias");

        cargarCategorias();
    }


}
