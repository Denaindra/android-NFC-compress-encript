package com.apiit.janith.nfcimagetranswer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.apiit.janith.nfcimagetranswer.Constant.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Gayan Denaindra on 1/28/2017.
 */

public class FileSettings {
    private static FileSettings instance;
    private File file;
    private File encriptImage;
    private Context context;

    public FileSettings(Context context) {
        this.context = context;
    }

    public static FileSettings getInstance(Context context) {

        return instance = new FileSettings(context);
    }

    public String getFileDetails(Cursor cursor, int columnIndex) {
        String picturePath = cursor.getString(columnIndex);
        file = new File(picturePath);
        return file.getName();
    }

    public File getFile() {
        return this.file;
    }

    public Boolean CheckFileAvailaBility() {
        if (file == null) {
            return false;
        } else {
            return true;
        }
    }

    public Bitmap FIleToBitmap(File compressFile) {
        Bitmap bitmap = BitmapFactory.decodeFile(compressFile.getPath());
        return bitmap;
    }

    public void SaveEncriptFile(String EncriptString) {
        try {
            File newFolder = new File(Environment.getExternalStorageDirectory(), "TestFolder");
            if (!newFolder.exists()) {
                newFolder.mkdir();
            }
            try {
                this.encriptImage = new File(newFolder, "image" + ".png");
                encriptImage.createNewFile();
                FileOutputStream fOut = new FileOutputStream(encriptImage);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(EncriptString);
                myOutWriter.close();
                fOut.flush();
                fOut.close();
            } catch (Exception ex) {
                System.out.println("ex: " + ex);
            }
        } catch (Exception e) {
            System.out.println("e: " + e);
        }
        setCompressedImage();
    }

    private void setCompressedImage() {
        try {
            Toast.makeText(this.context, Constants.getMessage4(), Toast.LENGTH_LONG).show();
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(encriptImage);
            mediaScanIntent.setData(contentUri);
            this.context.sendBroadcast(mediaScanIntent);
        } catch (Exception ex) {
            Log.d("Exception 2", ex.getMessage());
        }
    }

    public void ReadEncriptFile() {

    }

}
