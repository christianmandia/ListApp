package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_Categorias;
import cifprodolfoucha.com.listapp.Almacenamento.BaseDatos;
import cifprodolfoucha.com.listapp.Loxica.Categoria;

public class Activity_GestionCategoria extends Activity {
    ArrayList<Categoria> cat;

    private BaseDatos baseDatos;

    private void xestionarEventos(){
        ImageButton ibtnAdd=(ImageButton)findViewById(R.id.ibtnAnhadirCategoria_GestionCategorias);

        ibtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nC=((TextView)findViewById(R.id.etNombreCat_GestionCategorias)).getText().toString();

                //Log.i("PROBA3",String.valueOf(baseDatos.sqlLiteDB.isOpen()));
                if(!nC.equals("")){
                Categoria c = baseDatos.obterCategoria(nC);
                if(c.getNombre()==null) {
                    //Log.i("uno", "Entrando");
                    int size = cat.size() + 1;
                    long a = baseDatos.engadirCategoria(size, nC);
                    //if(a>0) {
                    //    Log.i("uno", "obtendo");
                    c = baseDatos.obterCategoria(nC);

                    cat.add(c);
                    Intent datos = new Intent();
                    datos.putExtra(Activity_MisListas.CATEGORIAS, cat);
                    setResult(RESULT_OK, datos);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Non se engadiu a categoria, xa existe", Toast.LENGTH_SHORT).show();
                }
                //}else{
                //    Toast.makeText(getApplicationContext(), "Non se engadiu a categoria", Toast.LENGTH_SHORT).show();
            //    }
            }else{
                    Toast.makeText(getApplicationContext(), "Non escribiches", Toast.LENGTH_SHORT).show();
                }





            }
        });


    }

    private void cargarCategorias(){

        Spinner categorias=findViewById(R.id.spnCategorias_GestionCategorias);


        Adaptador_Categorias miAdaptador=new Adaptador_Categorias(this,cat);
        categorias.setAdapter(miAdaptador);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (baseDatos==null) {   // Abrimos a base de datos para escritura
            baseDatos = baseDatos.getInstance(getApplicationContext());
            //baseDatos = new BaseDatos(getApplicationContext());
            baseDatos.abrirBD();
        }
        //Log.i("PROBA",String.valueOf(baseDatos.sqlLiteDB.isOpen()));

    }
/*
    @Override
    protected void onResume(){
        super.onResume();
        //Log.i("PROBA2",String.valueOf(baseDatos.sqlLiteDB.isOpen()));
    }

    /*
    @Override
    protected void onStop() {
        super.onStop();
        if (baseDatos!=null){    // Pechamos a base de datos.
            baseDatos.pecharBD();
            baseDatos=null;
        }
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gestion_categorias);
        cat=(ArrayList<Categoria>)getIntent().getSerializableExtra(Activity_MisListas.CATEGORIAS);
        //Log.i("a", "onCreate: ");

        xestionarEventos();
        cargarCategorias();
    }


}
