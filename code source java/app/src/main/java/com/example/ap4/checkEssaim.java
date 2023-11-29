package com.example.ap4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class checkEssaim extends AppCompatActivity {
    private EditText nomEditText;
    private EditText prenomEditText;
    private Button listEssaim;
    private EditText idEssaim;
    private Button validEssaim;
    private Button suppEssaim;
    private Button Return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkessaim);
        nomEditText = findViewById(R.id.editTextTextPersonName13);
        prenomEditText = findViewById(R.id.editTextTextPersonName14);
        listEssaim = findViewById(R.id.button12);
        idEssaim = findViewById(R.id.editTextTextPersonName15);
        validEssaim = findViewById(R.id.button15);
        suppEssaim = findViewById(R.id.button14);
        Return = findViewById(R.id.button16);
        listEssaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomEditText.getText().toString().trim();
                String prenom = prenomEditText.getText().toString().trim();
                if (nom.isEmpty() || prenom.isEmpty()) {
                    Toast.makeText(checkEssaim.this, "Veuillez saisir votre nom et prénom", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String url = "https://api.maximethibault.fr/checkList.php";
                    String type = "select";
                    backgroundWorkerCheck backgroundWorker = new backgroundWorkerCheck(checkEssaim.this);
                    backgroundWorker.execute(url,type,nom,prenom);
                }
            }
        });
        validEssaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomEditText.getText().toString().trim();
                String prenom = prenomEditText.getText().toString().trim();
                String id_essaim = idEssaim.getText().toString().trim();
                if (nom.isEmpty() || prenom.isEmpty() || id_essaim.isEmpty()) {
                    Toast.makeText(checkEssaim.this, "Veuillez saisir votre nom et prénom et id_essaim", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String url = "https://api.maximethibault.fr/checkValid.php";
                    String type = "insert";
                    backgroundWorkerCheckValid backgroundWorker = new backgroundWorkerCheckValid(checkEssaim.this);
                    backgroundWorker.execute(url,type,nom,prenom,id_essaim);
                }
            }
        });
        suppEssaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomEditText.getText().toString().trim();
                String prenom = prenomEditText.getText().toString().trim();
                String id_essaim = idEssaim.getText().toString().trim();
                if (nom.isEmpty() || prenom.isEmpty() || id_essaim.isEmpty()) {
                    Toast.makeText(checkEssaim.this, "Veuillez saisir votre nom et prénom et id_essaim", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String url = "https://api.maximethibault.fr/checkSupp.php";
                    String type = "delete";
                    backgroundWorkerCheckSupp backgroundWorker = new backgroundWorkerCheckSupp(checkEssaim.this);
                    backgroundWorker.execute(url,type,nom,prenom,id_essaim);
                }
            }
        });
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(checkEssaim.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
