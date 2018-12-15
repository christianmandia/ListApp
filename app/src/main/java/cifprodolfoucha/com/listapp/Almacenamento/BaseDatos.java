package cifprodolfoucha.com.listapp.Almacenamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Categoria;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Lista;

/**
 * @author Christian López Martín
 * @version 1
 **/

public class BaseDatos extends SQLiteOpenHelper implements Serializable{

    /**
     * PATH_BD é unha referencia á ruta onde se garda a bd internamente.
     **/
    public final static String PATH_BD="/data/data/cifprodolfoucha.com.listapp/databases";
    /**
     * NOME_BD é unha referencia ó nome da base de datos.
     **/
    public final static String NOME_BD="ListApp.db";
    /**
     * VERSION_BD é unha referenci á version da base de datos.
     **/
    public final static int VERSION_BD=1;
    /**
     * sInstance será unha referencia para as instancias que se fagan á base de datos, para ter sempre o mesmo acceso.
     **/
    private static BaseDatos sInstance;
    /**
     * sqlLiteDB será unha referencia ó obxeto que se encarga das consultas á base de datos.
     **/
    public SQLiteDatabase sqlLiteDB;
    /**
     * TABOA_LISTAS é unha referencia ó nome da taboa lista da base de datos.
     **/
    private final String TABOA_LISTAS="Lista";
    /**
     * TABOA_CATEGORIAS é unha referencia ó nome da taboa categoria da base de datos.
     **/
    private final String TABOA_CATEGORIAS="Categoria";
    /**
     * TABOA_ARTICULOS é unha referencia ó nome da taboa artículo da base de datos.
     **/
    private final String TABOA_ARTICULOS="Articulo";


    public BaseDatos(Context context) {
        super(context, NOME_BD, null, VERSION_BD);
        // TODO Auto-generated constructor stub
    }

    /**
     *  É o que devolverá a instancia da base de datos.
     * @param context
     * @return a instancia da base de datos para que outras acitivities podan facer uso dos métodos de esta clase.
     **/
    public static synchronized BaseDatos getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BaseDatos(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Modifica unha categoría da base de datos.
     * @param idC o id da categoría a modificar.
     * @param nMC o nome que se lle quere dar á categoría.
     * @return o número de filas afectadas pola consulta.
     **/
    public int modificarCategoria(int idC, String nMC){
        ContentValues datos = new ContentValues();
        datos.put("nombre_categoria",nMC);
        String condicionwhere = "id_categoria=?";
        String[] parametros = new String[]{idC+""};
        int res = sqlLiteDB.update(TABOA_CATEGORIAS,datos,condicionwhere,parametros);
        return res;
    }

    /**
     * Elimina unh categoría da base de datos.
     * @param idC o id da categoría a eliminar.
     * @return o número de filas eliminadas.
     **/
    public int eliminarCategoria(int idC){
        String condicionwhere = "id_categoria=?";
        String[] parametros = new String[]{idC+""};
        int rexistrosafectados = sqlLiteDB.delete(TABOA_CATEGORIAS,condicionwhere,parametros);
        return rexistrosafectados;
    }

    /**
     * Consulta para obter o número de listas asociadas a unha categoría.
     * @param categoria a Categoría pola cal se quere consultar.
     * @return o número de listas que teñen a categoría.
     **/
    public int obterNumListas(Loxica_Categoria categoria) {
        int res=0;
        String[] parametros = new String[]{categoria.getId()+""};
        Cursor datosListas = sqlLiteDB.rawQuery("select count(*) from lista l inner join categoria c on l.id_categoria=c.id_categoria where l.id_lista=?", parametros);
        if (datosListas.moveToFirst()) {
            while (!datosListas.isAfterLast()) {
                res=datosListas.getInt(0);
                datosListas.moveToNext();
            }
        }
        return res;
    }

    /**
     * Engade unha lista á base de datos.
     * @param nL nome da lista a engadir.
     * @param idC id da categoía á que se asociará á lista.
     * @return Un long que é o numero de fila da insercion.
     **/
    public long engadirLista(String nL,int idC){
        ContentValues valores = new ContentValues();
        valores.put("nombre_lista",nL);
        valores.put("id_categoria", idC);
        long id = sqlLiteDB.insert(TABOA_LISTAS,null,valores);

        return id;
    }

    /**
     * Engade unha categoría á base de datos.
     * @param c a categoría que se quere engadir.
     * @return Un long que é o numero de fila da insercion.
     **/
    public long engadirCategoria(Loxica_Categoria c){
        ContentValues valores = new ContentValues();
        valores.put("id_categoria",c.getId());
        valores.put("nombre_categoria", c.getNombre());
        long id = sqlLiteDB.insert(TABOA_CATEGORIAS,null,valores);
        return id;
    }

    /*
    public long engadirCategoria(int size,String nC){
        size+=1;
        ContentValues valores = new ContentValues();
        valores.put("id_categoria",size);
        valores.put("nombre_categoria", nC);
        long id = sqlLiteDB.insert(TABOA_CATEGORIAS,null,valores);

        return id;
    }
    */

    /**
     * Engade unha categoría á base de datos.
     * @param nC nome da categoría que se quere engadir.
     * @return Un long que é o numero de fila da insercion.
     **/
    public long engadirCategoria(String nC){
        ContentValues valores = new ContentValues();
        //valores.put("id_categoria",null);
        valores.put("nombre_categoria", nC);
        long id = sqlLiteDB.insert(TABOA_CATEGORIAS,null,valores);

        return id;
    }

    /**
     * Engade un artículo á base de datos.
     * @param a o obxeto Artículo cos datos que se queren engadir.
     * @param idL o id da Lista á que irá asociado o artículo.
     * @return Un long que é o numero de fila da insercion.
     **/
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
        long id = sqlLiteDB.insert(TABOA_ARTICULOS,null,valores);

        return id;
    }

