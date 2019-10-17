package com.dejota.petadote.httpt;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class PostThread {
    // Claase deve implementar a Interface Response
    //  public class Classe implements Response {
    //      @Override
    //      public void response(String response){
    //          println(response);
    //      }
    // }
    //
    //  Map<String, String> params = new HashMap<String, String>(2);
    //  params.put("foo", hash);
    //  params.put("bar", caption);
    //
    //  String result = request(this, URL, params, pathToVideoFile, "meuvideo", "video/mp4");
    //  String result = request(this, URL, params, pathToVideoFile, "minhafoto", "image/png");
    //  String result = request(this, URL, params, null, "null", null);
    //  String result = request(this, URL, null, pathToVideoFile, "minhafoto", "image/png");
    //  String result = request(this, URL, null, null, null, null);

    private static final int TIMEOUT = 15000;

    private Response context;
    private String url;
    private Map<String, String> parametros;
    private String filePath;
    private String fileTag;
    private String fileTypeExtension;
    private String responseHttp;

    public PostThread(Response context, String url, Map<String, String> parametros, String filePath, String fileTag, String fileTypeExtension) {
        this.context = context;
        this.url = url;
        this.parametros = parametros;
        this.filePath = filePath;
        this.fileTag = fileTag;
        this.fileTypeExtension = fileTypeExtension;
        responseHttp = "";
    }

    public void execute() {

        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    responseHttp = sendPost(url,parametros, filePath, fileTag, fileTypeExtension);
                }
            };
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            context.response("Ocorreu algum problema ao efetar a requisicão ao servidor.");
            return;
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
                    return;
                }
            };
            thread2.start();
        } catch (Exception e) {
            e.printStackTrace();
            context.response("Ocorreu algum problema na resposta enviada pelo servidor.");
            return;
        }
    }


    private String sendPost(String urlTo, Map<String, String> parmas, String filepath, String filefield, String fileMimeType) {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;
        FileInputStream fileInputStream = null;

        String twoHyphens = "--";
        String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
        String lineEnd = "\r\n";

        String result = "";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        try {

            URL url = new URL(urlTo);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data; charset=utf-8; boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());

            if (filepath != null && filefield != null &&  fileMimeType != null){

                File file = new File(filepath);
                fileInputStream = new FileInputStream(file);

                String[] q = filepath.split("/");
                int idx = q.length - 1;

                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + q[idx] + "\"" + lineEnd);
                outputStream.writeBytes("Content-Type: " + fileMimeType + lineEnd);
                outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

                outputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    outputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
            }

            outputStream.writeBytes(lineEnd);

            if (parmas != null){

                Iterator<String> keys = parmas.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = parmas.get(key);

                    outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                    outputStream.writeBytes("Content-Type: text/plain; charset=utf-8" + lineEnd);
                    outputStream.writeBytes(lineEnd);
                    outputStream.writeBytes(value);
                    outputStream.writeBytes(lineEnd);
                }
            }

            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


            if (200 != connection.getResponseCode()) {
                return "Problemas para enviar dados ao servidor, código de erro: "+connection.getResponseCode();
            }

            inputStream = connection.getInputStream();
            result = this.convertStreamToString(inputStream);

            if (fileInputStream != null){ fileInputStream.close(); }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
            connection.disconnect();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String convertStreamToString(InputStream is) {
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linha;
            while((linha = br.readLine())!=null){
                buffer.append(linha);
            }
            br.close();
        } catch(Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return buffer.toString();
    }


}
