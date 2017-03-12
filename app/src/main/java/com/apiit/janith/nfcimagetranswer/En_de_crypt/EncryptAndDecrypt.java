package com.apiit.janith.nfcimagetranswer.En_de_crypt;

import com.apiit.janith.nfcimagetranswer.Constant.Constants;
import com.scottyab.aescrypt.AESCrypt;

public class EncryptAndDecrypt {
    private static EncryptAndDecrypt instance;
    private String encrypteMsg;
    private String decriptMsg;

    public static EncryptAndDecrypt getInstance() {
        instance = new EncryptAndDecrypt();
        return instance;
    }

    public void EncriptImage(String encriptPass,String base64String) {
        try {
            encrypteMsg = AESCrypt.encrypt(encriptPass, base64String);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Decrypt(String dcriptPass,String encriptBase64String) {
        try {
            decriptMsg = AESCrypt.decrypt(dcriptPass, encriptBase64String);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String getEncripSting() {
        return encrypteMsg;
    }

    public String getdecrpytString() {
        return decriptMsg;
    }

}
