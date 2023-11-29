package com.example.ap4;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class backgroundWorkerGiveUp extends AsyncTask<String, Void, String> {
    private Context context;
    private AlertDialog alertDialog;

    backgroundWorkerGiveUp(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = params[0];
        String type = params[1];
        String id_apiculteur = params[2];
        String id_essaim = params[3];
        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = "";
            if(type.equals("update")) {
                post_data = URLEncoder.encode("id_apiculteur", "UTF-8") + "=" + URLEncoder.encode(id_apiculteur, "UTF-8") +
                        "&"
                + URLEncoder.encode("id_essaim", "UTF-8") + "=" + URLEncoder.encode(id_essaim, "UTF-8");
            }
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        alertDialog.setTitle("Temps écoulé");
        alertDialog.setMessage("Vous avez dépassé les 3 heures !");
        alertDialog.show();
        return null;
    }

    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        return;
    }

    @Override
    protected void onPostExecute(String result) {
        return;
    }
}
