package cifprodolfoucha.com.listapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    // Usado por si necesitamos diferentes permisos, para identificar cual de ellos es
    private final int CODIGO_PERMISO_ESCRITURA =1;
    private final int CODIGO_PERMISO_CAMARA=2;

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
/*
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //showDialog(ELIMINAR);
                pos=position;

                return false;
            }
        });
*/
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

    private void rexistarMenusEmerxentes(){
        ListView lv = findViewById(R.id.lvmislistas_mislistas);
        registerForContextMenu(lv);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        // Comprobamos se o menú contextual se lanzou sobre a etiqueta ou sobre
        // a lista
         if (v.getId() == R.id.lvmislistas_mislistas) {
            inflater.inflate(R.menu.menu_mislistas_emerxente, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        ListView lv = findViewById(R.id.lvmislistas_mislistas);


        switch (item.getItemId()) {

            // Ítems premidos sobre o TextView
            // Lanza un Toast coa opción do menú contextual que se seleccinou

            // Ítems premidos sobre o ListView
            case R.id.mniBorrarLista_MisListas:
                listas.remove(pos);
                miAdaptadorMisListas.notifyDataSetChanged();
                //miAdaptadorMisListas.remove(miAdaptadorMisListas.getItem(info.position));
                miAdaptadorMisListas.setNotifyOnChange(true);
                return true;

            case R.id.mniModificarLista_MisListas:
                /*
                adaptador.add(adaptador.getItem(info.position));
                adaptador.setNotifyOnChange(true);
                */
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
                /*
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
        */
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

/*
    public final static String Login= "login";
    public final static String Pass= "pass";
*/
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
                    //listas=new ArrayList<Lista>();
                    //cargarListas();
                    listas.remove(pos);
                    listas.add(pos,l);
                    miAdaptadorMisListas.notifyDataSetChanged();
                    //Toast.makeText(getApplicationContext(),l.getNombre(),Toast.LENGTH_SHORT).show();

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

    public void pedirPermiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, CODIGO_PERMISO_ESCRITURA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CODIGO_PERMISO_ESCRITURA: {
                // Se o usuario premeou o boton de cancelar o array volve cun null
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISO CONCEDIDO
                } else {
                    // PERMISO DENEGADO
                    Toast.makeText(this,"É NECESARIO O PERMISO PARA GARDAR A IMAXE NA SD CARD",Toast.LENGTH_LONG).show();
                }
                return;
            }

            // Comprobamos os outros permisos
            case CODIGO_PERMISO_CAMARA: {
                // Se o usuario premeou o boton de cancelar o array volve cun null
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISO CONCEDIDO
                } else {
                    // PERMISO DENEGADO
                    Toast.makeText(this,"É NECESARIO O PERMISO DA CAMARA",Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }


    public static enum TIPOREDE{MOBIL,ETHERNET,WIFI,SENREDE};
    private TIPOREDE conexion;

    private final String IMAXE_DESCARGAR="https://www.aspedrinas.com/imagenes/santiago/santiago1.jpg";
    private File rutaArquivo;
    private Thread thread;

    private TIPOREDE comprobarRede(){
        NetworkInfo networkInfo=null;

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            switch(networkInfo.getType()){
                case ConnectivityManager.TYPE_MOBILE:
                    return TIPOREDE.MOBIL;
                case ConnectivityManager.TYPE_ETHERNET:
                    // ATENCION API LEVEL 13 PARA ESTA CONSTANTE
                    return TIPOREDE.ETHERNET;
                case ConnectivityManager.TYPE_WIFI:
                    // NON ESTEAS MOITO TEMPO CO WIFI POSTO
                    // MAIS INFORMACION EN http://www.avaate.org/
                    return TIPOREDE.WIFI;
            }
        }
        return TIPOREDE.SENREDE;
    }


    private void descargarArquivo() {
        URL url=null;
        try {
            url = new URL(IMAXE_DESCARGAR);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
        }

        HttpURLConnection conn=null;
        String nomeArquivo = Uri.parse(IMAXE_DESCARGAR).getLastPathSegment();
        rutaArquivo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),nomeArquivo);
        try {

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);     /* milliseconds */
            conn.setConnectTimeout(15000);  /* milliseconds */
            conn.setRequestMethod("POST");
            conn.setDoInput(true);                  /* Indicamos que a conexión vai recibir datos */

            conn.connect();

            int response = conn.getResponseCode();
            if (response ==HttpURLConnection.HTTP_MOVED_TEMP){  // Se dera un código 302, sería necesario volver a descargar cunha uri nova que ven indicada no método getHeaderField("Location")
                // url = new URL(conn.getHeaderField("Location"));
                // Neste caso habería que refacer o método xa que teríamos que volver a poñer o mesmo código anterior de conexión pero con esta Url nova.
            }
            else if (response != HttpURLConnection.HTTP_OK){
                // Algo foi mal, deberíamos informar a Activity cunha mensaxe
                return;
            }

            OutputStream os = new FileOutputStream(rutaArquivo);
            InputStream in = conn.getInputStream();
            byte data[] = new byte[1024];   // Buffer a utilizar
            int count;
            while ((count = in.read(data)) != -1) {
                os.write(data, 0, count);
            }
            os.flush();
            os.close();
            in.close();
            conn.disconnect();
            Log.i("COMUNICACION","ACABO");
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e("COMUNICACION",e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("COMUNICACION",e.getMessage());
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
        rexistarMenusEmerxentes();
        gestionEventos();
        pedirPermiso();
    }
}
