package com.yourusername.craftcord.ui.theme;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ThemeManager {
    private static final Map<String, Theme> themes = new HashMap<>();
    private static Theme currentTheme;
    private static final Path THEMES_PATH = FabricLoader.getInstance()
        .getConfigDir().resolve("craftcord").resolve("themes");
    
    public static void init() {
        // Register default themes
        registerDefaultThemes();
        loadCustomThemes();
        currentTheme = themes.get("default");
    }
    
    private static void registerDefaultThemes() {
        // Default theme
        themes.put("default", new Theme.Builder()
            .name("Default")
            .colorScheme(new Theme.ColorScheme.Builder().build())
            .build());
            
        // Dark theme
        themes.put("dark", new Theme.Builder()
            .name("Dark")
            .colorScheme(new Theme.ColorScheme.Builder()
                .primary(0xFF121212)
                .secondary(0xFF1E1E1E)
                .accent(0xFF00BCD4)
                .build())
            .build());
            
        // Light theme
        themes.put("light", new Theme.Builder()
            .name("Light")
            .colorScheme(new Theme.ColorScheme.Builder()
                .primary(0xFFFAFAFA)
                .secondary(0xFFF5F5F5)
                .accent(0xFF2196F3)
                .text(0xFF000000)
                .build())
            .build());
    }
    
    public static Theme getCurrentTheme() {
        return currentTheme;
    }
    
    public static void setTheme(String themeName) {
        if (themes.containsKey(themeName)) {
            currentTheme = themes.get(themeName);
            saveCurrentTheme();
        }
    }
} 