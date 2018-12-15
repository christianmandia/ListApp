package cifprodolfoucha.com.listapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
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
import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Almacenamento.BaseDatos;
import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;
import cifprodolfoucha.com.listapp.Loxica.Loxica_GardarImaxe;

/**
 * @author Christian López Martín
 * @version 1
 **/

public class Activity_NuevoArticulo extends Activity {

    //private String nomeFoto="";
    //private boolean pasaFoto;

    /**
     * rutaArquivo será unha referencia á ruta da imaxe.
     **/
    private String rutaArquivo="";
    /**
     * nomeSobrescribir será unha referencia á ruta da imaxe, por se sacamos otra, eliminar a primeira.
     **/
    private String nomeSobrescribir="";
    /**
     * gardar é unha referencia que indica que se sacou unha foto.
     **/
    private static boolean gardar=false;
    /**
     * MY_CAMERA_REQUEST_CODE é un código utilizado ao chamar á camara e recibir os datos dela.
     **/
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    /**
     *      Serán os cartafoles dá aplicación e o ficheiro da imaxe.
     **/
    private File img,imaxe,directorio;
    /**
     * articulos é un ArrayList dos artículos, que recibe da Activity_Lista.
     **/
    private ArrayList<Loxica_Articulo> articulos =new ArrayList();
    /**
     * articulos2 é un ArrayList onde se gardarán os artículos que vaiamos engadindo, para mandalos de volta a Activity_Lista e que os engada ó RecycleView.
     **/
    private ArrayList<Loxica_Articulo> articulos2=new ArrayList();
    /**
     * idListaRecibida é unha referencia ao id da Lista, dato que se recibiu da Activity_Lista.
     **/
    private int idListaRecibida;
    /**
     * baseDatos é un acceso á clase BaseDatos onde se xestionan as consultas á base de datos.
     **/
    private BaseDatos baseDatos;
    /**
     * constraintLayout é unha referencia do layout da Activity para poder cambiar o fondo dependendo de se nos Axustes seleccionamos o modo noite.
     **/
    private static ConstraintLayout constraintLayout;
    /**
     * sNombre será unha referencia ao contido do EditText do nome do Artículo para poder recuperalo se se xira a pantalla.
     **/
    private String sNombre;
    /**
     * iCantidad será unha referencia ao contido do EditText da cantidade do Artículo para poder recuperalo se se xira a pantalla.
     **/
    private int iCantidad;
    /**
     * dPrecio será unha referencia ao contido do EditText do precio do Artículo para poder recuperalo se se xira a pantalla.
     **/
    private double dPrecio;
    /**
     * sNotas será unha referencia ao contido do EditText das notas do Artículo para poder recuperalas se se xira a pantalla.
     **/
    private String sNotas;
    /**
     * sImagen será unha referencia á ruta da imaxe do Artículo para poder recuperalo se se xira a pantalla, que se cargará no ImageView.
     **/
    private String sImagen="";
    /**
     * Cant será unha variable que indique se o Artículo a engadir ten algun dato escrito no EditText de cantidade.
     **/
    private boolean Cant;
    /**
     * Pre será unha variable que indique se o Artículo a engadir ten algun dato escrito no EditText de precio.
     **/
    private boolean Pre;


    /**
     * Controla os clicks que se realicen nos elementos da Activity.
     **/
    private void xestionarEventos(){
         ImageButton ibtn_Cancelar=findViewById(R.id.ibtn_CancelarNuevoArticulo);
         ImageButton ibtn_Guardar=findViewById(R.id.ibtn_GuardarNuevoArticulo);
         ImageButton ibtn_GuardarYContinuar=findViewById(R.id.ibtn_GuardarNuevoArticuloYContinuar);
         ImageButton ibtn_Foto=findViewById(R.id.ibtn_AñadirFoto_NuevoArticulo);

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
                    imaxe=null;
                    gardar=false;
                }
            }
        });


        ibtn_Foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                //Time now=new Time();
                //now.setToNow();

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
                }
//                nomeFoto="img-"+now+".jpg";
                //File arquivo = new File(ruta,nomeFoto);
                //imaxe = new File(img,nomeFoto);


