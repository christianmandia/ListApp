package cifprodolfoucha.com.listapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Modelos.Articulo;
import cifprodolfoucha.com.listapp.Modelos.Categoria;
import cifprodolfoucha.com.listapp.Modelos.Lista;

public class BaseDatos extends SQLiteOpenHelper implements Serializable{

    public final static String PATH_BD="/data/data/cifprodolfoucha.com.listapp/databases";
    public final static String NOME_BD="ListApp.db";
    public final static int VERSION_BD=1;
    private static BaseDatos sInstance;
    public SQLiteDatabase sqlLiteDB;

    private final String TABOA_LISTAS="Lista";
    private final String TABOA_CATEGORIAS="Categoria";
    private final String TABOA_ARTICULOS="Articulo";
    private final String CONSULTAR_LISTAS ="SELECT _id,nome FROM AULAS order by nome";


/*
    public static synchronized UD06_02_BaseDatos getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new UD06_02_BaseDatos(context.getApplicationContext());
        }
        return sInstance;
    }
*/
    public BaseDatos(Context context) {
        super(context, NOME_BD, null, VERSION_BD);
        // TODO Auto-generated constructor stub
    }

    public static synchronized BaseDatos getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BaseDatos(context.getApplicationContext());
        }
        return sInstance;
    }
