package com.dejota.petadote.httpt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetThread {
    private static final int TIMEOUT = 15000;
    private String url;
    private Response context;
    private String responseHttp;

    public GetThread(Response context, String url) {
        this.context = context;
        this.url = url;
        responseHttp = "";
        execute();
    }


    public void execute() {
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    responseHttp = sendGet(url);
                }
            };
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            responseHttp = "Ocorreu algum problema ao efetar a requisicão ao servidor.";
        }

        try {
            Thread thread2 = new Thread() {
                @Override
                public void run() {
                    Long inicio = System.currentTimeMillis();
                    while ("".equals(responseHttp)) {
                        if (System.currentTimeMillis() - inicio > TIMEOUT) {
                            context.response("Tempo de conexão esgotado.");
                            return;
                        }
                    }
                    context.response(responseHttp);
                }
            };
            thread2.start();
        } catch (Exception e) {
            e.printStackTrace();
            context.response("Ocorreu algum problema na resposta enviada pelo servidor.");
        }
    }




    private String sendGet(String url){
        String retorno = "";
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