package com.yourusername.craftcord.ui.widgets;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;

public class NotificationWidget extends Widget {
    private final String title;
    private final String message;
    private final NotificationType type;
    private float alpha = 1.0f;
    private long startTime;

    public NotificationWidget(String title, String message, NotificationType type) {
        super(0, 0, 200, 40, Text.empty());
        this.title = title;
        this.message = message;
        this.type = type;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Calculate fade
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed > 2000) {
            alpha = Math.max(0, 1 - (elapsed - 2000) / 1000f);
        }

        // Set position (top-right corner)
        setX(client.getWindow().getScaledWidth() - 220);
        setY(20);

        // Draw background
        int backgroundColor = type.getColor() & 0xFFFFFF | ((int)(alpha * 255) << 24);
        fill(matrices, getX(), getY(), getX() + width, getY() + height, backgroundColor);

        // Draw border
        drawHorizontalLine(matrices, getX(), getX() + width, getY(), 0xFFFFFFFF);
        drawHorizontalLine(matrices, getX(), getX() + width, getY() + height, 0xFFFFFFFF);
        drawVerticalLine(matrices, getX(), getY(), getY() + height, 0xFFFFFFFF);
        drawVerticalLine(matrices, getX() + width, getY(), getY() + height, 0xFFFFFFFF);

        // Draw text
        int textColor = 0xFFFFFF | ((int)(alpha * 255) << 24);
        drawTextWithShadow(matrices, client.textRenderer,
            title, getX() + 5, getY() + 5, textColor);
        drawTextWithShadow(matrices, client.textRenderer,
            message, getX() + 5, getY() + 20, textColor);
    }

    public boolean shouldRemove() {
        return alpha <= 0;
    }
} 