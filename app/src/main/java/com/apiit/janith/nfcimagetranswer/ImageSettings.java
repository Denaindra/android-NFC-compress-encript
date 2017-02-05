package com.apiit.janith.nfcimagetranswer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Gayan Denaindra on 1/28/2017.
 */

public class ImageSettings {

    private static ImageSettings instance;

    public ImageSettings() {

    }

    public static ImageSettings getInstance() {

        return instance = new ImageSettings();
    }

    public Bitmap ImageResize(Bitmap bmp) {
        return Bitmap.createScaledBitmap(bmp, 1440, 2560, false);
    }

    public String Based64Generator(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,50, baos);
        byte[] b = baos.toByteArray();
        String BitmapString = Base64.encodeToString(b, Base64.DEFAULT);
        return BitmapString;
    }

    public Bitmap BitMapGenerator(String encriptString) {
        try {
            byte[] encodeByte = Base64.decode(encriptString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
