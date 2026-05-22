package com.example.mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * EXERCICE 1 : Affichage de l'heure et de la date de connexion
 * EXERCICE 2 : Bouton vers ProfilActivity
 * EXERCICE 3 : Déconnexion avec AlertDialog de confirmation
 * EXERCICE 5 : Bouton vers Paramètres + application du thème
 */
public class AccueilActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MobileAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        TextView tvConnexion  = findViewById(R.id.tvConnexion);
        TextView tvDate       = findViewById(R.id.tvDate);
        Button   btnProfil    = findViewById(R.id.btnProfil);
        Button   btnParametres = findViewById(R.id.btnParametres);
        Button   btnDeconnexion = findViewById(R.id.btnDeconnexion);

        // EXERCICE 1 : Récupérer l'heure et la date transmises via Intent
        Intent intent = getIntent();
        String heureConnexion = intent.getStringExtra("heure_connexion");
        String dateConnexion  = intent.getStringExtra("date_connexion");

        tvConnexion.setText("Connecté à " + heureConnexion);
        tvDate.setText("Date : " + dateConnexion);

        // EXERCICE 2 : Bouton vers ProfilActivity
        btnProfil.setOnClickListener(v -> {
            Intent profilIntent = new Intent(this, ProfilActivity.class);
            startActivity(profilIntent);
        });

        // EXERCICE 3 : Déconnexion avec AlertDialog
        btnDeconnexion.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Déconnexion")
                    .setMessage("Voulez-vous vraiment vous déconnecter ?")
                    .setPositiveButton("OUI", (dialog, which) -> {
                        // Effacer le mot de passe, conserver "Se souvenir de moi"
                        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.remove("saved_password");
                        editor.apply();

                        Intent loginIntent = new Intent(this, MainActivity.class);
                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(loginIntent);
                    })
                    .setNegativeButton("NON", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        // EXERCICE 5 : Bouton vers ParametresActivity
        btnParametres.setOnClickListener(v -> {
            Intent parametresIntent = new Intent(this, ParametresActivity.class);
            startActivity(parametresIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // EXERCICE 5 : réappliquer le thème à chaque retour sur cet écran
        ThemeHelper.applyTheme(this, findViewById(R.id.rootLayout));
    }
}
