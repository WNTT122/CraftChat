package com.yourusername.craftcord.ui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import com.yourusername.craftcord.ui.widgets.ChatMessageWidget;
import com.yourusername.craftcord.ui.theme.Theme;

public class ChatScreen extends Screen {
    private final Theme theme;
    private final List<ChatMessageWidget> messages = new ArrayList<>();
    private TextFieldWidget inputField;
    private String currentChannel = "Global";
    private float scrollOffset = 0;
    
    public ChatScreen() {
        super(Text.literal("Chat"));
        this.theme = ThemeManager.getCurrentTheme();
    }
    
    @Override
    protected void init() {
        // Channel tabs
        int tabX = 10;
        for (String channel : ChatSystem.getChannels()) {
            this.addDrawableChild(new TabWidget(
                tabX, 5, 80, 20,
                Text.literal(channel),
                () -> switchChannel(channel),
                channel.equals(currentChannel)
            ));
            tabX += 85;
        }
        
        // Chat input
        this.inputField = new TextFieldWidget(
            this.textRenderer,
            10, this.height - 30,
            this.width - 20, 20,
            Text.literal("")
        ) {
            @Override
            public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                if (keyCode == GLFW.GLFW_KEY_RETURN && !this.getText().isEmpty()) {
                    sendMessage(this.getText());
                    this.setText("");
                    return true;
                }
                return super.keyPressed(keyCode, scanCode, modifiers);
            }
        };
        this.addSelectableChild(this.inputField);
        
        // Load messages
        updateMessages();
    }
    
    private void updateMessages() {
        messages.clear();
        List<ChatMessage> channelMessages = ChatSystem.getChannelMessages(currentChannel);
        int y = this.height - 50;
        
        for (ChatMessage msg : channelMessages) {
            messages.add(new ChatMessageWidget(
                10, y, this.width - 20, 20,
                msg, theme
            ));
            y -= 25;
        }
    }
    
    private void switchChannel(String channel) {
        this.currentChannel = channel;
        updateMessages();
        scrollOffset = 0;
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        
        // Apply theme background
        fill(matrices, 0, 0, this.width, this.height,
            theme.getColors().background);
            
        // Render messages with scroll
        matrices.push();
        matrices.translate(0, scrollOffset, 0);
        for (ChatMessageWidget message : messages) {
            message.render(matrices, mouseX, mouseY - (int)scrollOffset, delta);
        }
        matrices.pop();
        
        // Always render input on top
        this.inputField.render(matrices, mouseX, mouseY, delta);
        
        super.render(matrices, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        scrollOffset += amount * 20;
        // Limit scrolling
        scrollOffset = Math.min(0, Math.max(-messages.size() * 25 + height - 100, scrollOffset));
        return true;
    }
} 