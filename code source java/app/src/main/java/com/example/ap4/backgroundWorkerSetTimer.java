package com.example.ap4;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class backgroundWorkerSetTimer extends AsyncTask<String, Void, String> {
    private Context context;
    private AlertDialog alertDialog;
    private EditText timer;

    public static String datePriseEnCharge = "null";
    public static String actualTimer = "null";
    public static String dateFinPriseEnCharge = "null";
    public static String latitude = "null";
    public static String longitude = "null";
    private OnPostExecuteListener onPostExecuteListener;
    backgroundWorkerSetTimer(Context context, EditText timer) {
        this.context = context;
        this.timer = timer;
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
            if (type.equals("select")) {
                post_data = URLEncoder.encode("id_apiculteur", "UTF-8") + "=" + URLEncoder.encode(id_apiculteur, "UTF-8") +
                        "&" + URLEncoder.encode("id_essaim", "UTF-8") + "=" + URLEncoder.encode(id_essaim, "UTF-8");
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
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            datePriseEnCharge = obj.getString("datePriseEnCharge");
            dateFinPriseEnCharge = obj.getString("dateFinPriseEnCharge");
            latitude = obj.getString("latitude");
            longitude = obj.getString("longitude");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateFin = null;
            Date dateActuelle = new Date();
            try {
                dateFin = sdf.parse(dateFinPriseEnCharge);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (dateFin != null) {
                long diffMillis = dateFin.getTime() - dateActuelle.getTime();
                long diffSeconds = diffMillis / 1000;
                long diffMinutes = diffSeconds / 60;
                long diffHours = diffMinutes / 60;
                actualTimer = String.format("%02d:%02d:%02d", diffHours, diffMinutes % 60, diffSeconds % 60);
                // Mettre à jour le contenu de l'EditText "timer"
                if (timer != null) {
                    timer.setText(actualTimer);
                }
            } else {
                // Gérer le cas où dateFin est null
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (onPostExecuteListener != null) {
            onPostExecuteListener.onPostExecuteFinished();
        }
        return;
    }

    public void setTimerEditText(EditText timer) {
        this.timer = timer;
    }

    public interface OnPostExecuteListener {
        void onPostExecuteFinished();
    }

    public void setOnPostExecuteListener(OnPostExecuteListener listener) {
        this.onPostExecuteListener = listener;
    }
}
