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

// EXERCICE 1 : Affichage de l'heure de connexion
public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MobileAppPrefs";
    private static final String DEMO_PASSWORD = "1234";
    private EditText etEmail, etPassword;
    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRemember = findViewById(R.id.cbRememberMe);
        Button btnLogin = findViewById(R.id.btnLogin);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (prefs.getBoolean("remember_me", false)) {
            etEmail.setText(prefs.getString("saved_email", ""));
            cbRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                Toast.makeText(this, "Email invalide", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(DEMO_PASSWORD)) {
                Toast.makeText(this, "Mot de passe incorrect (utilisez : 1234)", Toast.LENGTH_SHORT).show();
                return;
            }
            Date now = new Date();
            SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String heure = tf.format(now);
            String date  = df.format(now);

            SharedPreferences.Editor ed = prefs.edit();
            ed.putBoolean("remember_me", cbRemember.isChecked());
            if (cbRemember.isChecked()) ed.putString("saved_email", email);
            else ed.remove("saved_email");
            String dh = df.format(now) + " " + tf.format(now);
            ed.putString("last_login", dh);
            ed.putString("current_email", email);
            if (!prefs.contains("first_login")) ed.putString("first_login", dh);
            ed.apply();

            Intent intent = new Intent(this, AccueilActivity.class);
            intent.putExtra("heure_connexion", heure);
            intent.putExtra("date_connexion", date);
            startActivity(intent);
            finish();
        });
    }
}
