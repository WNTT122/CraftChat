package com.yourusername.craftcord.ui.theme;

import net.minecraft.util.Identifier;
import java.awt.Color;

public class Theme {
    private final String name;
    private final ColorScheme colors;
    private final Identifier backgroundTexture;
    private final FontSettings fontSettings;
    
    public static class ColorScheme {
        public final int primary;
        public final int secondary;
        public final int accent;
        public final int text;
        public final int textSecondary;
        public final int background;
        public final int buttonNormal;
        public final int buttonHover;
        public final int buttonPressed;
        
        public ColorScheme(Builder builder) {
            this.primary = builder.primary;
            this.secondary = builder.secondary;
            this.accent = builder.accent;
            this.text = builder.text;
            this.textSecondary = builder.textSecondary;
            this.background = builder.background;
            this.buttonNormal = builder.buttonNormal;
            this.buttonHover = builder.buttonHover;
            this.buttonPressed = builder.buttonPressed;
        }
        
        public static class Builder {
            private int primary = 0xFF2196F3;    // Default blue
            private int secondary = 0xFF1976D2;
            private int accent = 0xFFFF4081;
            private int text = 0xFFFFFFFF;
            private int textSecondary = 0xB3FFFFFF;
            private int background = 0xCC000000;
            private int buttonNormal = 0xFF1E88E5;
            private int buttonHover = 0xFF2196F3;
            private int buttonPressed = 0xFF1976D2;
            
            public Builder primary(int color) { this.primary = color; return this; }
            public Builder secondary(int color) { this.secondary = color; return this; }
            public Builder accent(int color) { this.accent = color; return this; }
            // ... other setters ...
            
            public ColorScheme build() {
                return new ColorScheme(this);
            }
        }
    }
    
    public static class FontSettings {
        public final float scale;
        public final boolean shadow;
        public final int spacing;
        
        public FontSettings(float scale, boolean shadow, int spacing) {
            this.scale = scale;
            this.shadow = shadow;
            this.spacing = spacing;
        }
    }
} 