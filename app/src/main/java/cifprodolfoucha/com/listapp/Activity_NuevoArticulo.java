package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.Toast;

public class Activity_NuevoArticulo extends Activity {
/*
    ListView lvArticulos;
    public Activity_NuevoArticulo(ListView lvArticulos) {
        this.lvArticulos=lvArticulos;
    }
*/
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nuevoarticulo);

        xestionarEventos();
    }
}
