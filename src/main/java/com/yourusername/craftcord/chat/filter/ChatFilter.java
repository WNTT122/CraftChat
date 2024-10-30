package com.yourusername.craftcord.chat.filter;

import java.util.*;
import java.util.regex.Pattern;
import com.yourusername.craftcord.config.ModConfig;

public class ChatFilter {
    private static final Set<String> blockedWords = new HashSet<>();
    private static final Map<Pattern, String> replacementPatterns = new HashMap<>();
    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        
        // Load filter patterns
        loadFilters();
        
        // Create common patterns
        createDefaultPatterns();
        
        initialized = true;
    }

    public static String filterMessage(String message) {
        if (!ModConfig.getInstance().enableChatFilter) {
            return message;
        }

        String filtered = message;
        // Apply word filters
        for (Map.Entry<Pattern, String> entry : replacementPatterns.entrySet()) {
            filtered = entry.getKey().matcher(filtered).replaceAll(entry.getValue());
        }
        
        return filtered;
    }

    private static void loadFilters() {
        try {
            // Load from config/resource file
            ResourceLoader.loadFilterList().forEach(word -> {
                blockedWords.add(word.toLowerCase());
                Pattern pattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
                replacementPatterns.put(pattern, "*".repeat(word.length()));
            });
        } catch (Exception e) {
            CraftCord.LOGGER.error("Failed to load chat filters", e);
        }
    }

    private static void createDefaultPatterns() {
        // Add spam patterns
        replacementPatterns.put(Pattern.compile("(.)\\1{4,}"), "$1$1$1"); // Repeated characters
        replacementPatterns.put(Pattern.compile("(?i)(\\b\\w+\\b)\\s*(\\1\\s*){2,}"), "$1"); // Repeated words
    }
} 