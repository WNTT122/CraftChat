package com.yourusername.craftcord.chat;

import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

public class ChatFormatter {
    public static Text format(String channel, String sender, String message) {
        Style channelStyle = Style.EMPTY.withColor(getChannelColor(channel));
        Style senderStyle = Style.EMPTY.withColor(Formatting.YELLOW);
        Style messageStyle = Style.EMPTY.withColor(Formatting.WHITE);

        return Text.literal("[" + channel + "] ")
                .setStyle(channelStyle)
                .append(Text.literal(sender + ": ")
                        .setStyle(senderStyle))
                .append(Text.literal(message)
                        .setStyle(messageStyle));
    }

    private static Formatting getChannelColor(String channel) {
        return switch (channel.toLowerCase()) {
            case "global" -> Formatting.WHITE;
            case "friends" -> Formatting.GREEN;
            case "party" -> Formatting.BLUE;
            default -> Formatting.GRAY;
        };
    }

    public static Text formatFriendStatus(String username, String status) {
        return Text.literal(username)
                .setStyle(Style.EMPTY.withColor(Formatting.YELLOW))
                .append(Text.literal(" is now ")
                        .setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                .append(Text.literal(status)
                        .setStyle(Style.EMPTY.withColor(Formatting.GREEN)));
    }
} 