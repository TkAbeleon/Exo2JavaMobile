package com.example.mobileapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;

public class ThemeHelper {

    private static final String PREFS_NAME = "MobileAppPrefs";
    private static final String KEY_THEME = "app_theme";
    public static final String THEME_LIGHT = "light";
    public static final String THEME_DARK = "dark";

    /**
     * Sauvegarde le thème choisi dans SharedPreferences.
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
     * Applique le thème (fond + texte) sur la vue racine d'une activité.
     */
    public static void applyTheme(Context context, View rootView) {
        String theme = getTheme(context);
        if (THEME_DARK.equals(theme)) {
            rootView.setBackgroundColor(Color.parseColor("#121212"));
            applyTextColorRecursive(rootView, Color.WHITE);
        } else {
            rootView.setBackgroundColor(Color.WHITE);
            applyTextColorRecursive(rootView, Color.parseColor("#212121"));
        }
    }

    /**
     * Applique récursivement la couleur du texte sur tous les TextView
     * et la couleur de fond sur les EditText d'un groupe de vues.
     */
    private static void applyTextColorRecursive(View view, int textColor) {
        if (view instanceof android.widget.TextView) {
            ((android.widget.TextView) view).setTextColor(textColor);
        }
        if (view instanceof android.widget.EditText) {
            boolean isDark = textColor == Color.WHITE;
            ((android.widget.EditText) view).setTextColor(textColor);
            ((android.widget.EditText) view).setHintTextColor(isDark ? Color.GRAY : Color.DKGRAY);
            view.setBackgroundColor(isDark ? Color.parseColor("#1E1E1E") : Color.parseColor("#F5F5F5"));
        }
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup group = (android.view.ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                applyTextColorRecursive(group.getChildAt(i), textColor);
            }
        }
    }
}
