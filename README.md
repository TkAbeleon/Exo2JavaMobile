# Application Mobile Java — Programmation Mobile L3
**Université de Vakinankaratra — Enseignant : ANDRIAMIRADO Tohaina Tsaroana**

## Structure du projet

```
android-app/
├── app/src/main/
│   ├── AndroidManifest.xml         ← Déclaration de toutes les activités
│   ├── java/com/example/mobileapp/
│   │   ├── MainActivity.java       ← Exercice 1, 6
│   │   ├── AccueilActivity.java    ← Exercice 1, 2, 3, 5
│   │   ├── ProfilActivity.java     ← Exercice 2, 4, 5, 6
│   │   ├── ParametresActivity.java ← Exercice 5
│   │   └── ThemeHelper.java        ← Exercice 5 (helper)
│   └── res/
│       ├── layout/
│       │   ├── activity_main.xml
│       │   ├── activity_accueil.xml
│       │   ├── activity_profil.xml
│       └── └── activity_parametres.xml
├── create_commits.sh   ← Script pour créer les 6 commits Git
└── README.md
```

## Résumé des exercices

| # | Exercice | Fichiers modifiés |
|---|----------|-------------------|
| 1 | Affichage heure de connexion (Intent) | `MainActivity`, `AccueilActivity` |
| 2 | Écran Profil + SharedPreferences | `ProfilActivity` (nouveau), `AccueilActivity` |
| 3 | Déconnexion avec AlertDialog | `AccueilActivity` |
| 4 | Suivi des visites du profil | `ProfilActivity` |
| 5 | Thème clair/sombre | `ParametresActivity` (nouveau), `ThemeHelper` (nouveau), tous les écrans |
| 6 | Modification de l'email | `ProfilActivity`, `MainActivity` |

## Fonctionnement de l'application

### Connexion (MainActivity)
- Email : n'importe quel email valide (doit contenir `@` et `.`)
- Mot de passe : **1234** (de démonstration)
- Cocher "Se souvenir de moi" pour pré-remplir l'email au prochain lancement

### Navigation
```
MainActivity (Login)
    ↓ login réussi
AccueilActivity
    ├── Mon Profil → ProfilActivity
    ├── Paramètres → ParametresActivity
    └── Déconnexion → AlertDialog → retour à MainActivity
```

### SharedPreferences sauvegardées
| Clé | Description |
|-----|-------------|
| `current_email` | Email courant de l'utilisateur |
| `saved_email` | Email pour "Se souvenir de moi" |
| `first_login` | Date/heure de première connexion |
| `last_login` | Date/heure de dernière connexion |
| `last_profile_visit` | Dernière visite du profil |
| `profile_visit_count` | Nombre de visites du profil |
| `app_theme` | Thème choisi (`light` ou `dark`) |
| `remember_me` | Booléen "Se souvenir de moi" |

## Exécuter le projet

1. Ouvrir **Android Studio**
2. **File → Open** → sélectionner le dossier `android-app/` de ce projet
3. Attendre la synchronisation Gradle
4. Lancer sur un émulateur ou un appareil Android (API 24+)

### Compilation via ligne de commande (sans Android Studio)

Vous pouvez générer l'APK directement via la commande (nécessite Gradle) :
```bash
cd android-app
gradle assembleDebug
```
L'APK se trouvera dans `android-app/app/build/outputs/apk/debug/app-debug.apk`.
