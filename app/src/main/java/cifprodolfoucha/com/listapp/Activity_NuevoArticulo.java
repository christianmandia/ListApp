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
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Modelos.Articulo;

import static android.support.v4.content.FileProvider.getUriForFile;

public class Activity_NuevoArticulo extends Activity {
/*
    ListView lvArticulos;
    public Activity_NuevoArticulo(ListView lvArticulos) {
        this.lvArticulos=lvArticulos;
    }
*/
    private  ArrayList<Articulo> articulos=new ArrayList();
    private  ArrayList<Articulo> articulos2=new ArrayList();

    private String nomeFoto="";
    private String rutaArquivo="";
    private String nomeSobrescribir="";
    private int REQUEST_CODE_GRAVACION_OK = 1;
    private final int CODIGO_IDENTIFICADOR=1;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    private void xestionarEventos(){
         ImageButton ibtn_Cancelar=findViewById(R.id.ibtn_CancelarNuevoArticulo);
         ImageButton ibtn_Guardar=findViewById(R.id.ibtn_GuardarNuevoArticulo);
         ImageButton ibtn_GuardarYContinuar=findViewById(R.id.ibtn_GuardarNuevoArticuloYContinuar);
         ImageButton ibtn_Foto=findViewById(R.id.ibtn_AñadirFoto_NuevoArticulo);


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
                if(!rutaArquivo.equals("")){
                    String[] nombre=rutaArquivo.split("/");
                    nomeSobrescribir=nombre[nombre.length-1];
                    //Toast.makeText(getApplicationContext(),nomeSobrescribir,Toast.LENGTH_LONG).show();
                }
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
                if(!nomeSobrescribir.equals("")){
                    File f=new File(ruta,nomeSobrescribir);
                    f.delete();
                    arquivo.renameTo(f);
                }
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
            //Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imgview.setImageBitmap(bitmap);
            //imgview.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        }
    }
/*
    public void pedirPermiso(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CODIGO_IDENTIFICADOR);
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
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);

        EditText etNombre = (EditText) findViewById(R.id.etNombreArticulo_NuevoArticulo);
        EditText etCantidad = (EditText) findViewById(R.id.etCantidadArticulo_NuevoArticulo);
        EditText etPrecio = (EditText) findViewById(R.id.etPrecioArticulo_NuevoArticulo);
        EditText etNotas = (EditText) findViewById(R.id.etNotasArticulo_NuevoArticulo);
        ImageView ivimagen=(ImageView) findViewById(R.id.ivImagenArticulo_NuevoArticulo);

        sNombre=etNombre.getText().toString();
        if(!etCantidad.getText().toString().equals("")) {
            iCantidad = Integer.parseInt(etCantidad.getText().toString());
            if(etCantidad.getText().toString().isEmpty()){
                Cant=false;
            }else{
                Cant=true;
            }
        }
        if(!etPrecio.getText().toString().equals("")) {
            dPrecio = Double.parseDouble(etPrecio.getText().toString());
            Pre=true;
        }else{
            Pre=false;
        }
        sNotas=etNotas.getText().toString();

        sImagen = rutaArquivo;

        guardaEstado.putString("nombre",sNombre);
        guardaEstado.putString("notas",sNotas);
        guardaEstado.putString("imagen",sImagen);
        guardaEstado.putInt("cantidad",iCantidad);
        guardaEstado.putDouble("precio",dPrecio);

        guardaEstado.putSerializable("articulos",articulos);
        guardaEstado.putSerializable("articulos2",articulos2);
        //t = texto.getText().toString();
        //lo "guardamos" en el Bundle
        //guardaEstado.putString("text", t);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        EditText etNombre = (EditText) findViewById(R.id.etNombreArticulo_NuevoArticulo);
        EditText etCantidad = (EditText) findViewById(R.id.etCantidadArticulo_NuevoArticulo);
        EditText etPrecio = (EditText) findViewById(R.id.etPrecioArticulo_NuevoArticulo);
        EditText etNotas = (EditText) findViewById(R.id.etNotasArticulo_NuevoArticulo);
        ImageView ivimagen=(ImageView) findViewById(R.id.ivImagenArticulo_NuevoArticulo);

        sNombre=recuperaEstado.getString("nombre");
        iCantidad=recuperaEstado.getInt("cantidad");
        dPrecio=recuperaEstado.getDouble("precio");
        sNotas=recuperaEstado.getString("notas");

        sImagen = recuperaEstado.getString("imagen");

        articulos=(ArrayList<Articulo>)recuperaEstado.getSerializable("articulos");
        articulos2=(ArrayList<Articulo>)recuperaEstado.getSerializable("articulos2");


        etNombre.setText(sNombre);
        if(Cant) {
            etCantidad.setText(iCantidad);
        }
        if(Pre) {
            etPrecio.setText(dPrecio + "");
        }
        etNotas.setText(sNombre);
        if(!sImagen.equals("")){
            rutaArquivo=sImagen;
            Bitmap bitmap = BitmapFactory.decodeFile(sImagen);
            ivimagen.setImageBitmap(bitmap);
        }

        //recuperamos el String del Bundle
        //t = recuperaEstado.getString("text");
        //Seteamos el valor del EditText con el valor de nuestra cadena
       // texto.setText(t);
    }

    private String sNombre;
    private int iCantidad;
    private double dPrecio;
    private String sNotas;
    private String sImagen="";
    private boolean Cant;
    private boolean Pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nuevoarticulo);
        articulos=(ArrayList<Articulo>) getIntent().getSerializableExtra("lista");
//        pedirPermiso();
        xestionarEventos();
    }
}