package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity_Lista extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listadefault, menu);
        return true;
    }
/*
    private void cargarListas(){
        //Fonte de datos
        String[] froitas = new String[] { "Pera", "Mazá", "Plátano" };

        //Enlace do adaptador coa fonte de datos
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.layout_elemento_listadefault, froitas);

        ListView lvFroitas = findViewById(R.id.lvElementosLista_ListaDefault);
        //Enlace do adaptador co ListView
        lvFroitas.setAdapter(adaptador);
    }

*/
    private void cargarLista(){
        //String[] articulos={"pilas AA","articulo2","mazá","articulo4","articulo5","articulo6"};
        //int[] cantidad={1,2,10,0,0,4};
        //double[] precio={0.5,15,30,0,20,0};


        ListView lista = findViewById(R.id.lvElementosLista_Lista);

        //Adapatador_ListaDefault meuAdaptador = new Adapatador_ListaDefault(this,articulos,cantidad,precio);
        //lista.setAdapter(meuAdaptador);

        ArrayList<Articulo> articulos=new ArrayList();
        articulos.add(new Articulo("pilas AA",false,1,0.5,""));
        articulos.add(new Articulo("articulo2",true,3,15,""));
        articulos.add(new Articulo("mazá",true,10,30,""));
        articulos.add(new Articulo("articulo4",false,0,0,""));
        articulos.add(new Articulo("articulo5",true,0,20,""));
        articulos.add(new Articulo("articulo6",false,4,0,""));
        articulos.add(new Articulo("pilas AA",false,1,0.5,""));
        articulos.add(new Articulo("articulo2",true,3,15,""));
        articulos.add(new Articulo("mazá",true,10,30,""));
        articulos.add(new Articulo("articulo4",false,0,0,""));
        articulos.add(new Articulo("articulo5",true,0,20,""));
        articulos.add(new Articulo("articulo6",false,4,0,""));
        Adapatador_Lista adaptador=new Adapatador_Lista(this,articulos);
        lista.setAdapter(adaptador);

    }



    private void xestionarEventos(){


        Bundle bundle=new Bundle();

        final Button btnAñadirElemento=findViewById(R.id.btnCrearElemento_Lista);
        btnAñadirElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog(TEXTO);
                Intent nuevoArticulo=new Intent(getApplicationContext(), Activity_NuevoArticulo.class);


                startActivity(nuevoArticulo);
            }
        });

 /*       ListView lvFroitas = findViewById(R.id.lvElementosLista_ListaDefault);
        //Escoitador
        lvFroitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {


*/
        final ListView lvElListaD=findViewById(R.id.lvElementosLista_Lista);

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
        /*
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                //Toast.makeText(getBaseContext(), "Seleccionaches: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Seleccionaches: " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista);
        cargarLista();
        xestionarEventos();
    }

}
