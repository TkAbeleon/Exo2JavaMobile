package com.example.mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

// EXERCICE 1 + 2 + 3 : Deconnexion avec AlertDialog
public class AccueilActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MobileAppPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        TextView tvConnexion   = findViewById(R.id.tvConnexion);
        TextView tvDate        = findViewById(R.id.tvDate);
        Button   btnProfil     = findViewById(R.id.btnProfil);
        Button   btnDeconnexion = findViewById(R.id.btnDeconnexion);

        Intent intent = getIntent();
        tvConnexion.setText("Connecté à " + intent.getStringExtra("heure_connexion"));
        tvDate.setText("Date : " + intent.getStringExtra("date_connexion"));

        btnProfil.setOnClickListener(v -> startActivity(new Intent(this, ProfilActivity.class)));

        btnDeconnexion.setVisibility(android.view.View.VISIBLE);
        btnDeconnexion.setOnClickListener(v ->
            new AlertDialog.Builder(this)
                .setTitle("Déconnexion")
                .setMessage("Voulez-vous vraiment vous déconnecter ?")
                .setPositiveButton("OUI", (d, w) -> {
                    SharedPreferences.Editor ed = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    ed.remove("saved_password");
                    ed.apply();
                    Intent li = new Intent(this, MainActivity.class);
                    li.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(li);
                })
                .setNegativeButton("NON", (d, w) -> d.dismiss())
                .show());
    }
}
