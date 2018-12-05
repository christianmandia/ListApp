package cifprodolfoucha.com.listapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_Categorias;
import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_MisListas;
import cifprodolfoucha.com.listapp.Almacenamento.BaseDatos;
import cifprodolfoucha.com.listapp.Almacenamento.Preferencias_Ajustes;
import cifprodolfoucha.com.listapp.Loxica.Articulo;
import cifprodolfoucha.com.listapp.Loxica.Categoria;
import cifprodolfoucha.com.listapp.Loxica.Lista;



public class Activity_MisListas extends Activity {

    private BaseDatos baseDatos;
    private ArrayList<Categoria> cat;
    private ArrayList<Lista> listas=new ArrayList<Lista>();
    Menu m=null;
    private Lista lista1;
    private int pos;
    private Adaptador_Categorias miAdaptador;
    private Adaptador_MisListas miAdaptadorMisListas;
    private ListView lista;
    private int COD_LOGIN=30;
    private int COD_GCAT=35;
    private int COD_ADDLISTA=40;
    private int RESULT_LOGIN=10;
    private static final int COD_PETICION = 33;
    public static String LISTAENVIADA= "lista";
    public static String CATEGORIAS= "categorias";
    public static String NUEVALISTA="listaEnviada";
    private final int CODIGO_IDENTIFICADOR=1;

    private void copiarBD() {
        String bddestino = "/data/data/" + getPackageName() + "/databases/"
                + BaseDatos.NOME_BD;
        File file = new File(bddestino);
        if (file.exists()) {
            Toast.makeText(getApplicationContext(), "A BD NON SE VAI COPIAR. XA EXISTE", Toast.LENGTH_LONG).show();
            return; // XA EXISTE A BASE DE DATOS
        }

        String pathbd = "/data/data/" + getPackageName()
                + "/databases/";
        File filepathdb = new File(pathbd);
        filepathdb.mkdirs();

        InputStream inputstream;
        try {
            inputstream = getAssets().open(BaseDatos.NOME_BD);
            OutputStream outputstream = new FileOutputStream(bddestino);


            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputstream.read(buffer))>0) {
                Log.i("cosas", length+" 1 ");
                outputstream.write(buffer,0,length);
            }

            inputstream.close();
            outputstream.flush();
            outputstream.close();
            Toast.makeText(getApplicationContext(), "BASE DE DATOS COPIADA", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


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

        ImageButton ibtnAñadirLista=(ImageButton)findViewById(R.id.ibtnAñadir_MisListas);
        ibtnAñadirLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento=new Intent(getApplicationContext(),Activity_NuevaLista.class);
                intento.putExtra(CATEGORIAS,cat);
                startActivityForResult(intento,COD_ADDLISTA);
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this,"BEEEEEE",Toast.LENGTH_LONG).show();
        switch(item.getItemId()){
            case R.id.menuCategorias:

                Intent gCategorias=new Intent(getApplicationContext(), Activity_GestionCategoria.class);
                //modificarArticulo.putExtra("titulo", articuloSeleccionado.getNombre());

                if(cat==null){
                    cat=new ArrayList<Categoria>();
                }

                gCategorias.putExtra(CATEGORIAS,cat);
                startActivityForResult(gCategorias,COD_GCAT);
                //startActivity(gCategorias);
                return true;
            case R.id.ajustes:

                Intent ajustes=new Intent(getApplicationContext(), Preferencias_Ajustes.class);
                //modificarArticulo.putExtra("titulo", articuloSeleccionado.getNombre());
                //startActivityForResult(login,COD_LOGIN);
                startActivity(ajustes);
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
/*
        if(cat!=null) {
            listas = baseDatos.obterListas(cat);

            for (Lista l : listas) {
                ArrayList<Articulo> articulos = baseDatos.obterArticulos(l.getId());
                l.setArticulos(articulos);
            }
*/
        if(cat!=null) {
            listas = baseDatos.obterListas(cat);
            for(Lista l:listas){
                ArrayList<Articulo> articulos=baseDatos.obterArticulos(l.getId());
                if(articulos!=null) {
                    for (Articulo a:articulos){
                        Log.i("addArticulos", a.getNombre());
                    }
                    l.setArticulos(articulos);
                }
            }
/*
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
*/


            miAdaptadorMisListas = new Adaptador_MisListas(this, listas);
            lista.setAdapter(miAdaptadorMisListas);
        }
        }


