package com.dejota.petadote.conteudo2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dejota.petadote.entity.MeuPet;
import com.dejota.petadote.httpt.POST;
import com.dejota.petadote.imagem.IMG;
import com.dejota.petadote.R;
import com.dejota.petadote.acesso.Acesso;
import com.dejota.petadote.httpt.RespostaHttp;
import com.dejota.petadote.notificacao.NotificacaoErro;
import com.dejota.petadote.notificacao.NotificacaoSucesso;
import com.dejota.petadote.util.Global;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class CadastroPet extends AppCompatActivity implements RespostaHttp {

    private ImageView foto;
    private String fotoPath;
    private String fotoExtensao;
    private static final int CODIGO_CAMERA= 12;
    private static final int CODIGO_GALERIA = 13;
    private TextView tv_nome;
    private TextView tv_descricao;
    private Spinner sp_tipo;
    private Spinner sp_genero;
    private Spinner sp_faixa_etaria;
    private Spinner sp_tamanho;
    private Spinner sp_castrado;
    private Button btn_cadastrar_pet;
    private ProgressBar pb_carregamento;
    private MeuPet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        sp_tipo = (Spinner) findViewById(R.id.cadastro_pet_tipo);
        ArrayAdapter adapterTipo = ArrayAdapter.createFromResource(this, R.array.pet_tipo, R.layout.z_spinner_item_custom);
        sp_tipo.setAdapter(adapterTipo);

        sp_genero = (Spinner) findViewById(R.id.cadastro_pet_genero);
        ArrayAdapter adapterGenero = ArrayAdapter.createFromResource(this, R.array.pet_genero, R.layout.z_spinner_item_custom);
        sp_genero.setAdapter(adapterGenero);

        sp_faixa_etaria = (Spinner) findViewById(R.id.cadastro_pet_faixa_etaria);
        ArrayAdapter adapterFaixaEtaria = ArrayAdapter.createFromResource(this, R.array.pet_faixa_etaria, R.layout.z_spinner_item_custom);
        sp_faixa_etaria.setAdapter(adapterFaixaEtaria);

        sp_tamanho = (Spinner) findViewById(R.id.cadastro_pet_tamanho);
        ArrayAdapter adapterTamanho = ArrayAdapter.createFromResource(this, R.array.pet_tamanho, R.layout.z_spinner_item_custom);
        sp_tamanho.setAdapter(adapterTamanho);

        sp_castrado = (Spinner) findViewById(R.id.cadastro_pet_castrado);
        ArrayAdapter adapterCastrado = ArrayAdapter.createFromResource(this, R.array.pet_castrado, R.layout.z_spinner_item_custom);
        sp_castrado.setAdapter(adapterCastrado);

        foto = (ImageView) findViewById(R.id.cadasto_pet_foto);
        tv_nome = (TextView) findViewById(R.id.cadastro_pet_nome);
        tv_descricao = (TextView) findViewById(R.id.cadastro_pet_descricao);
        btn_cadastrar_pet = (Button) findViewById(R.id.cadastro_pet_cadastrar);

        pb_carregamento = (ProgressBar) findViewById(R.id.cadastro_pet_pg_carregando);
        pb_carregamento.setVisibility(View.INVISIBLE);
        pet = new MeuPet();
    }

    public void cadastro_pet_cadastrar(View view) {
        btn_cadastrar_pet.setClickable(false);
        pet.setNome(tv_nome.getText().toString());
        pet.setDescricao(tv_descricao.getText().toString());
        pet.setTipo(sp_tipo.getSelectedItem().toString());
        pet.setGenero(sp_genero.getSelectedItem().toString());
        pet.setFaixaEtaria(sp_faixa_etaria.getSelectedItem().toString());
        pet.setTamanho(sp_tamanho.getSelectedItem().toString());
        pet.setCastrado(sp_castrado.getSelectedItem().toString());

        if (fotoPath == null) {
            Toast.makeText(getApplicationContext(), "Adicione a foto do pet.", Toast.LENGTH_SHORT).show();
            btn_cadastrar_pet.setClickable(true);
            return;
        }

        if (pet.getNome().length() < 2) {
            Toast.makeText(getApplicationContext(), "Nome do pet deve ter 2 caracteres no mínimo.", Toast.LENGTH_SHORT).show();
            btn_cadastrar_pet.setClickable(true);
            return;
        }

        pb_carregamento.setVisibility(View.VISIBLE);

        Acesso ac = new Acesso(this);
        int usuario_id = 0;
        if (ac.isLogado()){ usuario_id = ac.getUsuario().getId(); }
        else { finish(); }

        Map<String, String> parametros = new HashMap<>();
        parametros.put("nome", pet.getNome());
        parametros.put("descricao", pet.getDescricao());
        parametros.put("tipo", pet.getTipo());
        parametros.put("genero", pet.getGenero());
        parametros.put("faixa_etaria", pet.getFaixaEtaria());
        parametros.put("tamanho", pet.getTamanho());
        parametros.put("castrado", pet.getCastrado());
        parametros.put("usuario_id", usuario_id+"");
        POST post = new POST(this,"http://smashsystem.com.br/android_meus_pets_cadastro",
                parametros, fotoPath, "foto", "image/"+fotoExtensao);
        post.execute();
    }


    @Override
    public void response(String response) {

        try {
            JSONObject json = new JSONObject(response);
            String respostaServer = json.getString("response");

            if ("ok".equals(respostaServer)) {
                File file = new File(fotoPath);
                file.delete();

                pet.setId(json.getInt("id"));
                pet.setFoto(json.getString("foto"));
                Global.meuspets.add(pet);

                Intent i = new Intent(this, NotificacaoSucesso.class);
                i.putExtra("sucesso", "Pet cadastrado com sucesso.");
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(this, NotificacaoErro.class);
                i.putExtra("erro", respostaServer);
                startActivity(i);
            }
        }  catch (Exception e) {
            e.printStackTrace();
            if (response == null){
                Intent i = new Intent(CadastroPet.this, NotificacaoErro.class);
                i.putExtra("erro", "Problemas com a conexão de internet.");
                startActivity(i);
            }
        }
        btn_cadastrar_pet.setClickable(true);
        pb_carregamento.setVisibility(View.INVISIBLE);
    }

    public void cadastro_pet_selecionarCameraOuGaleria(View view) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_dialer)
                .setTitle("Selecione.")
                .setPositiveButton("Galeria",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cadastro_pet_carregarFotoGaleria();
                    }
                })
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cadastro_pet_carregarFotoCamera();
                    }
                })
                .show();
    }

    private void cadastro_pet_carregarFotoCamera() {
        fotoExtensao = "jpeg";
        fotoPath = getExternalFilesDir(null)+"/"+ System.currentTimeMillis()+"."+ fotoExtensao;
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fotoPath)));
        startActivityForResult(intentCamera, CODIGO_CAMERA);
    }

    private void cadastro_pet_carregarFotoGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, CODIGO_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_CAMERA) {

            if (resultCode == Activity.RESULT_OK){
                try {
                    Bitmap fotoBMP = IMG.redimencionarImage(fotoPath, 300, 0, 0);
                    foto.setImageBitmap(fotoBMP);
                    File file = new File(fotoPath);
                    file.delete();
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    fotoBMP.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    file = new File(fotoPath);
                    FileOutputStream fo = new FileOutputStream(file);
                    fo.write(bytes.toByteArray());
                    fo.flush();
                    fo.close();
                } catch (Exception e){ e.printStackTrace(); }
            }

        } else if (requestCode == CODIGO_GALERIA){
            if (resultCode == Activity.RESULT_OK){
                try {
                    Uri imagemSelecionada = data.getData();
                    String[] colunas = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imagemSelecionada, colunas, null, null, null);
                    cursor.moveToFirst();
                    int indexColuna = cursor.getColumnIndex(colunas[0]);
                    fotoPath = cursor.getString(indexColuna);
                    cursor.close();
                    String[] aux = fotoPath.split("\\.");
                    fotoExtensao = aux[aux.length - 1];
                    Bitmap fotoBMP = IMG.redimencionarImage(fotoPath, 300, 0, 0);
                    foto.setImageBitmap(fotoBMP);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    fotoBMP.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    fotoPath = getExternalFilesDir(null)+"/"+ System.currentTimeMillis()+"."+ fotoExtensao;
                    File file = new File(fotoPath);
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes.toByteArray());
                    fos.flush();
                    fos.close();
                } catch (Exception e){ e.printStackTrace(); }
            }
        }
    }


    public void cadastro_pet_cancelar(View v){
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fotoPath != null){
            File file = new File(fotoPath);
            if (file.exists()){  file.delete();   }
        }

    }




}
