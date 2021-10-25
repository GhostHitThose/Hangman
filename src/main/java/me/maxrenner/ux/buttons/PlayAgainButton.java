package me.maxrenner.ux.buttons;

import java.awt.*;

public class PlayAgainButton extends Button {
    public PlayAgainButton(int x, int y, int width, int height, Color color, Color hoveredColor) {
        super(x, y, width, height, color, hoveredColor, false);
    }
    public PlayAgainButton(int x, int y, int width, int height, Color color, Color hoveredColor, boolean visible) {
        super(x, y, width, height, color, hoveredColor, visible);
    }

    @Override
    public void build(Graphics2D g) {
        super.build(g);
    }

    @Override
    public void onClick() {

    }
}
