package com.example.ap4;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.util.Log;
import com.android.volley.RequestQueue;
import android.os.AsyncTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public class authentification extends AppCompatActivity {
    private EditText identifiantEditText;
    private EditText mdpEditText;
    private Button matchButton;
    private Button notIdentifiedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentification);
        identifiantEditText = findViewById(R.id.editTextTextPersonName5);
        mdpEditText = findViewById(R.id.editTextTextPersonName6);
        matchButton = findViewById(R.id.button6);
        notIdentifiedButton = findViewById(R.id.button7);
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String identifiant = identifiantEditText.getText().toString().trim();
                String motdepasse = mdpEditText.getText().toString().trim();
                if (identifiant.isEmpty() || motdepasse.isEmpty()) {
                    Toast.makeText(authentification.this, "Remplissez les deux zones de texte", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = "https://api.maximethibault.fr/authApiculteur.php";
                String type = "authentification";
                backgroundWorkerAuth backgroundWorker = new backgroundWorkerAuth(authentification.this);
                backgroundWorker.execute(url, type, identifiant, motdepasse);
                String id_apiculteur = backgroundWorkerAuth.id_apiculteur;
                String essaimEnCharge = backgroundWorkerAuth.essaimEnCharge;
            }
        });
        notIdentifiedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Rediriger vers la page "createApiculteur"
                Intent intent = new Intent(authentification.this, createApiculteur.class);
                startActivity(intent);
            }
        });
    }
}
