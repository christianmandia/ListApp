package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class Activity_MisListas extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__mis_listas);
    }
}
