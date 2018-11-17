package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Adaptadores.Adapatador_Lista;
import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_ListaRV;

public class Activity_Lista extends Activity {

    public final static String NEWArticulos= "nuevo";
    private static final int COD_PETICION = 33;
    ArrayList<Articulo> articulos=new ArrayList();

    Menu m=null;
    //Adapatador_Lista adaptador=null;
    Adaptador_ListaRV adaptador=null;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listadefault, menu);

        m=menu;

        return true;
    }


    private void cargarLista(){
        //String[] articulos={"pilas AA","articulo2","mazá","articulo4","articulo5","articulo6"};
        //int[] cantidad={1,2,10,0,0,4};
        //double[] precio={0.5,15,30,0,20,0};


//        ListView lista = findViewById(R.id.lvElementosLista_Lista);
        final RecyclerView lista = findViewById(R.id.rvElementosLista_Lista);

        //Adapatador_ListaDefault meuAdaptador = new Adapatador_ListaDefault(this,articulos,cantidad,precio);
        //lista.setAdapter(meuAdaptador);


        articulos.add(new Articulo("pilas AA",false,1,0.5,""));
        articulos.add(new Articulo("articulo2",true,3,15,""));
        articulos.add(new Articulo("mazá",true,10,30,""));
        articulos.add(new Articulo("articulo4",false,0,0,""));
        articulos.add(new Articulo("articulo5",true,0,20,""));
        articulos.add(new Articulo("articulo6",false,4,0,""));
//        adaptador=new Adapatador_Lista(this,articulos);

        adaptador=new Adaptador_ListaRV(articulos);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckedTextView c = (CheckedTextView)v.findViewById(R.id.ctvNombreArticulo_ElementoLista);

                Articulo a=articulos.get(lista.getChildAdapterPosition(v));
                //Toast.makeText(getApplicationContext(),a.isSeleccionado()+"",Toast.LENGTH_LONG).show();
                if(a.isSeleccionado()){
                    a.setSeleccionado(false);
                    c.setChecked(false);
                }else{
                    a.setSeleccionado(true);
                    c.setChecked(true);
                }

                lista.getAdapter().notifyDataSetChanged();


                /*
                if(c.isChecked()){
                    c.setChecked(false);
                }else{
                    c.setChecked(true);
                }
                */
            }
        });
/*
        adaptador.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog(BOTONES);
                return false;
            }
        });
*/
        lista.setAdapter(adaptador);


        lista.setLayoutManager(new LinearLayoutManager(this));

    }



    private void xestionarEventos(){


        Bundle bundle=new Bundle();

        final ImageButton ibtnAñadirElemento=(ImageButton) findViewById(R.id.ibtnNuevoElemento_Lista);
        ibtnAñadirElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog(TEXTO);
                Intent nuevoArticulo=new Intent(getApplicationContext(), Activity_NuevoArticulo.class);
                ArrayList<Articulo> a2= (ArrayList<Articulo>) articulos.clone();
                nuevoArticulo.putExtra("lista",a2);
                //startActivity(nuevoArticulo);
                startActivityForResult(nuevoArticulo,COD_PETICION);
            }
        });



        final android.support.v7.widget.RecyclerView rvElListaD=findViewById(R.id.rvElementosLista_Lista);

        /*
            @Override
            public void onClick(View view) {
                CheckedTextView c = (CheckedTextView)view.findViewById(R.id.ctvNombreArticulo_ElementoLista);

                if(c.isChecked()){
                    c.setChecked(false);
                }else{
                    c.setChecked(true);
                }
            }
        });
        rvElListaD.setOnLongClickListener(new AdapterView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(BOTONES);
                return false;
            }
        });
        */
/*
        lvElListaD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView c = (CheckedTextView)view.findViewById(R.id.ctvNombreArticulo_ElementoLista);

                if(c.isChecked()){
                    c.setChecked(false);
                }else{
                    c.setChecked(true);
                }




            }


        });
        lvElListaD.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(BOTONES);
                return false;
            }

        });

 */



    }


    private static final int BOTONES = 1;
    private static final int TEXTO = 2;

    private AlertDialog.Builder venta;

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case BOTONES:
                venta = new AlertDialog.Builder(this);
                venta.setIcon(android.R.drawable.ic_dialog_info);
                venta.setTitle("Eliminar");
                venta.setMessage("Está seguro de que desea eliminar este elemento?");
                venta.setCancelable(false);
                venta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        /* Sentencias se o usuario preme Si */
                        // Toast.makeText(getApplicationContext(), "Premeches 'Si'", 1).show();
                    }
                });
                venta.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        /* Sentencias se o usuario preme Non */
                        //  Toast.makeText(getApplicationContext(), "Premeches'Non'", 1).show();
                    }
                });
                return venta.create();

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

                venta = new AlertDialog.Builder(this);
                venta.setTitle("Insertar nuevo artículo");
                // Asignamos o contido dentro do diálogo (o que inflamos antes)
                venta.setView(inflador);
                // Non se pode incluír unha mensaxe dentro deste tipo de diálogo!!!

                venta.setNeutralButton("Listo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        //Toast.makeText(getApplicationContext(), "Escribiches nome: '" + etNome.getText().toString() + "'. Contrasinal: '" + etContrasinal.getText().toString() + "' e premeches 'Aceptar'",
                        //        Toast.LENGTH_LONG).show();
                    }
                });

                return venta.create();
                //return null;
*/
        }


        return null;
    }

    public void haciendoCosas(){

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == COD_PETICION) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(Activity_Lista.NEWArticulos)) {
                   // Toast.makeText(this, "Saíches da actividade secundaria sen premer o botón Pechar", Toast.LENGTH_SHORT).show();
                    int TamañoActual = adaptador.getItemCount();
                    ArrayList<Articulo> articulos2=(ArrayList<Articulo>)data.getSerializableExtra("nuevo");
                    //Toast.makeText(this, articulos2.size()+"tam", Toast.LENGTH_SHORT).show();
                    /*
                    /*
                    */


                    articulos.addAll(articulos2);
                    adaptador.notifyItemRangeInserted(TamañoActual,articulos2.size());
                    Toast.makeText(this, articulos2.size()+"", Toast.LENGTH_LONG).show();
                    /*
                    */

/*
                    for(Articulo a:articulos2){
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
                    //Toast.makeText(this,((Articulo)articulos.get(articulos.size()-1)).getNombre()+"",Toast.LENGTH_SHORT).show();
*/

                    /*
                    for(Articulo a:articulos2){articulos.add(a);adaptador.notifyDataSetChanged();}
                    */
                    //for(Articulo a:articulos2){articulos.add(a);adaptador.notifyItemInserted(articulos.size()-1);}


                }

            }
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista);
        cargarLista();
        xestionarEventos();
    }

}
