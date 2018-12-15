package cifprodolfoucha.com.listapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
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


/**
 * @author Christian López Martín
 * @version 1
 **/

public class Activity_MisListas extends Activity {
    /**
     * baseDatos é un acceso á clase BaseDatos onde se xestionan as consultas á base de datos.
     **/
    private BaseDatos baseDatos;
    /**
     * cat é un ArrayList coas Categorias que mostra o spinner.
     **/
    private ArrayList<Loxica_Categoria> cat;
    /**
     * listas é o un ArayList coas listas que teñen a mesma categoría que o Spinner ten seleccionada.
     **/
    private ArrayList<Loxica_Lista> listas =new ArrayList<Loxica_Lista>();
    /**
     * pos é o número que representa a posicíon da lista dentro do ArrayList listas.
     **/
    private int pos;
    /**
     * Adaptador_Categorias é unha referencia global ao adaptador que utilizará o spinner das categorías.
     **/
    private Adaptador_Categorias Adaptador_Categorias;
    /**
     * Adaptador_MisListas é unha referencia global ao adaptador que utilizará o ListView das listas.
     **/
    private Adaptador_MisListas Adaptador_MisListas;
    /**
     * lista será unha referencia global ao ListView das listas.
     **/
    private ListView lista;
    /**
     * COD_GCAT é un numero que se utilizará para facer unha chamada a Activity_GestionCategoria e para recibir datos dela se fose necesario.
     **/
    private int COD_GCAT=35;
    /**
     * COD_ADDLISTA é un numero que se utilizará para facer unha chamada a Activity_NuevaLista e para recibir datos dela se fose necesario.
     **/
    private int COD_ADDLISTA=40;
    /**
     * COD_PETICION é un numero que se utilizará para facer unha chamada a Activity_Lista e para recibir datos dela se fose necesario.
     **/
    private static final int COD_PETICION = 33;
    /**
     * LISTAENVIADA é un String para utilizar como referencia ao envio do ArrayList listas a outras Activities ou para recibila de volta.
     **/
    public static String LISTAENVIADA= "lista";
    /**
     * CATEGORIAS é un String para utilizar como referencia ao envio do ArrayList cat a outras Activities.
     **/
    public static String CATEGORIAS= "categorias";
    /**
     * CODIGO_PERMISOS será o codigo que se utilizará para solicitar os permisos.
     **/
    private final int CODIGO_PERMISOS=1;
    /**
     * tarefaAsync será un fío secundario asíncrono para pechar o diálogo da ProgresBar.
     **/
    private TarefaAsync tarefaAsync;
    /**
     * TEMPO_FINAL será as veces que se execute un bucle do fío secundario asíncrono.
     **/
    private static final int TEMPO_FINAL = 10;
    /**
     * d será o builder dos AlertDialogs que se xerarán nesta Activity.
     **/
    private AlertDialog.Builder d;
    /**
     * dialog será o Dialogo da ProgresBar para poder Pechalo dende a o fío secundario.
     **/
    private static AlertDialog dialog;
    /**
     * ELIMINAR é o número de referencia para xerar o dialogo que aparecerá se queremos eliminar unha lista.
     **/
    private static final int ELIMINAR = 1;
    /**
     * PROGRESS é o número de referencia para xerar o dialogo da ProgresBar.
     **/
    private static final int PROGRESS = 2;
    /**
     * CATEGORIA é o número de referencia para xerar un diálogo de botóns se non
     **/
    private static final int CATEGORIA = 3;
    /**
     * IMAXE_DESCARGAR será un ficheiro que se descargará mendiante a enlace anterior, se decidimos descargalo.
     **/
    private final String IMAXE_DESCARGAR="http://wiki.cifprodolfoucha.es/AplicacionesAndroid/20182019/ListApp.xml";
    /**
     * newArquivo será un ficheiro que se descargará mendiante a enlace anterior, se decidimos descargalo.
     **/
    private File newArquivo;
    /**
     * thread será un fío secundario por onde se descargará un ficheiro, se decidimos descargalo.
     **/
    private Thread thread;
    /**
     * directorio será o cartafol da aplicación, onde se gardarán os documentos que se descarguen e a s imaxes.
     **/
    private File directorio;
    /**
     * ficheiros será o cartafol onde se gardarán os documentos.
     **/
    private File ficheiros;
    /**
     * msg é un String que se utilizará para mandar mensaxes por Toast ao usuario tras realizar algúns métodos.
     **/
    private String msg="";
    /**
     * cat2 é un ArrayList de Categorias que se utilizará para obter as Categorías dun ficheiro XML se decidimos descargalo.
     **/
    private ArrayList<Loxica_Categoria> cat2;
    /**
     * constraintLayout é unha referencia do layout da Activity para poder cambiar o fondo dependendo de se nos Axustes seleccionamos o modo noite.
     **/
    private static ConstraintLayout constraintLayout;
    /**
     * CAT_PREF é o nome do ficheiro onde se gardarán as referencias privadas (no caso desta Aplicación usaráse para gardar e ler a versión do documento XML se o chegamos a descargar).
     **/
    public static final String CAT_PREF = "CAT_PREF" ;
    /**
     * sp son as Preferencias compartidas que obteremos do ficheiro anteror.
     **/
    private SharedPreferences sp;


