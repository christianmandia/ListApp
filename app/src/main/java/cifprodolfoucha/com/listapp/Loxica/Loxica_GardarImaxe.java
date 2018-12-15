package cifprodolfoucha.com.listapp.Loxica;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Loxica_GardarImaxe {
    /**
     * TheThis é a referencia que se lle dará a un contexto que se lle pasa.
     **/
    private Context TheThis;
    /**
     * NameOfFolder é o nome do cartafol onde se vai gardar a imaxe.
     **/
    private String NameOfFolder = "/Listapp/imagenes";
    /**
     * NameOfFile é o nome que se lle dará a imaxe.
     **/
    private String NameOfFile;


    /**
     * Garda a imaxe
     * @param context é un contexto que se lle pasa.
     * @param ImageToSave é o bitmap da imaxe a gardar.
     * @param nombre é o nome que se lle quere dar á imaxe.
     * @return devolve a ruta onde se gardou a imaxe.
     */
    public String SaveImage(Context context, Bitmap ImageToSave, String nombre) {

        TheThis = context;
        String resultado="";
        String file_path = Environment.getExternalStorageDirectory()+ NameOfFolder;



        String CurrentDateAndTime = getCurrentDateAndTime();
        File dir = new File(file_path);

        if (!dir.exists()) {
            dir.mkdirs();
        }


        NameOfFile=nombre;
        File file = new File(dir, NameOfFile + CurrentDateAndTime + ".jpg");

        resultado=file.getAbsolutePath();
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        }

        catch(FileNotFoundException e) {
            UnableToSave();
            e.printStackTrace();
        }
        catch(IOException e) {
            UnableToSave();
            e.printStackTrace();
        }

        return resultado;
    }

    /**
     * Asegúrase de que creouse a imaxe.
     * @param file pásamoslle o ficheiro da imaxe.
     **/
    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(TheThis,
                new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    /**
     * Obten a fecha e hora actual para xerar un nome xenérico para a foto.
     * @return un String coa fecha e a hora actual.
     **/
    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    /**
     * Chámaselle cando non se puido gardar a imaxe.
     **/
    private void UnableToSave() {
        //Toast.makeText(TheThis, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Chámaselle cando se puido gardar a imaxe.
     **/
    private void AbleToSave() {
        //Toast.makeText(TheThis, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }
}
