package com.yourusername.craftcord.social.achievements;

import java.util.*;
import net.minecraft.server.network.ServerPlayerEntity;

public class AchievementManager {
    private static final Map<Identifier, Achievement> ACHIEVEMENTS = new HashMap<>();
    private static final Map<UUID, Set<Identifier>> PLAYER_ACHIEVEMENTS = new HashMap<>();

    public static void init() {
        registerAchievements();
    }

    public static void awardAchievement(ServerPlayerEntity player, Identifier achievementId) {
        Achievement achievement = ACHIEVEMENTS.get(achievementId);
        if (achievement == null) return;

        UUID playerId = player.getUuid();
        Set<Identifier> playerAchievements = PLAYER_ACHIEVEMENTS
            .computeIfAbsent(playerId, k -> new HashSet<>());

        if (playerAchievements.add(achievementId)) {
            // Notify player
            NotificationManager.showAchievement(player, achievement);
            // Save progress
            savePlayerAchievements(playerId);
        }
    }

    private static void registerAchievements() {
        // Social achievements
        registerAchievement(new Achievement(
            new Identifier(CraftCord.MOD_ID, "social_butterfly"),
            Text.literal("Social Butterfly"),
            Text.literal("Add 10 friends"),
            50,
            "textures/item/butterfly",
            AchievementCategory.SOCIAL
        ));

        // Party achievements
        registerAchievement(new Achievement(
            new Identifier(CraftCord.MOD_ID, "party_leader"),
            Text.literal("Party Leader"),
            Text.literal("Lead a party of 5 players"),
            100,
            "textures/item/crown",
            AchievementCategory.PARTY
        ));

        // More achievements...
    }
} 