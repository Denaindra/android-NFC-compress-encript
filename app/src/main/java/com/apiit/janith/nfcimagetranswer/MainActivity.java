package com.apiit.janith.nfcimagetranswer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 1;
    private Button btn1, btn2, btn3, btn4;
    private ImageView imageViwer;
    private String filename;
    private String imgDecodableString;
    private FileSettings filedetaisl;
    private ImageSettings con;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.loadimage);
        btn2 = (Button) findViewById(R.id.compress);
        btn3 = (Button) findViewById(R.id.zip);
        btn4 = (Button) findViewById(R.id.send);

        imageViwer = (ImageView) findViewById(R.id.image_view);
        filedetaisl = FileSettings.getInstance();
        con = ImageSettings.getInstance();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
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
                imageViwer.setImageBitmap(con.ImageResize(bmp));
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e + "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }
}


