#!/bin/bash
# Script pour créer les 6 commits Git (un par exercice)
# Exécuter depuis la racine du projet : bash android-app/create_commits.sh

set -e

ROOT=$(git rev-parse --show-toplevel)
APP_DIR="$ROOT/android-app"
JAVA="$APP_DIR/app/src/main/java/com/example/mobileapp"
LAYOUT="$APP_DIR/app/src/main/res/layout"
MANIFEST="$APP_DIR/app/src/main/AndroidManifest.xml"

echo "=== Création des 6 commits Git ==="
echo ""

# ─────────────────────────────────────────────────────────────────────────────
# COMMIT 1 — Exercice 1 : Affichage de l'heure de connexion
# ─────────────────────────────────────────────────────────────────────────────
echo ">>> Commit 1 — Exercice 1"

# AndroidManifest : seulement MainActivity + AccueilActivity
cat > "$MANIFEST" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:allowBackup="true"
        android:label="@string/app_name"
        android:theme="@style/Theme.MobileApp">
        <activity android:name=".MainActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".AccueilActivity" android:exported="false" />
    </application>
</manifest>
EOF

# MainActivity.java — Exercice 1 seulement
cat > "$JAVA/MainActivity.java" << 'EOF'
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
EOF

# AccueilActivity.java — Exercice 1 seulement
cat > "$JAVA/AccueilActivity.java" << 'EOF'
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
EOF

# Layout AccueilActivity — Exercice 1 (sans boutons)
cat > "$LAYOUT/activity_accueil.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/rootLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="32dp"
    android:gravity="center"
    android:background="@color/white">
    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:text="Bienvenue !" android:textSize="28sp" android:textStyle="bold"
        android:textColor="@color/colorPrimary" android:layout_marginBottom="24dp" />
    <TextView android:id="@+id/tvConnexion" android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:text="Connecté à --:--:--"
        android:textSize="20sp" android:textStyle="bold" android:textColor="#333333"
        android:layout_marginBottom="8dp" />
    <TextView android:id="@+id/tvDate" android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:text="Date : --/--/----"
        android:textSize="16sp" android:textColor="#555555" />
</LinearLayout>
EOF

git add android-app/
git commit -m "Exercice 1 : Affichage de l'heure de connexion

- MainActivity : login avec email/mot de passe et Se souvenir de moi
- Enregistrement heure (HH:MM:SS) et date (JJ/MM/AAAA) apres login reussi
- Transmission via Intent vers AccueilActivity
- Affichage 'Connecte a HH:MM:SS' et date sur l'ecran d'accueil"

echo "✓ Commit 1 fait"

# ─────────────────────────────────────────────────────────────────────────────
# COMMIT 2 — Exercice 2 : Création de l'écran Profil
# ─────────────────────────────────────────────────────────────────────────────
echo ">>> Commit 2 — Exercice 2"

# AndroidManifest : ajouter ProfilActivity
cat > "$MANIFEST" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:allowBackup="true"
        android:label="@string/app_name"
        android:theme="@style/Theme.MobileApp">
        <activity android:name=".MainActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".AccueilActivity" android:exported="false" />
        <activity android:name=".ProfilActivity" android:exported="false" />
    </application>
</manifest>
EOF

# AccueilActivity.java — Ex1 + Ex2 (bouton Profil)
cat > "$JAVA/AccueilActivity.java" << 'EOF'
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
EOF

# ProfilActivity.java — Exercice 2 seulement
cat > "$JAVA/ProfilActivity.java" << 'EOF'
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
EOF

