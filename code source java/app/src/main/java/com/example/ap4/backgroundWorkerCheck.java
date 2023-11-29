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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class backgroundWorkerCheck extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    backgroundWorkerCheck(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = params[0];
        String type = params[1];
        String nom = params[2];
        String prenom = params[3];
        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = "";
            if (type.equals("select")) {
                post_data = URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nom, "UTF-8") +
                        "&"
                        + URLEncoder.encode("prenom", "UTF-8") + "=" + URLEncoder.encode(prenom, "UTF-8");
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
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        return;
    }
    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                StringBuilder message = new StringBuilder();
                for (int i = 0; i < jsonArray.length(); i++) {
                   JSONObject essaimObject = jsonArray.getJSONObject(i);
                   String idEssaim = essaimObject.getString("id_essaim");
                   String latitude = essaimObject.getString("latitude");
                   String longitude = essaimObject.getString("longitude");
                   String dateCreation = essaimObject.getString("dateCreation");

                   // Ajoutez les informations de chaque ligne au message avec un saut de ligne
                    message.append("ID Essaim: ").append(idEssaim).append(" ")
                            .append("Latitude: ").append(latitude).append(" ")
                            .append("Longitude: ").append(longitude).append(" ")
                            .append("Date de création: ").append(dateCreation).append("\n\n");
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Liste des essaims");
                alertDialogBuilder.setMessage(message.toString());
                alertDialogBuilder.setPositiveButton("OK", null);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            alertDialog.setTitle("Particulier non reconnu");
            alertDialog.setMessage("Nom ou Prenom incorrect");
            alertDialog.show();
        }
    }
}
