package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
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
    private ArrayList<Loxica_Categoria> cat;
    private int posC=0;


    private BaseDatos baseDatos;

    private void xestionarEventos(){
        ImageButton ibtnAdd=(ImageButton)findViewById(R.id.ibtnAnhadirCategoria_GestionCategorias);
        ibtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView nombreCategoria = (TextView) findViewById(R.id.etNombreCat_GestionCategorias);
                String nC = nombreCategoria.getText().toString();

                //Log.i("PROBA3",String.valueOf(baseDatos.sqlLiteDB.isOpen()));
                if (!nC.equals("")) {
                    Loxica_Categoria c = baseDatos.obterCategoria(nC);
                    if (c.getNombre() == null) {
                        //Log.i("uno", "Entrando");
                        //int size = cat.size();
                        long a = baseDatos.engadirCategoria(nC);
                        //if(a>0) {
                        //    Log.i("uno", "obtendo");
                        c = baseDatos.obterCategoria(nC);

                        cat.add(c);
                        cargarCategorias();
                    /*
                    Intent datos = new Intent();
                    datos.putExtra(Activity_MisListas.CATEGORIAS, cat);
                    setResult(RESULT_OK, datos);
                    finish();
                    */
                        nombreCategoria.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "Non se engadiu a categoria, xa existe", Toast.LENGTH_SHORT).show();
                    }
                    //}else{
                    //    Toast.makeText(getApplicationContext(), "Non se engadiu a categoria", Toast.LENGTH_SHORT).show();
                    //    }
                } else {
                    Toast.makeText(getApplicationContext(), "Non escribiches", Toast.LENGTH_SHORT).show();
                }
            }
        });




        ImageButton ibtnAtras=(ImageButton)findViewById(R.id.ibtnAtras_GestionCategorias);
        ibtnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton ibtnEditar=(ImageButton)findViewById(R.id.ibtnEditarCat_GestionCategorias);
        ibtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView modificarCategoria=(TextView)findViewById(R.id.etModificarCategoria_GestionCategorias);
                String sMC=modificarCategoria.getText().toString();
                Loxica_Categoria c = baseDatos.obterCategoria(sMC);

                if(c.getNombre()==null) {
                    long a = baseDatos.modificarCategoria(cat.get(posC).getId(),sMC);
                    Log.i("prueba2", a+" modificar Categoria");
                    cargarCategorias2();
                    Spinner spnCat=(Spinner)findViewById(R.id.spnCategorias_GestionCategorias);
                    spnCat.setSelection(posC);
                }else{
                    Toast.makeText(getApplicationContext(), "Non se engadiu a categoria, xa existe", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton ibtnBorrar=(ImageButton)findViewById(R.id.ibtnEliminarCategoria_GestionCategorias);
        ibtnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int f;
                if((f=baseDatos.obterNumListas(cat.get(posC)))==0){
                    Log.i("prueba2", f+" - Numero");

                    if(baseDatos.eliminarCategoria(cat.get(posC).getId())>0) {
                        Log.i("prueba2", "eliminada");
                        cargarCategorias2();
                    }else{
                        Toast.makeText(getApplicationContext(),"Non se puido eliminar",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Non se pode eliminar unha categoria con listas asignadas",Toast.LENGTH_SHORT).show();
                }

            }
        });


        final EditText etCatMod=(EditText)findViewById(R.id.etModificarCategoria_GestionCategorias);
        Spinner spnCategorias=(Spinner)findViewById(R.id.spnCategorias_GestionCategorias);
        spnCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String nomeCategoria = ((TextView)((ConstraintLayout)view).getViewById(R.id.tvNombre_Categoria)).getText().toString();
                if(view!=null) {
                    String nomeCategoria = ((TextView) view).getText().toString();
                    etCatMod.setText(nomeCategoria);
                    posC=position;
                }
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

    private void cargarCategorias2(){

        cat=baseDatos.obterCategorias();
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
    protected void onResume() {
        super.onResume();
        aplicarPreferencias();
    }


    private static ConstraintLayout constraintLayout;

    private void aplicarPreferencias() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        constraintLayout = (ConstraintLayout) findViewById(R.id.bgFondo_GestionCategorias);

        Spinner spnC=(Spinner)findViewById(R.id.spnCategorias_GestionCategorias);

        EditText etNombreCategoria=(EditText)findViewById(R.id.etNombreCat_GestionCategorias);
        EditText etmodificarCategoria=(EditText)findViewById(R.id.etModificarCategoria_GestionCategorias);


        Boolean fondo= preferencias.getBoolean("preferencia_idFondo", false);
        if(fondo){
            setTheme(R.style.Nocturno);
            constraintLayout.setBackgroundColor(Color.BLACK);
            spnC.setBackgroundColor(Color.DKGRAY);
            etmodificarCategoria.setTextColor(getResources().getColor(R.color.white));
            etmodificarCategoria.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            etNombreCategoria.setTextColor(getResources().getColor(R.color.white));
            etNombreCategoria.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }else{
            setTheme(R.style.Diurno);
            constraintLayout.setBackgroundColor(Color.WHITE);
            spnC.setBackgroundColor(Color.DKGRAY);


        }
        //nome.setText(valorNome);


    }

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        EditText etNombreCategoria = (EditText) findViewById(R.id.etNombreCat_GestionCategorias);
        EditText etModificarCategoria = (EditText) findViewById(R.id.etModificarCategoria_GestionCategorias);
        sNombreCategoria=etNombreCategoria.getText().toString();
        sModificarCategoria=etModificarCategoria.getText().toString();
        guardaEstado.putInt("posC",posC);
        guardaEstado.putString("nombreCategoria",sNombreCategoria);
        guardaEstado.putString("modificarCategoria",sModificarCategoria);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        Spinner spnCat=(Spinner)findViewById(R.id.spnCategorias_GestionCategorias);
        EditText etNombreCategoria = (EditText) findViewById(R.id.etNombreCat_GestionCategorias);
        EditText etModificarCategoria = (EditText) findViewById(R.id.etModificarCategoria_GestionCategorias);
        sNombreCategoria=recuperaEstado.getString("nombreCategoria");
        sModificarCategoria=recuperaEstado.getString("modificarCategoria");
        posC=recuperaEstado.getInt("posC");
        etNombreCategoria.setText(sNombreCategoria);
        etModificarCategoria.setText(sModificarCategoria);
        cargarCategorias2();
        spnCat.setSelection(posC);
    }


    private String sNombreCategoria;
    private String sModificarCategoria;

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
