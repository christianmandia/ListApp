package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



///NO FUNCIONA POR AHORA
public class Activity_Login extends Activity {

    private void XestionarEventos(){
        final EditText etNombre=findViewById(R.id.etNombre_Login);
        final EditText etPass=findViewById(R.id.etPassword_Login);
        Button btnlogin=findViewById(R.id.btnLogin_Login);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent datos = new Intent();

                datos.putExtra(Activity_Lista.Login,etNombre.getText().toString());
                datos.putExtra(Activity_Lista.Pass,etPass.getText().toString());

                setResult(RESULT_LOGIN, datos);
                */
                finish();
            }
        });


        Button btnReg=findViewById(R.id.btnRegistrarse_Login);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg=new Intent(getApplicationContext(), Activity_Registro.class);
                startActivity(reg);
                finish();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        XestionarEventos();
    }
}
