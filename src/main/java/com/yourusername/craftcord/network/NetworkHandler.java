package com.yourusername.craftcord.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import com.yourusername.craftcord.ui.NotificationManager;

import java.util.UUID;

public class NetworkHandler {
    public static final Identifier FRIEND_REQUEST = new Identifier(CraftCord.MOD_ID, "friend_request");
    public static final Identifier FRIEND_CHAT = new Identifier(CraftCord.MOD_ID, "friend_chat");

    public static void registerServerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(FRIEND_REQUEST, (server, player, handler, buf, responseSender) -> {
            UUID targetPlayer = buf.readUuid();
            boolean isAdd = buf.readBoolean();
            
            server.execute(() -> {
                if (isAdd) {
                    FriendSystem.addFriend(targetPlayer, player.getName().getString());
                } else {
                    FriendSystem.removeFriend(targetPlayer);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(FRIEND_CHAT, (server, player, handler, buf, responseSender) -> {
            String message = buf.readString();
            
            server.execute(() -> {
                ChatSystem.sendFriendMessage(player.getUuid(), message);
            });
        });
    }

    public static void registerClientPackets() {
        // Handle incoming friend requests
        ClientPlayNetworking.registerGlobalReceiver(PacketTypes.FRIEND_REQUEST, (client, handler, buf, responseSender) -> {
            String requesterName = buf.readString();
            UUID requesterId = buf.readUuid();
            
            client.execute(() -> {
                NotificationManager.showFriendRequest(requesterName, requesterId);
            });
        });

        // Handle chat messages
        ClientPlayNetworking.registerGlobalReceiver(PacketTypes.CHAT_MESSAGE, (client, handler, buf, responseSender) -> {
            String channel = buf.readString();
            String sender = buf.readString();
            String message = buf.readString();
            
            client.execute(() -> {
                ChatSystem.receiveMessage(channel, sender, message);
            });
        });

        // Handle notifications
        ClientPlayNetworking.registerGlobalReceiver(PacketTypes.NOTIFICATION, (client, handler, buf, responseSender) -> {
            String title = buf.readString();
            String message = buf.readString();
            NotificationType type = NotificationType.valueOf(buf.readString());
            
            client.execute(() -> {
                NotificationManager.show(title, message, type);
            });
        });
    }

    public static void sendFriendRequest(UUID targetPlayer) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeUuid(targetPlayer);
        ClientPlayNetworking.send(PacketTypes.FRIEND_REQUEST, buf);
    }

    public static void sendChatMessage(String channel, String message) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(channel);
        buf.writeString(message);
        ClientPlayNetworking.send(PacketTypes.CHAT_MESSAGE, buf);
    }
} 