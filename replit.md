# Application Mobile Java — Programmation Mobile L3

Application Android native en Java couvrant les 6 exercices du sujet de Programmation Mobile (Université de Vakinankaratra).

## Run & Operate

- `pnpm --filter @workspace/api-server run dev` — run the API server (port 5000)
- `pnpm run typecheck` — full typecheck across all packages
- `pnpm run build` — typecheck + build all packages

## Stack

- pnpm workspaces, Node.js 24, TypeScript 5.9
- API: Express 5
- DB: PostgreSQL + Drizzle ORM

## Android App

L'application Android se trouve dans `android-app/`. À ouvrir avec Android Studio.

- **Mot de passe de démo** : `1234`
- **API min** : Android 24 (Android 7.0)

### Exercices implémentés

| # | Exercice | Fichiers |
|---|----------|---------|
| 1 | Heure de connexion via Intent | `MainActivity`, `AccueilActivity` |
| 2 | Écran Profil + SharedPreferences | `ProfilActivity` |
| 3 | Déconnexion avec AlertDialog | `AccueilActivity` |
| 4 | Suivi des visites du profil | `ProfilActivity` |
| 5 | Thème clair/sombre | `ParametresActivity`, `ThemeHelper` |
| 6 | Modification de l'email | `ProfilActivity`, `MainActivity` |

### Créer les 6 commits Git

```bash
bash android-app/create_commits.sh
```

## Where things live

- `android-app/` — projet Android Java (6 exercices)
- `artifacts/api-server/` — serveur Express (non utilisé par l'app Android)
- `lib/` — librairies partagées TypeScript

## User preferences

- Application Android en Java natif (pas Expo/React Native)
- 6 commits Git : un par exercice

## Gotchas

- Le script `create_commits.sh` doit être exécuté depuis la racine du projet
- Le mot de passe de démo est `1234`
