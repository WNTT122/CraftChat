package com.yourusername.craftcord.ui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import com.yourusername.craftcord.ui.theme.Theme;
import com.yourusername.craftcord.ui.widgets.ColorPickerWidget;

public class ThemeCustomizationScreen extends Screen {
    private final Theme currentTheme;
    private Theme.ColorScheme.Builder colorBuilder;
    private boolean previewMode = false;
    
    public ThemeCustomizationScreen() {
        super(Text.literal("Theme Customization"));
        this.currentTheme = ThemeManager.getCurrentTheme();
        this.colorBuilder = new Theme.ColorScheme.Builder()
            .primary(currentTheme.getColors().primary)
            .secondary(currentTheme.getColors().secondary)
            .accent(currentTheme.getColors().accent)
            // ... copy other colors ...
    }
    
    @Override
    protected void init() {
        int y = 40;
        
        // Theme preset selector
        this.addDrawableChild(new DropdownWidget<>(
            10, y, 200, 20,
            ThemeManager.getAvailableThemes(),
            theme -> loadThemePreset(theme)
        ));
        y += 30;
        
        // Color pickers
        addColorPicker("Primary Color", colorBuilder.primary, y, 
            color -> colorBuilder.primary(color));
        y += 60;
        
        addColorPicker("Secondary Color", colorBuilder.secondary, y,
            color -> colorBuilder.secondary(color));
        y += 60;
        
        addColorPicker("Accent Color", colorBuilder.accent, y,
            color -> colorBuilder.accent(color));
        y += 60;
        
        // Preview toggle
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Toggle Preview"),
            button -> togglePreview())
            .dimensions(this.width - 110, 10, 100, 20)
            .build());
            
        // Save button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Save Theme"),
            button -> saveTheme())
            .dimensions(this.width / 2 - 100, this.height - 30, 200, 20)
            .build());
    }
    
    private void addColorPicker(String label, int initialColor, int y, 
                              Consumer<Integer> onColorChange) {
        drawTextWithShadow(matrices, this.textRenderer,
            label, 10, y, 0xFFFFFF);
            
        this.addDrawableChild(new ColorPickerWidget(
            10, y + 15, 200, 40,
            initialColor,
            onColorChange
        ));
    }
    
    private void togglePreview() {
        previewMode = !previewMode;
        if (previewMode) {
            ThemeManager.previewTheme(new Theme.Builder()
                .colorScheme(colorBuilder.build())
                .build());
        } else {
            ThemeManager.cancelPreview();
        }
    }
    
    private void saveTheme() {
        Theme newTheme = new Theme.Builder()
            .colorScheme(colorBuilder.build())
            .build();
        ThemeManager.saveCustomTheme(newTheme);
        ThemeManager.setTheme(newTheme.getName());
        this.client.setScreen(null);
    }
} 