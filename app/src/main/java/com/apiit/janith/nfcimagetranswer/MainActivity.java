package com.apiit.janith.nfcimagetranswer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.apiit.janith.nfcimagetranswer.Constant.Constants;
import com.apiit.janith.nfcimagetranswer.En_de_crypt.EncryptAndDecrypt;
import com.apiit.janith.nfcimagetranswer.En_de_crypt.VideoSettings;

import java.io.File;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 1;
    private static int REQUEST_TAKE_GALLERY_VIDEO = 1;
    private Button loadimage, loadvideos, compress, encript, decript, nfcsend, decriptVideo;
    private EditText encerippass;
    private ImageView imageViwer;
    private String filename;
    private String imgDecodableString;
    private FileSettings filedetaisl;
    private ImageSettings imagesettings;
    private ImageCompressor imgCompressor;
    private VideoSettings videoInstance;
    private EncryptAndDecrypt encription;
    private NFCSettings nfcinstace;
    private Boolean imageStaus;
    private Boolean videoStatus;
    private String VideoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI propperties
        loadimage = (Button) findViewById(R.id.loadimage);
        loadvideos = (Button) findViewById(R.id.loadvide);
        compress = (Button) findViewById(R.id.compress);
        encript = (Button) findViewById(R.id.encript);
        nfcsend = (Button) findViewById(R.id.send);
        decript = (Button) findViewById(R.id.decript);
        imageViwer = (ImageView) findViewById(R.id.image_view);
        encerippass = (EditText) findViewById(R.id.encriptPass);
        decriptVideo = (Button) findViewById(R.id.decriptvideo);

        //static instances
        filedetaisl = FileSettings.getInstance(this);
        imagesettings = ImageSettings.getInstance();
        imgCompressor = ImageCompressor.getInstance(this);
        encription = EncryptAndDecrypt.getInstance();
        nfcinstace = NFCSettings.getinstace(this);
        videoInstance = VideoSettings.getinstace();

        //Click events
        //image button
        loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageStaus = true;
                videoStatus = false;
                compress.setEnabled(true);
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }

        });
        //video button
        loadvideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoStatus = true;
                imageStaus = false;
                compress.setEnabled(true);
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_TAKE_GALLERY_VIDEO);

            }

        });
        compress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    //image file
                    if (imageStaus) {
                        if (filedetaisl.CheckFileAvailaBility()) {
                            imgCompressor.StartCompressImage(filedetaisl.getFile());
                        } else {
                            Toast.makeText(getApplicationContext(), Constants.getMessage2(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    //video file
                    if (videoStatus) {
                        if (filedetaisl.CheckFileAvailaBility()) {
                            File videoFile = filedetaisl.getFile();
                            try {
                                byte[] file = filedetaisl.FileConvertToByteArray(videoFile.getAbsolutePath());
                                VideoString = videoInstance.FileConvertsToBase64(file);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), Constants.getMessage2(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception ex) {

                }
                encript.setEnabled(true);

            }
        });

        encript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //encript image
                if (imageStaus) {
                    if (encerippass.getText().toString() != null && encerippass.getText().toString() != "") {
                        File file = imgCompressor.getCompressImageFile();
                        Bitmap map = filedetaisl.FIleToBitmap(file);
                        String base64 = imagesettings.Based64Generator(map);
                        encription.Encriptfile(encerippass.getText().toString(), base64);
                        filedetaisl.WriteEncriptFile(encription.getEncripSting());
                    } else {
                        Toast.makeText(getApplicationContext(), Constants.getNfcmessage8(), Toast.LENGTH_SHORT).show();
                    }
                }
                //encript video
                if (videoStatus) {
                    encription.Encriptfile(encerippass.getText().toString(), VideoString);
                    filedetaisl.writeVideoFile(encription.getEncripSting());
                }
                nfcsend.setEnabled(true);
            }
        });

        nfcsend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                nfcinstace.CheckNFCSettings();
                if(imageStaus) {
                    if (nfcinstace.getNfcCondition()) {
                        nfcinstace.sendFile(Constants.getEncriptFile());
                    }
                }
                if(videoStatus){
                    if(nfcinstace.getNfcCondition()){
                        nfcinstace.sendFile(Constants.getEncriptvideFile());
                    }

                }
                disableButtons();
            }
        });

        decript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //decript image
                try {
                    String encriptText = filedetaisl.ReadEncriptFile();
                    encription.Decrypt(encerippass.getText().toString(), encriptText);
                    Bitmap image = imagesettings.BitMapGenerator(encription.getdecrpytString());
                    filedetaisl.BitmapToFile(image);
                    imageViwer.setImageBitmap(image);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), Constants.getNfcmessage7(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        decriptVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //decript video
                try {
                    String encriptText = filedetaisl.ReadtheVideFile();
                    encription.Decrypt(encerippass.getText().toString(), encriptText);
                    byte[] videofile = videoInstance.StringToByetArray(encription.getdecrpytString());
                    filedetaisl.ByteArrayToVideoFile(videofile);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), Constants.getNfcmessage7(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void disableButtons() {
        compress.setEnabled(false);
        encript.setEnabled(false);
        nfcsend.setEnabled(false);
        this.imageStaus = false;
        this.videoStatus = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == RESULT_LOAD_IMG && requestCode == REQUEST_TAKE_GALLERY_VIDEO && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                filename = filedetaisl.getFileDetails(cursor, columnIndex);
                cursor.close();
                //image scussfully loadded
                if (imageStaus) {
                    //set validation for images and videos
                    Bitmap bmp = BitmapFactory.decodeFile(imgDecodableString);
                    imageViwer.setImageBitmap(imagesettings.ImageResize(bmp));
                    makeText(this, Constants.getMessage1(), LENGTH_LONG).show();
                }
            } else {
                makeText(this, Constants.getMessage2(), LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


