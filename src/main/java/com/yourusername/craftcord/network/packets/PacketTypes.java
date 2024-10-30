package com.yourusername.craftcord.network.packets;

import net.minecraft.util.Identifier;
import com.yourusername.craftcord.CraftCord;

public class PacketTypes {
    public static final Identifier FRIEND_REQUEST = new Identifier(CraftCord.MOD_ID, "friend_request");
    public static final Identifier FRIEND_RESPONSE = new Identifier(CraftCord.MOD_ID, "friend_response");
    public static final Identifier CHAT_MESSAGE = new Identifier(CraftCord.MOD_ID, "chat_message");
    public static final Identifier STATUS_UPDATE = new Identifier(CraftCord.MOD_ID, "status_update");
    public static final Identifier NOTIFICATION = new Identifier(CraftCord.MOD_ID, "notification");
} 