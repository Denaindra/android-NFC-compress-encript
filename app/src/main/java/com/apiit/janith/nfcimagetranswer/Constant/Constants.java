package com.apiit.janith.nfcimagetranswer.Constant;

public class Constants {

    private static String Message1 = "Picture Sucessfully loaded";
    private static String Message2 = "You haven't picked Image";
    private static String Message3 = "Something went wrong";
    private static String Message4 = "File sucessfully save with the Encript image";
    private static String Message5 = "File sucessfully Compressed";
    private static Integer MaxWidth = 640;
    private static Integer MaxHeight = 480;
    private static Integer Qulity = 50;
    private static String Password = "password";
    private static String encriptFile = "image.png";
    private static String FolderName = "TestFolder";
    private static String NFCmessage1 = "The device does not has NFC hardware.";
    private static String NFCmessage2 = "Android Beam is not supported.";
    private static String NFcmessage3 = "Android Beam is supported on your device";
    private static String NFCmessage4 = "Please enable Android Beam ";
    private static String Nfcmessage5 = "Please enable NFC.";
    private static String Nfcmessahe6 = "File Sucessfully, Please close to receiver devices";
    private static String Nfcmessage7 = "PleseCheck encript and decript Password";
    private static String nfcmessage8 = "encript pass not valid input";
    private static String NfcFolder = "Download";
    private static String NfcImage = "image-0.png";
    private static String DecriptFilePath = "//storage//emulated//0//Download//image-0.png";
    private static String StringVideoFile = "video.Mp4";
    private static String VideoFile="tempMp3.Mp4";


    public static String getVideoFile(){
        return VideoFile;
    }
    public static String getStringvideFile() {
        return StringVideoFile;
    }

    public static String getDecriptFilePath() {
        return DecriptFilePath;
    }

    public static String getNfcFolder() {
        return NfcFolder;
    }

    public static String getNfcImage() {
        return NfcImage;
    }

    public static String getNfcmessahe6() {
        return Nfcmessahe6;
    }

    public static String getNfcmessage5() {
        return Nfcmessage5;
    }

    public static String getNfcmessage7() {
        return Nfcmessage7;
    }

    public static String getNFCmessage4() {
        return NFCmessage4;
    }

    public static String getNFCmessage3() {
        return NFcmessage3;
    }

    public static String getNFCmessage2() {
        return NFCmessage2;
    }

    public static String getNFCmessage1() {
        return NFCmessage1;
    }

    public static String getFolderName() {
        return FolderName;
    }

    public static String getEncriptFile() {

        return encriptFile;
    }

    public static String getMessage5() {
        return Message5;
    }

    public static String getPassword() {
        return Password;
    }

    public static String getMessage4() {
        return Message4;
    }

    public static Integer getMaxWidth() {
        return MaxWidth;
    }

    public static Integer getMaxHeight() {
        return MaxHeight;
    }

    public static Integer getQulity() {
        return Qulity;
    }

    public static String getMessage1() {
        return Message1;
    }

    public static String getNfcmessage8() {
        return nfcmessage8;
    }

    public static String getMessage2() {
        return Message2;
    }

    public static String getMessage3() {
        return Message3;
    }


}
