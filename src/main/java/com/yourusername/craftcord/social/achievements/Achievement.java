package com.yourusername.craftcord.social.achievements;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Achievement {
    private final Identifier id;
    private final Text title;
    private final Text description;
    private final int points;
    private final String icon;
    private final AchievementCategory category;

    public Achievement(Identifier id, Text title, Text description, 
                      int points, String icon, AchievementCategory category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.points = points;
        this.icon = icon;
        this.category = category;
    }

    // Getters
    public Identifier getId() { return id; }
    public Text getTitle() { return title; }
    public Text getDescription() { return description; }
    public int getPoints() { return points; }
    public String getIcon() { return icon; }
    public AchievementCategory getCategory() { return category; }
} 