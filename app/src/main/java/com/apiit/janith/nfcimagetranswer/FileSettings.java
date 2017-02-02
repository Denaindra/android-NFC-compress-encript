package com.apiit.janith.nfcimagetranswer;

import android.database.Cursor;

import java.io.File;

/**
 * Created by Gayan Denaindra on 1/28/2017.
 */

public class FileSettings {
    private static FileSettings instance;
    public FileSettings(){

    }
    public static FileSettings getInstance(){

        return instance=new FileSettings();
    }
    public String getFileDetails(Cursor cursor, int columnIndex ){

        String picturePath = cursor.getString(columnIndex);
        File file=new File(picturePath);

        return file.getName();
    }
}
