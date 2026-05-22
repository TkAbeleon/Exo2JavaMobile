package com.example.mobileapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeHelper {

    private static final String PREFS_NAME = "MobileAppPrefs";
    private static final String KEY_THEME = "app_theme";
    public static final String THEME_LIGHT = "light";
    public static final String THEME_DARK = "dark";

    /**
     * Sauvegarde le thème choisi dans SharedPreferences et applique le changement.
     */
    public static void saveTheme(Context context, String theme) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_THEME, theme).apply();
    }

    /**
     * Récupère le thème actuel depuis SharedPreferences (light par défaut).
     */
    public static String getTheme(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_THEME, THEME_LIGHT);
    }

    /**
     * Applique le thème globalement grâce au AppCompatDelegate.
     * Le View n'est gardé que pour la rétrocompatibilité des appels existants.
     */
    public static void applyTheme(Context context, View rootView) {
        String theme = getTheme(context);
        if (THEME_DARK.equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
