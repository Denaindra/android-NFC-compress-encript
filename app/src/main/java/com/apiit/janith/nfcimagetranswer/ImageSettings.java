package com.apiit.janith.nfcimagetranswer;

import android.graphics.Bitmap;

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
}
