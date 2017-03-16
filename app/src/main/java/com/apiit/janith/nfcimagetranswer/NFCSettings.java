package com.apiit.janith.nfcimagetranswer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.apiit.janith.nfcimagetranswer.Constant.Constants;

import java.io.File;


public class NFCSettings {
    private static NFCSettings instance;
    private NfcAdapter nfcAdapter;
    private Context context;
    private Boolean nfcCondition;

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
            this.nfcCondition = false;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(context, Constants.getNFCmessage2(), Toast.LENGTH_SHORT).show();
            this.nfcCondition = false;
        } else {
            Toast.makeText(context, Constants.getNFCmessage3(), Toast.LENGTH_SHORT).show();
            this.nfcCondition = true;
        }
    }

    public boolean getNfcCondition() {
        return nfcCondition;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendFile(String fileName) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(context, Constants.getNfcmessage5(), Toast.LENGTH_SHORT).show();
        } else if (!nfcAdapter.isNdefPushEnabled()) {
            Toast.makeText(context, Constants.getNFCmessage4(), Toast.LENGTH_SHORT).show();
        } else {
            try {
                File sdcard = new File(Environment.getExternalStorageDirectory(), Constants.getFolderName());
                File fileToTransfer = new File(sdcard,fileName);
                fileToTransfer.setReadable(true, false);
                nfcAdapter.setBeamPushUris(new Uri[]{Uri.fromFile(fileToTransfer)}, (Activity) context);
                Toast.makeText(context, Constants.getNfcmessahe6(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