/*
    public long engadirAula(Aulas aula_engadir){
        ContentValues valores = new ContentValues();
        valores.put("nome", aula_engadir.getNome());
        long id = sqlLiteDB.insert("AULAS",null,valores);

        return id;
    }

    public int borrarAula(Aulas aula){
        String condicionwhere = "_id=?";
        String[] parametros = new String[]{String.valueOf(aula.get_id())};
        int rexistrosafectados = sqlLiteDB.delete(TABOA_AULAS,condicionwhere,parametros);

        return rexistrosafectados;

    }

    public int modificarAula(Aulas aula_modificar){
        ContentValues datos = new ContentValues();
        datos.put("nome", aula_modificar.getNome());

        String where = "_id=?";
        String[] params = new String[]{String.valueOf(aula_modificar.get_id())};

        int rexistrosModificados = sqlLiteDB.update(TABOA_AULAS, datos, where, params);

        return rexistrosModificados;
    }
*/

    public long engadirLista(String nL,int idC){
        ContentValues valores = new ContentValues();
        //valores.put("id_categoria",null);
        valores.put("nombre_lista",nL);
        valores.put("id_categoria", idC);
        //Log.i("uno", "engadirLista: ");
        long id = sqlLiteDB.insert(TABOA_LISTAS,null,valores);
        //Log.i("aasa", id+"");

        return id;
    }

    public long engadirCategoria(int size,String nC){
        size+=1;
        ContentValues valores = new ContentValues();
        //valores.put("id_categoria",null);
        valores.put("id_categoria",size);
        valores.put("nombre_categoria", nC);
        Log.i("uno", "engadirCategoria: ");
        long id = sqlLiteDB.insert(TABOA_CATEGORIAS,null,valores);
        Log.i("aasa", id+"");

        return id;
    }

    public ArrayList<Articulo> obterArticulos(int id_lista) {
        ArrayList<Articulo> articulos = new ArrayList<Articulo>();

        Cursor datosArticulos = sqlLiteDB.rawQuery("select a.nombre_articulo,a.comprado,a.cantidad,a.precio,a.notas,a.imagen from Articulo a inner join Lista l on a.id_lista=l.id_lista where a.id_lista=?", null);
        if (datosArticulos.moveToFirst()) {
            Articulo articulo;
            while (!datosArticulos.isAfterLast()) {
                boolean comprado = datosArticulos.getInt(1) > 0;

                articulo = new Articulo(datosArticulos.getString(0),
                        comprado,datosArticulos.getInt(2),datosArticulos.getDouble(3),
                        datosArticulos.getString(4),datosArticulos.getString(5));
                articulos.add(articulo);
                datosArticulos.moveToNext();
            }
        }
        return articulos;
    }


    public ArrayList<Categoria> obterCategorias() {
        ArrayList<Categoria> categorias = new ArrayList<Categoria>();

        Log.i("cosas", "obterCategorias");
        Cursor datosCategorias = sqlLiteDB.rawQuery("select * from Categoria", null);
        if (datosCategorias.moveToFirst()) {
            Categoria categoria;
            while (!datosCategorias.isAfterLast()) {
                categoria = new Categoria(datosCategorias.getInt(0),
                        datosCategorias.getString(1));

                Log.i("cosas", "añadida");
                categorias.add(categoria);
                Log.i("cosas", categoria.getNombre().toString());
                datosCategorias.moveToNext();
            }
        }
        return categorias;
    }

    public Lista obterIdLista(String nL,int idC,Categoria C) {
        Lista lista=new Lista();

        String[] parametros = new String[]{nL,idC+""};
        Cursor datosListas = sqlLiteDB.rawQuery("select * from Lista where nombre_lista like ? and id_categoria=?", parametros);
        if (datosListas.moveToFirst()) {

            while (!datosListas.isAfterLast()) {
                lista = new Lista(datosListas.getInt(0),
                        datosListas.getString(1),C);
                datosListas.moveToNext();
            }
        }
        return lista;
    }

    public Lista obterLista(String nL,Categoria c) {
        Lista lista=new Lista();

        Log.i("datos", nL);
        Log.i("datos", c.getId()+"");
        String[] parametros = new String[]{nL,c.getId()+""};
        Cursor datosListas = sqlLiteDB.rawQuery("select * from Lista where nombre_lista like ? and id_categoria=?", parametros);
        if (datosListas.moveToFirst()) {

            while (!datosListas.isAfterLast()) {
                lista = new Lista(datosListas.getInt(0),
                        datosListas.getString(1));
                datosListas.moveToNext();
            }
        }
        return lista;
    }

    public int obterIdCategoria(String nC) {
        int id=0;

        String[] parametros = new String[]{nC};
        Cursor datosCategorias = sqlLiteDB.rawQuery("select id_categoria from Categoria where nombre_categoria like ?", parametros);
        if (datosCategorias.moveToFirst()) {

            while (!datosCategorias.isAfterLast()) {
                id = datosCategorias.getInt(0);
                datosCategorias.moveToNext();
            }
        }
        return id;
    }

    public Categoria obterCategoria(int id) {
        Categoria categoria=new Categoria();

        String[] parametros = new String[]{id+""};
        Cursor datosCategorias = sqlLiteDB.rawQuery("select * from Categoria where id_categoria=?", parametros);
        if (datosCategorias.moveToFirst()) {

            while (!datosCategorias.isAfterLast()) {
                categoria = new Categoria(datosCategorias.getInt(0),
                        datosCategorias.getString(1));
                datosCategorias.moveToNext();
            }
        }
        return categoria;
    }

    public Categoria obterCategoria(String nC) {
        Categoria categoria=new Categoria();

        String[] parametros = new String[]{nC};
        Cursor datosCategorias = sqlLiteDB.rawQuery("select * from Categoria where nombre_categoria like ?", parametros);
        if (datosCategorias.moveToFirst()) {

            while (!datosCategorias.isAfterLast()) {
                categoria = new Categoria(datosCategorias.getInt(0),
                        datosCategorias.getString(1));
                datosCategorias.moveToNext();
            }
        }
        return categoria;
    }


    public ArrayList<Lista> obterListas(ArrayList<Categoria>categorias) {
        ArrayList<Lista> listas = new ArrayList<Lista>();

        Cursor datosListas = sqlLiteDB.rawQuery("select l.*,c.id_categoria from Lista l inner join Categoria c on l.id_categoria=c.id_categoria", null);
        if (datosListas.moveToFirst()) {
            Lista lista;
            while (!datosListas.isAfterLast()) {

                for(Categoria c:categorias) {
                    if(datosListas.getInt(2)==c.getId()) {
                        lista = new Lista(datosListas.getInt(0),
                                datosListas.getString(1));
                        listas.add(lista);
                    }
                }
                datosListas.moveToNext();
            }
        }
        return listas;
    }

    public boolean checkDataBase() {
        File dbFile = new File(PATH_BD + NOME_BD);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    public void abrirBD(){
        //if(checkDataBase()){
        if (sqlLiteDB==null || !sqlLiteDB.isOpen()){
            sqlLiteDB = sInstance.getWritableDatabase();
        }
        //}
    }

    public void pecharBD(){
        if (sqlLiteDB!=null && sqlLiteDB.isOpen()){
            sqlLiteDB.close();
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
