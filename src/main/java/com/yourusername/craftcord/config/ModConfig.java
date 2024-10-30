package com.yourusername.craftcord.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import com.yourusername.craftcord.CraftCord;

import java.io.*;
import java.nio.file.Path;

public class ModConfig {
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("craftcord")
            .resolve("config.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    private static ModConfig INSTANCE;
    
    // Config options
    public boolean enableFriendNotifications = true;
    public boolean showFriendStatusInChat = true;
    public int maxChatHistory = 100;
    public String defaultChatChannel = "Global";
    
    public static ModConfig getInstance() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }
    
    public static void load() {
        try {
            if (CONFIG_PATH.toFile().exists()) {
                try (Reader reader = new FileReader(CONFIG_PATH.toFile())) {
                    INSTANCE = GSON.fromJson(reader, ModConfig.class);
                }
            }
            if (INSTANCE == null) {
                INSTANCE = new ModConfig();
            }
            save();
        } catch (IOException e) {
            CraftCord.LOGGER.error("Failed to load config", e);
            INSTANCE = new ModConfig();
        }
    }
    
    public static void save() {
        try {
            CONFIG_PATH.getParent().toFile().mkdirs();
            try (Writer writer = new FileWriter(CONFIG_PATH.toFile())) {
                GSON.toJson(INSTANCE, writer);
            }
        } catch (IOException e) {
            CraftCord.LOGGER.error("Failed to save config", e);
        }
    }
} 