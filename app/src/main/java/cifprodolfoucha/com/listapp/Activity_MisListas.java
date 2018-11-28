package cifprodolfoucha.com.listapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Adaptadores.Adapatador_Lista;
import cifprodolfoucha.com.listapp.Adaptadores.Adapatador_ListaDefault;
import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_Categorias;
import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_MisListas;
import cifprodolfoucha.com.listapp.Modelos.Articulo;
import cifprodolfoucha.com.listapp.Modelos.Categoria;
import cifprodolfoucha.com.listapp.Modelos.Lista;



public class Activity_MisListas extends Activity {

    private ArrayList<Lista> listas=null;
    Menu m=null;
    private Lista lista1;
    private int pos;
    private Adaptador_Categorias miAdaptador;
    private Adaptador_MisListas miAdaptadorMisListas;
    private ListView lista;
    private int COD_LOGIN=30;
    private int RESULT_LOGIN=10;
    private static final int COD_PETICION = 33;
    public static final String LISTAENVIADA= "lista";
    private final int CODIGO_IDENTIFICADOR=1;

    public void gestionEventos(){
        lista = findViewById(R.id.lvmislistas_mislistas);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){
                //ItemClicked item = adapter.getItemAtPosition(position);


                //Intent intent = new Intent(Activity.this,destinationActivity.class);
                //based on item add info to intent
                //startActivity(intent);
                //Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_LONG).show();
                Lista lista1=listas.get(position);
                pos=position;
                Intent intento=new Intent(getApplicationContext(),Activity_Lista.class);
                intento.putExtra("list",lista1);
                startActivityForResult(intento,COD_PETICION);


            }

        });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(ELIMINAR);
                pos=position;
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this,"BEEEEEE",Toast.LENGTH_LONG).show();
        switch(item.getItemId()){
            case R.id.Login:

                Intent login=new Intent(getApplicationContext(), Activity_Login.class);
                //modificarArticulo.putExtra("titulo", articuloSeleccionado.getNombre());
                //startActivityForResult(login,COD_LOGIN);
                startActivity(login);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    private AlertDialog.Builder d;
    private static final int ELIMINAR = 1;


    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ELIMINAR:
                d = new AlertDialog.Builder(this);
                d.setIcon(android.R.drawable.ic_dialog_info);
                d.setTitle("Eliminar");
                d.setMessage("Está seguro de que desea eliminar este elemento?");
                d.setCancelable(false);
                d.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {

                         listas.remove(pos);
                        miAdaptadorMisListas.notifyDataSetChanged();

                    }
                });
                d.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                    }
                });
                return d.create();
        }
        return null;
    }

            @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        m=menu;
        return true;
    }

    private void cargarListas(){
        ListView lista = findViewById(R.id.lvmislistas_mislistas);

        listas= new ArrayList<>();
        ArrayList<Articulo> articulos=new ArrayList<>();
        articulos.add(new Articulo("pilas AA",false,1,0.5,""));
        articulos.add(new Articulo("articulo2",true,3,15,""));
        articulos.add(new Articulo("mazá",true,10,30,""));

        listas.add(new Lista("Lista1",(new Categoria("Categoria1","Imagen1")),articulos));

        ArrayList<Articulo> articulos2=new ArrayList<>();
        articulos2.add(new Articulo("articulo4",true,1,2,""));
        articulos2.add(new Articulo("articulo5",true,1,20,""));
        articulos2.add(new Articulo("articulo6",true,4,5,""));

        listas.add(new Lista("Lista2",(new Categoria("Categoria2","Imagen2")),articulos2));

        miAdaptadorMisListas = new Adaptador_MisListas(this,listas);
        lista.setAdapter(miAdaptadorMisListas);


    }
    private void cargarCategorias(){
        Spinner categorias=findViewById(R.id.spnCategorias_mislistas);
        ArrayList<Categoria> categorias1=new ArrayList<>();
        categorias1.add(new Categoria("categoria1","imagen1"));
        categorias1.add(new Categoria("categoria2","imagen2"));
        categorias1.add(new Categoria("categoria3","imagen3"));
        categorias1.add(new Categoria("categoria4","imagen4"));

        miAdaptador=new Adaptador_Categorias(this,categorias1);
        categorias.setAdapter(miAdaptador);
    }


    public final static String Login= "login";
    public final static String Pass= "pass";
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
        if (requestCode == COD_LOGIN) {
            if (resultCode == RESULT_LOGIN) {
                if (data.hasExtra(Activity_Lista.Login && Activity_Lista.Pass){

                }
            }
        }
        */

        if (requestCode == COD_PETICION) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(Activity_Lista.NEWArticulos)) {
                    // Toast.makeText(this, "Saíches da actividade secundaria sen premer o botón Pechar", Toast.LENGTH_SHORT).show();

                    Lista l=(Lista)data.getSerializableExtra(LISTAENVIADA);
                    //Toast.makeText(this, articulos2.size()+"tam", Toast.LENGTH_SHORT).show();
                    /*
                    /*
                    */


                    Toast.makeText(getApplicationContext(),l.getNombre(),Toast.LENGTH_SHORT).show();
                    listas.remove(pos);
                    listas.add(pos,l);
                    miAdaptadorMisListas.notifyDataSetChanged();
                    cargarListas();
                    miAdaptadorMisListas.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),l.getNombre()+" asa ",Toast.LENGTH_SHORT).show();

                }

            }
        }
    }

    public void pedirPermiso(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CODIGO_IDENTIFICADOR);
            //requestPermissions( new String[]{Manifest.permission.CAMERA},CODIGO_IDENTIFICADOR);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__mis_listas);
        cargarListas();
        cargarCategorias();
        gestionEventos();
        pedirPermiso();
    }
}
