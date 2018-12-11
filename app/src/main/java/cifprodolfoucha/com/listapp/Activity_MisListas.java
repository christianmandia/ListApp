package cifprodolfoucha.com.listapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
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
import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Categoria;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Lista;


public class Activity_MisListas extends Activity {

    private BaseDatos baseDatos;
    private ArrayList<Loxica_Categoria> cat;
    private ArrayList<Loxica_Lista> listas =new ArrayList<Loxica_Lista>();
    Menu m=null;
    private Loxica_Lista lista1;
    private int pos;
    private Adaptador_Categorias miAdaptador;
    private Adaptador_MisListas miAdaptadorMisListas;
    private ListView lista;
    //private int COD_LOGIN=30;
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
                Loxica_Lista lista1 = listas.get(position);
                pos=position;
                Intent intento=new Intent(getApplicationContext(),Activity_Lista.class);
                intento.putExtra("list", lista1);
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
        final Spinner spinerCat=(Spinner)findViewById(R.id.spnCategorias_mislistas);
        spinerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(view!=null) {
                    //String nomeCategoria = ((TextView)((ConstraintLayout)view).getViewById(R.id.tvNombre_Categoria)).getText().toString();
                    String nomeCategoria = ((TextView) view).getText().toString();
                    Loxica_Categoria c = baseDatos.obterCategoria(nomeCategoria);
                    if (c.getId() != 0) {
                        cargarListas(c);
                    } else {
                        cargarListas();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                genDialogs(ELIMINAR);
                /*
                listas.remove(pos);
                miAdaptadorMisListas.notifyDataSetChanged();
                //miAdaptadorMisListas.remove(miAdaptadorMisListas.getItem(info.position));
                miAdaptadorMisListas.setNotifyOnChange(true);
                */
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
    public synchronized boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this,"BEEEEEE",Toast.LENGTH_LONG).show();
        switch(item.getItemId()){
            case R.id.menuCategorias:

                Intent gCategorias=new Intent(getApplicationContext(), Activity_GestionCategoria.class);
                //modificarArticulo.putExtra("titulo", articuloSeleccionado.getNombre());

                if(cat==null){
                    cat=new ArrayList<Loxica_Categoria>();
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
            case R.id.descargarCategorias:

                thread = new Thread(){

                    @Override
                    public void run(){
                        descargarArquivo();
                    }
                };

                genDialogs(PROGRESS);
                thread.start();

                if ((miñaTarefa==null) || (miñaTarefa.getStatus()== AsyncTask.Status.FINISHED)){
                    miñaTarefa = new MiñaTarefa();
                    miñaTarefa.execute();
                }

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    lerArquivo();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }


                if(!erro.equals("")) {
                    Toast.makeText(getApplicationContext(), erro, Toast.LENGTH_LONG).show();
                }
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }


    private static final int TEMPO_FINAL = 10;
    private MiñaTarefa miñaTarefa;

    private class MiñaTarefa extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            for (int i = 1; i <= TEMPO_FINAL; i++) {
                try {
                    Thread.sleep(2000);
                    dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);

                if (isCancelled())
                    break;
            }
            return true;
        }
    };



    private AlertDialog.Builder d;
    private static AlertDialog dialog;
    private static final int ELIMINAR = 1;
    private static final int PROGRESS = 2;


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
            case PROGRESS:

                String infService = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(infService);
                // Inflamos o compoñente composto definido no XML
                d=new AlertDialog.Builder(this);
                View inflador = li.inflate(R.layout.dlg_progressbarr, null);
                d.setView(inflador);
                d.setTitle("Descargando");
                dialog=d.create();
                d.show();
                closeOptionsMenu();
                break;
        }

        return null;
    }
    protected void genDialogs(int id){
        switch(id){
            default:
                return;
            case ELIMINAR:
                d = new AlertDialog.Builder(this);
                d.setIcon(android.R.drawable.ic_dialog_info);
                d.setTitle("Eliminar");
                d.setMessage("Está seguro de que desea eliminar este elemento?");
                d.setCancelable(false);
                d.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {

                        //baseDatos.eliminarLista(listas.get(pos).getId());
                        listas.remove(pos);
                        miAdaptadorMisListas.notifyDataSetChanged();


                    }
                });
                d.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                    }
                });
                dialog=d.create();
                d.show();
                return;
                case PROGRESS:

                String infService = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(infService);
                // Inflamos o compoñente composto definido no XML
                d=new AlertDialog.Builder(this);
                View inflador = li.inflate(R.layout.dlg_progressbarr, null);
                d.setView(inflador);
                d.setTitle("Descargando");
                dialog=d.create();
                //d.show();
                dialog.show();
                    ///

                    //
                break;
        }


    }
    private void setProgressDialog(boolean show){
        d= new AlertDialog.Builder(this);
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(infService);
        View inflador = li.inflate(R.layout.dlg_progressbarr, null);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        d.setView(inflador);
        Dialog dialog = d.create();
        if (show)dialog.show();
        else dialog.dismiss();
    }

            @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        m=menu;
        return true;
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    private void cargarListas(){
        ListView lista = findViewById(R.id.lvmislistas_mislistas);
/*
        if(cat!=null) {
            listas = baseDatos.obterListas(cat);

            for (Loxica_Lista l : listas) {
                ArrayList<Loxica_Articulo> articulos = baseDatos.obterArticulos(l.getId());
                l.setArticulos(articulos);
            }
*/
        if(cat!=null) {
            listas = baseDatos.obterListas(cat);
            for(Loxica_Lista l: listas){
                ArrayList<Loxica_Articulo> articulos =baseDatos.obterArticulos(l.getId());
                if(articulos !=null) {
                    for (Loxica_Articulo a: articulos){
                        Log.i("addArticulos", a.getNombre());
                    }
                    l.setArticulos(articulos);
                }
            }
/*
        ArrayList<Loxica_Articulo> articulos=new ArrayList<>();
        articulos.add(new Loxica_Articulo("pilas AA",false,1,0.5,""));
        articulos.add(new Loxica_Articulo("articulo2",true,3,15,""));
        articulos.add(new Loxica_Articulo("mazá",true,10,30,""));

        listas.add(new Loxica_Lista("Lista1",(new Loxica_Categoria("Categoria1","Imagen1")),articulos));

        ArrayList<Loxica_Articulo> articulos2=new ArrayList<>();
        articulos2.add(new Loxica_Articulo("articulo4",true,1,2,""));
        articulos2.add(new Loxica_Articulo("articulo5",true,1,20,""));
        articulos2.add(new Loxica_Articulo("articulo6",true,4,5,""));

        listas.add(new Loxica_Lista("Lista2",(new Loxica_Categoria("Categoria2","Imagen2")),articulos2));
*/


            miAdaptadorMisListas = new Adaptador_MisListas(this, listas);
            lista.setAdapter(miAdaptadorMisListas);
        }
        }
    private void cargarListas(Loxica_Categoria c){
        ListView lista = findViewById(R.id.lvmislistas_mislistas);
        if(cat!=null) {
            listas = baseDatos.obterListas(c);

            for(Loxica_Lista l: listas){
                ArrayList<Loxica_Articulo> articulos =baseDatos.obterArticulos(l.getId());
                if(articulos !=null) {
                    for (Loxica_Articulo a: articulos){
                        Log.i("addArticulos", a.getNombre());
                    }
                    l.setArticulos(articulos);
                }
            }
            miAdaptadorMisListas = new Adaptador_MisListas(this, listas);
            lista.setAdapter(miAdaptadorMisListas);
        }
    }

    private void cargarCategorias(){
        Spinner categorias=findViewById(R.id.spnCategorias_mislistas);

        Log.i("prueba", "cargarCategorias: antes try ");
        try{
            Log.i("prueba", "cargarCategorias: ");
            cat=baseDatos.obterCategorias();

            if(cat.size()==0){
                Loxica_Categoria c=new Loxica_Categoria(0,getResources().getString(R.string.nomeCategoria));
                baseDatos.engadirCategoria(c);
                cat.add(c);
            }

            Log.i("prueba", cat.size()+"");
            miAdaptador = new Adaptador_Categorias(this, cat);
//            Log.i("uno", cat.size()+"");
            categorias.setAdapter(miAdaptador);
        }catch(Exception e){
            cat=null;
            Log.i("prueba", "error");
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

                    Loxica_Lista l=(Loxica_Lista)data.getSerializableExtra(LISTAENVIADA);
                    //Toast.makeText(this, articulos2.size()+"tam", Toast.LENGTH_SHORT).show();
                    /*
                    /*
                    */


                    //Toast.makeText(getApplicationContext(),l.getNombre(),Toast.LENGTH_SHORT).show();
                    //listas=new ArrayList<Loxica_Lista>();
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
                    Loxica_Lista l=(Loxica_Lista)data.getSerializableExtra(NUEVALISTA);
                    listas.add(l);



                    miAdaptadorMisListas.notifyDataSetChanged();
                    //Toast.makeText(getApplicationContext(),l.getNombre(),Toast.LENGTH_SHORT).show();

                }

            }
        }

        if(requestCode == COD_GCAT){
            if(resultCode == RESULT_OK){
                cargarCategorias();
                /*
                if(data.hasExtra(CATEGORIAS)){

                    //Spinner categorias=findViewById(R.id.spnCategorias_mislistas);
                    //cat=(ArrayList<Loxica_Categoria>)data.getSerializableExtra(CATEGORIAS);
                    //miAdaptador = new Adaptador_Categorias(this, cat);
                    cargarCategorias();
                    //categorias.setAdapter(miAdaptador);

                }
                */
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

    private final String IMAXE_DESCARGAR="http://wiki.cifprodolfoucha.es/AplicacionesAndroid/20182019/ListApp.xml";
    private File newArquivo;
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

    private File directorio,ficheiros;
    private String erro="";
    private void descargarArquivo() {

        directorio = new File(Environment.getExternalStorageDirectory(), "ListApp");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        ficheiros=new File(directorio, "documentos");
        if (!ficheiros.exists()) {
            ficheiros.mkdirs();
        }

        URL url=null;
        try {
            url = new URL(IMAXE_DESCARGAR);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return;
        }

        HttpURLConnection conn=null;
        String nomeArquivo = Uri.parse(IMAXE_DESCARGAR).getLastPathSegment();
        newArquivo = new File(ficheiros,nomeArquivo);
        if(newArquivo.exists()){
            //Toast.makeText(getApplicationContext(),"As categorias xa están importadas",Toast.LENGTH_SHORT).show();
            erro="As categorias xa están importadas";
            return;}
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
                erro="Erro de conexion";
                Log.i("COMUNICACION", "fallo");
                return;
            }

            OutputStream os = new FileOutputStream(newArquivo);
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
            erro="Categorias importadas";
            Log.i("COMUNICACION","ACABO");
        }
        catch (FileNotFoundException e) {
            erro="Non atopado ficheiro";
            Log.e("COMUNICACION",e.getMessage());
        } catch (IOException e) {
            erro="";
            e.printStackTrace();
            Log.e("COMUNICACION",e.getMessage());
        }

    }

    private ArrayList<Loxica_Categoria> cat2;
    private void lerArquivo() throws IOException, XmlPullParserException {

        cat2=new ArrayList<Loxica_Categoria>();
        InputStream is = new FileInputStream(newArquivo);

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");

        int evento = parser.nextTag();
        Loxica_Categoria c= null;

        while(evento != XmlPullParser.END_DOCUMENT) {
            if(evento == XmlPullParser.START_TAG) {

                if (parser.getName().equals("ListApp")) {

                    //Log.i("prueba", "NOOO");
                    evento=parser.nextTag();
                }
                if (parser.getName().equals("version_ficheiro")) {
                    //Log.i("prueba", Integer.parseInt(parser.nextText())+"");
                    int v=0,v2;

                    if (sp.contains("VERSION")) {
                        v=sp.getInt("VERSION",0);
                    }
                    if((v2=Integer.parseInt(parser.nextText()))<=v){
                        return;
                    }else{
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("VERSION", v2);
                        editor.commit();
                    }

                }
                 if (parser.getName().equals("categoria")) {
                     c = new Loxica_Categoria();
                     //evento = parser.nextTag();
                     c.setNombre(parser.nextText());
                     cat2.add(c);
                     //Log.i("prueba", c.getNombre()+"");
                     //evento=parser.nextTag();
                 }
            }
            if(evento == XmlPullParser.END_TAG) {
                if (parser.getName().equals("categoria")) {
                    //cat2.add(c);
                }
            }

            evento = parser.next();
        }

        is.close();

        for(Loxica_Categoria categoria :cat2){
            Log.i("prueba", categoria.getNombre()+"");

            baseDatos.engadirCategoria(categoria.getNombre());
        }
       cargarCategorias();

    }


    private void aplicarPreferencias() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Spinner spnC=(Spinner)findViewById(R.id.spnCategorias_mislistas);

        Boolean fondo= preferencias.getBoolean("preferencia_idFondo", false);
        String s=preferencias.getString("preferencia_idVersionCategorias","0");

        sp = getSharedPreferences(CAT_PREF, Context.MODE_PRIVATE);


        if(fondo){
            setTheme(R.style.Nocturno);
            constraintLayout.setBackgroundColor(Color.BLACK);
            spnC.setBackgroundColor(Color.DKGRAY);
        }else{
            setTheme(R.style.Diurno);
            constraintLayout.setBackgroundColor(Color.WHITE);
            spnC.setBackgroundColor(Color.DKGRAY);


        }
        //nome.setText(valorNome);


    }


    //private int spnPos;


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //baseDatos=(BaseDatos)savedInstanceState.getSerializable("baseDatos");
        //Spinner spnC=(Spinner)findViewById(R.id.spnCategorias_mislistas);
        //spnC.setSelection(savedInstanceState.getInt("spnPos"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable("baseDatos",baseDatos);
        //outState.putInt("spnPos",spnPos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicarPreferencias();
    }

    private static ConstraintLayout constraintLayout;
    public static final String CAT_PREF = "USER_PREF" ;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mislistas);
        constraintLayout = (ConstraintLayout) findViewById(R.id.bgFondo_MisLista);


/*
        copiarBD();
        cargarListas();
        cargarCategorias();
*/
        rexistarMenusEmerxentes();
        gestionEventos();
        pedirPermiso();
        //aplicarPreferencias();
    }

}
