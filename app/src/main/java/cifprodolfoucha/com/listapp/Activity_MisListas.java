package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Activity_MisListas extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    private void cargarListas(){
        //Fonte de datos
        String[] listas = new String[] { "Cosas","lista2","fiesta 5/11" };

        //Enlace do adaptador coa fonte de datos
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listas);

        ListView lvmisListas = findViewById(R.id.lvmislistas_mislistas);
        //Enlace do adaptador co ListView
        lvmisListas.setAdapter(adaptador);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__mis_listas);
        cargarListas();
    }
}
