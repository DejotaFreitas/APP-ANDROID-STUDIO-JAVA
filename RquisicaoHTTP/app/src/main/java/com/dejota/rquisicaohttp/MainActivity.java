package com.dejota.rquisicaohttp;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static final int CODIGO_PEGAR_IMAGEM = 13;
    private String imagemFormato;
    private String imagemPath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void enviarServidor(View view) {
        try {
            EditText edtnome = (EditText) findViewById(R.id.inputnome);
            String nome = edtnome.getText().toString();
            EditText edtemail = (EditText) findViewById(R.id.inputemail);
            String email = edtemail.getText().toString();

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>(2);
                        params.put("name", "Dejota");
                        params.put("email", "djspgs@hotmail.com");
                        HttpPostMultipartFormData send = new HttpPostMultipartFormData();
                        String result = send.multipartRequest("http://smashsystem.com.br/", params, imagemPath, "imagem", "image/"+imagemFormato);
                        Log.i("LogX", result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selecionarImagem(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, CODIGO_PEGAR_IMAGEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_PEGAR_IMAGEM && resultCode == Activity.RESULT_OK) {
            Uri imagemSelecionada = data.getData();
            String[] colunas = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(imagemSelecionada, colunas, null, null, null);
            cursor.moveToFirst();
            int indexColuna = cursor.getColumnIndex(colunas[0]);
            String caminhoImagem = cursor.getString(indexColuna);
            cursor.close();
            imagemPath = caminhoImagem;
            String[] aux = caminhoImagem.split("\\.");
            imagemFormato = aux[aux.length -1];
            Log.i("LogX", "FORMATO IMAGEM "+imagemFormato);
            Bitmap imagem = Imagem.redimencionarImage(caminhoImagem, 300, 0);
            ImageView iv = (ImageView) findViewById(R.id.imagem);
            iv.setImageBitmap(imagem);
        }
    }






}
