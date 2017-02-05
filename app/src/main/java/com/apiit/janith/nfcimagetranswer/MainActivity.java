package com.apiit.janith.nfcimagetranswer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.apiit.janith.nfcimagetranswer.Constant.Constants;
import com.apiit.janith.nfcimagetranswer.En_de_crypt.EncryptAndDecrypt;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 1;
    private Button loadimage, compress, encript, nfcsend;
    private ImageView imageViwer;
    private String filename;
    private String imgDecodableString;
    private FileSettings filedetaisl;
    private ImageSettings imagesettings;
    private ImageCompressor imgCompressor;
    private GoogleApiClient client;
    private EncryptAndDecrypt encription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI propperties
        loadimage = (Button) findViewById(R.id.loadimage);
        compress = (Button) findViewById(R.id.compress);
        encript = (Button) findViewById(R.id.encript);
        nfcsend = (Button) findViewById(R.id.send);
        imageViwer = (ImageView) findViewById(R.id.image_view);

        //static instances
        filedetaisl = FileSettings.getInstance(this);
        imagesettings = ImageSettings.getInstance();
        imgCompressor = ImageCompressor.getInstance(this);
        encription = EncryptAndDecrypt.getInstance();

        //Click events
        loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }

        });
        compress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filedetaisl.CheckFileAvailaBility()) {
                    imgCompressor.StartCompressImage(filedetaisl.getFile());
                } else {
                    Toast.makeText(getApplicationContext(), Constants.getMessage2(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        encript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //encript image
//                File file = imgCompressor.getCompressImageFile();
//                Bitmap map = filedetaisl.FIleToBitmap(file);
//                String base64 = imagesettings.Based64Generator(map);
//                encription.EncriptImage(base64);
//                filedetaisl.WriteEncriptFile(encription.getEncripSting());

                //decript image
                String encriptText = filedetaisl.ReadEncriptFile();
                encription.Decrypt(encriptText);
                Bitmap image = imagesettings.BitMapGenerator(encription.getdecrpytString());
                imageViwer.setImageBitmap(image);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                filename = filedetaisl.getFileDetails(cursor, columnIndex);
                cursor.close();
                Bitmap bmp = BitmapFactory.decodeFile(imgDecodableString);
                imageViwer.setImageBitmap(imagesettings.ImageResize(bmp));
                makeText(this, Constants.getMessage1(), LENGTH_LONG).show();
            } else {
                makeText(this, Constants.getMessage2(),
                        LENGTH_LONG).show();
            }
        } catch (Exception e) {
            makeText(this, e + Constants.getMessage3(), LENGTH_LONG)
                    .show();
        }
    }


}


