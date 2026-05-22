package com.example.mobileapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ParametresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        Button btnClair  = findViewById(R.id.btnThemeClair);
        Button btnSombre = findViewById(R.id.btnThemeSombre);
        Button btnRetour = findViewById(R.id.btnRetour);

        // Appliquer le thème actuel dès l'ouverture
        ThemeHelper.applyTheme(this, findViewById(R.id.rootLayout));

        // Thème clair : fond blanc, texte noir
        btnClair.setOnClickListener(v -> {
            ThemeHelper.saveTheme(this, ThemeHelper.THEME_LIGHT);
            ThemeHelper.applyTheme(this, findViewById(R.id.rootLayout));
            Toast.makeText(this, "Thème clair activé", Toast.LENGTH_SHORT).show();
        });

        // Thème sombre : fond noir, texte blanc
        btnSombre.setOnClickListener(v -> {
            ThemeHelper.saveTheme(this, ThemeHelper.THEME_DARK);
            ThemeHelper.applyTheme(this, findViewById(R.id.rootLayout));
            Toast.makeText(this, "Thème sombre activé", Toast.LENGTH_SHORT).show();
        });

        btnRetour.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeHelper.applyTheme(this, findViewById(R.id.rootLayout));
    }
}
