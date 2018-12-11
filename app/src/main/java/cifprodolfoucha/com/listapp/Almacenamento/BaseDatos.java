package cifprodolfoucha.com.listapp.Almacenamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Categoria;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Lista;

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

    public long engadirCategoria(Loxica_Categoria c){
        ContentValues valores = new ContentValues();
        //valores.put("id_categoria",null);
        valores.put("id_categoria",c.getId());
        valores.put("nombre_categoria", c.getNombre());
        Log.i("uno", c.getNombre());
        long id = sqlLiteDB.insert(TABOA_CATEGORIAS,null,valores);
        Log.i("uno", id+"");

        return id;
    }

    public long engadirCategoria(int size,String nC){
        size+=1;
        ContentValues valores = new ContentValues();
        //valores.put("id_categoria",null);
        valores.put("id_categoria",size);
        valores.put("nombre_categoria", nC);
        Log.i("uno", nC);
        long id = sqlLiteDB.insert(TABOA_CATEGORIAS,null,valores);
        Log.i("aasa", id+"");

        return id;
    }

    public long engadirCategoria(String nC){
        ContentValues valores = new ContentValues();
        //valores.put("id_categoria",null);
        valores.put("nombre_categoria", nC);
        Log.i("uno", nC);
        long id = sqlLiteDB.insert(TABOA_CATEGORIAS,null,valores);
        Log.i("aasa", id+"");

        return id;
    }

    public long engadirArticulo(Loxica_Articulo a, int idL){
        ContentValues valores = new ContentValues();
        valores.put("id_articulo",a.getId());
        valores.put("id_lista",idL);
        valores.put("nombre_articulo", a.getNombre());
        valores.put("cantidad",a.getCantidad());
        valores.put("precio",a.getPrecio());
        valores.put("notas",a.getNotas());
        valores.put("imagen",a.getRutaImagen());
        valores.put("comprado",a.isSeleccionado());
        //Log.i("prueba", "engadirCategoria: ");
        long id = sqlLiteDB.insert(TABOA_ARTICULOS,null,valores);
        //Log.i("prueba", id+"");

        return id;
    }

    public void setComprado(int idA,int idL){
        ContentValues datos = new ContentValues();
        int comprado =1;
        datos.put("comprado",comprado+"");
        String condicionwhere = "id_articulo=? and id_lista=?";
        String[] parametros = new String[]{idA+"",idL+""};
        int res = sqlLiteDB.update(TABOA_ARTICULOS,datos,condicionwhere,parametros);
    }

    public void setNoComprado(int idA,int idL){
        ContentValues datos = new ContentValues();
        int comprado=0;
        datos.put("comprado",comprado+"");
        String condicionwhere = "id_articulo=? and id_lista=?";
        String[] parametros = new String[]{idA+"",idL+""};
        int res = sqlLiteDB.update(TABOA_ARTICULOS,datos,condicionwhere,parametros);
    }

    public void eliminarArticulo(Loxica_Articulo a, int idL){
        String condicionwhere = "id_articulo=? and id_lista=?";
        String[] parametros = new String[]{a.getId()+"",idL+""};
        int rexistrosafectados = sqlLiteDB.delete(TABOA_ARTICULOS,condicionwhere,parametros);
    }

    public ArrayList<Loxica_Articulo> obterArticulos(int idL) {
        ArrayList<Loxica_Articulo> articulos = new ArrayList<Loxica_Articulo>();

        String[] parametros = new String[]{idL+""};
        Cursor datosArticulos = sqlLiteDB.rawQuery("select a.id_articulo,a.nombre_articulo,a.comprado,a.cantidad,a.precio,a.notas,a.imagen from Articulo a inner join Lista l on a.id_lista=l.id_lista where a.id_lista=?", parametros);
        if (datosArticulos.moveToFirst()) {
            Loxica_Articulo articulo;
            while (!datosArticulos.isAfterLast()) {
                boolean comprado = datosArticulos.getInt(2) > 0;

                articulo = new Loxica_Articulo(datosArticulos.getInt(0),datosArticulos.getString(1),
                        comprado,datosArticulos.getInt(3),datosArticulos.getDouble(4),
                        datosArticulos.getString(5),datosArticulos.getString(6));
                articulos.add(articulo);
                datosArticulos.moveToNext();
            }
        }
        return articulos;
    }

    public int obterNovoIdArticulo(int idL) {
        int id=0;
        String[] parametros = new String[]{idL+""};
        Cursor datosArticulos = sqlLiteDB.rawQuery("select id_articulo from Articulo where id_lista=?", parametros);
        if (datosArticulos.moveToFirst()) {
            while (!datosArticulos.isAfterLast()) {
                if(datosArticulos.getInt(0)>id){
                    id=datosArticulos.getInt(0);
                }
                datosArticulos.moveToNext();
            }
        }
        return id+1;
    }

    public ArrayList<Loxica_Categoria> obterCategorias() {
        ArrayList<Loxica_Categoria> categorias = new ArrayList<Loxica_Categoria>();

        Log.i("cosas", "obterCategorias");
        Cursor datosCategorias = sqlLiteDB.rawQuery("select * from Categoria", null);
        if (datosCategorias.moveToFirst()) {
            Loxica_Categoria categoria;
            while (!datosCategorias.isAfterLast()) {
                categoria = new Loxica_Categoria(datosCategorias.getInt(0),
                        datosCategorias.getString(1));

                Log.i("cosas", "añadida");
                categorias.add(categoria);
                Log.i("cosas", categoria.getNombre().toString());
                datosCategorias.moveToNext();
            }
        }
        return categorias;
    }

    public int modArticulo(int idA,int idL, String nA,int cant,double p,String notas,String img,boolean comp){
        int comprado=0;
        if(comp){
            comprado=1;
        }else{
            comprado=0;
        }

        ContentValues datos = new ContentValues();
        datos.put("nombre_articulo",nA);
        datos.put("cantidad",cant);
        datos.put("precio",p);
        datos.put("notas",notas);
        datos.put("imagen",img);
        datos.put("comprado",comprado+"");
        String condicionwhere = "id_articulo=? and id_lista=?";
        String[] parametros = new String[]{idA+"",idL+""};
        int res = sqlLiteDB.update(TABOA_ARTICULOS,datos,condicionwhere,parametros);

        return res;
    }

    public Loxica_Lista obterIdLista(String nL, int idC, Loxica_Categoria C) {
        Loxica_Lista lista =new Loxica_Lista();

        String[] parametros = new String[]{nL,idC+""};
        Cursor datosListas = sqlLiteDB.rawQuery("select * from Lista where nombre_lista like ? and id_categoria=?", parametros);
        if (datosListas.moveToFirst()) {

            while (!datosListas.isAfterLast()) {
                lista = new Loxica_Lista(datosListas.getInt(0),
                        datosListas.getString(1),C,new ArrayList<Loxica_Articulo>());
                datosListas.moveToNext();
            }
        }
        return lista;
    }

    public Loxica_Lista obterLista(String nL, Loxica_Categoria c) {
        Loxica_Lista lista =new Loxica_Lista();

        Log.i("datos", nL);
        Log.i("datos", c.getId()+"");
        String[] parametros = new String[]{nL,c.getId()+""};
        Cursor datosListas = sqlLiteDB.rawQuery("select * from Lista where nombre_lista like ? and id_categoria=?", parametros);
        if (datosListas.moveToFirst()) {

            while (!datosListas.isAfterLast()) {
                lista = new Loxica_Lista(datosListas.getInt(0),
                        datosListas.getString(1),c,new ArrayList<Loxica_Articulo>());
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

    public Loxica_Categoria obterCategoria(int id) {
        Loxica_Categoria categoria =new Loxica_Categoria();

        String[] parametros = new String[]{id+""};
        Cursor datosCategorias = sqlLiteDB.rawQuery("select * from Categoria where id_categoria=?", parametros);
        if (datosCategorias.moveToFirst()) {

            while (!datosCategorias.isAfterLast()) {
                categoria = new Loxica_Categoria(datosCategorias.getInt(0),
                        datosCategorias.getString(1));
                datosCategorias.moveToNext();
            }
        }
        return categoria;
    }

    public Loxica_Categoria obterCategoria(String nC) {
        Loxica_Categoria categoria =new Loxica_Categoria();

        String[] parametros = new String[]{nC};
        Cursor datosCategorias = sqlLiteDB.rawQuery("select * from Categoria where nombre_categoria like ?", parametros);
        if (datosCategorias.moveToFirst()) {

            while (!datosCategorias.isAfterLast()) {
                categoria = new Loxica_Categoria(datosCategorias.getInt(0),
                        datosCategorias.getString(1));
                datosCategorias.moveToNext();
            }
        }
        return categoria;
    }


    public ArrayList<Loxica_Lista> obterListas(ArrayList<Loxica_Categoria> categorias) {
        ArrayList<Loxica_Lista> listas = new ArrayList<Loxica_Lista>();
        ArrayList<Loxica_Articulo> articulos;

        Cursor datosListas = sqlLiteDB.rawQuery("select l.*,c.id_categoria from Lista l inner join Categoria c on l.id_categoria=c.id_categoria", null );
        if (datosListas.moveToFirst()) {
            Loxica_Lista lista;
            while (!datosListas.isAfterLast()) {

                for(Loxica_Categoria c: categorias) {
                    articulos =new ArrayList<Loxica_Articulo>();
                    if(datosListas.getInt(2)==c.getId()) {
                        lista = new Loxica_Lista(datosListas.getInt(0),
                                datosListas.getString(1), c, articulos);
                        listas.add(lista);
                    }
                }
                datosListas.moveToNext();
            }
        }
        return listas;
    }
    public ArrayList<Loxica_Lista> obterListas(Loxica_Categoria categoria) {
        ArrayList<Loxica_Lista> listas = new ArrayList<Loxica_Lista>();
        ArrayList<Loxica_Articulo> articulos;

        String[] parametros = new String[]{categoria.getId()+""};
        Cursor datosListas = sqlLiteDB.rawQuery("select l.*,c.id_categoria from Lista l inner join Categoria c on l.id_categoria=c.id_categoria where c.id_categoria like ?", parametros );
        if (datosListas.moveToFirst()) {
            Loxica_Lista lista;
            while (!datosListas.isAfterLast()) {

//                for(Loxica_Categoria c:categorias) {
                articulos =new ArrayList<Loxica_Articulo>();
                    if(datosListas.getInt(2)== categoria.getId()) {
                        lista = new Loxica_Lista(datosListas.getInt(0),
                                datosListas.getString(1), categoria, articulos);
                        listas.add(lista);
                    }
//                }
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
