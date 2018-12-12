package cifprodolfoucha.com.listapp.Loxica;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Loxica_GardarImaxe {
    private Context TheThis;
    private String NameOfFolder = "/Listapp/imagenes";
    private String NameOfFile;

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
        Log.i("imagen",file.getAbsolutePath()+"");
        try {
            FileOutputStream fOut = new FileOutputStream(file);

            Log.i("imageToSave",ImageToSave+"");
            Log.i("fileOutputfalla",fOut+"");


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

    public void DeleteImage(Context context,String nombreRemplazo){
        TheThis=context;
        File remplazar=new File(nombreRemplazo);
        remplazar.delete();

    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(TheThis,
                new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void UnableToSave() {
        Toast.makeText(TheThis, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }

    private void AbleToSave() {
        Toast.makeText(TheThis, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }
}
