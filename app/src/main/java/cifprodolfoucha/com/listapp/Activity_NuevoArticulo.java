package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_NuevoArticulo extends Activity {
/*
    ListView lvArticulos;
    public Activity_NuevoArticulo(ListView lvArticulos) {
        this.lvArticulos=lvArticulos;
    }
*/
ArrayList<Articulo> articulos=new ArrayList();
ArrayList<Articulo> articulos2=new ArrayList();

String prueba;

    private void xestionarEventos(){

        final ImageButton ibtn_Cancelar=findViewById(R.id.ibtn_CancelarNuevoArticulo);
        final ImageButton ibtn_Guardar=findViewById(R.id.ibtn_GuardarNuevoArticulo);
        final ImageButton ibtn_GuardarYContinuar=findViewById(R.id.ibtn_GuardarNuevoArticuloYContinuar);

        //final EditText etNombreArticulo=findViewById(R.id.etNombreArticulo_NuevoArtículo);




        ibtn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ibtn_Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  añadirArticulo();
                  finish();
                }
        });


        ibtn_GuardarYContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadirArticulo();
                borrarDatos();
            }
        });
    }

    public void borrarDatos(){
        EditText etNombre=(EditText) findViewById(R.id.etNombreArticulo_NuevoArticulo);
        EditText etCantidad=(EditText) findViewById(R.id.etCantidadArticulo_NuevoArticulo);
        EditText etPrecio=(EditText) findViewById(R.id.etPrecioArticulo_NuevoArticulo);
        EditText etNotas=(EditText) findViewById(R.id.etNotasArticulo_NuevoArticulo);

        etNombre.setText("");
        etCantidad.setText("");
        etPrecio.setText("");
        etNotas.setText("");
    }

    public void añadirArticulo() {

        boolean encontrado=false;
        EditText etNombre=(EditText) findViewById(R.id.etNombreArticulo_NuevoArticulo);
        for(Articulo a:articulos){

            if(a.getNombre().toLowerCase().equals(etNombre.getText().toString().toLowerCase())) {
                //llamadas();
                encontrado=true;
            }

        }

        if(!encontrado) {
            EditText etCantidad = (EditText) findViewById(R.id.etCantidadArticulo_NuevoArticulo);
            EditText etPrecio = (EditText) findViewById(R.id.etPrecioArticulo_NuevoArticulo);
            EditText etNotas = (EditText) findViewById(R.id.etNotasArticulo_NuevoArticulo);
            int cantidad = 0;
            double precio = 0;
            if (!etCantidad.getText().toString().isEmpty()) {
                cantidad = Integer.parseInt(etCantidad.getText().toString());
            }
            if (!etPrecio.getText().toString().isEmpty()) {
                precio = Double.parseDouble(etPrecio.getText().toString());
            }

            //llamadas(etPrecio.getText().toString());
            Articulo a = new Articulo(etNombre.getText().toString(), false, cantidad, precio, etNotas.getText().toString());
            articulos2.add(a);
            /*
            Intent datos = new Intent();
            datos.putExtra(Activity_Lista.NEWArticulos, articulos2);
            setResult(RESULT_OK, datos);
            */
        }
    }

    @Override
    public void finish() {
        Intent datos = new Intent();
        datos.putExtra(Activity_Lista.NEWArticulos,articulos2);
        setResult(RESULT_OK, datos);
        super.finish();
    }


    public void llamadas(String texto){
        Toast.makeText(this,texto,Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nuevoarticulo);

        articulos=(ArrayList<Articulo>) getIntent().getSerializableExtra("lista");
        xestionarEventos();
    }
}