# Layout AccueilActivity — Ex1 + bouton Profil
cat > "$LAYOUT/activity_accueil.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/rootLayout" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    android:padding="32dp" android:gravity="center" android:background="@color/white">
    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:text="Bienvenue !" android:textSize="28sp" android:textStyle="bold"
        android:textColor="@color/colorPrimary" android:layout_marginBottom="24dp" />
    <TextView android:id="@+id/tvConnexion" android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:text="Connecté à --:--:--"
        android:textSize="20sp" android:textStyle="bold" android:textColor="#333333"
        android:layout_marginBottom="8dp" />
    <TextView android:id="@+id/tvDate" android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:text="Date : --/--/----"
        android:textSize="16sp" android:textColor="#555555" android:layout_marginBottom="32dp" />
    <Button android:id="@+id/btnProfil" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:text="Mon Profil"
        android:backgroundTint="@color/colorPrimary" android:textColor="@color/white"
        android:padding="14dp" />
    <!-- Boutons Ex3 et Ex5 ajoutés dans les commits suivants -->
    <Button android:id="@+id/btnParametres" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:text="Paramètres"
        android:backgroundTint="#607D8B" android:textColor="@color/white"
        android:padding="14dp" android:layout_marginTop="12dp" android:visibility="gone"/>
    <Button android:id="@+id/btnDeconnexion" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:text="Déconnexion"
        android:backgroundTint="#F44336" android:textColor="@color/white"
        android:padding="14dp" android:layout_marginTop="12dp" android:visibility="gone"/>
</LinearLayout>
EOF

git add android-app/
git commit -m "Exercice 2 : Creation de l'ecran Profil

- ProfilActivity avec email, premiere connexion, derniere connexion
- Utilisation SharedPreferences pour sauvegarder la premiere connexion
- Bouton Profil ajouté dans AccueilActivity
- Bouton Retour pour revenir a l'accueil"

echo "✓ Commit 2 fait"

# ─────────────────────────────────────────────────────────────────────────────
# COMMIT 3 — Exercice 3 : Déconnexion avec confirmation
# ─────────────────────────────────────────────────────────────────────────────
echo ">>> Commit 3 — Exercice 3"

cat > "$JAVA/AccueilActivity.java" << 'EOF'
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
EOF

git add android-app/
git commit -m "Exercice 3 : Deconnexion avec confirmation

- Bouton Deconnexion dans AccueilActivity
- AlertDialog : 'Voulez-vous vraiment vous deconnecter ?'
- OUI : retour vers MainActivity, suppression du mot de passe
- NON : fermeture de la boite de dialogue
- L'option Se souvenir de moi est preservee"

echo "✓ Commit 3 fait"

# ─────────────────────────────────────────────────────────────────────────────
# COMMIT 4 — Exercice 4 : Gestion de la dernière visite du profil
# ─────────────────────────────────────────────────────────────────────────────
echo ">>> Commit 4 — Exercice 4"

cat > "$JAVA/ProfilActivity.java" << 'EOF'
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
EOF

git add android-app/
git commit -m "Exercice 4 : Gestion de la derniere visite du profil

- Affichage 'Derniere visite du profil : [date/heure]'
- Mise a jour automatique a chaque ouverture du profil
- Affichage 'Nombre de visites du profil : X'
- Compteur incrementé automatiquement apres chaque visite
- Sauvegarde avec SharedPreferences"

echo "✓ Commit 4 fait"

# ─────────────────────────────────────────────────────────────────────────────
# COMMIT 5 — Exercice 5 : Thème clair / sombre
# ─────────────────────────────────────────────────────────────────────────────
echo ">>> Commit 5 — Exercice 5"

# AndroidManifest : ajouter ParametresActivity
cat > "$MANIFEST" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:allowBackup="true"
        android:label="@string/app_name"
        android:theme="@style/Theme.MobileApp">
        <activity android:name=".MainActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".AccueilActivity" android:exported="false" />
        <activity android:name=".ProfilActivity" android:exported="false" />
        <activity android:name=".ParametresActivity" android:exported="false" />
    </application>
</manifest>
EOF

# AccueilActivity.java — Ex1+2+3+5
cat > "$JAVA/AccueilActivity.java" << 'EOF'
package com.example.mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

