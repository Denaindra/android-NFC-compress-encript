package com.apiit.janith.nfcimagetranswer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.apiit.janith.nfcimagetranswer.Constant.Constants;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

import static android.R.attr.bitmap;
import static android.R.attr.name;

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

    public void WriteEncriptFile(String EncriptString) {
        try {
            File newFolder = new File(Environment.getExternalStorageDirectory(), Constants.getFolderName());
            if (!newFolder.exists()) {
                newFolder.mkdir();
            }
            try {
                this.encriptImage = new File(newFolder, Constants.getEncriptFile());
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
        setCompressedImage(encriptImage);
    }

    public void BitmapToFile(Bitmap image) {
        File imageFile = new File(Constants.getDecriptFilePath());
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        setCompressedImage(imageFile);
    }

    public void DeleteFile() {
        try {
            File file = new File(Constants.getDecriptFilePath());
            file.delete();
        } catch (Exception ex) {

        }
    }

    public void setCompressedImage(File imageFile) {
        try {
            Toast.makeText(this.context, Constants.getMessage4(), Toast.LENGTH_LONG).show();
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(imageFile);
            mediaScanIntent.setData(contentUri);
            this.context.sendBroadcast(mediaScanIntent);
        } catch (Exception ex) {
            Log.d("Exception 2", ex.getMessage());
        }
    }

    public String ReadEncriptFile() {
        File sdcard = new File(Environment.getExternalStorageDirectory(), Constants.getNfcFolder());
        File file = new File(sdcard, Constants.getNfcImage());

        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = text.toString();
        return line;
    }

    public byte[] FileConvertToByteArray(String fileName) {

        RandomAccessFile f;
        try {
            f = new RandomAccessFile(fileName, "r");
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength)
                throw new IOException("File size >= 2 GB");
            // Read file and return data
            byte[] FileByteString = new byte[length];
            f.readFully(FileByteString);
            f.close();

            return FileByteString;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeVideoFile(String VideBase64) {
        try {
            File newFolder = new File(Environment.getExternalStorageDirectory(), Constants.getFolderName());
            if (!newFolder.exists()) {
                newFolder.mkdir();
            }
            try {
                File StringVideo = new File(newFolder,Constants.getStringvideFile());
                StringVideo.createNewFile();
                FileOutputStream fOut = new FileOutputStream(StringVideo);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(VideBase64);
                myOutWriter.close();
                fOut.flush();
                fOut.close();
            } catch (Exception ex) {
                System.out.println("ex: " + ex);
            }
        } catch (Exception e) {
            System.out.println("e: " + e);
        }
        setCompressedImage(encriptImage);
    }

    public String ReadtheVideFile() {
        File sdcard = new File(Environment.getExternalStorageDirectory(), Constants.getFolderName());
        File file = new File(sdcard,Constants.getStringvideFile());

        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = text.toString();
        return line;
    }

    public void ByteArrayToString(byte[] bytearray) {
        try {
            File newFolder = new File(Environment.getExternalStorageDirectory(), Constants.getFolderName());
            if (!newFolder.exists()) {
                newFolder.mkdir();
            }
            File VideoFile = new File(newFolder, Constants.getVideoFile());
            VideoFile.createNewFile();
            FileOutputStream out = new FileOutputStream(VideoFile);

            out.write(bytearray);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
