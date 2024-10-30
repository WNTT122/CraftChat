package com.yourusername.craftcord.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import java.util.Queue;
import java.util.LinkedList;
import java.util.UUID;

public class NotificationManager {
    private static final Queue<Notification> notificationQueue = new LinkedList<>();
    private static final int MAX_NOTIFICATIONS = 5;

    public static void show(String title, String message, NotificationType type) {
        Notification notification = new Notification(title, message, type);
        
        MinecraftClient.getInstance().execute(() -> {
            SystemToast.show(
                MinecraftClient.getInstance().getToastManager(),
                SystemToast.Type.TUTORIAL_HINT,
                Text.literal(title),
                Text.literal(message)
            );
        });

        notificationQueue.offer(notification);
        while (notificationQueue.size() > MAX_NOTIFICATIONS) {
            notificationQueue.poll();
        }
    }

    public static void showFriendRequest(String requesterName, UUID requesterId) {
        show(
            "Friend Request",
            "From: " + requesterName,
            NotificationType.FRIEND_REQUEST
        );
        
        // Show friend request screen
        MinecraftClient.getInstance().execute(() -> {
            MinecraftClient.getInstance().setScreen(
                new FriendRequestScreen(requesterName, requesterId)
            );
        });
    }
} 