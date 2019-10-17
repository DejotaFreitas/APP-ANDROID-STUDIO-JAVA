package com.dejota.petadote.imagem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class IMG {

    // BaixarImagem.carregarPath(Context context, fullPath);
    public static Bitmap carregarPath(Context context, String fullPath) {
        try{
            File file = new File(fullPath);
            Bitmap imagem = null;
            if(file.exists()){
                imagem = BitmapFactory.decodeFile(file.getAbsolutePath());
            }
            return imagem;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // BaixarImagem.carregarUrl("http://smashsystem.com.br/", "img/foto.png");
    public static Bitmap carregarUrl(String dominio, String imgPathServer) {
        try{
            URL endereco = new URL(dominio+imgPathServer);
            InputStream inputStream = endereco.openStream();
            Bitmap imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return imagem;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // BaixarImagem.download(this, "http://smashsystem.com.br/", "img/foto.png");
    public static String download(Context context, String dominio, String imagemPath) {
        try{
            URL endereco = new URL(dominio+imagemPath);
            InputStream inputStream = endereco.openStream();
            Bitmap imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imagem.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String[] aux = imagemPath.split("/");
            String fileName = aux[aux.length - 1];
            String saveLocalPath = context.getExternalFilesDir(null)+"/"+ fileName;
            File file = new File(saveLocalPath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes.toByteArray());
            fos.flush();
            fos.close();
            return saveLocalPath;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap byteArrayForBitmap(byte[] bytes) {
        try {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] bitmapForByteArray(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap redimencionarImage(String caminho, int escala ,int largura, int altura){
        try {
            Bitmap image = BitmapFactory.decodeFile(caminho);
            int largura_original = image.getWidth();
            int altura_original = image.getHeight();
            float scalaX = 0;
            float scalaY = 0;

            if(escala != 0){
                if (largura_original >= altura_original){
                    scalaX = (float) escala / largura_original;
                    scalaY = scalaX;
                } else {
                    scalaY = (float) escala / altura_original;
                    scalaX = scalaY;
                }

            } else if (largura != 0 && altura == 0){
                scalaX = (float) largura / largura_original;
                scalaY = scalaX;

            } else if (largura == 0 && altura != 0){
                scalaY = altura / altura_original;
                scalaX = scalaY;

            } else if (largura != 0 && altura != 0){
                scalaX = largura / largura_original;
                scalaY = altura / altura_original;

            } else {
                scalaX = 1;
                scalaY = 1;
            }

            Matrix matrix = new Matrix();
            ExifInterface exif = new ExifInterface(caminho);
            int orientacao = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientacao){
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
                default:break;
            }


            matrix.postScale(scalaX, scalaY);
            image = Bitmap.createBitmap(image, 0, 0, largura_original, altura_original, matrix, true);
            return image;
        } catch (Exception e){
            return null;
        }
    }


    public static String getImagemExtensao(String caminho){
        String[] aux = caminho.split("\\.");
        return aux[aux.length -1];
    }




}
