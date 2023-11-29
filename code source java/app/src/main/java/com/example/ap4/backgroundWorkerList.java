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
public class backgroundWorkerList extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    backgroundWorkerList(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = params[0];
        String type = params[1];
        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = "";
            if (type.equals("Select")) {

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
        return;
    }
    protected void onPostExecute(String result) {
        try {
            JSONArray essaimsArray = new JSONArray(result);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("id  " + "     " + "Latitude  " + "     " + "Longitude  " + "     " + "Date  " + "\n");
            for (int i = 0; i < essaimsArray.length(); i++) {
                JSONObject essaimObj = essaimsArray.getJSONObject(i);
                String id_essaim = essaimObj.getString("id_essaim");
                String latitude = essaimObj.getString("latitude");
                String longitude = essaimObj.getString("longitude");
                String dateCreation = essaimObj.getString("dateCreation");
                // Données récupérées ici!
                stringBuilder.append(id_essaim + "         " + latitude + "        " + longitude + "        " + dateCreation + "\n");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(stringBuilder.toString())
                    .setTitle("                 Liste des essaims")
                    .setPositiveButton("OK", null)
                    .create()
                    .show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
