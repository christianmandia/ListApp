package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_ListaDefault extends Activity {

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
        String[] articulos={"pilas AA","articulo2","mazá","articulo4","articulo5","articulo6"};
        int[] cantidad={1,2,10,0,0,4};
        double[] precio={0.5,15,30,0,20,0};


        ListView lista = findViewById(R.id.lvElementosLista_ListaDefault);

        Adapatador_ListaDefault meuAdaptador = new Adapatador_ListaDefault(this,articulos,cantidad,precio);
        lista.setAdapter(meuAdaptador);

    }



    private void xestionarEventos(){

        final Button btnAñadirElemento=findViewById(R.id.btnCrearElemento_ListaDefault);
        btnAñadirElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOGO_ENTRADA_TEXTO);
            }
        });

 /*       ListView lvFroitas = findViewById(R.id.lvElementosLista_ListaDefault);
        //Escoitador
        lvFroitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {


*/
        final ListView lvElListaD=findViewById(R.id.lvElementosLista_ListaDefault);

        lvElListaD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView c = (CheckedTextView)view.findViewById(R.id.ctvNombreArticulo_ElementoListaDefault);
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
                showDialog(DIALOGO_TRES_BOTONS);
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


    private static final int DIALOGO_TRES_BOTONS = 1;
    private static final int DIALOGO_ENTRADA_TEXTO = 2;

    private AlertDialog.Builder venta;

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOGO_TRES_BOTONS:
                venta = new AlertDialog.Builder(this);
                venta.setIcon(android.R.drawable.ic_dialog_info);
                venta.setTitle("Eliminar");
                venta.setMessage("Está seguro de que desea eliminar este elemnto?");
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


            case DIALOGO_ENTRADA_TEXTO:

                // Primeiro preparamos o interior da ventá de diálogo inflando o seu
                // fichero XML
                String infService = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(infService);
                // Inflamos o compoñente composto definido no XML
                View inflador = li.inflate(R.layout.dlg_nuevoelemento_listadefault, null);
                // Buscamos os compoñentes dentro do Diálogo

                final TextView etNome = (TextView) inflador.findViewById(R.id.etNombreElemento);

                venta = new AlertDialog.Builder(this);
                venta.setTitle("Insertar nuevo elemento");
                // Asignamos o contido dentro do diálogo (o que inflamos antes)
                venta.setView(inflador);
                // Non se pode incluír unha mensaxe dentro deste tipo de diálogo!!!

                venta.setPositiveButton("Listo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        //Toast.makeText(getApplicationContext(), "Escribiches nome: '" + etNome.getText().toString() + "'. Contrasinal: '" + etContrasinal.getText().toString() + "' e premeches 'Aceptar'",
                        //        Toast.LENGTH_LONG).show();
                    }
                });
                venta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        // Toast.makeText(getApplicationContext(), "Premeches en 'Cancelar'", Toast.LENGTH_LONG).show();
                    }
                });

                return venta.create();
                //return null;
        }
        return null;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listadefault);
        cargarLista();
        xestionarEventos();
    }

}
