package com.dejota.cameraougaleria;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;

public class Formulario {

    private Contato contato;
    private EditText nome;
    private ImageView foto;

    public Formulario(MainActivity activity) {
        this.contato = new Contato();
        this.nome = (EditText) activity.findViewById(R.id.edt_nome);
        this.foto = (ImageView) activity.findViewById(R.id.imagem);
    }

    public Contato pegarContatoFormulario (){
        this.contato.setNome(nome.getText().toString());
        this.contato.setFoto((String) this.foto.getTag());
        nome.setText("");
        return this.contato;
    }

    public void carregarFormulario(Contato contato){
        nome.setText(contato.getNome());
        carregarFoto(contato.getFoto());
        this.carregarFoto(contato.getFoto());
    }

    public void carregarFoto(String locaDaFoto) {
        if (locaDaFoto != null){
            Bitmap imagemFoto = BitmapFactory.decodeFile(locaDaFoto);
			Bitmap imagem = Imagem.redimencionarImage(caminhoImagem, 300, 0);
            this.foto.setImageBitmap(imagemFoto);
            this.foto.setTag(locaDaFoto);
        }
    }

    public Contato getContato() {
        return contato;
    }
}