//                Uri contentUri=null;
/*
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
*/



                Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //intento.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                Intent cam=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cam, MY_CAMERA_REQUEST_CODE);

                if(!nomeSobrescribir.equals("")){
                    File f=new File(img,nomeSobrescribir);
                    f.delete();
                    imaxe.renameTo(f);
                }

            }

        });
    }

    /**
     * Limpia os EditText e o ImageView se se queren engadir máis Artículos.
     **/
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

    /**
     * Engade un Artículo á base de datos e ó ArrayList articulos2, sempre que non exista nesa lista un Artículo co mesmo nome.
     * @return true se o Artículo puido ser engadido.
     **/
    public boolean añadirArticulo() {
        boolean añadido=false;

        boolean encontrado=false;
        EditText etNombre=(EditText) findViewById(R.id.etNombreArticulo_NuevoArticulo);
        for(Loxica_Articulo a: articulos){

            if(a.getNombre().toLowerCase().equals(etNombre.getText().toString().toLowerCase())) {

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
            Loxica_Articulo a;

            /*
            if(pasaFoto) {
                rutaArquivo = imaxe.getAbsolutePath();

            }
            */

            int id = baseDatos.obterNovoIdArticulo(idListaRecibida);
            if(!etNombre.getText().toString().equals("")) {
                if (!rutaArquivo.equals("")) {
                    a = new Loxica_Articulo(id, etNombre.getText().toString(), false, cantidad, precio, etNotas.getText().toString(), rutaArquivo);
                } else {
                    a = new Loxica_Articulo(id, etNombre.getText().toString(), false, cantidad, precio, etNotas.getText().toString(), "");
                }
                long res = baseDatos.engadirArticulo(a, idListaRecibida);
                if (res > 0) {
                    articulos2.add(a);
                    añadido=true;
                }else{
                    Toast.makeText(this,getResources().getString(R.string.str_nuevoarticulo_erro1), Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(this,getResources().getString(R.string.str_nuevoarticulo_erro2), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,getResources().getString(R.string.str_nuevoarticulo_erro3), Toast.LENGTH_SHORT).show();
        }


        return añadido;
    }

    @Override
    public void finish() {
        Intent datos = new Intent();
        datos.putExtra(Activity_Lista.NEWArticulos,articulos2);
        setResult(RESULT_OK, datos);
        if(!gardar){
            if(imaxe!=null) {
                imaxe.delete();
            }
        }
        super.finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==MY_CAMERA_REQUEST_CODE && resultCode == RESULT_OK )
        {
            {
            File arquivo;
            Loxica_GardarImaxe lg=new Loxica_GardarImaxe();
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
                if(bitmap!=null) {
                    String img = lg.SaveImage(this, bitmap, "Img");
                    ImageView imgview = (ImageView) findViewById(R.id.ivImagenArticulo_NuevoArticulo);
                    arquivo=new File(img);
                    imaxe=arquivo;
                    rutaArquivo=img;
                    Bitmap bm = BitmapFactory.decodeFile(img);
                    imgview.setImageBitmap(bm);
                    gardar=true;

                }

            }
        }
    }

    /**
     * Aplica a preferencia do Modo Nocturno e obten a preferencia onde se garda a versión do XMl.
     **/
    private void aplicarPreferencias() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        EditText etNombre = (EditText) findViewById(R.id.etNombreArticulo_NuevoArticulo);
        EditText etCantidad = (EditText) findViewById(R.id.etCantidadArticulo_NuevoArticulo);
        EditText etPrecio = (EditText) findViewById(R.id.etPrecioArticulo_NuevoArticulo);
        EditText etNotas = (EditText) findViewById(R.id.etNotasArticulo_NuevoArticulo);
        TextView tvNombre=(TextView) findViewById(R.id.tvNombreArticulo_NuevoAticulo);
        TextView tvNotas=(TextView) findViewById(R.id.tvNotasArticulo_NuevoArticulo);
        TextView tvCantidad=(TextView) findViewById(R.id.tvCantidadArticulo_NuevoArticulo);
        TextView tvPrecio=(TextView) findViewById(R.id.tvPrecioArticulo_NuevoArticulo);

        Boolean fondo= preferencias.getBoolean("preferencia_idFondo", false);
        if(fondo){

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

            setTheme(R.style.Nocturno);
            constraintLayout.setBackgroundColor(Color.BLACK);
        }else{
            setTheme(R.style.Diurno);
            constraintLayout.setBackgroundColor(Color.WHITE);
        }


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
        etNotas.setText(sNotas);
        if(!sImagen.equals("")){
            rutaArquivo=sImagen;
            Bitmap bitmap = BitmapFactory.decodeFile(sImagen);
            ivimagen.setImageBitmap(bitmap);
        }else{
            ivimagen.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_report_image));
        }
    }

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
        if((articulos =(ArrayList<Loxica_Articulo>) getIntent().getSerializableExtra("articulos"))==null){
            articulos =new ArrayList<Loxica_Articulo>();
        }
        xestionarEventos();
    }
}