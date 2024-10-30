package com.yourusername.craftcord.friends;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import com.yourusername.craftcord.CraftCord;

import java.io.*;
import java.util.*;
import java.nio.file.Path;

public class FriendSystem {
    private static final Map<UUID, FriendData> friends = new HashMap<>();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("craftcord")
            .resolve("friends.json");
    private static final Gson GSON = new Gson();

    public static void init() {
        loadFriends();
    }

    public static void addFriend(UUID playerUUID, String username) {
        friends.put(playerUUID, new FriendData(playerUUID, username));
        saveFriends();
        CraftCord.LOGGER.info("Added friend: " + username);
    }

    public static void removeFriend(UUID playerUUID) {
        friends.remove(playerUUID);
        saveFriends();
    }

    public static boolean isFriend(UUID playerUUID) {
        return friends.containsKey(playerUUID);
    }

    public static List<FriendData> getOnlineFriends() {
        return friends.values().stream()
                .filter(FriendData::isOnline)
                .toList();
    }

    private static void saveFriends() {
        try {
            CONFIG_PATH.getParent().toFile().mkdirs();
            try (Writer writer = new FileWriter(CONFIG_PATH.toFile())) {
                GSON.toJson(friends, writer);
            }
        } catch (IOException e) {
            CraftCord.LOGGER.error("Failed to save friends", e);
        }
    }

    private static void loadFriends() {
        if (!CONFIG_PATH.toFile().exists()) return;

        try (Reader reader = new FileReader(CONFIG_PATH.toFile())) {
            Map<UUID, FriendData> loaded = GSON.fromJson(reader,
                new TypeToken<Map<UUID, FriendData>>(){}.getType());
            if (loaded != null) {
                friends.clear();
                friends.putAll(loaded);
            }
        } catch (IOException e) {
            CraftCord.LOGGER.error("Failed to load friends", e);
        }
    }
} 