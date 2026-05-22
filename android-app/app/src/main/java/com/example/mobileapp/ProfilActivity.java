package com.example.mobileapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// EXERCICE 2 + EXERCICE 4 : Suivi des visites du profil
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

        // EXERCICE 4 : afficher et mettre a jour la derniere visite + compteur
        TextView tvDerniereVisite = findViewById(R.id.tvDerniereVisite);
        TextView tvNbVisites      = findViewById(R.id.tvNbVisites);
        tvDerniereVisite.setVisibility(android.view.View.VISIBLE);
        tvNbVisites.setVisibility(android.view.View.VISIBLE);

        String maintenant = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        String derniereVisite = prefs.getString("last_profile_visit", "Jamais");
        int nbVisites = prefs.getInt("profile_visit_count", 0) + 1;

        tvDerniereVisite.setText("Dernière visite du profil : " + derniereVisite);
        tvNbVisites.setText("Nombre de visites du profil : " + nbVisites);

        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("last_profile_visit", maintenant);
        ed.putInt("profile_visit_count", nbVisites);
        ed.apply();

        // Masquer les champs de l'exercice 6
        findViewById(R.id.etNouvelEmail).setVisibility(android.view.View.GONE);
        findViewById(R.id.btnChangerEmail).setVisibility(android.view.View.GONE);
        ((Button) findViewById(R.id.btnRetour)).setOnClickListener(v -> finish());
    }
}
