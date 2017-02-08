package com.apiit.janith.nfcimagetranswer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import com.apiit.janith.nfcimagetranswer.Constant.Constants;

import java.io.File;

/**
 * Created by Gayan Denaindra on 2/7/2017.
 */

public class NFCSettings {
    private static NFCSettings instance;
    private NfcAdapter nfcAdapter;
    private Context context;

    public NFCSettings(Context context) {
        this.context = context;
    }

    public static NFCSettings getinstace(Context context) {
        instance = new NFCSettings(context);
        return instance;
    }

    public void CheckNFCSettings() {
        PackageManager pm = context.getPackageManager();

        if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
            Toast.makeText(context, Constants.getNFCmessage1(), Toast.LENGTH_SHORT).show();
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(context, Constants.getNFCmessage2(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, Constants.getNFCmessage3(), Toast.LENGTH_SHORT).show();
        }
    }


    public void sendFile() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(context, Constants.getNfcmessage5(), Toast.LENGTH_SHORT).show();
        } else if (!nfcAdapter.isNdefPushEnabled()) {
            Toast.makeText(context, Constants.getNFCmessage4(), Toast.LENGTH_SHORT).show();
        } else {
            try {
                File sdcard = new File(Environment.getExternalStorageDirectory(), Constants.getFolderName());
                File fileToTransfer = new File(sdcard, Constants.getEncriptFile());
                fileToTransfer.setReadable(true, false);
                nfcAdapter.setBeamPushUris(new Uri[]{Uri.fromFile(fileToTransfer)}, (Activity) context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