    /**
     * Realiza a copia, se non existe xa, da base de datos aloxada no cartafol Assets
     *          no cartafol databases da aplicación.
     *          (assets -> /data/data/cifprodolfoucha.com.listapp/databases)
     **/
    private void copiarBD() {
        String bddestino = "/data/data/" + getPackageName() + "/databases/"
                + BaseDatos.NOME_BD;
        File file = new File(bddestino);
        if (file.exists()) {
            //Toast.makeText(getApplicationContext(), "A BD NON SE VAI COPIAR. XA EXISTE", Toast.LENGTH_LONG).show();
            return;
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
                outputstream.write(buffer,0,length);
            }

            inputstream.close();
            outputstream.flush();
            outputstream.close();
            //Toast.makeText(getApplicationContext(), "BASE DE DATOS COPIADA", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            //Toast.makeText(getApplicationContext(), "Mensage de error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    /**
     * Controla os clicks que se realicen nos elementos da Activity.
     **/
    public void gestionEventos(){
        lista = findViewById(R.id.lvmislistas_mislistas);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){
                Loxica_Lista lista1 = listas.get(position);
                pos=position;
                Intent intento=new Intent(getApplicationContext(),Activity_Lista.class);
                intento.putExtra("list", lista1);
                startActivityForResult(intento,COD_PETICION);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                return false;
            }
        });

        ImageButton ibtnAñadirLista=(ImageButton)findViewById(R.id.ibtnAñadir_MisListas);
        ibtnAñadirLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cat.size()>1 && cat.get(0).getId()==0) {
                    Intent intento = new Intent(getApplicationContext(), Activity_NuevaLista.class);
                    intento.putExtra(CATEGORIAS, cat);
                    startActivityForResult(intento, COD_ADDLISTA);
                }else{
                    genDialogs(CATEGORIA);
                }
            }
        });
        final Spinner spinerCat=(Spinner)findViewById(R.id.spnCategorias_mislistas);
        spinerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(view!=null) {
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

    /**
     * É o metodo que creará e vinculará o menú contextual á ListView das listas.
     **/
    private void rexistarMenusEmerxentes(){
        ListView lv = findViewById(R.id.lvmislistas_mislistas);
        registerForContextMenu(lv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
         if (v.getId() == R.id.lvmislistas_mislistas) {
            inflater.inflate(R.menu.menu_mislistas_emerxente, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        ListView lv = findViewById(R.id.lvmislistas_mislistas);


        switch (item.getItemId()) {
            case R.id.mniBorrarLista_MisListas:
                genDialogs(ELIMINAR);
                return true;

            case R.id.mniInicializarLista_MisListas:
                Loxica_Lista listaSel=listas.get(pos);
                inicializar(listaSel);
                Adaptador_MisListas.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    /**
     * Rexistrará todos os artículos da lista onde se fixo o LongClick.
     * @param listaSel será a lista onde se fixo o LongClick.
     **/
    private void inicializar(Loxica_Lista listaSel) {
        for(Loxica_Articulo articuloL:listaSel.getArticulos()){
            baseDatos.setNoComprado(articuloL.getId(),listaSel.getId());
            articuloL.setSeleccionado(false);
        }
    }


    @Override
    public synchronized boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuCategorias:

                Intent gCategorias=new Intent(getApplicationContext(), Activity_GestionCategoria.class);

                if(cat==null){
                    cat=new ArrayList<Loxica_Categoria>();
                }

                gCategorias.putExtra(CATEGORIAS,cat);
                startActivityForResult(gCategorias,COD_GCAT);
                return true;
            case R.id.ajustes:

                Intent ajustes=new Intent(getApplicationContext(), Preferencias_Ajustes.class);
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

                if ((tarefaAsync ==null) || (tarefaAsync.getStatus()== AsyncTask.Status.FINISHED)){
                    tarefaAsync = new TarefaAsync();
                    tarefaAsync.execute();
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


                if(!msg.equals("")) {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.inicializarListas:
                for(Loxica_Lista l:listas){
                    inicializar(l);
                }
                Adaptador_MisListas.notifyDataSetChanged();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Clase que fai un fío secundario, que nesta Activity úsase para pechar o diálogo da ProgresBar.
     **/
    private class TarefaAsync extends AsyncTask<Void, Integer, Boolean> {

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


    /**
     * Xerará os diálogos que se utilizan nesta Activity.
     * @param id o id polo cal se xerará un tipo de diálogo ou outro.
     **/
    protected void genDialogs(int id){
        switch(id){
            default:
                return;
            case ELIMINAR:
                final Loxica_Lista listaE=listas.get(pos);

                if(listaE.TienesTodo()) {

                d = new AlertDialog.Builder(this);
                d.setIcon(android.R.drawable.ic_dialog_info);
                d.setTitle(R.string.str_mislistas_mensaxe_eliminar);
                d.setMessage(R.string.str_mislistas_mensaxe_eliminar2+listaE.getNombre()+" ?");
                d.setCancelable(false);
                d.setPositiveButton(R.string.str_all_mensaxe_si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {



                            for(Loxica_Articulo articuloLista:listaE.getArticulos()){
                                if(!articuloLista.getRutaImagen().equals("")){
                                    File foto=new File(articuloLista.getRutaImagen());
                                    foto.delete();
                                }

                                baseDatos.eliminarArticulo(articuloLista,listaE.getId());
                            }

                            int res = baseDatos.eliminarLista(listaE.getId());
                            if (res > 0) {

                                listas.remove(pos);
                                Adaptador_MisListas.notifyDataSetChanged();

                            } else {
                                Toast.makeText(getApplicationContext(), String.valueOf(R.string.str_mislistas_mensaxe_eliminarLista_erro1), Toast.LENGTH_SHORT).show();
                            }
                        }



                    });

                    d.setNegativeButton(R.string.str_all_mensaxe_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int boton) {
                        }
                    });
                    dialog=d.create();
                    d.show();
                }else{
                    Toast.makeText(getApplicationContext(), String.valueOf(R.string.str_mislistas_mensaxe_eliminarLista_erro2), Toast.LENGTH_SHORT).show();
                }
                return;
                case PROGRESS:

                String infService = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(infService);
                d=new AlertDialog.Builder(this);
                View inflador = li.inflate(R.layout.dlg_progressbarr, null);
                d.setView(inflador);
                d.setTitle(getResources().getString(R.string.ImportarCategorias));
                dialog=d.create();
                //d.show();
                dialog.show();
                    ///

                    //
                break;
            case CATEGORIA:

                d=new AlertDialog.Builder(this);
                d.setTitle(R.string.menu_botones_titulo)
                        .setItems(R.array.menu_botones, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int posicion) {
                                switch(posicion){
                                    case 0:
                                        thread = new Thread(){

                                            @Override
                                            public void run(){
                                                descargarArquivo();
                                            }
                                        };

                                        genDialogs(PROGRESS);
                                        thread.start();

                                        if ((tarefaAsync ==null) || (tarefaAsync.getStatus()== AsyncTask.Status.FINISHED)){
                                            tarefaAsync = new TarefaAsync();
                                            tarefaAsync.execute();
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
                                        break;
                                    case 1:
                                        Intent gCategorias=new Intent(getApplicationContext(), Activity_GestionCategoria.class);
                                        if(cat==null){
                                            cat=new ArrayList<Loxica_Categoria>();
                                        }

                                        gCategorias.putExtra(CATEGORIAS,cat);
                                        startActivityForResult(gCategorias,COD_GCAT);
                                        break;

                                    default:break;
                                }
                            }
                        });
                d.create();
                d.show();

                break;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    /**
     * Cargará as listas no ArrayList listas e as colocará na ListView se o ArrayList das categorías cat non é nulo.
     **/
    private void cargarListas(){
        ListView lista = findViewById(R.id.lvmislistas_mislistas);
        if(cat!=null) {
            listas = baseDatos.obterListas(cat);
            for(Loxica_Lista l: listas){
                ArrayList<Loxica_Articulo> articulos =baseDatos.obterArticulos(l.getId());
                if(articulos !=null) {
                    l.setArticulos(articulos);
                }
            }
            Adaptador_MisListas = new Adaptador_MisListas(this, listas);
            lista.setAdapter(Adaptador_MisListas);
        }
    }

    /**
     * Cargará as listas, que pertenzan á Categoría que se lle pasa,
     *      no ArrayList listas e as colocará na ListView se o ArrayList das categorías cat non é nulo.
     * @param c será a categoría pola cal se filtará na base de datos para oter as listas.
     **/
    private void cargarListas(Loxica_Categoria c){
        ListView lista = findViewById(R.id.lvmislistas_mislistas);
        if(cat!=null) {
            listas = baseDatos.obterListas(c);

            for(Loxica_Lista l: listas){
                ArrayList<Loxica_Articulo> articulos =baseDatos.obterArticulos(l.getId());
                if(articulos !=null) {
                    l.setArticulos(articulos);
                }
            }
            Adaptador_MisListas = new Adaptador_MisListas(this, listas);
            lista.setAdapter(Adaptador_MisListas);
        }
    }

    /**
     * Cargará o ArrayList cat e o Spinner coas categorias que obterá da base de datos.
     **/
    private void cargarCategorias(){
        Spinner categorias=findViewById(R.id.spnCategorias_mislistas);
        try{
            cat=baseDatos.obterCategorias();
            if(cat.size()==0){
                Loxica_Categoria c=new Loxica_Categoria(0,getResources().getString(R.string.nomeCategoria));
                baseDatos.engadirCategoria(c);
                cat.add(c);
            }
            Adaptador_Categorias = new Adaptador_Categorias(this, cat);
            categorias.setAdapter(Adaptador_Categorias);
        }catch(Exception e){
            cat=null;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COD_PETICION) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(Activity_Lista.LISTAENVIADA)) {
                    Loxica_Lista l=(Loxica_Lista)data.getSerializableExtra(LISTAENVIADA);
                    listas.remove(pos);
                    listas.add(pos,l);
                    Adaptador_MisListas.notifyDataSetChanged();
                }

            }
        }

        if (requestCode == COD_ADDLISTA) {
            cargarListas();
        }

        if(requestCode == COD_GCAT){
            cargarCategorias();
            /*
            if(resultCode == RESULT_OK){
            }
            */
        }
    }


    /**
     * Comproba se a base de datos xa existe.
     * @return true se a base de datos xa eiste.
     **/
    private boolean existeBD(){
        String bddestino = "/data/data/" + getPackageName() + "/databases/"
                + BaseDatos.NOME_BD;
        File file = new File(bddestino);
        if (file.exists()) {
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (baseDatos!=null){    // Pechamos a base de datos.
//            Log.i("PROBA-1",String.valueOf(baseDatos.sqlLiteDB.isOpen()));
            baseDatos.pecharBD();
            baseDatos=null;
        }
    }

    /**
     * Fai a petición dos permisos de escritura e de uso da cámara.
     **/
    public void pedirPermiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, CODIGO_PERMISOS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CODIGO_PERMISOS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this,String.valueOf(R.string.str_mislistas_mensaxe_permisos),Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    /**
     * Intenta descargar o ficheiro XML no cartafol documentos, dentro do cartafol creado para a aplicación.
     **/
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
        try {

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);     /* milliseconds */
            conn.setConnectTimeout(15000);  /* milliseconds */
            conn.setRequestMethod("POST");
            conn.setDoInput(true);                  /* Indicamos que a conexión vai recibir datos */

            conn.connect();

            int response = conn.getResponseCode();
            if (response ==HttpURLConnection.HTTP_MOVED_TEMP){

            }
            else if (response != HttpURLConnection.HTTP_OK){
                // Algo foi mal, deberíamos informar a Activity cunha mensaxe
                msg=String.valueOf(R.string.str_mislistas_mensaxe_descargaError);
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
            msg=String.valueOf(R.string.str_mislistas_categoriasImportadas);
        }
        catch (FileNotFoundException e) {
            //msg="Non atopado ficheiro";
        } catch (IOException e) {
            //msg="";
            e.printStackTrace();
        }

    }

    /**
     * Fai a lectura do ficheiro XML unha vez descargado e carga as categorías se a versión do XML é superior
     *              á gardada na preferencia.
     * @throws IOException
     * @throws XmlPullParserException
     **/
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
                    evento=parser.nextTag();
                }
                if (parser.getName().equals("version_ficheiro")) {
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
                     c.setNombre(parser.nextText());
                     cat2.add(c);
                 }
            }
            if(evento == XmlPullParser.END_TAG) {
                if (parser.getName().equals("categoria")) {
                }
            }
            evento = parser.next();
        }
        is.close();
        for(Loxica_Categoria categoria :cat2){
            baseDatos.engadirCategoria(categoria.getNombre());
        }
       cargarCategorias();
    }

    /**
     * Aplica a preferencia do Modo Nocturno e obten a preferencia onde se garda a versión do XMl.
     **/
    private void aplicarPreferencias() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Spinner spnC=(Spinner)findViewById(R.id.spnCategorias_mislistas);

        Boolean fondo= preferencias.getBoolean("preferencia_idFondo", false);

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
    }


    @Override
    protected void onResume() {
        super.onResume();
        aplicarPreferencias();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mislistas);
        constraintLayout = (ConstraintLayout) findViewById(R.id.bgFondo_MisLista);
        rexistarMenusEmerxentes();
        gestionEventos();
        pedirPermiso();
    }

}
