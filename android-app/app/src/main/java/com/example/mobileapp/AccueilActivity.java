package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// EXERCICE 1 : Affichage de l'heure de connexion
public class AccueilActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        TextView tvConnexion = findViewById(R.id.tvConnexion);
        TextView tvDate      = findViewById(R.id.tvDate);
        Intent intent = getIntent();
        tvConnexion.setText("Connecté à " + intent.getStringExtra("heure_connexion"));
        tvDate.setText("Date : " + intent.getStringExtra("date_connexion"));
    }
}
