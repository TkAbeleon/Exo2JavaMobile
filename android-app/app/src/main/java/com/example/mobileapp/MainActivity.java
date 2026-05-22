package com.example.mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * EXERCICE 1 : Login avec enregistrement heure/date de connexion
 * EXERCICE 6 : Réutilise l'email sauvegardé après modification depuis ProfilActivity
 */
public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME      = "MobileAppPrefs";
    private static final String KEY_REMEMBER    = "remember_me";
    private static final String KEY_EMAIL_SAVED = "saved_email";

    // Mot de passe de démonstration
    private static final String DEMO_PASSWORD = "1234";

    private EditText etEmail;
    private EditText etPassword;
    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail    = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRemember = findViewById(R.id.cbRememberMe);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Charger les préférences sauvegardées (Ex1 + Ex6)
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean remember   = prefs.getBoolean(KEY_REMEMBER, false);
        String  savedEmail = prefs.getString(KEY_EMAIL_SAVED, "");

        // EXERCICE 6 : réutiliser l'email sauvegardé (qu'il vienne du login ou du profil)
        if (remember && !savedEmail.isEmpty()) {
            etEmail.setText(savedEmail);
            cbRemember.setChecked(true);
        }

        // EXERCICE 5 : appliquer le thème sur cet écran
        ThemeHelper.applyTheme(this, findViewById(R.id.rootLayout));

        btnLogin.setOnClickListener(v -> {
            String email    = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }
            // Validation email
            if (!email.contains("@") || !email.contains(".")) {
                Toast.makeText(this, "Email invalide", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(DEMO_PASSWORD)) {
                Toast.makeText(this, "Mot de passe incorrect (utilisez : 1234)", Toast.LENGTH_SHORT).show();
                return;
            }

            // EXERCICE 1 : Enregistrer l'heure et la date de connexion
            Date now = new Date();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String heureConnexion = timeFormat.format(now);
            String dateConnexion  = dateFormat.format(now);

            // Gérer "Se souvenir de moi"
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(KEY_REMEMBER, cbRemember.isChecked());
            if (cbRemember.isChecked()) {
                editor.putString(KEY_EMAIL_SAVED, email);
            } else {
                editor.remove(KEY_EMAIL_SAVED);
            }

            // Sauvegarder les informations de connexion
            String dateHeure = dateFormat.format(now) + " " + timeFormat.format(now);
            editor.putString("last_login", dateHeure);
            editor.putString("current_email", email);
            if (!prefs.contains("first_login")) {
                editor.putString("first_login", dateHeure);
            }
            editor.apply();

            // Transmettre l'heure et la date vers AccueilActivity via Intent
            Intent intent = new Intent(this, AccueilActivity.class);
            intent.putExtra("heure_connexion", heureConnexion);
            intent.putExtra("date_connexion", dateConnexion);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // EXERCICE 5 : réappliquer le thème à chaque retour
        ThemeHelper.applyTheme(this, findViewById(R.id.rootLayout));
    }
}
