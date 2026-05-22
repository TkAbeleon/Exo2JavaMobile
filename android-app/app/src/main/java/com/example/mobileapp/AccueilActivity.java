package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// EXERCICE 1 + EXERCICE 2 : Bouton vers ProfilActivity
public class AccueilActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        TextView tvConnexion = findViewById(R.id.tvConnexion);
        TextView tvDate      = findViewById(R.id.tvDate);
        Button   btnProfil   = findViewById(R.id.btnProfil);

        Intent intent = getIntent();
        tvConnexion.setText("Connecté à " + intent.getStringExtra("heure_connexion"));
        tvDate.setText("Date : " + intent.getStringExtra("date_connexion"));

        btnProfil.setOnClickListener(v ->
            startActivity(new Intent(this, ProfilActivity.class)));
    }
}
