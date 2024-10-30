package com.yourusername.craftcord.ui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import com.yourusername.craftcord.config.ModConfig;

public class PerformanceSettingsScreen extends Screen {
    private final Screen parent;

    public PerformanceSettingsScreen(Screen parent) {
        super(Text.literal("Performance Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        ModConfig config = ModConfig.getInstance();
        int y = 40;

        // Memory optimization toggle
        this.addDrawableChild(new CheckboxWidget(
            this.width / 2 - 100, y, 200, 20,
            Text.literal("Enable Memory Optimization"),
            config.enableMemoryOptimization
        ));
        y += 25;

        // Render distance slider
        this.addDrawableChild(new SliderWidget(
            this.width / 2 - 100, y, 200, 20,
            Text.literal("Render Distance"), 
            config.renderDistance / 32.0) {
            @Override
            protected void updateMessage() {
                setMessage(Text.literal("Render Distance: " + 
                    (int)(value * 32)));
            }

            @Override
            protected void applyValue() {
                config.renderDistance = (int)(value * 32);
            }
        });
        y += 25;

        // Save button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Save and Close"),
            button -> {
                ModConfig.save();
                this.client.setScreen(parent);
            })
            .dimensions(this.width / 2 - 100, this.height - 40, 200, 20)
            .build());
    }
} 