package com.yourusername.craftcord.friends;

import java.util.UUID;

public class FriendData {
    private final UUID uuid;
    private final String username;
    private boolean online;
    private String status;

    public FriendData(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
        this.online = false;
        this.status = "Offline";
    }

    // Getters and setters
    public UUID getUuid() { return uuid; }
    public String getUsername() { return username; }
    public boolean isOnline() { return online; }
    public void setOnline(boolean online) { this.online = online; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 