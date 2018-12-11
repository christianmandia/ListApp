package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_Categorias;
import cifprodolfoucha.com.listapp.Almacenamento.BaseDatos;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Categoria;

public class Activity_GestionCategoria extends Activity {
    ArrayList<Loxica_Categoria> cat;

    private BaseDatos baseDatos;

    private void xestionarEventos(){
        ImageButton ibtnAdd=(ImageButton)findViewById(R.id.ibtnAnhadirCategoria_GestionCategorias);

        ibtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nC=((TextView)findViewById(R.id.etNombreCat_GestionCategorias)).getText().toString();

                //Log.i("PROBA3",String.valueOf(baseDatos.sqlLiteDB.isOpen()));
                if(!nC.equals("")){
                Loxica_Categoria c = baseDatos.obterCategoria(nC);
                if(c.getNombre()==null) {
                    //Log.i("uno", "Entrando");
                    //int size = cat.size();
                    long a = baseDatos.engadirCategoria(nC);
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

        final EditText etCatMod=(EditText)findViewById(R.id.etModificarCategoria_GestionCategorias);
        Spinner spnCategorias=(Spinner)findViewById(R.id.spnCategorias_GestionCategorias);
        spnCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nomeCategoria = ((TextView)((ConstraintLayout)view).getViewById(R.id.tvNombre_Categoria)).getText().toString();
                etCatMod.setText(nomeCategoria);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void cargarCategorias(){

        Spinner categorias=findViewById(R.id.spnCategorias_GestionCategorias);
        if(cat.get(0).getId()==0) {
            cat.remove(0);
        }

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
        cat=(ArrayList<Loxica_Categoria>)getIntent().getSerializableExtra(Activity_MisListas.CATEGORIAS);

        //Log.i("a", "onCreate: ");

        xestionarEventos();
        cargarCategorias();
    }


}
