package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageButton;

import java.io.File;
import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_ListaRV;
import cifprodolfoucha.com.listapp.Adaptadores.ItemClickSupport;
import cifprodolfoucha.com.listapp.Almacenamento.BaseDatos;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Lista;

/**
 * @author Christian López Martín
 * @version 1
 **/

public class Activity_Lista extends Activity {

    /**
     * NEWArticulos é un String para utilizar comom referencia ao envio dun Articulo a outras Activities.
     **/
    public final static String NEWArticulos = "nuevo";
    /**
     * MODArticulo é un String para utilizar comom referencia ao envio dun Articulo a outras Activities.
     **/
    public final static String MODArticulo = "articuloModificado";
    /**
     * COD_PETICION é un numero que se utilizará para facer unha chamada a Activity_NuevoArticulo e para recibir datos dela se fose necesario.
     **/
    private static final int COD_PETICION = 33;
    /**
     * COD_PETICION_MODIFICACION é un numero que se utilizará para facer unha chamada a Activity_ModificarArticulo e para recibir datos dela se fose necesario.
     **/
    private static final int COD_PETICION_MODIFICACION = 34;
    /**
     * constraintLayout é unha referencia do layout da Activity para poder cambiar o fondo dependendo de se nos Axustes seleccionamos o modo noite.
     **/
    private static ConstraintLayout constraintLayout;
    /**
     * listaRecibida será a Lista que chegará da Activity_MisListas, e da cal se extraerán os Articulos para mostrar no RecyclerView.
     **/
    private Loxica_Lista listaRecibida;
    private ArrayList<Loxica_Articulo> articulos = new ArrayList();
    /**
     * a é unha referencia ao Contexto da propia Activity.
     **/
    private Context a = this;
    /**
     * CATEGORIAS é un String para utilizar como referencia para recibir unha Lista da Activity_MisListas.
     **/
    public static final String LISTAENVIADA= "lista";
    /**
     * baseDatos é un acceso á clase BaseDatos onde se xestionan as consultas á base de datos.
     **/
    private BaseDatos baseDatos;
    /**
     * ELIMINAR é o número de referencia para xerar o dialogo que aparecerá se queremos eliminar un Artículo.
     **/
    private static final int ELIMINAR = 1;
    /**
     * d será o builder dos AlertDialogs que se xerarán nesta Activity.
     **/
    private AlertDialog.Builder d;
    /**
     * prevPos será unha referencia á posición do último Artículo ao que se lle fixo LongClick e poder desmarcalo se fose necesario.
     **/
    int prevPos = -1;
    /**
     * articuloSeleccionado será o Artículo ao que se lle ficho LongClick.
     **/
    private Loxica_Articulo articuloSeleccionado = new Loxica_Articulo();
    /**
     * adaptador será unha referencia ao Adaptador personalizado do RecyclerView.
     **/
    private Adaptador_ListaRV adaptador = null;
    /**
     * mActionMode é unha referencia ao modo do Menu de Acción, para poder facelo aparecer e desaparecer.
     **/
    private ActionMode mActionMode;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listadefault, menu);
        return true;
    }

    /**
     * Cargará o RecyclerView cos Articulos da lista.
     **/
    private void cargarLista() {
        final RecyclerView rvLista = findViewById(R.id.rvElementosLista_Lista);

        articulos = listaRecibida.getArticulos();
        adaptador = new Adaptador_ListaRV(articulos);

        adaptador.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((Activity_Lista)a).destuirMenuAccion();

                CheckedTextView c = (CheckedTextView) v.findViewById(R.id.ctvNombreArticulo_ElementoLista2);

                Loxica_Articulo articulo = articulos.get(rvLista.getChildAdapterPosition(v));

                if (!articulo.isMarcado()) {
                    if (articulo.isSeleccionado()) {
                        articulo.setSeleccionado(false);
                        c.setChecked(false);
                        baseDatos.setNoComprado(articulo.getId(), listaRecibida.getId());
                    } else {
                        articulo.setSeleccionado(true);
                        c.setChecked(true);
                        baseDatos.setComprado(articulo.getId(), listaRecibida.getId());
                    }
                }

                rvLista.getAdapter().notifyDataSetChanged();
                if (prevPos != -1) {
                    rvLista.getAdapter().notifyItemChanged(prevPos);
                }
                desmarcarArticulo();
                prevPos=-1;

            }
        });
        rvLista.setAdapter(adaptador);


        rvLista.setLayoutManager(new LinearLayoutManager(this));


    }

    /**
     * Desmarcará o último Articulo seleccionado.
     **/
    private void desmarcarArticulo(){
        if (prevPos != -1 && articulos.get(prevPos).isMarcado()) {
            articulos.get(prevPos).setMarcado(false);
            adaptador.notifyItemChanged(prevPos);
        }
    }

    /**
    * Controla os clicks que se realicen nos elementos da Activity.
    **/
    private void xestionarEventos() {

        ImageButton ibtnAñadirElemento = (ImageButton) findViewById(R.id.ibtnNuevoElemento_Lista);
        ibtnAñadirElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desmarcarArticulo();
                prevPos=-1;
                destuirMenuAccion();
                Intent nuevoArticulo = new Intent(getApplicationContext(), Activity_NuevoArticulo.class);
                nuevoArticulo.putExtra("idLista", listaRecibida.getId());
                nuevoArticulo.putExtra("articulos", articulos);
                startActivityForResult(nuevoArticulo, COD_PETICION);
            }
        });


        android.support.v7.widget.RecyclerView rvElListaD = findViewById(R.id.rvElementosLista_Lista);

        ItemClickSupport.addTo(rvElListaD).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {

                articuloSeleccionado = articulos.get(position);

                if (prevPos != -1 && position!=prevPos && articulos.get(prevPos).isMarcado() ) {
                    articulos.get(prevPos).setMarcado(false);
                    adaptador.notifyItemChanged(prevPos);
                }

                if(mActionMode==null && prevPos==position ){
                    prevPos=-1;
                    //adaptador.notifyItemChanged(prevPos);
                }

                if (mActionMode != null) {
                    mActionMode.finish();
                    mActionMode = null;
                }

                if(prevPos==position && !articuloSeleccionado.isMarcado()){
                    prevPos=-1;
                    destuirMenuAccion();
                    return true;
                }else {

                    articuloSeleccionado.setMarcado(true);

                    adaptador.notifyItemChanged(position);

                    prevPos = position;

                    mActionMode = ((Activity) a).startActionMode(mActionModeCallback);
                    return true;
                }
            }


        });

    }


    /**
     * Destruirá o menú de accion.
     **/
    private void destuirMenuAccion(){
        if (mActionMode != null) {
            mActionMode.finish();
            mActionMode = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        destuirMenuAccion();
        switch (item.getItemId()) {
            case R.id.MarcarComprados:
                for(int i=0;i<articulos.size();i++){
                    baseDatos.setComprado(articulos.get(i).getId(),listaRecibida.getId());
                    articulos.get(i).setSeleccionado(true);
                    adaptador.notifyItemChanged(i);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Xestionará a creación e destruccion do menu de Acción, asi como os clicks sobre os seus elementos.
     **/
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_lista, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.EditarArticulo:
                    articulos.get(prevPos).setMarcado(false);
                    adaptador.notifyItemChanged(prevPos);
                    Intent modificarArticulo = new Intent(getApplicationContext(), Activity_ModificarArticulo.class);
                    modificarArticulo.putExtra("idLista", listaRecibida.getId());
                    modificarArticulo.putExtra("articulo", articuloSeleccionado);
                    startActivityForResult(modificarArticulo, COD_PETICION_MODIFICACION);
                    destuirMenuAccion();
                    return true;
                case R.id.EliminarArticulo:
                    genDialogs(ELIMINAR);
                    return true;
                case R.id.MostrarArticulo:
                    desmarcarArticulo();
                    prevPos=-1;
                    Intent mostrarArticulo = new Intent(getApplicationContext(), Activity_MostrarArticulo.class);
                    mostrarArticulo.putExtra("articulo", articuloSeleccionado);
                    startActivity(mostrarArticulo);
                    destuirMenuAccion();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;

            if(articuloSeleccionado !=null) {

                if (prevPos != -1 && articuloSeleccionado.isMarcado()) {
                    articuloSeleccionado.setMarcado(false);
                    adaptador.notifyItemChanged(prevPos);
                }
            }

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
                final Activity_Lista ALista=((Activity_Lista)this);
                d = new AlertDialog.Builder(this);
                d.setIcon(android.R.drawable.ic_dialog_info);
                d.setTitle(R.string.str_lista_mensaxe_eliminar);
                d.setMessage(String.valueOf(R.string.str_lista_mensaxe_eliminar2)+articuloSeleccionado.getNombre()+" ?");
                d.setCancelable(false);
                d.setPositiveButton(R.string.str_all_mensaxe_si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        baseDatos.eliminarArticulo(articuloSeleccionado, listaRecibida.getId());
                        if(!articuloSeleccionado.getRutaImagen().equals("")){
                            File f=new File(articuloSeleccionado.getRutaImagen());
                            f.delete();
                        }
                        articulos.remove(articuloSeleccionado);
                        adaptador.notifyItemRemoved(prevPos);
                        prevPos=-1;
                        ALista.destuirMenuAccion();



                    }
                });
                d.setNegativeButton(R.string.str_all_mensaxe_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                    }
                });
                d.create();
                d.show();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        aplicarPreferencias();
        if (requestCode == COD_PETICION) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(Activity_Lista.NEWArticulos)) {
                    int TamañoActual = adaptador.getItemCount();
                    ArrayList<Loxica_Articulo> articulos2 = (ArrayList<Loxica_Articulo>) data.getSerializableExtra("nuevo");
                    articulos.addAll(articulos2);
                    adaptador.notifyItemRangeInserted(TamañoActual, articulos2.size());
                }

            }
            prevPos=-1;
        }
        if (requestCode == COD_PETICION_MODIFICACION) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(Activity_Lista.MODArticulo)) {

                    Loxica_Articulo articuloRecibido = (Loxica_Articulo) data.getSerializableExtra("articuloModificado");
                    articuloSeleccionado.setNombre(articuloRecibido.getNombre());
                    articuloSeleccionado.setCantidad(articuloRecibido.getCantidad());
                    articuloSeleccionado.setRutaImagen(articuloRecibido.getRutaImagen());
                    articuloSeleccionado.setNotas(articuloRecibido.getNotas());
                    articuloSeleccionado.setPrecio(articuloRecibido.getPrecio());
                    adaptador.notifyItemChanged(prevPos);

                }

            }
            prevPos=-1;

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
            baseDatos = baseDatos.getInstance(getApplicationContext());
            baseDatos.abrirBD();
    }

    /**
     * Aplica a preferencia do Modo Nocturno e obten a preferencia onde se garda a versión do XMl.
     **/
    private void aplicarPreferencias() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Boolean fondo= preferencias.getBoolean("preferencia_idFondo", false);
        if(fondo){
            setTheme(R.style.Nocturno);
            constraintLayout.setBackgroundColor(Color.BLACK);
        }else{
            setTheme(R.style.Diurno);
            constraintLayout.setBackgroundColor(Color.WHITE);


        }


    }

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        desmarcarArticulo();
        guardaEstado.putInt("prevPos", prevPos);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        prevPos = recuperaEstado.getInt("prevPos");

        aplicarPreferencias();
    }

    @Override
    public void finish() {
        Intent datos = new Intent();
        datos.putExtra(Activity_MisListas.LISTAENVIADA, listaRecibida);
        setResult(RESULT_OK, datos);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        Intent datos = new Intent();
        datos.putExtra(Activity_MisListas.LISTAENVIADA, listaRecibida);
        setResult(RESULT_OK, datos);
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicarPreferencias();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista);
        constraintLayout = (ConstraintLayout) findViewById(R.id.bgFondo_Lista);

        listaRecibida = (Loxica_Lista) getIntent().getSerializableExtra("list");

        setTitle(listaRecibida.getNombre());

        xestionarEventos();
        cargarLista();
    }
}
