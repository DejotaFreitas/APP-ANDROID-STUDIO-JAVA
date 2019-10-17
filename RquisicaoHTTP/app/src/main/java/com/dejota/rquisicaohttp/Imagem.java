package com.dejota.rquisicaohttp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Imagem {

    public static String converterBitmapBase64(Bitmap imagem){
        Bitmap immagex = imagem;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String imgBase64) {
        byte[] decodedByte = Base64.decode(imgBase64, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static Bitmap redimencionarImage(String caminho, int largura, int altura){
        Bitmap image = BitmapFactory.decodeFile(caminho);
        int largura_original = image.getWidth();
        int altura_original = image.getHeight();
        float scalaX = (float) largura / largura_original;
        float scalaY = 1;
        if (altura != 0){
            scalaY = (float) altura / altura_original;
        } else {
            scalaY = scalaX;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scalaX, scalaY);
        image = Bitmap.createBitmap(image, 0, 0, largura_original, altura_original, matrix, true);
        image = corrigirRotacaoJPG(caminho, image);
        return image;
    }

    private static Bitmap corrigirRotacaoJPG(String caminho, Bitmap bmp) {
        try {
            Matrix matrix = new Matrix();
            boolean fixed = false;
            ExifInterface exif = new ExifInterface(caminho);
            int orientacao = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientacao){
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    fixed = true;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    fixed = true;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    fixed = true;
                    break;
                default:
                    fixed = false;
                    break;
            }
            if (!fixed){
                return bmp;
            }
            Bitmap bmpaux = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            bmp.recycle();
            bmp = null;
            return bmpaux;
        } catch (Exception e){
            return bmp;
        }
    }

    public static String getFormatoImagem(String caminho){
        String[] aux = caminho.split("\\.");
        return aux[aux.length -1];
    }


    public static String imagemParaString(String caminhoImagem){
        try {
            FileInputStream entrada = new FileInputStream(caminhoImagem);
            InputStreamReader entradaFormatada = new InputStreamReader(entrada);
            BufferedReader entradaString = new BufferedReader(entradaFormatada);
            String linha = entradaString.readLine();
            StringBuilder sb = new StringBuilder();
            while (linha != null) {
                sb.append(linha);
                linha = entradaString.readLine();
            }
            return sb.toString();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }




}
