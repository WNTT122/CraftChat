package com.yourusername.craftcord.ui.widgets;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;
import java.util.List;

public class GraphWidget extends Widget {
    private final List<DataPoint> data;
    private final int width;
    private final int height;
    private int maxValue;

    public GraphWidget(int x, int y, int width, int height, List<DataPoint> data) {
        super(x, y, width, height, Text.empty());
        this.width = width;
        this.height = height;
        this.data = data;
        updateMaxValue();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Draw background
        fill(matrices, getX(), getY(), getX() + width, getY() + height, 0x33000000);
        
        // Draw grid lines
        for (int i = 0; i <= 4; i++) {
            int y = getY() + (height * i / 4);
            drawHorizontalLine(matrices, getX(), getX() + width, y, 0x44FFFFFF);
            
            // Draw value labels
            int value = maxValue * (4 - i) / 4;
            drawTextWithShadow(matrices, client.textRenderer,
                String.valueOf(value),
                getX() - 25, y - 4, 0xFFFFFF);
        }

        // Draw graph line
        if (data.size() > 1) {
            for (int i = 0; i < data.size() - 1; i++) {
                DataPoint current = data.get(i);
                DataPoint next = data.get(i + 1);
                
                int x1 = getX() + (width * i / (data.size() - 1));
                int x2 = getX() + (width * (i + 1) / (data.size() - 1));
                int y1 = getY() + height - (height * current.value() / maxValue);
                int y2 = getY() + height - (height * next.value() / maxValue);
                
                drawLine(matrices, x1, y1, x2, y2, 0xFF00FF00);
            }
        }

        // Draw hover tooltip
        if (isHovered(mouseX, mouseY)) {
            int dataIndex = (mouseX - getX()) * (data.size() - 1) / width;
            if (dataIndex >= 0 && dataIndex < data.size()) {
                DataPoint point = data.get(dataIndex);
                renderTooltip(matrices, 
                    Text.literal(point.date() + ": " + point.value()),
                    mouseX, mouseY);
            }
        }
    }

    public void updateData(List<DataPoint> newData) {
        this.data.clear();
        this.data.addAll(newData);
        updateMaxValue();
    }

    private void updateMaxValue() {
        this.maxValue = data.stream()
            .mapToInt(DataPoint::value)
            .max()
            .orElse(100);
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= getX() && mouseX <= getX() + width &&
               mouseY >= getY() && mouseY <= getY() + height;
    }
} 