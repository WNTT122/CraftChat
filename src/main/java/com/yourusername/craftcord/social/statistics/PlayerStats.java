package com.yourusername.craftcord.social.statistics;

import java.util.*;

public class PlayerStats {
    private final UUID playerId;
    private final Map<StatisticType, Integer> statistics;
    private final List<Achievement> achievements;
    private int socialScore;

    public PlayerStats(UUID playerId) {
        this.playerId = playerId;
        this.statistics = new EnumMap<>(StatisticType.class);
        this.achievements = new ArrayList<>();
        this.socialScore = 0;
    }

    public void incrementStat(StatisticType type) {
        statistics.merge(type, 1, Integer::sum);
        checkAchievements();
        updateSocialScore();
    }

    public void addAchievement(Achievement achievement) {
        if (!achievements.contains(achievement)) {
            achievements.add(achievement);
            socialScore += achievement.getPoints();
            NotificationManager.showAchievement(playerId, achievement);
        }
    }

    private void updateSocialScore() {
        int newScore = calculateSocialScore();
        if (newScore != socialScore) {
            socialScore = newScore;
            NotificationManager.showScoreUpdate(playerId, socialScore);
        }
    }

    private int calculateSocialScore() {
        return achievements.stream().mapToInt(Achievement::getPoints).sum() +
               statistics.entrySet().stream()
                   .mapToInt(e -> e.getKey().getPointValue() * e.getValue())
                   .sum();
    }
} 