package com.apiit.janith.nfcimagetranswer;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.apiit.janith.nfcimagetranswer.Constant.Constants;

import java.io.File;

import id.zelory.compressor.Compressor;

public class ImageCompressor {

    private static ImageCompressor instance;
    private File compressedImage;
    private Context context;

    public ImageCompressor(Context context) {
        this.context = context;
    }

    public static ImageCompressor getInstance(Context context) {
        instance = new ImageCompressor(context);
        return instance;
    }

    public void StartCompressImage(File CaptureImage) {

        try {
            compressedImage = new Compressor.Builder(context)
                    .setMaxWidth(Constants.getMaxWidth())
                    .setMaxHeight(Constants.getMaxHeight())
                    .setQuality(Constants.getQulity())
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .build()
                    .compressToFile(CaptureImage);
            //setCompressedImage();
            Toast.makeText(this.context, Constants.getMessage5(), Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d("Exception 1", ex.getMessage());
        }

    }

    public File getCompressImageFile() {
        return compressedImage;
    }

}
