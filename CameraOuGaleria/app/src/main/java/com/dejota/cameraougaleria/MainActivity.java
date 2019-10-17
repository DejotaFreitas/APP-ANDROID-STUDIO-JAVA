package com.dejota.cameraougaleria;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Formulario formulario;
    Boolean salvaaOuAlterar;
    Bitmap bitmap;

    ImageView foto;
    String direotiroFoto;
    static final int CAMERA_ID = 111;
    static final int GALERIA_ID = 222;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salvaaOuAlterar = true;
        foto = (ImageView) findViewById(R.id.imagem);
        formulario = new Formulario(this);

        Intent intent = this.getIntent();
        Contato contato= (Contato) intent.getSerializableExtra("itemSelecionado");
        if (contato != null){
            formulario.carregarFormulario(contato);
            salvaaOuAlterar = false;
        }

    }

    public void salvar(View view){
        Contato c = formulario.pegarContatoFormulario();
        ContatoDAO dao = new ContatoDAO(this);
        if (salvaaOuAlterar == true){
            dao.inserirContato(c);
        } else {
            dao.alterarContato(c);
            salvaaOuAlterar = true;
        }
    }

    public void listar(View view){
        Intent it = new Intent(MainActivity.this, Lista.class);
        startActivity(it);
    }


    public void carregarFoto(View view){
      selecionarCameraOuGaleria();
    }

    private void selecionarCameraOuGaleria() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_dialer)
                .setTitle("Camera ou Galeria")
                .setMessage("Selecione a ofrma que deseja obter a sua foto ?")
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        carregarFotoCamera();
                    }
                })
                .setNegativeButton("Galeria",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        carregarFotoGaleria();
                    }
                }).show();
    }

    private void carregarFotoGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALERIA_ID);
    }

    private void carregarFotoCamera() {
        direotiroFoto = getExternalFilesDir(null)+"/"+ System.currentTimeMillis()+".jppg";
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(direotiroFoto)));
        startActivityForResult(intentCamera, CAMERA_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_ID){
            if(resultCode == Activity.RESULT_OK){
                formulario.carregarFoto(this.direotiroFoto);
            }
        } else {
            this.direotiroFoto = null;
        }

        if (requestCode == GALERIA_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imagemSelecionada = data.getData();
                String[] colunas = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(imagemSelecionada, colunas, null, null, null);
                cursor.moveToFirst();
                int indexColuna = cursor.getColumnIndex(colunas[0]);
                String caminhoImagem = cursor.getString(indexColuna);
                cursor.close();
                this.direotiroFoto = caminhoImagem;
                String[] aux = caminhoImagem.split("\\.");
                String imagemFormato = aux[aux.length - 1];
                Log.i("LogX", "FORMATO IMAGEM " + imagemFormato + " - Caminho imagem: " + caminhoImagem);
                formulario.carregarFoto(this.direotiroFoto);
            } else {
                this.direotiroFoto = null;
            }

        }
    }






}
