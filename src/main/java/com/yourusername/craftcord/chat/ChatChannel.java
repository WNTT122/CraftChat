package com.yourusername.craftcord.chat;

import net.minecraft.util.Formatting;
import java.util.ArrayList;
import java.util.List;

public class ChatChannel {
    private final String name;
    private final Formatting color;
    private final List<String> history;

    public ChatChannel(String name, Formatting color) {
        this.name = name;
        this.color = color;
        this.history = new ArrayList<>();
    }

    public String getName() { return name; }
    public Formatting getColor() { return color; }
    
    public void addMessage(String message) {
        history.add(message);
        if (history.size() > 100) { // Keep only last 100 messages
            history.remove(0);
        }
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
} 