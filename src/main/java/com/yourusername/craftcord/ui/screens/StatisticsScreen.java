package com.yourusername.craftcord.ui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import com.yourusername.craftcord.ui.widgets.GraphWidget;
import com.yourusername.craftcord.social.statistics.PlayerStats;

public class StatisticsScreen extends Screen {
    private final PlayerStats stats;
    private GraphWidget socialGraph;
    private int selectedTimeRange = 7; // days

    public StatisticsScreen() {
        super(Text.literal("Statistics"));
        this.stats = StatisticsManager.getPlayerStats(client.player.getUuid());
    }

    @Override
    protected void init() {
        // Time range selector
        addDrawableChild(ButtonWidget.builder(
            Text.literal("Last 7 Days"),
            button -> setTimeRange(7))
            .dimensions(10, 10, 100, 20)
            .build());

        addDrawableChild(ButtonWidget.builder(
            Text.literal("Last 30 Days"),
            button -> setTimeRange(30))
            .dimensions(120, 10, 100, 20)
            .build());

        // Initialize graph
        socialGraph = new GraphWidget(
            width / 2 - 150, 40, 300, 200,
            stats.getHistoricalData(selectedTimeRange)
        );
        addDrawableChild(socialGraph);

        // Stats categories
        int y = height - 120;
        for (StatisticType type : StatisticType.values()) {
            int value = stats.getStat(type);
            addDrawableChild(new StatWidget(
                10, y, width - 20, 20,
                type.getName(),
                value
            ));
            y += 25;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        
        // Render social score
        drawCenteredText(
            matrices, textRenderer,
            "Social Score: " + stats.getSocialScore(),
            width / 2, 10, 0xFFFFFF
        );

        super.render(matrices, mouseX, mouseY, delta);
    }

    private void setTimeRange(int days) {
        this.selectedTimeRange = days;
        socialGraph.setData(stats.getHistoricalData(days));
    }
} 