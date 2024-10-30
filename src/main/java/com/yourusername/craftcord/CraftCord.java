package com.yourusername.craftcord;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yourusername.craftcord.config.ModConfig;
import com.yourusername.craftcord.network.NetworkHandler;
import com.yourusername.craftcord.ui.NotificationManager;

public class CraftCord implements ModInitializer {
    public static final String MOD_ID = "craftcord";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static KeyBinding friendListKey;
    private static KeyBinding chatKey;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing CraftCord");
        
        // Load config
        ModConfig.load();
        
        // Initialize systems
        ChatSystem.init();
        FriendSystem.init();
        
        // Register network handlers
        NetworkHandler.registerClientPackets();
        NetworkHandler.registerServerPackets();
        
        // Register keybindings
        registerKeybindings();
        
        // Register commands
        ModCommands.register();
        
        // Initialize notification system
        NotificationManager.init();
    }
    
    private void registerKeybindings() {
        friendListKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.craftcord.friendlist",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            "category.craftcord.general"
        ));
        
        chatKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.craftcord.chat",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            "category.craftcord.general"
        ));
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (friendListKey.wasPressed()) {
                client.setScreen(new FriendListScreen());
            }
            while (chatKey.wasPressed()) {
                client.setScreen(new ChatScreen());
            }
        });
    }
} 