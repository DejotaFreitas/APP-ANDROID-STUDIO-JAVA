package com.dejota.sqlitedb;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageUrlArraybyte {


    public static Bitmap getImagemBytes(byte [] bytes){
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    public static byte[] getImagemBytesFromUrl(String url){
        try{
            URL endereco = new URL(url);
            InputStream inputStream;
            final Bitmap imagem;
            inputStream = endereco.openStream();
            imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imagem.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            return byteArray;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}



