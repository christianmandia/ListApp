package cifprodolfoucha.com.listapp.Almacenamento;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import cifprodolfoucha.com.listapp.R;

public class Preferencias_Ajustes extends Activity {

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.layout__ajustes);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();
    }
}
