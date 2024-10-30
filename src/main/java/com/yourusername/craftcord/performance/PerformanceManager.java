package com.yourusername.craftcord.performance;

import net.minecraft.client.MinecraftClient;
import com.yourusername.craftcord.config.ModConfig;

public class PerformanceManager {
    private static long lastGC = 0;
    private static final long GC_INTERVAL = 300000; // 5 minutes
    
    public static void init() {
        // Register periodic tasks
        registerPerformanceTasks();
    }

    public static void optimizeMemory() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastGC > GC_INTERVAL) {
            System.gc();
            lastGC = currentTime;
        }
    }

    public static void optimizeRendering() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.worldRenderer != null) {
            // Optimize chunk rendering
            client.worldRenderer.reload();
        }
    }

    public static void clearCaches() {
        // Clear various caches
        ChatSystem.clearOldMessages();
        TextureManager.clearUnused();
    }

    private static void registerPerformanceTasks() {
        // Register periodic optimization tasks
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (ModConfig.getInstance().enablePerformanceOptimizations) {
                optimizeMemory();
                clearCaches();
            }
        });
    }
} 