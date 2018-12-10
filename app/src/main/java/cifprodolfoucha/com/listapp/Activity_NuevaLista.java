package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import cifprodolfoucha.com.listapp.Loxica.Categoria;
import cifprodolfoucha.com.listapp.Loxica.Lista;

public class Activity_NuevaLista extends Activity {
    ArrayList<Categoria> cat;


    private BaseDatos baseDatos;

    private String nCat;

    private void xestionarEventos(){
        final Spinner spnCat=(Spinner)findViewById(R.id.spnCategorias_NuevaLista);

        spnCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                nCat=((TextView)(view.findViewById(R.id.tvNombre_Categoria))).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageButton ibtnAñadirLista=(ImageButton)findViewById(R.id.ibtnAñadirLista_NuevaLista);
        ibtnAñadirLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etNombreLista=(EditText)findViewById(R.id.etNombreLista_NuevaLista);

                Log.i("aadaa", "onClick: ");

                Log.i("aadaa", "obter id");
                int id=baseDatos.obterIdCategoria(nCat);
                Log.i("aadaa", "obterCat");
                Categoria c=baseDatos.obterCategoria(id);
                String nL=etNombreLista.getText().toString();

                if(!nL.equals("")){

                    Lista l=baseDatos.obterLista(nL,c);
                    if(l.getNombre()==null) {

                        Log.i("aadaa", "engadirLista");
                        long a = baseDatos.engadirLista(nL, id);
                        Log.i("aadaa", "obterLista");
                        l = baseDatos.obterLista(nL,c);

                        Intent datos = new Intent();
                        datos.putExtra(Activity_MisListas.NUEVALISTA, l);
                        setResult(RESULT_OK, datos);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Xa existe unha lista chamada así  en esa categoría",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Non escribiches nada",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cargarCategorias(){

        Spinner categorias=findViewById(R.id.spnCategorias_NuevaLista);

        if(cat.get(0).getId()==0) {
            cat.remove(0);
        }

        Adaptador_Categorias miAdaptador=new Adaptador_Categorias(this,cat);
        categorias.setAdapter(miAdaptador);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (baseDatos==null) {   // Abrimos a base de datos para escritura
            baseDatos = baseDatos.getInstance(getApplicationContext());
            //baseDatos = new BaseDatos(getApplicationContext());
            baseDatos.abrirBD();
//        }
        //Log.i("PROBA",String.valueOf(baseDatos.sqlLiteDB.isOpen()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nuevalista);

        cat=(ArrayList<Categoria>)getIntent().getSerializableExtra(Activity_MisListas.CATEGORIAS);
        xestionarEventos();
        cargarCategorias();
    }
}
