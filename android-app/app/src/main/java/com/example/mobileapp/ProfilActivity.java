package com.example.mobileapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * EXERCICE 2 : Écran Profil avec informations utilisateur et SharedPreferences
 * EXERCICE 4 : Suivi de la dernière visite et du nombre de visites du profil
 * EXERCICE 5 : Application du thème clair/sombre
 * EXERCICE 6 : Modification de l'email utilisateur
 */
public class ProfilActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MobileAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        TextView tvEmail          = findViewById(R.id.tvEmail);
        TextView tvPremiere       = findViewById(R.id.tvPremiereConnexion);
        TextView tvDerniere       = findViewById(R.id.tvDerniereConnexion);
        TextView tvDerniereVisite = findViewById(R.id.tvDerniereVisite);
        TextView tvNbVisites      = findViewById(R.id.tvNbVisites);
        EditText etNouvelEmail    = findViewById(R.id.etNouvelEmail);
        Button   btnChangerEmail  = findViewById(R.id.btnChangerEmail);
        Button   btnRetour        = findViewById(R.id.btnRetour);

        // EXERCICE 2 : Afficher les informations utilisateur
        String email      = prefs.getString("current_email", "Non défini");
        String firstLogin = prefs.getString("first_login", "Non enregistrée");
        String lastLogin  = prefs.getString("last_login", "Non enregistrée");

        tvEmail.setText("Email : " + email);
        tvPremiere.setText("Première connexion : " + firstLogin);
        tvDerniere.setText("Dernière connexion : " + lastLogin);

        // EXERCICE 4 : Suivi des visites du profil
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String maintenant = sdf.format(new Date());

        String derniereVisite = prefs.getString("last_profile_visit", "Jamais");
        int nbVisites         = prefs.getInt("profile_visit_count", 0) + 1;

        tvDerniereVisite.setText("Dernière visite du profil : " + derniereVisite);
        tvNbVisites.setText("Nombre de visites du profil : " + nbVisites);

        // Sauvegarder la visite courante (mise à jour automatique)
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("last_profile_visit", maintenant);
        editor.putInt("profile_visit_count", nbVisites);
        editor.apply();

        // EXERCICE 6 : Modification de l'email
        btnChangerEmail.setOnClickListener(v -> {
            String nouvelEmail = etNouvelEmail.getText().toString().trim();

            // Vérifier que le champ n'est pas vide
            if (TextUtils.isEmpty(nouvelEmail)) {
                Toast.makeText(this, "Le champ email ne peut pas être vide", Toast.LENGTH_SHORT).show();
                return;
            }
            // Vérifier que l'email contient @ et .
            if (!nouvelEmail.contains("@") || !nouvelEmail.contains(".")) {
                Toast.makeText(this, "Email invalide (doit contenir @ et .)", Toast.LENGTH_SHORT).show();
                return;
            }

            // Sauvegarder le nouvel email avec SharedPreferences
            SharedPreferences.Editor ed = prefs.edit();
            ed.putString("current_email", nouvelEmail);
            ed.putString("saved_email", nouvelEmail); // réutilisé au prochain login (Ex6)
            ed.apply();

            tvEmail.setText("Email : " + nouvelEmail);
            etNouvelEmail.setText("");
            Toast.makeText(this, "Email mis à jour", Toast.LENGTH_SHORT).show();
        });

        // EXERCICE 2 : Bouton Retour vers AccueilActivity
        btnRetour.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // EXERCICE 5 : réappliquer le thème à chaque retour
        ThemeHelper.applyTheme(this, findViewById(R.id.rootLayout));
    }
}
