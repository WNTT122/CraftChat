package com.yourusername.craftcord.ui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import com.yourusername.craftcord.friends.FriendData;
import com.yourusername.craftcord.friends.FriendSystem;

public class FriendListScreen extends Screen {
    private static final int ENTRY_HEIGHT = 20;
    private static final int PADDING = 4;
    
    public FriendListScreen() {
        super(Text.literal("Friends"));
    }
    
    @Override
    protected void init() {
        int y = 40;
        
        // Add friend button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Add Friend"),
            button -> this.client.setScreen(new AddFriendScreen(this)))
            .dimensions(this.width / 2 - 100, 10, 200, 20)
            .build());
            
        // List friends
        for (FriendData friend : FriendSystem.getAllFriends()) {
            final int currentY = y;
            
            // Friend entry
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(friend.getUsername() + " - " + friend.getStatus()),
                button -> this.openFriendActions(friend))
                .dimensions(this.width / 2 - 100, currentY, 180, ENTRY_HEIGHT)
                .build());
                
            y += ENTRY_HEIGHT + PADDING;
        }
        
        // Back button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Back"),
            button -> this.close())
            .dimensions(this.width / 2 - 100, this.height - 30, 200, 20)
            .build());
    }
    
    private void openFriendActions(FriendData friend) {
        this.client.setScreen(new FriendActionsScreen(this, friend));
    }
} 