    private void cargarCategorias(){
        Spinner categorias=findViewById(R.id.spnCategorias_mislistas);

        Log.i("cosas", "cargarCategorias: antes try ");
        try{
            Log.i("cosas", "cargarCategorias: ");
            cat=baseDatos.obterCategorias();

            miAdaptador = new Adaptador_Categorias(this, cat);
//            Log.i("uno", cat.size()+"");
            categorias.setAdapter(miAdaptador);
        }catch(Exception e){
            cat=null;
        }

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
                if (data.hasExtra(Activity_Lista.LISTAENVIADA)) {
                    //Toast.makeText(this, "Saíches da actividade secundaria sen premer o botón Pechar", Toast.LENGTH_SHORT).show();

                    Lista l=(Lista)data.getSerializableExtra(LISTAENVIADA);
                    //Toast.makeText(this, articulos2.size()+"tam", Toast.LENGTH_SHORT).show();
                    /*
                    /*
                    */


                    //Toast.makeText(getApplicationContext(),l.getNombre(),Toast.LENGTH_SHORT).show();
                    listas.remove(pos);
                    listas.add(pos,l);
                    miAdaptadorMisListas.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),l.getNombre(),Toast.LENGTH_SHORT).show();

                }

            }
        }

        if (requestCode == COD_ADDLISTA) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(NUEVALISTA)) {
                    Lista l=(Lista)data.getSerializableExtra(NUEVALISTA);
                    listas.add(l);



                    miAdaptadorMisListas.notifyDataSetChanged();
                    //Toast.makeText(getApplicationContext(),l.getNombre(),Toast.LENGTH_SHORT).show();

                }

            }
        }

        if(requestCode == COD_GCAT){
            if(resultCode == RESULT_OK){
                if(data.hasExtra(CATEGORIAS)){
                    Spinner categorias=findViewById(R.id.spnCategorias_mislistas);
                    cat=(ArrayList<Categoria>)data.getSerializableExtra(CATEGORIAS);
                    miAdaptador = new Adaptador_Categorias(this, cat);
                    categorias.setAdapter(miAdaptador);

                }
            }
        }
    }


    private boolean existeBD(){
        String bddestino = "/data/data/" + getPackageName() + "/databases/"
                + BaseDatos.NOME_BD;
        File file = new File(bddestino);
        if (file.exists()) {
            Toast.makeText(getApplicationContext(), "A BD NON SE VAI COPIAR. XA EXISTE", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
    @Override
    protected void onStart() {
        super.onStart();


        if(!existeBD()) {
            copiarBD();
        }
        baseDatos = baseDatos.getInstance(getApplicationContext());
        baseDatos.abrirBD();
        cargarCategorias();
        cargarListas();
    }
/*
    @Override
    protected void onStop() {

        super.onStop();
        if (baseDatos!=null){    // Pechamos a base de datos.
            Log.i("PROBA-1",String.valueOf(baseDatos.sqlLiteDB.isOpen()));

            baseDatos.pecharBD();
            baseDatos=null;
        }

    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (baseDatos!=null){    // Pechamos a base de datos.
//            Log.i("PROBA-1",String.valueOf(baseDatos.sqlLiteDB.isOpen()));

            baseDatos.pecharBD();
            baseDatos=null;
        }
    }

    public void pedirPermiso(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CODIGO_IDENTIFICADOR);
            requestPermissions( new String[]{Manifest.permission.CAMERA},CODIGO_IDENTIFICADOR);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mislistas);

/*
        copiarBD();
        cargarListas();
        cargarCategorias();
*/
        gestionEventos();
        pedirPermiso();
    }
}