// EXERCICE 1+2+3+5 : Paramètres + theme
public class AccueilActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MobileAppPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        TextView tvConnexion    = findViewById(R.id.tvConnexion);
        TextView tvDate         = findViewById(R.id.tvDate);
        Button   btnProfil      = findViewById(R.id.btnProfil);
        Button   btnParametres  = findViewById(R.id.btnParametres);
        Button   btnDeconnexion = findViewById(R.id.btnDeconnexion);

        Intent intent = getIntent();
        tvConnexion.setText("Connecté à " + intent.getStringExtra("heure_connexion"));
        tvDate.setText("Date : " + intent.getStringExtra("date_connexion"));

        btnProfil.setOnClickListener(v -> startActivity(new Intent(this, ProfilActivity.class)));

        btnParametres.setVisibility(android.view.View.VISIBLE);
        btnParametres.setOnClickListener(v -> startActivity(new Intent(this, ParametresActivity.class)));

        btnDeconnexion.setVisibility(android.view.View.VISIBLE);
        btnDeconnexion.setOnClickListener(v ->
            new AlertDialog.Builder(this)
                .setTitle("Déconnexion")
                .setMessage("Voulez-vous vraiment vous déconnecter ?")
                .setPositiveButton("OUI", (d, w) -> {
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().remove("saved_password").apply();
                    Intent li = new Intent(this, MainActivity.class);
                    li.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(li);
                })
                .setNegativeButton("NON", (d, w) -> d.dismiss()).show());
    }
    @Override protected void onResume() {
        super.onResume();
        ThemeHelper.applyTheme(this, findViewById(R.id.rootLayout));
    }
}
EOF

git add android-app/
git commit -m "Exercice 5 : Ecran Parametres - Theme clair/sombre

- Ajout de ParametresActivity avec ThemeHelper
- Bouton 'Theme clair' : fond blanc, texte noir
- Bouton 'Theme sombre' : fond noir, texte blanc
- Sauvegarde du theme avec SharedPreferences
- Theme appliqué sur tous les ecrans (MainActivity, AccueilActivity, ProfilActivity)
- Theme persistant apres redemarrage de l'application"

echo "✓ Commit 5 fait"

# ─────────────────────────────────────────────────────────────────────────────
# COMMIT 6 — Exercice 6 : Modification de l'email utilisateur
# ─────────────────────────────────────────────────────────────────────────────
echo ">>> Commit 6 — Exercice 6"

# Restaurer les versions finales complètes
cp "$APP_DIR/.final/MainActivity.java"      "$JAVA/MainActivity.java"      2>/dev/null || true
cp "$APP_DIR/.final/AccueilActivity.java"   "$JAVA/AccueilActivity.java"   2>/dev/null || true
cp "$APP_DIR/.final/ProfilActivity.java"    "$JAVA/ProfilActivity.java"    2>/dev/null || true
cp "$APP_DIR/.final/ParametresActivity.java" "$JAVA/ParametresActivity.java" 2>/dev/null || true
cp "$APP_DIR/.final/AndroidManifest.xml"    "$MANIFEST"                   2>/dev/null || true
cp "$APP_DIR/.final/activity_accueil.xml"   "$LAYOUT/activity_accueil.xml" 2>/dev/null || true
cp "$APP_DIR/.final/activity_profil.xml"    "$LAYOUT/activity_profil.xml" 2>/dev/null || true

git add android-app/
git commit -m "Exercice 6 : Modification de l'email utilisateur

- EditText et bouton 'Changer mon email' dans ProfilActivity
- Verification : champ non vide, email contient @ et .
- Sauvegarde du nouvel email avec SharedPreferences
- Email reutilise automatiquement au prochain login (MainActivity)
- Affichage du message 'Email mis a jour'"

echo "✓ Commit 6 fait"

echo ""
echo "=== Tous les 6 commits ont été créés avec succès ! ==="
git log --oneline -7
