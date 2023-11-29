package com.example.ap4;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
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
public class backgroundWorkerCreateApiculteur extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    backgroundWorkerCreateApiculteur(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = params[0];
        String type = params[1];
        String nom = params[2];
        String prenom = params[3];
        String telephone = params[4];
        String adresse = params[5];
        String CP = params[6];
        String ville = params[7];
        String identifiant = params[8];
        String motdepasse = params[9];
        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = "";
            if (type.equals("insert")) {
                post_data = URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nom, "UTF-8") +
                        "&"
                        + URLEncoder.encode("prenom", "UTF-8") + "=" + URLEncoder.encode(prenom, "UTF-8") +
                        "&"
                        + URLEncoder.encode("telephone", "UTF-8") + "=" + URLEncoder.encode(telephone, "UTF-8") +
                        "&"
                        + URLEncoder.encode("adresse", "UTF-8") + "=" + URLEncoder.encode(adresse, "UTF-8") +
                        "&"
                        + URLEncoder.encode("CP", "UTF-8") + "=" + URLEncoder.encode(CP, "UTF-8") +
                        "&"
                        + URLEncoder.encode("ville", "UTF-8") + "=" + URLEncoder.encode(ville, "UTF-8") +
                        "&"
                        + URLEncoder.encode("identifiant", "UTF-8") + "=" + URLEncoder.encode(identifiant, "UTF-8") +
                        "&"
                        + URLEncoder.encode("motdepasse", "UTF-8") + "=" + URLEncoder.encode(motdepasse, "UTF-8");
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
        return null;
    }
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Inscription Apiculteur");
    }
    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
