package com.yourusername.craftcord.ui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class MainMenuScreen extends Screen {
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int PADDING = 4;

    public MainMenuScreen() {
        super(Text.literal("CraftCord Menu"));
    }

    @Override
    protected void init() {
        int y = height / 4;

        // Social button
        addDrawableChild(ButtonWidget.builder(
            Text.literal("Social & Friends"),
            button -> client.setScreen(new SocialScreen()))
            .dimensions(width / 2 - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT)
            .build());
        y += BUTTON_HEIGHT + PADDING;

        // Party button
        addDrawableChild(ButtonWidget.builder(
            Text.literal("Party Management"),
            button -> client.setScreen(new PartyScreen()))
            .dimensions(width / 2 - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT)
            .build());
        y += BUTTON_HEIGHT + PADDING;

        // Achievements button
        addDrawableChild(ButtonWidget.builder(
            Text.literal("Achievements"),
            button -> client.setScreen(new AchievementsScreen()))
            .dimensions(width / 2 - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT)
            .build());
        y += BUTTON_HEIGHT + PADDING;

        // Statistics button
        addDrawableChild(ButtonWidget.builder(
            Text.literal("Statistics"),
            button -> client.setScreen(new StatisticsScreen()))
            .dimensions(width / 2 - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT)
            .build());
        y += BUTTON_HEIGHT + PADDING;

        // Settings button
        addDrawableChild(ButtonWidget.builder(
            Text.literal("Settings"),
            button -> client.setScreen(new SettingsScreen()))
            .dimensions(width / 2 - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT)
            .build());
    }
} 