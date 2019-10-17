package com.dejota.petadote.acesso;

import android.app.Activity;
import android.content.Context;
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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dejota.petadote.R;
import com.dejota.petadote.adapter.CidadeAdapter;
import com.dejota.petadote.adapter.EstadoAdapter;
import com.dejota.petadote.entity.Usuario;
import com.dejota.petadote.httpt.POST;
import com.dejota.petadote.httpt.RespostaHttp;
import com.dejota.petadote.notificacao.NotificacaoErro;
import com.dejota.petadote.notificacao.NotificacaoSucesso;
import com.dejota.petadote.imagem.IMG;
import com.dejota.petadote.util.Constantes;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity implements RespostaHttp {

    private Context context;
    private String fotoPath;
    private String fotoExtensao;
    private static final int CODIGO_CAMERA= 12;
    private static final int CODIGO_GALERIA = 13;
    private ImageView foto;
    private EditText edtnome;
    private EditText edtemail;
    private EditText edtsenha;
    private EditText edtsenha2;
    private Spinner sp_estado;
    private Spinner sp_cidade;
    private Button btn_cadastrar;
    private ScrollView tela_cadastro;
    private ProgressBar pb_carregamento;
    private Usuario usuario;
    private Acesso acesso;

    private int[] arrayCidades = {R.array.cidade_estado_1, R.array.cidade_estado_2, R.array.cidade_estado_3,
            R.array.cidade_estado_4, R.array.cidade_estado_5, R.array.cidade_estado_6, R.array.cidade_estado_7,
            R.array.cidade_estado_8, R.array.cidade_estado_9, R.array.cidade_estado_10, R.array.cidade_estado_11,
            R.array.cidade_estado_12, R.array.cidade_estado_13, R.array.cidade_estado_14, R.array.cidade_estado_15,
            R.array.cidade_estado_16, R.array.cidade_estado_17, R.array.cidade_estado_18, R.array.cidade_estado_19,
            R.array.cidade_estado_20, R.array.cidade_estado_21, R.array.cidade_estado_22, R.array.cidade_estado_23,
            R.array.cidade_estado_24, R.array.cidade_estado_25, R.array.cidade_estado_26, R.array.cidade_estado_27};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        usuario = new Usuario();
        acesso = new Acesso(this);
        context = this;
        edtnome = (EditText) findViewById(R.id.cadastro_nome);
        edtemail = (EditText) findViewById(R.id.cadastro_email);
        edtsenha = (EditText) findViewById(R.id.cadastro_senha);
        edtsenha2 = (EditText) findViewById(R.id.cadastro_senha2);
        sp_estado = (Spinner) findViewById(R.id.cadasto_estado);
        sp_cidade = (Spinner) findViewById(R.id.cadasto_cidade);
        foto = (ImageView) findViewById(R.id.cadasto_foto);
        btn_cadastrar = (Button) findViewById(R.id.cadastro_cadastrar);

        tela_cadastro = (ScrollView) findViewById(R.id.cadastro_tela_cadastro);
        pb_carregamento = (ProgressBar) findViewById(R.id.cadastro_pg_carregando);
        pb_carregamento.setVisibility(View.INVISIBLE);

        String[] arrayestados = getResources().getStringArray(R.array.estados);
        EstadoAdapter adpt_estado = new EstadoAdapter(this, arrayestados);
        sp_estado.setAdapter(adpt_estado);

        sp_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] arraycidades = getResources().getStringArray(arrayCidades[i]);
                CidadeAdapter adpt_estado = new CidadeAdapter(getApplicationContext(), arraycidades);
                sp_cidade.setAdapter(adpt_estado);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {    }
        });

        sp_cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                usuario.setCidadeId((int)adapterView.getSelectedItemId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {    }
        });
    }

    public void cadastro_cadastrar(View view) {
        btn_cadastrar.setClickable(false);
        usuario.setNome(edtnome.getText().toString());
        usuario.setEmail(edtemail.getText().toString());;
        final String senha = edtsenha.getText().toString();
        final String senha2 = edtsenha2.getText().toString();

        if (fotoPath == null) {
            Toast.makeText(getApplicationContext(), "Adicione a sua foto.", Toast.LENGTH_SHORT).show();
            btn_cadastrar.setClickable(true);
            return;
        }

        if (usuario.getNome().length() < 2) {
            Toast.makeText(getApplicationContext(), "Nome deve ter 2 caracteres no mínimo.", Toast.LENGTH_SHORT).show();
            btn_cadastrar.setClickable(true);
            return;
        }

        if (usuario.getEmail().length() < 5) {
            Toast.makeText(getApplicationContext(), "Email deve ter 5 caracteres no mínimo.", Toast.LENGTH_SHORT).show();
            btn_cadastrar.setClickable(true);
            return;
        }

        if (!senha2.equals(senha)) {
            Toast.makeText(getApplicationContext(), "As senhas não conferem.", Toast.LENGTH_SHORT).show();
            btn_cadastrar.setClickable(true);
            return;
        }

        if (senha.length() < 4) {
            Toast.makeText(getApplicationContext(), "Senha deve ter 4 caracteres no mínimo.", Toast.LENGTH_SHORT).show();
            btn_cadastrar.setClickable(true);
            return;
        }

        pb_carregamento.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(), "Processando...", Toast.LENGTH_SHORT).show();

        Map<String, String> parametros = new HashMap<>();
        parametros.put("cidade_id", usuario.getCidadeId()+"");
        parametros.put("nome", usuario.getNome());
        parametros.put("email", usuario.getEmail());
        parametros.put("senha", senha);
        new POST(this, Constantes.DOMINIO+"android_cadastro", parametros, fotoPath, "foto", "image/"+fotoExtensao).execute();
    }


    @Override
    public void response(String response) {

        try{
            JSONObject json = new JSONObject(response);
            String respostaServer = json.getString("response");

            if ("ok".equals(respostaServer)) {
                usuario.setId(json.getInt("id"));
                usuario.setSenha(json.getString("senha"));
                usuario.setFoto(json.getString("foto"));
                usuario.setTime(json.getLong("time"));
                acesso.logar(usuario);
                Intent i = new Intent(Cadastro.this, NotificacaoSucesso.class);
                i.putExtra("sucesso", "Cadastro e Login efetuado com sucesso.");
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(Cadastro.this, NotificacaoErro.class);
                i.putExtra("erro", respostaServer);
                startActivity(i);
            }
        }  catch (Exception e) {
            e.printStackTrace();
            Log.i("LogX", response);
            if (response == null){
                Intent i = new Intent(Cadastro.this, NotificacaoErro.class);
                i.putExtra("erro", "Problemas com a conexão de internet.");
                startActivity(i);
            }
        }

        btn_cadastrar.setClickable(true);
        pb_carregamento.setVisibility(View.INVISIBLE);
    }





    public void cadastro_selecionarCameraOuGaleria(View view) {
        new AlertDialog.Builder(Cadastro.this)
                .setIcon(android.R.drawable.ic_dialog_dialer)
                .setTitle("Selecione.")
                .setPositiveButton("Galeria",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cadastro_carregarFotoGaleria();
                    }
                })
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cadastro_carregarFotoCamera();
                    }
                })
                .show();
    }

    private void cadastro_carregarFotoCamera() {
        fotoExtensao = "jpeg";
        fotoPath = getExternalFilesDir(null)+"/"+ System.currentTimeMillis()+"."+ fotoExtensao;
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fotoPath)));
        startActivityForResult(intentCamera, CODIGO_CAMERA);
    }

    private void cadastro_carregarFotoGaleria() {
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

    public void enableDisableView(View view, boolean enabled) {
        view.setClickable(enabled);
        if ( view instanceof ViewGroup ) {
            ViewGroup group = (ViewGroup)view;
            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }



}
