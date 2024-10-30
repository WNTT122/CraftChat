package com.yourusername.craftcord.ui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import com.yourusername.craftcord.ui.theme.Theme;
import com.yourusername.craftcord.ui.widgets.FriendListWidget;

public class FriendManagementScreen extends Screen {
    private FriendListWidget friendList;
    private final Theme theme;
    private String searchQuery = "";
    
    public FriendManagementScreen() {
        super(Text.literal("Friends"));
        this.theme = ThemeManager.getCurrentTheme();
    }
    
    @Override
    protected void init() {
        // Search bar
        this.addDrawableChild(new TextFieldWidget(
            this.textRenderer, 
            this.width / 2 - 100, 20, 200, 20,
            Text.literal("Search friends...")
        ) {
            @Override
            public void onChange(String newText) {
                searchQuery = newText;
                updateFriendList();
            }
        });
        
        // Friend list
        this.friendList = new FriendListWidget(
            this.client,
            this.width,
            this.height,
            50,  // top
            this.height - 50, // bottom
            30   // item height
        );
        this.addSelectableChild(this.friendList);
        
        // Add friend button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Add Friend"),
            button -> this.client.setScreen(new AddFriendScreen(this)))
            .dimensions(this.width / 2 - 100, this.height - 40, 200, 20)
            .build());
            
        updateFriendList();
    }
    
    private void updateFriendList() {
        List<FriendData> friends = FriendSystem.getFriends().stream()
            .filter(friend -> searchQuery.isEmpty() || 
                friend.getUsername().toLowerCase().contains(searchQuery.toLowerCase()))
            .collect(Collectors.toList());
        friendList.updateEntries(friends);
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        
        // Apply theme
        fill(matrices, 0, 0, this.width, this.height, 
            theme.getColors().background);
            
        this.friendList.render(matrices, mouseX, mouseY, delta);
        
        drawCenteredText(matrices, this.textRenderer,
            this.title,
            this.width / 2,
            8,
            theme.getColors().text);
            
        super.render(matrices, mouseX, mouseY, delta);
    }
} 