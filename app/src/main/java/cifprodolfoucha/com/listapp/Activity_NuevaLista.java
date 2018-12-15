package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Categoria;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Lista;

/**
 * @author Christian López Martín
 * @version 1
 **/

public class Activity_NuevaLista extends Activity {

    /**
     * cat é un ArrayList coas Categorias que recibe da Activity_MisListas que mostra o Spinner.
     **/
    ArrayList<Loxica_Categoria> cat;
    /**
     * constraintLayout é unha referencia do layout da Activity para poder cambiar o fondo dependendo de se nos Axustes seleccionamos o modo noite.
     **/
    private static ConstraintLayout constraintLayout;
    /**
     * baseDatos é un acceso á clase BaseDatos onde se xestionan as consultas á base de datos.
     **/
    private BaseDatos baseDatos;
    /**
     * nCat é unha referencia ao nome da Categoría que está seleccionada no Spinner.
     **/
    private String nCat;
    /**
     * sNombreLista é un String que garda como referencia o contenido do EditText do nome da Lista a engadir.
     **/
    private String sNombreLista;

    /**
     * Controla os clicks que se realicen nos elementos da Activity.
     **/
    private void xestionarEventos(){
        final Spinner spnCat=(Spinner)findViewById(R.id.spnCategorias_NuevaLista);

        spnCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(view!=null) {
                    nCat = ((TextView) (view.findViewById(R.id.tvNombre_Categoria))).getText().toString();
                }
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
                int id=baseDatos.obterIdCategoria(nCat);
                Loxica_Categoria c=baseDatos.obterCategoria(id);
                String nL=etNombreLista.getText().toString();

                if(!nL.equals("")){

                    Loxica_Lista l=baseDatos.obterLista(nL,c);
                    if(l.getNombre()==null) {
                        baseDatos.engadirLista(nL, id);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.str_nuevalista_mensaxe_erro1),Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.str_nuevalista_mensaxe_erro2),Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageButton ibtnAtras=(ImageButton)findViewById(R.id.ibtnAtras_NuevaLista);
        ibtnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Cargará o ArrayList cat e o Spinner coas categorias que recibirá da Activity_MisListas.
     **/
    private void cargarCategorias(){

        Spinner categorias=findViewById(R.id.spnCategorias_NuevaLista);

        if(cat.get(0).getId()==0) {
            cat.remove(0);
        }

        Adaptador_Categorias miAdaptador=new Adaptador_Categorias(this,cat);
        categorias.setAdapter(miAdaptador);

    }
    /**
     * Aplica a preferencia do Modo Nocturno e obten a preferencia onde se garda a versión do XMl.
     **/
    private void aplicarPreferencias() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Spinner spnC=(Spinner)findViewById(R.id.spnCategorias_NuevaLista);

        EditText etNombreLista=(EditText)findViewById(R.id.etNombreLista_NuevaLista);
        TextView tvNombreLista=(TextView)findViewById(R.id.tvNombreLista_NuevaLista);
        TextView tvCategoriaLista=(TextView)findViewById(R.id.tvCategoria_NuevaLista);

        Boolean fondo= preferencias.getBoolean("preferencia_idFondo", false);

        if(fondo){
            setTheme(R.style.Nocturno);
            constraintLayout.setBackgroundColor(Color.BLACK);
            //spnC.setBackgroundColor(Color.DKGRAY);

            etNombreLista.setTextColor(getResources().getColor(R.color.white));
            etNombreLista.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            tvCategoriaLista.setTextColor(getResources().getColor(R.color.white));
            tvNombreLista.setTextColor(getResources().getColor(R.color.white));
        }else{
            setTheme(R.style.Diurno);
            constraintLayout.setBackgroundColor(Color.WHITE);
            //spnC.setBackgroundColor(Color.DKGRAY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        EditText etNombreLista = (EditText) findViewById(R.id.etNombreLista_NuevaLista);
        sNombreLista=etNombreLista.getText().toString();
        guardaEstado.putString("nombreListaSel",nCat);
        guardaEstado.putString("nombreLista",sNombreLista);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        EditText etNombreLista = (EditText) findViewById(R.id.etNombreLista_NuevaLista);
        sNombreLista=recuperaEstado.getString("nombreLista");
        nCat=recuperaEstado.getString("nombreListaSel");
        etNombreLista.setText(sNombreLista);
    }

    @Override
    protected void onStart() {
        super.onStart();
        baseDatos = baseDatos.getInstance(getApplicationContext());
        baseDatos.abrirBD();
    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicarPreferencias();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nuevalista);
        constraintLayout = (ConstraintLayout) findViewById(R.id.bgFondo_NuevaLista);

        cat=(ArrayList<Loxica_Categoria>)getIntent().getSerializableExtra(Activity_MisListas.CATEGORIAS);
        xestionarEventos();
        cargarCategorias();
    }

}
