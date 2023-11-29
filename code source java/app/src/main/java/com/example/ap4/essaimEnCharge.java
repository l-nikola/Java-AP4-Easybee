package com.example.ap4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class essaimEnCharge extends AppCompatActivity {
    private EditText timer;
    private EditText coordinate;
    private Button giveUp;
    private String idApiculteur = backgroundWorkerAuth.id_apiculteur;
    private String idEssaim = backgroundWorkerAuth.essaimEnCharge;
    private Handler handler = new Handler();
    private backgroundWorkerSetTimer backgroundWorker;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void startTimer(final long milliseconds) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Décrémenter le temps restant d'une seconde
                long updatedMilliseconds = milliseconds - 1000;

                //Changer le textView de coordinate
                if(backgroundWorkerSetTimer.latitude == null || backgroundWorkerSetTimer.longitude == null) {
                    coordinate.setText("Undefined");
                } else {
                    coordinate.setText("Latitude : " + backgroundWorkerSetTimer.latitude + " ; Longitude : " + backgroundWorkerSetTimer.longitude + " ;");
                }

                if (updatedMilliseconds > 0) {
                    // Convertir le temps restant en format hh:mm:ss
                    int seconds = (int) (updatedMilliseconds / 1000) % 60;
                    int minutes = (int) ((updatedMilliseconds / (1000 * 60)) % 60);
                    int hours = (int) ((updatedMilliseconds / (1000 * 60 * 60)) % 24);
                    String timerText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                    System.out.println("timer : " + timerText);

                    // Mettre à jour le contenu de l'EditText "timer"
                    timer.setText(timerText);

                    // Appeler récursivement la méthode startTimer() après une seconde
                    startTimer(updatedMilliseconds);
                } else {
                    // Le temps est écoulé, appuyer sur le bouton "giveUp"
                    giveUp.performClick();
                }
            }
        }, 1000); // Démarrer après une seconde (1000 millisecondes)
    }
    private void preTimer() {
        // Obtenir la durée restante en millisecondes à partir de la date de fin de prise en charge
        String dateFinPriseEnCharge = backgroundWorkerSetTimer.dateFinPriseEnCharge;
        if (dateFinPriseEnCharge != null) {
            try {
                Date dateFin = sdf.parse(dateFinPriseEnCharge);
                Date dateActuelle = new Date();
                long diffMillis = dateFin.getTime() - dateActuelle.getTime();

                // Démarrer le timer avec la durée restante
                startTimer(diffMillis);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "La date de fin de prise en charge est nulle.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.essaimencharge);
        // Récupération des références des éléments de l'interface utilisateur
        timer = findViewById(R.id.timer);
        coordinate = findViewById(R.id.coordinate);
        giveUp = findViewById(R.id.giveUp);
        timer.setText("unreceived");

        //Si l'apiculteur vient de choisir un essaim, on récupère la valeur
        //En effet, la valeur est récupérée de base sur backgroundWorkerAuth
        if(idEssaim == null || idEssaim == "null") {
            idEssaim = backgroundWorkerTakeCharge.EssaimEnCharge;
        }

        // backgroundWorkerSetTimer
        String url = "https://api.maximethibault.fr/setTimer.php";
        String type = "select";
        backgroundWorker = new backgroundWorkerSetTimer(this, timer);
        backgroundWorker.setTimerEditText(timer);
        backgroundWorker.execute(url, type, idApiculteur, idEssaim);
        backgroundWorker.setOnPostExecuteListener(new backgroundWorkerSetTimer.OnPostExecuteListener() {
            @Override
            public void onPostExecuteFinished() {
                // La méthode à activer après la fin de onPostExecute()
                preTimer();
            }
        });

        // Mise en place du clic sur le bouton "Abandonner"
        giveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Arrêter le timer en supprimant les rappels du Handler
                handler.removeCallbacksAndMessages(null);

                // Définition de l'URL pour la requête d'abandon
                String url = "https://api.maximethibault.fr/giveUp.php";
                String type = "update";

                // Création d'une instance de BackgroundWorkerGiveUp et exécution de la tâche en arrière-plan
                backgroundWorkerGiveUp backgroundWorkerGiveUp = new backgroundWorkerGiveUp(essaimEnCharge.this);
                backgroundWorkerGiveUp.execute(url, type, idApiculteur, idEssaim);

                // Retourner à l'authentification
                Intent intent = new Intent(essaimEnCharge.this, authentification.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
