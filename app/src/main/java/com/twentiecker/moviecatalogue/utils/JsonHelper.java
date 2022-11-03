package com.twentiecker.moviecatalogue.utils;

import android.content.Context;


import java.io.IOException;
import java.io.InputStream;

public class JsonHelper {

    private final Context context;

    public JsonHelper(Context context) {
        this.context = context;
    }

    private String parsingFileToString(String filename) {
        try {
            InputStream inputStream = context.getAssets().open(filename);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
