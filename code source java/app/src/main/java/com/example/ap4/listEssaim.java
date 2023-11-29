package com.example.ap4;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class listEssaim extends AppCompatActivity {
    private Button showList;
    private EditText idEssaim;
    private Button takeCharge;
    private Button disconnect;
    private String id_apiculteur = backgroundWorkerAuth.id_apiculteur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listessaim);
        showList = findViewById(R.id.button11);
        idEssaim = findViewById(R.id.editTextTextPersonName16);
        takeCharge = findViewById(R.id.button9);
        disconnect = findViewById(R.id.button5);
        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.maximethibault.fr/essaimView.php";
                String type = "select";
                backgroundWorkerList backgroundWorker = new backgroundWorkerList(listEssaim.this);
                backgroundWorker.execute(url, type);
            }
        });
        takeCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_essaim = idEssaim.getText().toString().trim();
                if(id_essaim.isEmpty()) {
                    Toast.makeText(listEssaim.this, "Veuillez saisir id_essaim", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Dans votre méthode onClick() du bouton, vous pouvez ajouter le code suivant :
                AlertDialog.Builder builder = new AlertDialog.Builder(listEssaim.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Êtes-vous sûr de vouloir continuer ?");
                // Si l'utilisateur clique sur "oui"
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // L'utilisateur clique sur "oui"
                        String url = "https://api.maximethibault.fr/takeCharge.php";
                        String type = "update";
                        backgroundWorkerTakeCharge backgroundWorker = new backgroundWorkerTakeCharge(listEssaim.this);
                        backgroundWorker.execute(url, type, id_apiculteur, id_essaim);
                        Intent intent = new Intent(view.getContext(), essaimEnCharge.class);
                        startActivity(intent);
                    }
                });
                // Si l'utilisateur clique sur "non"
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // L'utilisateur clique sur "non"
                        dialog.dismiss();
                    }
                });
                // Affichez la fenêtre de confirmation
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(listEssaim.this, authentification.class);
                startActivity(intent);
            }
        });
    }
}
