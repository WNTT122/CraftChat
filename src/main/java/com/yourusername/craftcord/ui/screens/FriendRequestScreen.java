package com.yourusername.craftcord.ui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.util.UUID;

public class FriendRequestScreen extends Screen {
    private final String requesterName;
    private final UUID requesterId;

    public FriendRequestScreen(String requesterName, UUID requesterId) {
        super(Text.literal("Friend Request"));
        this.requesterName = requesterName;
        this.requesterId = requesterId;
    }

    @Override
    protected void init() {
        // Accept button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Accept"),
            button -> {
                FriendSystem.acceptFriendRequest(requesterId);
                this.close();
            })
            .dimensions(this.width / 2 - 105, this.height / 2, 100, 20)
            .build());

        // Decline button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Decline"),
            button -> {
                FriendSystem.declineFriendRequest(requesterId);
                this.close();
            })
            .dimensions(this.width / 2 + 5, this.height / 2, 100, 20)
            .build());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer,
            "Friend Request from " + requesterName,
            this.width / 2, this.height / 2 - 30, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
} 