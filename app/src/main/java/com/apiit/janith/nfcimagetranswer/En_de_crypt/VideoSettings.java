package com.apiit.janith.nfcimagetranswer.En_de_crypt;


import android.util.Base64;

public class VideoSettings {

    private static VideoSettings instance;

    public static VideoSettings getinstace(){
        instance=new VideoSettings();
        return instance;
    }

    public String FileConvertsToBase64(byte[]FileByteString){
        try {
            String videString = Base64.encodeToString(FileByteString, Base64.NO_WRAP);
            return videString;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public byte[] StringToByetArray(String videoString){
        try {
            byte[] videoarray = Base64.decode(videoString, Base64.NO_WRAP);
                   return videoarray;
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