    /**
     * Marcará un artículo como comprado.
     * @param idA o id do artículo a modificar.
     * @param idL o id da lista asociada ao artículo.
     **/
    public void setComprado(int idA,int idL){
        ContentValues datos = new ContentValues();
        int comprado =1;
        datos.put("comprado",comprado+"");
        String condicionwhere = "id_articulo=? and id_lista=?";
        String[] parametros = new String[]{idA+"",idL+""};
        int res = sqlLiteDB.update(TABOA_ARTICULOS,datos,condicionwhere,parametros);
    }

    /**
     * marcará un artículo como non comprado.
     * @param idA o id do artículo a modificar.
     * @param idL o id da lista asociada ao artículo.
     **/
    public void setNoComprado(int idA,int idL){
        ContentValues datos = new ContentValues();
        int comprado=0;
        datos.put("comprado",comprado+"");
        String condicionwhere = "id_articulo=? and id_lista=?";
        String[] parametros = new String[]{idA+"",idL+""};
        int res = sqlLiteDB.update(TABOA_ARTICULOS,datos,condicionwhere,parametros);
    }

    /**
     * Elimina un Artículo da base de datos.
     * @param a O artículo a eliminar.
     * @param idL o id da lista asociada ao arículo.
     **/
    public void eliminarArticulo(Loxica_Articulo a, int idL){
        String condicionwhere = "id_articulo=? and id_lista=?";
        String[] parametros = new String[]{a.getId()+"",idL+""};
        int rexistrosafectados = sqlLiteDB.delete(TABOA_ARTICULOS,condicionwhere,parametros);
    }

    /**
     * Elimina unha lista da base de datos.
     * @param idL o id da lista a elimina.
     * @return o número de dilas afectadas pola consulta.
     */
    public int eliminarLista(int idL){
        String condicionwhere = "id_lista=?";
        String[] parametros = new String[]{idL+""};
        int rexistrosafectados = sqlLiteDB.delete(TABOA_LISTAS,condicionwhere,parametros);
        return rexistrosafectados;
    }

    /**
     * Obten os artículos asociados a unha lista.
     * @param idL o id da lista da que se queren obter os artículos.
     * @return un ArrayList cos artículos da lista.
     **/
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

