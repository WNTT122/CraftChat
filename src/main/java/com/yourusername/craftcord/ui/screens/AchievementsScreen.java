package com.yourusername.craftcord.ui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import com.yourusername.craftcord.social.achievements.Achievement;
import com.yourusername.craftcord.ui.widgets.AchievementWidget;

public class AchievementsScreen extends Screen {
    private static final int ACHIEVEMENTS_PER_ROW = 4;
    private static final int ACHIEVEMENT_SIZE = 64;
    private static final int PADDING = 10;
    
    private float scrollOffset = 0;
    private final List<Achievement> achievements;

    public AchievementsScreen() {
        super(Text.literal("Achievements"));
        this.achievements = AchievementManager.getPlayerAchievements(
            client.player.getUuid());
    }

    @Override
    protected void init() {
        // Category filter buttons
        int categoryX = 10;
        for (AchievementCategory category : AchievementCategory.values()) {
            addDrawableChild(ButtonWidget.builder(
                Text.literal(category.getName()),
                button -> filterByCategory(category))
                .dimensions(categoryX, 10, 80, 20)
                .build());
            categoryX += 85;
        }

        // Back button
        addDrawableChild(ButtonWidget.builder(
            Text.literal("Back"),
            button -> client.setScreen(new MainMenuScreen()))
            .dimensions(width / 2 - 100, height - 30, 200, 20)
            .build());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        
        // Draw achievements grid
        int startX = (width - (ACHIEVEMENTS_PER_ROW * (ACHIEVEMENT_SIZE + PADDING))) / 2;
        int startY = 40 + (int)scrollOffset;
        
        int index = 0;
        for (Achievement achievement : achievements) {
            int row = index / ACHIEVEMENTS_PER_ROW;
            int col = index % ACHIEVEMENTS_PER_ROW;
            
            int x = startX + (col * (ACHIEVEMENT_SIZE + PADDING));
            int y = startY + (row * (ACHIEVEMENT_SIZE + PADDING));
            
            renderAchievement(matrices, achievement, x, y, mouseX, mouseY);
            index++;
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    private void renderAchievement(MatrixStack matrices, Achievement achievement, 
                                 int x, int y, int mouseX, int mouseY) {
        // Draw achievement background
        RenderSystem.setShaderTexture(0, new Identifier("craftcord", "textures/gui/achievement_bg.png"));
        drawTexture(matrices, x, y, 0, 0, ACHIEVEMENT_SIZE, ACHIEVEMENT_SIZE);

        // Draw achievement icon
        RenderSystem.setShaderTexture(0, new Identifier(achievement.getIcon()));
        drawTexture(matrices, x + 16, y + 16, 0, 0, 32, 32);

        // Draw completion overlay if achieved
        if (achievement.isCompleted()) {
            RenderSystem.setShaderTexture(0, new Identifier("craftcord", "textures/gui/completed.png"));
            drawTexture(matrices, x, y, 0, 0, ACHIEVEMENT_SIZE, ACHIEVEMENT_SIZE);
        }

        // Show tooltip on hover
        if (mouseX >= x && mouseX <= x + ACHIEVEMENT_SIZE && 
            mouseY >= y && mouseY <= y + ACHIEVEMENT_SIZE) {
            renderTooltip(matrices, achievement, mouseX, mouseY);
        }
    }

    private void renderTooltip(MatrixStack matrices, Achievement achievement, 
                             int mouseX, int mouseY) {
        List<Text> tooltip = new ArrayList<>();
        tooltip.add(achievement.getTitle().copy().formatted(Formatting.YELLOW));
        tooltip.add(achievement.getDescription().copy().formatted(Formatting.GRAY));
        tooltip.add(Text.literal("Points: " + achievement.getPoints())
            .formatted(Formatting.GREEN));
        
        renderTooltip(matrices, tooltip, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        scrollOffset += amount * 20;
        return true;
    }
} 