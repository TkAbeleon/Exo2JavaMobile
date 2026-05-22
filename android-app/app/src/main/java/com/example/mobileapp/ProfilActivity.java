package com.example.mobileapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// EXERCICE 2 : Ecran Profil avec informations utilisateur
public class ProfilActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MobileAppPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        ((TextView) findViewById(R.id.tvEmail)).setText("Email : " + prefs.getString("current_email", "Non défini"));
        ((TextView) findViewById(R.id.tvPremiereConnexion)).setText("Première connexion : " + prefs.getString("first_login", "Non enregistrée"));
        ((TextView) findViewById(R.id.tvDerniereConnexion)).setText("Dernière connexion : " + prefs.getString("last_login", "Non enregistrée"));
        // Masquer les champs des exercices suivants
        findViewById(R.id.tvDerniereVisite).setVisibility(android.view.View.GONE);
        findViewById(R.id.tvNbVisites).setVisibility(android.view.View.GONE);
        findViewById(R.id.etNouvelEmail).setVisibility(android.view.View.GONE);
        findViewById(R.id.btnChangerEmail).setVisibility(android.view.View.GONE);
        ((Button) findViewById(R.id.btnRetour)).setOnClickListener(v -> finish());
    }
}
