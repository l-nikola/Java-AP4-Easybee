package com.example.ap4;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class particulier extends AppCompatActivity {
    private EditText nomEditText;
    private EditText prenomEditText;
    private Button localiserButton;
    private Button checkEssaim;

    private static final int REQUEST_LOCATION_PERMISSION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.particulier);
        nomEditText = findViewById(R.id.editTextTextPersonName3);
        prenomEditText = findViewById(R.id.editTextTextPersonName4);
        localiserButton = findViewById(R.id.button3);
        checkEssaim = findViewById(R.id.button4);

        // Vérification des autorisations
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            initLocation();
        }
        localiserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomEditText.getText().toString().trim();
                String prenom = prenomEditText.getText().toString().trim();
                if (nom.isEmpty() || prenom.isEmpty()) {
                    Toast.makeText(particulier.this, "Veuillez saisir votre nom et prénom", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Vérification des autorisations
                if (ContextCompat.checkSelfPermission(particulier.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(particulier.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(particulier.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            REQUEST_LOCATION_PERMISSION);
                    return;
                }
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                String provider = locationManager.getBestProvider(criteria, true);
                if (provider != null) {
                    Location location = locationManager.getLastKnownLocation(provider);
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        String latitudeStr = String.valueOf(latitude);
                        String longitudeStr = String.valueOf(longitude);
                        String url = "https://api.maximethibault.fr/insertParticulier.php";
                        String type = "insert";
                        backgroundWorkerParticulier backgroundWorker = new backgroundWorkerParticulier(particulier.this);
                        backgroundWorker.execute(url,type,nom,prenom,latitudeStr, longitudeStr);
                    } else {
                        Toast.makeText(particulier.this, "Impossible de récupérer votre position", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(particulier.this, "Impossible de récupérer votre position", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkEssaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(particulier.this, checkEssaim.class);
                startActivity(intent);
            }
        });
    }
    private void initLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (provider != null) {
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                } else {
                    Toast.makeText(particulier.this, "Impossible de récupérer votre position", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(particulier.this, "Impossible de récupérer votre position", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(particulier.this, "Autorisation refusée", Toast.LENGTH_SHORT).show();
        }
    }
}