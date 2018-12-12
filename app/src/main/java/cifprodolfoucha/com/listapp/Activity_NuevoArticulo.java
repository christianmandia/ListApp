package cifprodolfoucha.com.listapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Almacenamento.BaseDatos;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;
import cifprodolfoucha.com.listapp.Loxica.Loxica_GardarImaxe;

import static android.support.v4.content.FileProvider.getUriForFile;

public class Activity_NuevoArticulo extends Activity {
/*
    ListView lvArticulos;
    public Activity_NuevoArticulo(ListView lvArticulos) {
        this.lvArticulos=lvArticulos;
    }
*/
    private  ArrayList<Loxica_Articulo> articulos =new ArrayList();
    private  ArrayList<Loxica_Articulo> articulos2=new ArrayList();

    private int idListaRecibida;

    private String nomeFoto="";
    private String rutaArquivo="";
    private String nomeSobrescribir="";
    private int REQUEST_CODE_GRAVACION_OK = 1;
    private final int CODIGO_IDENTIFICADOR=1;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private File img,imaxe,directorio,temp;
    private boolean pasaFoto;
    //private Bitmap bitmap;
    private BaseDatos baseDatos;

    private void xestionarEventos(){
         ImageButton ibtn_Cancelar=findViewById(R.id.ibtn_CancelarNuevoArticulo);
         ImageButton ibtn_Guardar=findViewById(R.id.ibtn_GuardarNuevoArticulo);
         ImageButton ibtn_GuardarYContinuar=findViewById(R.id.ibtn_GuardarNuevoArticuloYContinuar);
         ImageButton ibtn_Foto=findViewById(R.id.ibtn_AñadirFoto_NuevoArticulo);


        //final EditText etNombreArticulo=findViewById(R.id.etNombreArticulo_NuevoArtículo);




        ibtn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imaxe!=null){
                    imaxe.delete();
                }

