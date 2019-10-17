package com.dejota.petadote.imagem;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.dejota.petadote.R;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class ImgLoadPath extends AsyncTask<String, Void, Bitmap>{

    private ImageView imgView;
    private String path;

    public ImgLoadPath(ImageView imgView, String path) {
        this.imgView = imgView;
        this.path = path;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            File file = new File(path);
            Bitmap imagem = null;
            if(file.exists()){
                imagem = BitmapFactory.decodeFile(file.getAbsolutePath());
            } else { return null; }
            return imagem;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null){
            imgView.setImageBitmap(bitmap);
        }
    }
}
