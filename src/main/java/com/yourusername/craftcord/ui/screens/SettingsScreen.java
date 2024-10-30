package com.yourusername.craftcord.ui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import com.yourusername.craftcord.config.ModConfig;

public class SettingsScreen extends Screen {
    private final Screen parent;
    private CheckboxWidget friendNotifications;
    private CheckboxWidget showFriendStatus;

    public SettingsScreen(Screen parent) {
        super(Text.literal("CraftCord Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        ModConfig config = ModConfig.getInstance();

        // Friend Notifications toggle
        this.friendNotifications = new CheckboxWidget(
            this.width / 2 - 100, 50, 200, 20,
            Text.literal("Friend Notifications"),
            config.enableFriendNotifications
        );
        this.addDrawableChild(this.friendNotifications);

        // Friend Status in Chat toggle
        this.showFriendStatus = new CheckboxWidget(
            this.width / 2 - 100, 80, 200, 20,
            Text.literal("Show Friend Status in Chat"),
            config.showFriendStatusInChat
        );
        this.addDrawableChild(this.showFriendStatus);

        // Save button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Save"),
            button -> this.saveSettings())
            .dimensions(this.width / 2 - 100, this.height - 40, 200, 20)
            .build());
    }

    private void saveSettings() {
        ModConfig config = ModConfig.getInstance();
        config.enableFriendNotifications = this.friendNotifications.isChecked();
        config.showFriendStatusInChat = this.showFriendStatus.isChecked();
        ModConfig.save();
        this.client.setScreen(this.parent);
    }
} 