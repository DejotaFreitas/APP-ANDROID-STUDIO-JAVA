package com.dejota.petadote.imagem;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ImgLoadUrl extends AsyncTask<String, Void, Bitmap>{

    private ImageView imgView;
    private String dominio;
    private String imgPathServer;


    // BaixarImagem.download(imageView, "http://smashsystem.com.br/", "img/foto.png");
    public ImgLoadUrl(ImageView imgView, String dominio, String imgPathServer) {
        this.imgView = imgView;
        this.dominio = dominio;
        this.imgPathServer = imgPathServer;
    }


    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL endereco = new URL(dominio+imgPathServer);
            InputStream inputStream = endereco.openStream();
            Bitmap imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
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
