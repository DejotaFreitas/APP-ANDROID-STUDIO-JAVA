package com.dejota.petadote.httpt;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class GET extends AsyncTask<Objects, Void, String> {

    private static final int TIMEOUT = 15000;
    private String url;
    private RespostaHttp response;

    public GET(RespostaHttp context, String url) {
        this.url = url;
        this.response = context;
    }

    @Override
    protected String doInBackground(Objects... objectses) {
        String retorno = null;
        try {
            URL url2 = new URL(url);
            HttpURLConnection conexao = (HttpURLConnection) url2.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(TIMEOUT);
            conexao.setReadTimeout(TIMEOUT);
            conexao.setDoInput(true);
            conexao.setDoOutput(true);
            conexao.setUseCaches(false);
            conexao.connect();

            if(conexao.getResponseCode() != 200){
                return "Erro na reqisição, codigo de erro: "+conexao.getResponseCode();
            }
            InputStream is = conexao.getInputStream();
            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();
            return retorno;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        response.response(s);
    }

    private String converterInputStreamToString(InputStream is){
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linha;
            while((linha = br.readLine())!=null){
                buffer.append(linha);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return buffer.toString();
    }

}
