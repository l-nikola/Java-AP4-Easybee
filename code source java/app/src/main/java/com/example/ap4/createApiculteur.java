package com.example.ap4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class createApiculteur extends AppCompatActivity {
    private EditText nomEdit;
    private EditText prenomEdit;
    private EditText telephoneEdit;
    private EditText adresseEdit;
    private EditText CPEdit;
    private EditText villeEdit;
    private EditText identifiantEdit;
    private EditText mdpEdit;
    private Button inscrire;
    private Button annuler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createapiculteur);
        nomEdit = findViewById(R.id.editTextTextPersonName);
        prenomEdit = findViewById(R.id.editTextTextPersonName2);
        telephoneEdit = findViewById(R.id.editTextTextPersonName7);
        adresseEdit = findViewById(R.id.editTextTextPersonName8);
        CPEdit = findViewById(R.id.editTextTextPersonName9);
        villeEdit = findViewById(R.id.editTextTextPersonName10);
        identifiantEdit = findViewById(R.id.editTextTextPersonName11);
        mdpEdit = findViewById(R.id.editTextTextPersonName12);
        inscrire = findViewById(R.id.button8);
        annuler = findViewById(R.id.button10);
        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomEdit.getText().toString().trim();
                String prenom = prenomEdit.getText().toString().trim();
                String telephone = telephoneEdit.getText().toString().trim();
                String adresse = adresseEdit.getText().toString().trim();
                String CP = CPEdit.getText().toString().trim();
                String ville = villeEdit.getText().toString().trim();
                String identifiant = identifiantEdit.getText().toString().trim();
                String motdepasse = mdpEdit.getText().toString().trim();
                if (identifiant.isEmpty() || motdepasse.isEmpty()) {
                    Toast.makeText(createApiculteur.this, "Remplissez les deux zones de texte", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = "https://api.maximethibault.fr/createApiculteur.php";
                String type = "insert";
                backgroundWorkerCreateApiculteur backgroundWorker = new backgroundWorkerCreateApiculteur(createApiculteur.this);
                backgroundWorker.execute(url, type, nom, prenom, telephone, adresse, CP, ville, identifiant, motdepasse);
            }
        });
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Rediriger vers la page "authentification"
                Intent intent = new Intent(createApiculteur.this, authentification.class);
                startActivity(intent);
            }
        });
    }
}
