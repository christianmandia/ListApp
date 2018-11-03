package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listadefault);
        cargarLista();
        xestionarEventos();
    }

}
