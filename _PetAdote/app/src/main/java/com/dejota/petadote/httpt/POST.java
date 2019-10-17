package com.dejota.petadote.httpt;
import android.os.AsyncTask;

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
import java.util.Objects;

public class POST extends AsyncTask<Objects, Void, String> {

    private RespostaHttp response;
    private String urlTo;
    private Map<String, String> parmas;
    private String filepath;
    private String filefield;
    private String fileMimeType;
    private static final int TIMEOUT = 15000;

//    String result = request(this, URL, params, pathToVideoFile, "minhafoto", "image/png");
    public POST(RespostaHttp response, String urlTo, Map<String, String> parmas, String filepath, String filefield, String fileMimeType) {
        this.response = response;
        this.urlTo = urlTo;
        this.parmas = parmas;
        this.filepath = filepath;
        this.filefield = filefield;
        this.fileMimeType = fileMimeType;
    }

    @Override
    protected String doInBackground(Objects... objectses) {
        String result = null;
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;
        FileInputStream fileInputStream = null;

        String twoHyphens = "--";
        String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
        String lineEnd = "\r\n";

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


    @Override
    protected void onPostExecute(String s) {
        response.response(s);
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
