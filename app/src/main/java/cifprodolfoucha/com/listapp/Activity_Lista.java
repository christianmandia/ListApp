package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_ListaRV;
import cifprodolfoucha.com.listapp.Adaptadores.ItemClickSupport;
import cifprodolfoucha.com.listapp.Almacenamento.BaseDatos;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Lista;

public class Activity_Lista extends Activity {

    public final static String NEWArticulos = "nuevo";
    public final static String MODArticulo = "articuloModificado";
    private static final int COD_PETICION = 33;
    private static final int COD_PETICION_MODIFICACION = 34;
    private Loxica_Lista listaRecibida;
    private ArrayList<Loxica_Articulo> articulos = new ArrayList();
    private Context a = this;
    public static final String LISTAENVIADA= "lista";
    private BaseDatos baseDatos;


    Loxica_Articulo articuloSeleccionado = new Loxica_Articulo();
    int prevPos = -1;

    //Menu m = null;

    //Adapatador_Lista adaptador=null;
    Adaptador_ListaRV adaptador = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listadefault, menu);

    //    m = menu;

        return true;
    }


    private void cargarLista() {
        //String[] articulos={"pilas AA","articulo2","mazá","articulo4","articulo5","articulo6"};
        //int[] cantidad={1,2,10,0,0,4};
        //double[] precio={0.5,15,30,0,20,0};


//        ListView lista = findViewById(R.id.lvElementosLista_Lista);
        final RecyclerView rvLista = findViewById(R.id.rvElementosLista_Lista);

        //Adapatador_ListaDefault meuAdaptador = new Adapatador_ListaDefault(this,articulos,cantidad,precio);
        //lista.setAdapter(meuAdaptador);

/*
        articulos.add(new Loxica_Articulo("pilas AA",false,1,0.5,""));
        articulos.add(new Loxica_Articulo("articulo2",true,3,15,""));
        articulos.add(new Loxica_Articulo("mazá",true,10,30,""));
        articulos.add(new Loxica_Articulo("articulo4",false,1,0,""));
        articulos.add(new Loxica_Articulo("articulo5",true,1,20,""));
        articulos.add(new Loxica_Articulo("articulo6",false,4,0,""));
*/
//        adaptador=new Adapatador_Lista(this,articulos);

        //adaptador=new Adaptador_ListaRV(articulos);

        articulos = listaRecibida.getArticulos();
        adaptador = new Adaptador_ListaRV(articulos);

        adaptador.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
/*
                if(mActionMode!=null) {
                    mActionMode.finish();
                    mActionMode = null;
                }
*/




                ((Activity_Lista)a).destuirMenuAccion();

                CheckedTextView c = (CheckedTextView) v.findViewById(R.id.ctvNombreArticulo_ElementoLista2);

                Loxica_Articulo articulo = articulos.get(rvLista.getChildAdapterPosition(v));
                //Toast.makeText(getApplicationContext(),a.isSeleccionado()+"",Toast.LENGTH_LONG).show();

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

    private void desmarcarArticulo(){
        if (prevPos != -1 && articulos.get(prevPos).isMarcado()) {
            articulos.get(prevPos).setMarcado(false);
            adaptador.notifyItemChanged(prevPos);
        }


    }



    private void xestionarEventos() {


        Bundle bundle = new Bundle();

        ImageButton ibtnAñadirElemento = (ImageButton) findViewById(R.id.ibtnNuevoElemento_Lista);
        ibtnAñadirElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog(TEXTO);
                desmarcarArticulo();
                prevPos=-1;
                destuirMenuAccion();
                Intent nuevoArticulo = new Intent(getApplicationContext(), Activity_NuevoArticulo.class);
                //ArrayList<Loxica_Articulo> a2= (ArrayList<Loxica_Articulo>) articulos.clone();
                nuevoArticulo.putExtra("idLista", listaRecibida.getId());
                nuevoArticulo.putExtra("articulos", articulos);
                //startActivity(nuevoArticulo);
                startActivityForResult(nuevoArticulo, COD_PETICION);
            }
        });


        android.support.v7.widget.RecyclerView rvElListaD = findViewById(R.id.rvElementosLista_Lista);

        ItemClickSupport.addTo(rvElListaD).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {

                articuloSeleccionado = articulos.get(position);

                //Arreglo chapuza
                //if(prevPos!=-1 && prevPos!=position && articulos.get(prevPos).isMarcado()) {
                //Toast.makeText(getApplicationContext(),prevPos+" "+position,Toast.LENGTH_LONG).show();

                if (prevPos != -1 && position!=prevPos && articulos.get(prevPos).isMarcado() ) {
                    //rvElListaD.findViewHolderForAdapterPosition(prevPos).itemView.setBackgroundColor(0xFF00FFFF);
                    articulos.get(prevPos).setMarcado(false);
                    adaptador.notifyItemChanged(prevPos);
                    Log.i("prueba", "desmarcar PRE");
                }

                //Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();



                //v.setBackgroundColor(0xFF00FF00);
                //if(prevPos!=position) {

                /*

                    if (articuloSeleccionado.isMarcado()) {
                        articuloSeleccionado.setMarcado(false);

                        adaptador.notifyItemChanged(position);
//                    setMenuDefecto();
                    } else {
                        articuloSeleccionado.setMarcado(true);

                        adaptador.notifyItemChanged(position);
                        Log.i("prueba", "marcar");
//                    setMenu2();
                    }
                    */
                //}
                //v.setBackground(null);
                ////////////////






                    if (mActionMode != null) {

                        mActionMode.finish();
                        mActionMode = null;

                    }

                    if(prevPos==position && !articuloSeleccionado.isMarcado()){
                        prevPos=-1;
                        return true;

                    }else {

                        articuloSeleccionado.setMarcado(true);

                        adaptador.notifyItemChanged(position);

                        prevPos = position;
                        // Start the CAB using the ActionMode.Callback defined above

                        mActionMode = ((Activity) a).startActionMode(mActionModeCallback);
                        //view.setSelected(true);

                        return true;
                    }
            }


        });

    }

    private ActionMode mActionMode;

    private void destuirMenuAccion(){
        if (mActionMode != null) {

            mActionMode.finish();
            mActionMode = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this,"BEEEEEE",Toast.LENGTH_LONG).show();

        destuirMenuAccion();

        switch (item.getItemId()) {
        /*
            case R.id.EditarArticulo:
                //Toast.makeText(this,"AAAAA",Toast.LENGTH_LONG).show();
                articulos.get(prevPos).setMarcado(false);
                adaptador.notifyItemChanged(prevPos);
                destuirMenuAccion();
//                setMenuDefecto();

                Intent modificarArticulo = new Intent(getApplicationContext(), Activity_ModificarArticulo.class);
                //modificarArticulo.putExtra("titulo", articuloSeleccionado.getNombre());
                modificarArticulo.putExtra("articulo", articuloSeleccionado);
                startActivityForResult(modificarArticulo, COD_PETICION_MODIFICACION);
                return true;
            case R.id.EliminarArticulo:

                showDialog(ELIMINAR);
  //            destuirMenuAccion();
                return true;
            case R.id.MostrarArticulo:
                Intent mostrarArticulo = new Intent(getApplicationContext(), Activity_MostrarArticulo.class);
                //modificarArticulo.putExtra("titulo", articuloSeleccionado.getNombre());
                destuirMenuAccion();

                mostrarArticulo.putExtra("articulo", articuloSeleccionado);
                startActivity(mostrarArticulo);

                return true;
        */
            default:
                return super.onOptionsItemSelected(item);

        }

    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_lista, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.EditarArticulo:
                    //Toast.makeText(this,"AAAAA",Toast.LENGTH_LONG).show();
                    articulos.get(prevPos).setMarcado(false);
                    adaptador.notifyItemChanged(prevPos);
                    //setMenuDefecto();
                    Intent modificarArticulo = new Intent(getApplicationContext(), Activity_ModificarArticulo.class);
                    //modificarArticulo.putExtra("titulo", articuloSeleccionado.getNombre());
                    modificarArticulo.putExtra("idLista", listaRecibida.getId());
                    modificarArticulo.putExtra("articulo", articuloSeleccionado);
                    startActivityForResult(modificarArticulo, COD_PETICION_MODIFICACION);
                    destuirMenuAccion();
                    return true;
                case R.id.EliminarArticulo:
                    genDialogs(ELIMINAR);
                    //showDialog(ELIMINAR);
                    return true;
                case R.id.MostrarArticulo:
                    desmarcarArticulo();
                    prevPos=-1;
                    Intent mostrarArticulo = new Intent(getApplicationContext(), Activity_MostrarArticulo.class);
                    //modificarArticulo.putExtra("titulo", articuloSeleccionado.getNombre());
                    mostrarArticulo.putExtra("articulo", articuloSeleccionado);
                    startActivity(mostrarArticulo);
                    destuirMenuAccion();
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
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
/*
    private void setMenuDefecto(){
        m.setGroupVisible(R.id.GrupoOpciones,true);
        m.setGroupVisible(R.id.GrupoGArticulo,false);
        /*
        m.findItem(R.id.MostrarArticulo).setVisible(false);
        m.findItem(R.id.EditarArticulo).setVisible(false);
        m.findItem(R.id.EliminarArticulo).setVisible(false);
        m.findItem(R.id.CompartirLista).setVisible(true);

    }

    private void setMenu2(){
        m.setGroupVisible(R.id.GrupoOpciones,false);
        m.setGroupVisible(R.id.GrupoGArticulo,true);
        /*
        m.findItem(R.id.CompartirLista).setVisible(false);
        m.findItem(R.id.MostrarArticulo).setVisible(true);
        m.findItem(R.id.EditarArticulo).setVisible(true);
        m.findItem(R.id.EliminarArticulo).setVisible(true);

    }
*/

    private static final int ELIMINAR = 1;

    private AlertDialog.Builder d;


    protected void genDialogs(int id){
        switch(id){
            default:
                return;
            case ELIMINAR:
                final Activity_Lista ALista=((Activity_Lista)this);
                d = new AlertDialog.Builder(this);
                d.setIcon(android.R.drawable.ic_dialog_info);
                d.setTitle("Eliminar");
                d.setMessage("Está seguro de que desea eliminar este elemento?");
                d.setCancelable(false);
                d.setPositiveButton("Si", new DialogInterface.OnClickListener() {
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
                d.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                    }
                });
                d.create();
                d.show();

        }
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            /*
            case ELIMINAR:
                final Activity_Lista ALista=((Activity_Lista)this);
                d = new AlertDialog.Builder(this);
                d.setIcon(android.R.drawable.ic_dialog_info);
                d.setTitle("Eliminar");
                d.setMessage("Está seguro de que desea eliminar este elemento?");
                d.setCancelable(false);
                d.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        articulos.remove(articuloSeleccionado);
                        adaptador.notifyItemRemoved(prevPos);
                        prevPos=-1;
                        ALista.destuirMenuAccion();



                    }
                });
                d.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                    }
                });
                return d.create();

/*
            case TEXTO:

                // Primeiro preparamos o interior da ventá de diálogo inflando o seu
                // fichero XML
                String infService = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(infService);
                // Inflamos o compoñente composto definido no XML
                View inflador = li.inflate(R.layout.dlg_nuevoelemento_listadefault, null);
                // Buscamos os compoñentes dentro do Diálogo

                final TextView etNome = (TextView) inflador.findViewById(R.id.etNombreElemento0000000000);

                d = new AlertDialog.Builder(this);
                d.setTitle("Insertar nuevo artículo");
                // Asignamos o contido dentro do diálogo (o que inflamos antes)
                d.setView(inflador);
                // Non se pode incluír unha mensaxe dentro deste tipo de diálogo!!!

                d.setNeutralButton("Listo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        //Toast.makeText(getApplicationContext(), "Escribiches nome: '" + etNome.getText().toString() + "'. Contrasinal: '" + etContrasinal.getText().toString() + "' e premeches 'Aceptar'",
                        //        Toast.LENGTH_LONG).show();
                    }
                });

                return d.create();
                //return null;
*/
        }


        return null;
    }

    public void haciendoCosas() {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        aplicarPreferencias();
        if (requestCode == COD_PETICION) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(Activity_Lista.NEWArticulos)) {
                    // Toast.makeText(this, "Saíches da actividade secundaria sen premer o botón Pechar", Toast.LENGTH_SHORT).show();
                    int TamañoActual = adaptador.getItemCount();
                    ArrayList<Loxica_Articulo> articulos2 = (ArrayList<Loxica_Articulo>) data.getSerializableExtra("nuevo");
                    //Toast.makeText(this, articulos2.size()+"tam", Toast.LENGTH_SHORT).show();
                    /*
                    /*
                    */


                    articulos.addAll(articulos2);
                    adaptador.notifyItemRangeInserted(TamañoActual, articulos2.size());
                    //Toast.makeText(this, articulos2.size()+"", Toast.LENGTH_LONG).show();
                    /*
                     */

/*
                    for(Loxica_Articulo a:articulos2){
                        articulos.add(a);
                        //adaptador.notifyItemInserted(0);
                        //Toast.makeText(this,adaptador.getItemId(0)+"",Toast.LENGTH_SHORT).show();
                        Toast.makeText(this,articulos.get(articulos.size()-1).getNombre()+"",Toast.LENGTH_SHORT).show();
                        //adaptador.notifyItemInserted(0);
                        //adaptador.notifyItemRangeChanged(TamañoActual,articulos2.size());
                        //adaptador.notifyItemInserted(articulos.size()-1);
                        //adaptador.notifyItemChanged(articulos.size()-1);

                        //Toast.makeText(this,a.getNombre(),Toast.LENGTH_SHORT).show();
                    }
                    adaptador.notifyItemRangeInserted(TamañoActual,articulos2.size());
                    adaptador.notifyItemRangeChanged(TamañoActual,articulos2.size());
                    //Toast.makeText(this,((Loxica_Articulo)articulos.get(articulos.size()-1)).getNombre()+"",Toast.LENGTH_SHORT).show();
*/

                    /*
                    for(Loxica_Articulo a:articulos2){articulos.add(a);adaptador.notifyDataSetChanged();}
                    */
                    //for(Loxica_Articulo a:articulos2){articulos.add(a);adaptador.notifyItemInserted(articulos.size()-1);}


                }

            }
            prevPos=-1;
        }
        if (requestCode == COD_PETICION_MODIFICACION) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(Activity_Lista.MODArticulo)) {

                    Loxica_Articulo articuloRecibido = (Loxica_Articulo) data.getSerializableExtra("articuloModificado");

                    //Toast.makeText(getApplicationContext(),"Llega",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),articuloRecibido.getNombre().toString(),Toast.LENGTH_SHORT).show();


                    articuloSeleccionado.setNombre(articuloRecibido.getNombre());
                    articuloSeleccionado.setCantidad(articuloRecibido.getCantidad());
                    articuloSeleccionado.setRutaImagen(articuloRecibido.getRutaImagen());
                    articuloSeleccionado.setNotas(articuloRecibido.getNotas());
                    articuloSeleccionado.setPrecio(articuloRecibido.getPrecio());
                    adaptador.notifyItemChanged(prevPos);

                    /*
                    articulos.remove(prevPos);
                    adaptador.notifyItemRemoved(prevPos);
                    articulos.add(prevPos,articuloRecibido);
                    //articulos.add(articuloRecibido);
                    adaptador.notifyItemInserted(prevPos);
                    //adaptador.notifyItemInserted(adaptador.getItemCount());
                    /**/
                    //Toast.makeText(this,articuloRecibido.toString(),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this,prevPos+"",Toast.LENGTH_SHORT).show();


                }

            }
            prevPos=-1;

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);

        //guardaEstado.putSerializable("articulos",articulos);
        //guardaEstado.putSerializable("articuloSeleccionado",articuloSeleccionado);
        guardaEstado.putInt("prevPos", prevPos);


    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (baseDatos==null) {   // Abrimos a base de datos para escritura
            baseDatos = baseDatos.getInstance(getApplicationContext());
            //baseDatos = new BaseDatos(getApplicationContext());
            baseDatos.abrirBD();
//        }
        //Log.i("PROBA",String.valueOf(baseDatos.sqlLiteDB.isOpen()));

    }

    private static ConstraintLayout constraintLayout;

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
        //nome.setText(valorNome);


    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        //articuloSeleccionado=(Loxica_Articulo)recuperaEstado.getSerializable("articuloSeleccionado");
        //articulos=(ArrayList<Loxica_Articulo>)recuperaEstado.getSerializable("articulos");
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
        aplicarPreferencias();
    }

}
