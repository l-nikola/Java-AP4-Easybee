package com.example.ap4;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    private Button particulierBtn;
    private Button apiculteurBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        particulierBtn = findViewById(R.id.button);
        apiculteurBtn = findViewById(R.id.button2);
        particulierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers la page "particulier"
                Intent intent = new Intent(MainActivity.this, particulier.class);
                startActivity(intent);
            }
        });
        apiculteurBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers la page "authentification"
                Intent intent = new Intent(MainActivity.this, authentification.class);
                startActivity(intent);
            }
        });
    }
}