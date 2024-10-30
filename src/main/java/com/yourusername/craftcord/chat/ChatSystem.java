package com.yourusername.craftcord.chat;

import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import com.yourusername.craftcord.friends.FriendSystem;
import java.util.*;

public class ChatSystem {
    private static final Map<String, ChatChannel> channels = new HashMap<>();
    
    public static void init() {
        // Create default channels
        createChannel("Global", Formatting.WHITE);
        createChannel("Friends", Formatting.GREEN);
        createChannel("Party", Formatting.BLUE);
    }

    public static void createChannel(String name, Formatting color) {
        channels.put(name.toLowerCase(), new ChatChannel(name, color));
    }

    public static Text formatMessage(String sender, String message, String channel) {
        ChatChannel chatChannel = channels.get(channel.toLowerCase());
        if (chatChannel == null) {
            chatChannel = channels.get("global");
        }

        return Text.literal("[" + chatChannel.getName() + "] ")
                .setStyle(Style.EMPTY.withColor(chatChannel.getColor()))
                .append(Text.literal(sender + ": ")
                        .setStyle(Style.EMPTY.withColor(Formatting.YELLOW)))
                .append(Text.literal(message)
                        .setStyle(Style.EMPTY.withColor(Formatting.WHITE)));
    }

    public static void sendFriendMessage(UUID sender, String message) {
        // Only send to online friends
        FriendSystem.getOnlineFriends().forEach(friend -> {
            // Here you would send the message to each online friend
            // This requires network implementation
        });
    }
} 