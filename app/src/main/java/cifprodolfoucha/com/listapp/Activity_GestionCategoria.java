package cifprodolfoucha.com.listapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Adaptadores.Adaptador_Categorias;
import cifprodolfoucha.com.listapp.Modelos.Categoria;

public class Activity_GestionCategoria extends AppCompatActivity {

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
        setContentView(R.layout.activity__gestion_categoria);
        cargarCategorias();
    }


}
