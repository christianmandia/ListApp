package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Adaptadores.Adapatador_Lista;
import cifprodolfoucha.com.listapp.Adaptadores.Adapatador_ListaDefault;
import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_Categorias;
import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_MisListas;
import cifprodolfoucha.com.listapp.Modelos.Articulo;
import cifprodolfoucha.com.listapp.Modelos.Categoria;
import cifprodolfoucha.com.listapp.Modelos.Lista;



public class Activity_MisListas extends Activity {

    private ArrayList<Lista> listas=null;

    public void gestionEventos(){
        ListView lista = findViewById(R.id.lvmislistas_mislistas);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){
                //ItemClicked item = adapter.getItemAtPosition(position);

                //Intent intent = new Intent(Activity.this,destinationActivity.class);
                //based on item add info to intent
                //startActivity(intent);
                //Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_LONG).show();
                Lista lista1=listas.get(position);

                Intent intento=new Intent(getApplicationContext(),Activity_Lista.class);
                intento.putExtra("list",lista1);
                startActivity(intento);


            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    private void cargarListas(){
        ListView lista = findViewById(R.id.lvmislistas_mislistas);

        listas= new ArrayList<>();
        ArrayList<Articulo> articulos=new ArrayList<>();
        articulos.add(new Articulo("pilas AA",false,1,0.5,""));
        articulos.add(new Articulo("articulo2",true,3,15,""));
        articulos.add(new Articulo("mazá",true,10,30,""));

        listas.add(new Lista("Lista1",(new Categoria("Categoria1","Imagen1")),articulos));

        ArrayList<Articulo> articulos2=new ArrayList<>();
        articulos2.add(new Articulo("articulo4",true,1,2,""));
        articulos2.add(new Articulo("articulo5",true,1,20,""));
        articulos2.add(new Articulo("articulo6",true,4,5,""));

        listas.add(new Lista("Lista2",(new Categoria("Categoria2","Imagen2")),articulos2));

        Adaptador_MisListas miAdaptador = new Adaptador_MisListas(this,listas);
        lista.setAdapter(miAdaptador);


    }
    private void cargarCategorias(){
        Spinner categorias=findViewById(R.id.spnCategorias_mislistas);
        ArrayList<Categoria> categorias1=new ArrayList<>();
        categorias1.add(new Categoria("categoria1","imagen1"));
        categorias1.add(new Categoria("categoria2","imagen2"));
        categorias1.add(new Categoria("categoria3","imagen3"));
        categorias1.add(new Categoria("categoria4","imagen4"));

        Adaptador_Categorias miAdaptador=new Adaptador_Categorias(this,categorias1);
        categorias.setAdapter(miAdaptador);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__mis_listas);
        cargarListas();
        cargarCategorias();
        gestionEventos();
    }
}