    /**
     * Xera un id para un artículo a partir do maior id de artículo asociado a unha lista.
     * @param idL id da lista da cal obter os artículo.
     * @return un novo id para un artículo.
     **/
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

    /**
     * Obtén as cateorías da base de datos.
     * @return un ArrayList coas categorías almacenadasa na base de datos.
     **/
    public ArrayList<Loxica_Categoria> obterCategorias() {
        ArrayList<Loxica_Categoria> categorias = new ArrayList<Loxica_Categoria>();

        Cursor datosCategorias = sqlLiteDB.rawQuery("select * from Categoria", null);
        if (datosCategorias.moveToFirst()) {
            Loxica_Categoria categoria;
            while (!datosCategorias.isAfterLast()) {
                categoria = new Loxica_Categoria(datosCategorias.getInt(0),
                        datosCategorias.getString(1));
                categorias.add(categoria);
                datosCategorias.moveToNext();
            }
        }
        return categorias;
    }

    /**
     * Modifica un artículo na base de datos
     * @param idA o id do artíuclo a modificar.
     * @param idL o id da lista á cal está asociado o atíuculo.
     * @param nA o nome do artículo.
     * @param cant a cantidade do artículo.
     * @param p o precio do artículo.
     * @param notas as notas do artículo.
     * @param img a ruta da imaxe do art´culo.
     * @param comp se o artículo foi comprado ou non.
     * @return o número de filas afectadas.
     **/
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

/*
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
*/

    /**
     * Obten unha lista a partir do nome e a categoría.
     * @param nL o nome da lista que se quere buscar.
     * @param c a Categoríada lista.
     * @return a lista atopada.
     **/
    public Loxica_Lista obterLista(String nL, Loxica_Categoria c) {
        Loxica_Lista lista =new Loxica_Lista();

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

    /**
     * Obten o id dunha categoría.
     * @param nC o nome da categoría a buscar.
     * @return o id da categoría.
     **/
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

    /**
     * Obten unha categoría a partir dun id.
     * @param id o id da categoría a buscar.
     * @return a categoría con dito id.
     **/
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

    /**
     * Obtén unha categoría a partir dun nome.
     * @param nC o nome da Categoría.
     * @return a Categoria co nome .
     **/
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

    /**
     * Obten ás listas que están asociadas a unhas categoría.
     * @param categorias As categorías das cales se queren obter as listas.
     * @return un ArrayList coas Listas asociadas á categoría.
     **/
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

    /**
     * Obten ás listas que están asociadas a unha categoría.
     * @param categoria A categoría pola cal se queren filtrar as listas.
     * @return un ArrayList coas Listas asociadas á categoría.
     **/
    public ArrayList<Loxica_Lista> obterListas(Loxica_Categoria categoria) {
        ArrayList<Loxica_Lista> listas = new ArrayList<Loxica_Lista>();
        ArrayList<Loxica_Articulo> articulos;

        String[] parametros = new String[]{categoria.getId()+""};
        Cursor datosListas = sqlLiteDB.rawQuery("select l.*,c.id_categoria from Lista l inner join Categoria c on l.id_categoria=c.id_categoria where c.id_categoria like ?", parametros );
        if (datosListas.moveToFirst()) {
            Loxica_Lista lista;
            while (!datosListas.isAfterLast()) {

                articulos =new ArrayList<Loxica_Articulo>();
                    if(datosListas.getInt(2)== categoria.getId()) {
                        lista = new Loxica_Lista(datosListas.getInt(0),
                                datosListas.getString(1), categoria, articulos);
                        listas.add(lista);
                    }
                datosListas.moveToNext();
            }
        }
        return listas;
    }

    /**
     * Abre a instancia da base de datos.
     **/
    public void abrirBD(){
        if (sqlLiteDB==null || !sqlLiteDB.isOpen()){
            sqlLiteDB = sInstance.getWritableDatabase();
        }
    }

    /**
     * Pecha a instancia da base de datos.
     **/
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
