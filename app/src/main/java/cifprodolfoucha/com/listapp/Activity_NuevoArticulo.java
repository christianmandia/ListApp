package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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



    private void xestionarEventos(){

        final ImageButton ibtn_Cancelar=findViewById(R.id.ibtn_CancelarNuevoElemento);
        final ImageButton ibtn_Guardar=findViewById(R.id.ibtn_GuardarNuevoArtículo);
        final ImageButton ibtn_GuardarYContinuar=findViewById(R.id.ibtn_GuardarNuevoArtículo_Comtinuar);

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
                boolean encontrado=false;
                EditText etNombre=(EditText) findViewById(R.id.etNombreArticulo_NuevoArtículo);
                for(Articulo a:articulos){

                    if(a.getNombre().toLowerCase().equals(etNombre.getText().toString().toLowerCase())) {
                        llamadas();
                        encontrado=true;
                    }

                }

                if(!encontrado){
                    EditText etCantidad=(EditText)findViewById(R.id.etCantidadArticulo_NuevoArticuloArticulo);
                    EditText etPrecio=(EditText)findViewById(R.id.etPrecioArticulo_EditarArticulo);
                    EditText etNotas=(EditText)findViewById(R.id.etNotasArticulo_NuevoArticulo);
                    int cantidad=0;
                    double precio=0;
                    if(!etCantidad.getText().toString().isEmpty()){
                        cantidad=Integer.parseInt(etCantidad.getText().toString());
                    }/*
                    if(!etPrecio.getText().toString().isEmpty()){
                        precio=Double.parseDouble(etPrecio.getText().toString());
                    }
*/
                    Articulo a=new Articulo(etNombre.getText().toString(),false,cantidad,0,etNotas.getText().toString());
                    articulos2.add(a);
                    Intent datos = new Intent();
                    datos.putExtra(Activity_Lista.NEWArticulos,articulos2);
                    setResult(RESULT_OK, datos);
                    finish();
                }
               // finish();

                /*
                for(int i=0;i<lvLista.getChildCount();i++){

                    CheckedTextView ctvArticulo=findViewById(R.id.ctvNombreArticulo_ElementoLista);
                    if(ctvArticulo.getText().equals(etNombreArticulo.getText())){
                        finish();
                    }
                }
                */
            }
        });


        ibtn_GuardarYContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void llamadas(){
        Toast.makeText(this,"aAAAAAAAAAAAAAAAAAAAA",Toast.LENGTH_LONG);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nuevoarticulo);

        articulos=(ArrayList<Articulo>) getIntent().getSerializableExtra("lista");
        xestionarEventos();
    }
}
