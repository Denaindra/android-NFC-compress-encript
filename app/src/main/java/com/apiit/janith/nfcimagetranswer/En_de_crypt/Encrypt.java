package com.apiit.janith.nfcimagetranswer.En_de_crypt;

public class Encrypt {
    private static Encrypt instance;


    public static Encrypt getInstance() {
        instance = new Encrypt();
        return instance;
    }

}
