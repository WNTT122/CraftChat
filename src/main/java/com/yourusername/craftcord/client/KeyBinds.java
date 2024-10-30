package com.yourusername.craftcord.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    // Define keybindings
    public static KeyBinding openFriendList;
    public static KeyBinding openChat;
    public static KeyBinding openPartyMenu;
    public static KeyBinding quickMessage;
    public static KeyBinding openSettings;

    public static void register() {
        // Friend List - Default: F
        openFriendList = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.craftcord.friendlist",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_F,
            "category.craftcord.general"
        ));

        // Chat - Default: C
        openChat = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.craftcord.chat",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "category.craftcord.general"
        ));

        // Party Menu - Default: P
        openPartyMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.craftcord.party",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_P,
            "category.craftcord.general"
        ));

        // Quick Message - Default: R
        quickMessage = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.craftcord.quickmessage",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.craftcord.general"
        ));

        // Settings - Default: K
        openSettings = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.craftcord.settings",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "category.craftcord.general"
        ));
    }
} 