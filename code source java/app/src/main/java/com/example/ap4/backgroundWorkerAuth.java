package com.example.ap4;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

public class backgroundWorkerAuth extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    public static String id_apiculteur = "null";
    public static String essaimEnCharge = "null";
    backgroundWorkerAuth(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = params[0];
        String type = params[1];
        String identifiant = params[2];
        String motdepasse = params[3];
        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = "";
            if (type.equals("authentification")) {
                post_data = URLEncoder.encode("identifiant", "UTF-8") + "=" + URLEncoder.encode(identifiant, "UTF-8") +
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
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        return;
    }
    protected void onPostExecute(String result) {
        Intent intent;
        String nom_apiculteur = "";
        String prenom_apiculteur = "";
        System.out.println("le result" + result);
        try {
            JSONObject obj = new JSONObject(result);
            id_apiculteur = obj.getString("id_apiculteur");
            essaimEnCharge = obj.getString("essaimEnCharge");
            nom_apiculteur = obj.getString("nom_apiculteur");
            prenom_apiculteur = obj.getString("prenom_apiculteur");
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
        System.out.println("id_apiculteur received : " + id_apiculteur);
        System.out.println("essaumEnCharge received : " + essaimEnCharge);
        // Afficher un message avec les valeurs récupérées
        if(id_apiculteur == "null"){
            alertDialog.setTitle("Authentification refusee");
            alertDialog.setMessage("Identifiant ou mot de passe incorrect.");
            System.out.println("Non authentifié");
            alertDialog.show();
        }
        else{
            alertDialog.setTitle("Authentification reussie");
            alertDialog.setMessage("Bonjour " + prenom_apiculteur + " " + nom_apiculteur);
            alertDialog.show();
            if(essaimEnCharge == "null"){
                System.out.println("Pas d'essaim en charge");
                intent = new Intent(context, listEssaim.class);
            }
            else{
                System.out.println("Un essaim en charge");
                intent = new Intent(context, essaimEnCharge.class);
            }
            context.startActivity(intent);
        }
        return;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