                finish();
            }
        });

        ibtn_Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(añadirArticulo()) {
                    finish();
                }
            }
        });


        ibtn_GuardarYContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(añadirArticulo()) {
                    borrarDatos();
                }
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
                //File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                Time now=new Time();
                now.setToNow();

                directorio = new File(Environment.getExternalStorageDirectory(), "ListApp");
                if (!directorio.exists()) {
                    directorio.mkdirs();
                }
                img=new File(directorio, "imagenes");
                if (!img.exists()) {
                    img.mkdirs();
                }



                if(!rutaArquivo.equals("")){
                    String[] nombre=rutaArquivo.split("/");
                    nomeSobrescribir=nombre[nombre.length-1];
                    //Toast.makeText(getApplicationContext(),nomeSobrescribir,Toast.LENGTH_LONG).show();
                }




                nomeFoto="img-"+now+".jpg";
                //File arquivo = new File(ruta,nomeFoto);
                imaxe = new File(img,nomeFoto);


                Uri contentUri=null;

                if (Build.VERSION.SDK_INT >= 24) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_REQUEST_CODE);
                    }
                    contentUri = getUriForFile(getApplicationContext(), getApplicationContext()
                            .getPackageName() + ".provider", imaxe);
                }
                else {
                    contentUri = Uri.fromFile(imaxe);
                }




                Log.i("inicia","llega a llamada");
                Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Log.i("media","llega a putExtra");
                intento.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                Log.i("continua","llega a startActivity");


                Intent cam=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                startActivityForResult(cam, 0);
                Log.i("finaliza","final");

                if(!nomeSobrescribir.equals("")){
                    File f=new File(img,nomeSobrescribir);
                    f.delete();
                    imaxe.renameTo(f);
                }

            }

        });
    }
    /*
    public void saveImage(Bitmap bmp, String filename)
    {
        Intent mediaScanIntent = new Intent(Intent.Action_);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/
    public void borrarDatos(){
        EditText etNombre=(EditText) findViewById(R.id.etNombreArticulo_NuevoArticulo);
        EditText etCantidad=(EditText) findViewById(R.id.etCantidadArticulo_NuevoArticulo);
        EditText etPrecio=(EditText) findViewById(R.id.etPrecioArticulo_NuevoArticulo);
        EditText etNotas=(EditText) findViewById(R.id.etNotasArticulo_NuevoArticulo);
        ImageView ivFoto=(ImageView)findViewById(R.id.ivImagenArticulo_NuevoArticulo);


        etNombre.setText("");
        etCantidad.setText("");
        etPrecio.setText("");
        etNotas.setText("");
        rutaArquivo="";

        ivFoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_report_image));
    }

    public boolean añadirArticulo() {
        boolean añadido=false;

        boolean encontrado=false;
        EditText etNombre=(EditText) findViewById(R.id.etNombreArticulo_NuevoArticulo);
        for(Loxica_Articulo a: articulos){

            if(a.getNombre().toLowerCase().equals(etNombre.getText().toString().toLowerCase())) {
                //llamadas();
                encontrado=true;
            }

        }

        if(!encontrado) {
            EditText etCantidad = (EditText) findViewById(R.id.etCantidadArticulo_NuevoArticulo);
            EditText etPrecio = (EditText) findViewById(R.id.etPrecioArticulo_NuevoArticulo);
            EditText etNotas = (EditText) findViewById(R.id.etNotasArticulo_NuevoArticulo);
            int cantidad = 1;
            double precio = 0;
            if (!etCantidad.getText().toString().isEmpty()) {
                cantidad = Integer.parseInt(etCantidad.getText().toString());
            }
            if (!etPrecio.getText().toString().isEmpty()) {
                precio = Double.parseDouble(etPrecio.getText().toString());
            }
            //llamadas(etPrecio.getText().toString());
            Loxica_Articulo a =null;

            if(pasaFoto) {

                //Toast.makeText(this,arquivo.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                //if (!arquivo.exists()) return;          // Non hai foto
                rutaArquivo = imaxe.getAbsolutePath();




            }


            int id = baseDatos.obterNovoIdArticulo(idListaRecibida);
            //id+=1;
            Log.i("prueba", id+" - "+idListaRecibida);
            if(!etNombre.getText().toString().equals("")) {
                if (!rutaArquivo.equals("")) {
                    a = new Loxica_Articulo(id, etNombre.getText().toString(), false, cantidad, precio, etNotas.getText().toString(), rutaArquivo);
                } else {
                    a = new Loxica_Articulo(id, etNombre.getText().toString(), false, cantidad, precio, etNotas.getText().toString(), "");
                }
                long res = baseDatos.engadirArticulo(a, idListaRecibida);
                if (res > 0) {
                    Log.i("LRecibida", ":Exito: ");
                } else {
                    Log.i("LRecibida", ":NO: ");
                }

                articulos2.add(a);
                añadido=true;

            }else{
                Toast.makeText(this,"No puedes crear un articulo sin nombre, animal", Toast.LENGTH_SHORT).show();
            }
            /*
            Intent datos = new Intent();
             datos.putExtra(Activity_Lista.NEWArticulos, articulos2);
             setResult(RESULT_OK, datos);
             */
        }


        return añadido;
    }

    private void callMesaxe(String msg){
        Toast.makeText(this,msg+"", Toast.LENGTH_SHORT).show();
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
        Log.i("informacionsobrerequest",requestCode+"");
        Log.i("informacionsobreresult",resultCode+"");
        if(requestCode ==0 && resultCode == RESULT_OK )
        { {
            // Saca foto
            //File ruta = img;
            Log.i("entra","entra a guardar");
            File arquivo = new File(img, nomeFoto);
            Loxica_GardarImaxe lg=new Loxica_GardarImaxe();


            Bitmap bitmap=(Bitmap)data.getExtras().get("data");

            if(bitmap!=null) {
                String img = lg.SaveImage(this, bitmap, "Img");
                //if (!arquivo.exists()) return;          // Non hai foto
                //rutaArquivo=arquivo.getAbsolutePath();

                Log.i("entraguardarfoto", "entra a guardar");

                ImageView imgview = (ImageView) findViewById(R.id.ivImagenArticulo_NuevoArticulo);
                //Bitmap bitmap = BitmapFactory.decodeFile(rutaArquivo);
                //Toast.makeText(this,"LAs cosaS",Toast.LENGTH_SHORT).show();

                //bitmap = (Bitmap)data.getExtras().get("data");
//            imgview.setImageBitmap(bitmap);

                Log.i("rutaimagen", img);


                Bitmap bm = BitmapFactory.decodeFile(img);

                Log.i("bitmapimagen", bm + "");
                imgview.setImageBitmap(bm);


                //imaxe=new File(img,nomeFoto);

                if (!imaxe.getAbsoluteFile().equals("")) {
                    Toast.makeText(this, "aaaaa", Toast.LENGTH_SHORT).show();
                }

                //imgview.setScaleType(ImageView.ScaleType.FIT_XY);
/*
            if(!nomeSobrescribir.equals("")){
                //File f=new File(ruta,nomeSobrescribir);
                File f=new File(img,nomeSobrescribir);
                f.delete();
                arquivo.renameTo(f);
            }
*/


            }

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

    private static ConstraintLayout constraintLayout;

    private void aplicarPreferencias() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Boolean fondo= preferencias.getBoolean("preferencia_idFondo", false);
        if(fondo){
            setTheme(R.style.Nocturno);
            constraintLayout.setBackgroundColor(Color.BLACK);
        }else{
            setTheme(R.style.Diurno);
            constraintLayout.setBackgroundColor(Color.WHITE);


        }
        //nome.setText(valorNome);


    }

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

        guardaEstado.putSerializable("articulos", articulos);
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

        articulos =(ArrayList<Loxica_Articulo>)recuperaEstado.getSerializable("articulos");
        articulos2=(ArrayList<Loxica_Articulo>)recuperaEstado.getSerializable("articulos2");


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
        }else{
            ivimagen.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_report_image));
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
    protected void onStart() {
        super.onStart();
        baseDatos = baseDatos.getInstance(getApplicationContext());
        baseDatos.abrirBD();
    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicarPreferencias();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nuevoarticulo);
        constraintLayout = (ConstraintLayout) findViewById(R.id.bgFondo_NuevoArticulo);
        idListaRecibida=getIntent().getIntExtra("idLista",0);
//        Log.i("LRecibida", idListaRecibida+"");
        if((articulos =(ArrayList<Loxica_Articulo>) getIntent().getSerializableExtra("articulos"))==null){
            articulos =new ArrayList<Loxica_Articulo>();
        }

//        pedirPermiso();
        xestionarEventos();
    }
}