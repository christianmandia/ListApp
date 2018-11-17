package cifprodolfoucha.com.listapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


import static android.support.v4.content.FileProvider.getUriForFile;


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

    private String nomeFoto="foto.jpg";
    private String rutaArquivo="";
    private int REQUEST_CODE_GRAVACION_OK = 1;
    private final int CODIGO_IDENTIFICADOR=1;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    private void xestionarEventos(){

        final ImageButton ibtn_Cancelar=findViewById(R.id.ibtn_CancelarNuevoArticulo);
        final ImageButton ibtn_Guardar=findViewById(R.id.ibtn_GuardarNuevoArticulo);
        final ImageButton ibtn_GuardarYContinuar=findViewById(R.id.ibtn_GuardarNuevoArticuloYContinuar);
        final ImageButton ibtn_Foto=findViewById(R.id.ibtn_AñadirFoto_NuevoArticulo);


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


        ibtn_Foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File arquivo = new File(ruta,nomeFoto);

                Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intento.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivo));
                startActivityForResult(intento, REQUEST_CODE_GRAVACION_OK);
                */
                File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                Time now=new Time();
                now.setToNow();
                nomeFoto="img-"+now+".jpg";
                File arquivo = new File(ruta,nomeFoto);

                Uri contentUri=null;
                if (Build.VERSION.SDK_INT >= 24) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_REQUEST_CODE);
                    }

                    contentUri = getUriForFile(getApplicationContext(), getApplicationContext()
                            .getPackageName() + ".provider", arquivo);
                }
                else {
                    contentUri = Uri.fromFile(arquivo);
                }

                Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                intento.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

                startActivityForResult(intento, 0);
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
            Articulo a =null;
            if(!rutaArquivo.equals("")){
                a = new Articulo(etNombre.getText().toString(), false, cantidad, precio, etNotas.getText().toString(),rutaArquivo);
            }else {
                a = new Articulo(etNombre.getText().toString(), false, cantidad, precio, etNotas.getText().toString());
            }
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==0 && resultCode == RESULT_OK)
        { {
                // Saca foto

                File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File arquivo = new File(ruta, nomeFoto);
                if (!arquivo.exists()) return;          // Non hai foto

                rutaArquivo=arquivo.getAbsolutePath();
                ImageView imgview = (ImageView) findViewById(R.id.ivImagenArticulo_NuevoArticulo);
                Bitmap bitmap = BitmapFactory.decodeFile(rutaArquivo);
                imgview.setImageBitmap(bitmap);
                //imgview.setScaleType(ImageView.ScaleType.FIT_XY);

            }

        }
    }

    public void pedirPermiso(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CODIGO_IDENTIFICADOR);
            //requestPermissions( new String[]{Manifest.permission.CAMERA},CODIGO_IDENTIFICADOR);
        }

    }
/*
    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

                //Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }
*/
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nuevoarticulo);

        articulos=(ArrayList<Articulo>) getIntent().getSerializableExtra("lista");
        pedirPermiso();
        xestionarEventos();
    }
}
