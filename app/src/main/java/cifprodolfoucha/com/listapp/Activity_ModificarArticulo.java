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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import cifprodolfoucha.com.listapp.Almacenamento.BaseDatos;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;
import cifprodolfoucha.com.listapp.Loxica.Loxica_GardarImaxe;

import static android.support.v4.content.FileProvider.getUriForFile;

public class Activity_ModificarArticulo extends Activity {

    private String nomeFoto="";
    private String rutaArquivo="",rutaArquivoRecibido="";
    private String nomeSobrescribir="";
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private final int CODIGO_IDENTIFICADOR=1;
    private File img,imaxeRecibida,imaxe,directorio,temp;
    private Loxica_Articulo articulo;
    private int idL;
    private boolean modFoto=false;

    private BaseDatos baseDatos;


    private void xestionarEventos() {
        final ImageButton ibtn_Cancelar = findViewById(R.id.ibtn_VolverModificarArticulo);
        final ImageButton ibtn_Guardar = findViewById(R.id.ibtn_GuardarModificarArticulo);
        final ImageButton ibtn_Foto=findViewById(R.id.ibtn_Foto_ModificarArticulo);

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
                Log.i("prueba", articulo.getNombre()+"");
                Loxica_Articulo a=modificarArticulo(articulo);

                if(imaxeRecibida!=null){
                    imaxeRecibida.delete();
                }else if(imaxe!=null){

                }



                baseDatos.modArticulo(a.getId(),idL,a.getNombre(),a.getCantidad(),a.getPrecio(),a.getNotas(),a.getRutaImagen(),a.isSeleccionado());

                Intent datos = new Intent();

                datos.putExtra(Activity_Lista.MODArticulo,a);
                setResult(RESULT_OK, datos);
                finish();
                /**/
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

                if(!rutaArquivoRecibido.equals("")){
                    String[] nombre=rutaArquivoRecibido.split("/");
                    nomeSobrescribir=nombre[nombre.length-1];
                    //Toast.makeText(getApplicationContext(),nomeSobrescribir,Toast.LENGTH_LONG).show();
                }


                //nomeFoto="img-"+now+".jpg";
                //imaxe = new File(img,nomeFoto);


                /*
                Uri contentUri=null;
                if (Build.VERSION.SDK_INT >= 24) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_REQUEST_CODE);
                    }

                    contentUri = getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", imaxe);
                }
                else {
                    contentUri = Uri.fromFile(imaxe);
                }

                */
                Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                /*
                intento.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                */
                startActivityForResult(intento, MY_CAMERA_REQUEST_CODE);


            }
        });



    }

    private void cargarArticulo(){

        EditText etNombreArticulo=findViewById(R.id.etNombreArticulo_ModificarArticulo);
        EditText etPrecioArticulo=findViewById(R.id.etPrecioArticulo_ModificarArticulo);
        EditText etCantidadArticulo=findViewById(R.id.etCantidadArticulo_ModificarArticulo);
        EditText etNotasArticulo=findViewById(R.id.etNotasArticulo_ModificarArticulo);

        ImageView ivFoto=findViewById(R.id.ivImagenArticulo_ModificarArticulo);

        etNombreArticulo.setText(articulo.getNombre());
        if(articulo.getPrecio()!=0) {
            etPrecioArticulo.setText(articulo.getPrecio()+ "");
        }
        etCantidadArticulo.setText(articulo.getCantidad()+"");
        etNotasArticulo.setText(articulo.getNotas());
        if(!articulo.getRutaImagen().equals("")){
            Bitmap bitmap = BitmapFactory.decodeFile(articulo.getRutaImagen());
            ivFoto.setImageBitmap(bitmap);
        }else{
            ivFoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_report_image));
        }


    }

    private Loxica_Articulo modificarArticulo(Loxica_Articulo art){
        EditText etNombreArticulo=findViewById(R.id.etNombreArticulo_ModificarArticulo);
        EditText etPrecioArticulo=findViewById(R.id.etPrecioArticulo_ModificarArticulo);
        EditText etCantidadArticulo=findViewById(R.id.etCantidadArticulo_ModificarArticulo);
        EditText etNotasArticulo=findViewById(R.id.etNotasArticulo_ModificarArticulo);

        int cantidad = 0;
        double precio = 0;
        if (!etCantidadArticulo.getText().toString().isEmpty()) {
            cantidad = Integer.parseInt(etCantidadArticulo.getText().toString());
        }
        if (!etPrecioArticulo.getText().toString().isEmpty()) {
            precio = Double.parseDouble(etPrecioArticulo.getText().toString());
        }

        art.setNombre(etNombreArticulo.getText().toString());
        art.setCantidad(cantidad);
        art.setPrecio(precio);
        art.setNotas(etNotasArticulo.getText().toString());
        if(modFoto) {
            if (!rutaArquivo.equals("")) {
                art.setRutaImagen(rutaArquivo);
            }else{
                art.setRutaImagen("");
            }
        }else{
            if (!rutaArquivoRecibido.equals("")) {
                art.setRutaImagen(rutaArquivoRecibido);
            }else{
                art.setRutaImagen("");
            }
        }
        //Toast.makeText(this,articulo.getNombre().toString(),Toast.LENGTH_SHORT).show();
        return art;
        //Toast.makeText(this,precio+"",Toast.LENGTH_SHORT).show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.i("prueba", resultCode+"");
        if(requestCode ==MY_CAMERA_REQUEST_CODE && resultCode == RESULT_OK)
        { {
            /*
            if (!imaxe.exists()) return;          // Non hai foto
            rutaArquivo=imaxe.getAbsolutePath();

            */

            File arquivo = new File(img, nomeFoto);
            Loxica_GardarImaxe lg=new Loxica_GardarImaxe();


            Bitmap bitmap=(Bitmap)data.getExtras().get("data");

            if(bitmap!=null) {
                String img = lg.SaveImage(this, bitmap, "Img");
                //if (!arquivo.exists()) return;          // Non hai foto
                //rutaArquivo=arquivo.getAbsolutePath();

                Log.i("entraguardarfoto", "entra a guardar");

                ImageView imgview = (ImageView) findViewById(R.id.ivImagenArticulo_ModificarArticulo);
                //Bitmap bitmap = BitmapFactory.decodeFile(rutaArquivo);
                //Toast.makeText(this,"LAs cosaS",Toast.LENGTH_SHORT).show();

                //bitmap = (Bitmap)data.getExtras().get("data");
//            imgview.setImageBitmap(bitmap);

                Log.i("rutaimagen", img);

                arquivo = new File(img);
                imaxe = arquivo;
                rutaArquivo = img;


                Bitmap bm = BitmapFactory.decodeFile(img);

                Log.i("bitmapimagen", bm + "");
                imgview.setImageBitmap(bm);
                modFoto=true;


            }
        }
        }
    }
    public void pedirPermiso(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CODIGO_IDENTIFICADOR);
        }
    }

    private static ConstraintLayout constraintLayout;

    private void aplicarPreferencias() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        EditText etNombre = (EditText) findViewById(R.id.etNombreArticulo_ModificarArticulo);
        EditText etCantidad = (EditText) findViewById(R.id.etCantidadArticulo_ModificarArticulo);
        EditText etPrecio = (EditText) findViewById(R.id.etPrecioArticulo_ModificarArticulo);
        EditText etNotas = (EditText) findViewById(R.id.etNotasArticulo_ModificarArticulo);
        TextView tvNombre=(TextView) findViewById(R.id.tvArticulo_ModificarAticulo);
        TextView tvNotas=(TextView) findViewById(R.id.tvNotasArticulo_ModificarArticulo);
        TextView tvCantidad=(TextView) findViewById(R.id.tvCantidadArticulo_ModificarArticulo);
        TextView tvPrecio=(TextView) findViewById(R.id.tvPrecioArticulo_ModificarArticulo);

        Boolean fondo= preferencias.getBoolean("preferencia_idFondo", false);
        if(fondo){
            setTheme(R.style.Nocturno);
            constraintLayout.setBackgroundColor(Color.BLACK);

            etNombre.setTextColor(getResources().getColor(R.color.white));
            etNombre.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            etPrecio.setTextColor(getResources().getColor(R.color.white));
            etPrecio.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            etNotas.setTextColor(getResources().getColor(R.color.white));
            etNotas.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            etCantidad.setTextColor(getResources().getColor(R.color.white));
            etCantidad.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            tvNombre.setTextColor(getResources().getColor(R.color.white));
            tvNotas.setTextColor(getResources().getColor(R.color.white));
            tvPrecio.setTextColor(getResources().getColor(R.color.white));
            tvCantidad.setTextColor(getResources().getColor(R.color.white));
        }else{
            setTheme(R.style.Diurno);
            constraintLayout.setBackgroundColor(Color.WHITE);


        }
        //nome.setText(valorNome);


    }

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);

        EditText etNombre = (EditText) findViewById(R.id.etNombreArticulo_ModificarArticulo);
        EditText etCantidad = (EditText) findViewById(R.id.etCantidadArticulo_ModificarArticulo);
        EditText etPrecio = (EditText) findViewById(R.id.etPrecioArticulo_ModificarArticulo);
        EditText etNotas = (EditText) findViewById(R.id.etNombreArticulo_ModificarArticulo);
        ImageView ivimagen=(ImageView) findViewById(R.id.ivImagenArticulo_ModificarArticulo);

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

        guardaEstado.putSerializable("articuloModificado", articulo);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        EditText etNombre = (EditText) findViewById(R.id.etNombreArticulo_ModificarArticulo);
        EditText etCantidad = (EditText) findViewById(R.id.etCantidadArticulo_ModificarArticulo);
        EditText etPrecio = (EditText) findViewById(R.id.etPrecioArticulo_ModificarArticulo);
        EditText etNotas = (EditText) findViewById(R.id.etNombreArticulo_ModificarArticulo);
        ImageView ivimagen=(ImageView) findViewById(R.id.ivImagenArticulo_ModificarArticulo);

        sNombre=recuperaEstado.getString("nombre");
        iCantidad=recuperaEstado.getInt("cantidad");
        dPrecio=recuperaEstado.getDouble("precio");
        sNotas=recuperaEstado.getString("notas");

        sImagen = recuperaEstado.getString("imagen");

        articulo =(Loxica_Articulo)recuperaEstado.getSerializable("articuloModificado");


        etNombre.setText(sNombre);
        if(Cant) {
            etCantidad.setText(iCantidad);
        }
        if(Pre) {
            etPrecio.setText(dPrecio + "");
        }
        etNotas.setText(sNotas);
        if(!sImagen.equals("")){
            rutaArquivoRecibido=sImagen;
            Bitmap bitmap = BitmapFactory.decodeFile(sImagen);
            ivimagen.setImageBitmap(bitmap);
        }
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
    public void onBackPressed() {
        super.onBackPressed();
        if(imaxe!=null){
            imaxe.delete();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicarPreferencias();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_modificararticulo);
        constraintLayout = (ConstraintLayout) findViewById(R.id.bgFondo_ModificarArticulo);
        idL=getIntent().getIntExtra("idLista",0);
        articulo =(Loxica_Articulo) getIntent().getSerializableExtra("articulo");
        setTitle("Modificar: "+ articulo.getNombre());
        rutaArquivoRecibido= articulo.getRutaImagen();
        if(!rutaArquivoRecibido.equals("")){
            imaxeRecibida=new File(rutaArquivoRecibido);
        }
        xestionarEventos();
        pedirPermiso();
        cargarArticulo();


    }
}
