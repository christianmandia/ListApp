package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.io.File;
import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_Categorias;
import cifprodolfoucha.com.listapp.Almacenamento.BaseDatos;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Categoria;

/**
 * @author Christian López Martín
 * @version 1
 **/

public class Activity_GestionCategoria extends Activity {

    /**
     * cat é un ArrayList coas Categorias que mostra o spinner,
     *       este Array estará cargado coas categorías que lle mande a Activity_MisListas.
     *       ou coas Categorías que obteña da base de datos.
     **/
    private ArrayList<Loxica_Categoria> cat;
    /**
     * posC é un número que referenciará á posición da Categoria que stá seleccionada no Spinner.
     **/
    private int posC=0;
    /**
     * baseDatos é un acceso á clase BaseDatos onde se xestionan as consultas á base de datos.
     **/
    private BaseDatos baseDatos;
    /**
     * constraintLayout é unha referencia do layout da Activity para poder cambiar o fondo dependendo de se nos Axustes seleccionamos o modo noite.
     **/
    private static ConstraintLayout constraintLayout;
    /**
     * sNombreCategoria é un String que garda como referencia o contenido do EditText do nome da Categoría a engadir.
     **/
    private String sNombreCategoria;
    /**
     * sModificarCategoria é un String que garda como referencia o contenido do EditText do nome da Categoría a modificar.
     **/
    private String sModificarCategoria;
    final Activity_GestionCategoria GCategoria=this;

    /**
     * Controla os clicks que se realicen nos elementos da Activity.
     **/
    private void xestionarEventos(){
        ImageButton ibtnAdd=(ImageButton)findViewById(R.id.ibtnAnhadirCategoria_GestionCategorias);
        ibtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView nombreCategoria = (TextView) findViewById(R.id.etNombreCat_GestionCategorias);
                String nC = nombreCategoria.getText().toString();
                if (!nC.equals("")) {
                    Loxica_Categoria c = baseDatos.obterCategoria(nC);
                    if (c.getNombre() == null) {
                        long a = baseDatos.engadirCategoria(nC);
                        c = baseDatos.obterCategoria(nC);

                        cat.add(c);
                        cargarCategorias();

                        nombreCategoria.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), String.valueOf(R.string.str_gestioncategoria_mensaxe_engadir), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), String.valueOf(R.string.str_gestioncategoria_mensaxe_erro), Toast.LENGTH_SHORT).show();
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
                    cargarCategorias2();
                    Spinner spnCat=(Spinner)findViewById(R.id.spnCategorias_GestionCategorias);
                    spnCat.setSelection(posC);
                }else{
                    Toast.makeText(getApplicationContext(), String.valueOf(R.string.str_gestioncategoria_mensaxe_modificar), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton ibtnBorrar=(ImageButton)findViewById(R.id.ibtnEliminarCategoria_GestionCategorias);
        ibtnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int f;
                if((f=baseDatos.obterNumListas(cat.get(posC)))==0){


                    AlertDialog.Builder d = new AlertDialog.Builder(GCategoria);
                    d.setTitle(R.string.str_gestioncategoria_mensaxe_eliminar);
                    d.setMessage(R.string.str_gestioncategoria_mensaxe_eliminar2+cat.get(posC).getNombre()+" ?");
                    d.setCancelable(false);
                    d.setPositiveButton(R.string.str_all_mensaxe_si, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int boton) {
                            if(baseDatos.eliminarCategoria(cat.get(posC).getId())>0) {
                                cargarCategorias2();
                            }else{
                                Toast.makeText(getApplicationContext(),String.valueOf(R.string.str_gestioncategoria_mensaxe_eliminarCategoria_erro1),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    d.setNegativeButton(R.string.str_all_mensaxe_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int boton) {
                        }
                    });
                    d.create();
                    d.show();


                }else{
                    Toast.makeText(getApplicationContext(),String.valueOf(R.string.str_gestioncategoria_mensaxe_eliminarCategoria_erro2),Toast.LENGTH_SHORT).show();
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

    /**
     * Cargará o ArrayList cat e o Spinner coas categorias que recibirá da Activity_MisListas.
     **/
    private void cargarCategorias(){

        Spinner categorias=findViewById(R.id.spnCategorias_GestionCategorias);
        if(cat.get(0).getId()==0) {
            cat.remove(0);
        }

        Adaptador_Categorias miAdaptador=new Adaptador_Categorias(this,cat);
        categorias.setAdapter(miAdaptador);

    }

    /**
     * Cargará o ArrayList cat e o Spinner coas categorias que obterá da base de datos.
     **/
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
        if (baseDatos==null) {
            baseDatos = baseDatos.getInstance(getApplicationContext());
            baseDatos.abrirBD();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicarPreferencias();
    }

    /**
     * Aplica a preferencia do Modo Nocturno e obten a preferencia onde se garda a versión do XMl.
     **/
    private void aplicarPreferencias() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        constraintLayout = (ConstraintLayout) findViewById(R.id.bgFondo_GestionCategorias);

        Spinner spnC=(Spinner)findViewById(R.id.spnCategorias_GestionCategorias);

        EditText etNombreCategoria=(EditText)findViewById(R.id.etNombreCat_GestionCategorias);
        EditText etmodificarCategoria=(EditText)findViewById(R.id.etModificarCategoria_GestionCategorias);

        TextView tvCategorias=(TextView)findViewById(R.id.tvCategorias_GestionCategorias);

        Boolean fondo= preferencias.getBoolean("preferencia_idFondo", false);
        if(fondo){
            setTheme(R.style.Nocturno);
            constraintLayout.setBackgroundColor(Color.BLACK);
            spnC.setBackgroundColor(Color.DKGRAY);
            etmodificarCategoria.setTextColor(getResources().getColor(R.color.white));
            etmodificarCategoria.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            etNombreCategoria.setTextColor(getResources().getColor(R.color.white));
            etNombreCategoria.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            tvCategorias.setTextColor(getResources().getColor(R.color.white));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gestion_categorias);
        cat=(ArrayList<Loxica_Categoria>)getIntent().getSerializableExtra(Activity_MisListas.CATEGORIAS);
        xestionarEventos();
        cargarCategorias();
    }


}
