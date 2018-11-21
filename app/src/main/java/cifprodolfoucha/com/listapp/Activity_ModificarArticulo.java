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

public class Activity_ModificarArticulo extends Activity {

    private String nomeFoto="";
    private String rutaArquivo="";
    private String nomeSobrescribir="";
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private final int CODIGO_IDENTIFICADOR=1;
    private Articulo articulo=new Articulo();



    private void xestionarEventos() {
        final ImageButton ibtn_Cancelar = findViewById(R.id.ibtn_VolverModificarArticulo);
        final ImageButton ibtn_Guardar = findViewById(R.id.ibtn_GuardarModificarArticulo);
        final ImageButton ibtn_Foto=findViewById(R.id.ibtn_Foto_ModificarArticulo);

        ibtn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ibtn_Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Articulo a=modificarArticulo();
                //Toast.makeText(getApplicationContext(),articulo.getNombre().toString(),Toast.LENGTH_SHORT).show();
                //Articulo a=articulo;
                //Toast.makeText(getParent(),a.getNombre().toString(),Toast.LENGTH_SHORT).show();

                Intent datos = new Intent();

                datos.putExtra(Activity_Lista.MODArticulo,articulo);
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
                File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                Time now=new Time();
                now.setToNow();
                if(!rutaArquivo.equals("")){
                    String[] nombre=rutaArquivo.split("/");
                    nomeSobrescribir=nombre[nombre.length-1];
                    //Toast.makeText(getApplicationContext(),nomeSobrescribir,Toast.LENGTH_LONG).show();
                }
                nomeFoto = "img-" + now + ".jpg";
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
            Bitmap bitmap = BitmapFactory.decodeFile(null);
            ivFoto.setImageBitmap(bitmap);
        }


    }

    private Articulo modificarArticulo(){
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

        articulo.setNombre(etNombreArticulo.getText().toString());
        articulo.setCantidad(cantidad);
        articulo.setPrecio(precio);
        articulo.setNotas(etNotasArticulo.getText().toString());
        if(!rutaArquivo.equals("")){
            articulo.setRutaImagen(rutaArquivo);
        }
        //Toast.makeText(this,articulo.getNombre().toString(),Toast.LENGTH_SHORT).show();
        return articulo;
        //Toast.makeText(this,precio+"",Toast.LENGTH_SHORT).show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==0 && resultCode == RESULT_OK)
        { {
            // Saca foto
            File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File arquivo = new File(ruta, nomeFoto);
            if (!arquivo.exists()) return;          // Non hai foto
            rutaArquivo=arquivo.getAbsolutePath();
            ImageView imgview = (ImageView) findViewById(R.id.ivImagenArticulo_ModificarArticulo);
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

        guardaEstado.putSerializable("articuloModificado",articulo);
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
/*
        articulo.setNombre(sNombre);
        articulo.setCantidad(iCantidad);
        articulo.setPrecio(dPrecio);
        articulo.setNotas(sNotas);
        articulo.setRutaImagen(sImagen);
*/
        articulo=(Articulo)recuperaEstado.getSerializable("articulo");


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
        setContentView(R.layout.layout_modificararticulo);
        articulo=(Articulo) getIntent().getSerializableExtra("articulo");
        setTitle("Modificar: "+articulo.getNombre());
        rutaArquivo=articulo.getRutaImagen();
        xestionarEventos();
        pedirPermiso();
        cargarArticulo();


    }
}
