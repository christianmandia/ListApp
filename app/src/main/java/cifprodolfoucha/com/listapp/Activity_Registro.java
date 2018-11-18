package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//NO FUNCIONA POR AHORA
public class Activity_Registro extends Activity {

    private void XestionarEventos(){
        Button reg=findViewById(R.id.btnCrearCuenta_Registro);
        TextView log=findViewById(R.id.tvLogin_Registro);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login =new Intent(getApplicationContext(), Activity_Login.class);
                startActivity(login);
                finish();

            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registro);
        XestionarEventos();
    }
}
