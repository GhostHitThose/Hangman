package me.maxrenner.ux.buttons;

import me.maxrenner.enums.GameState;
import me.maxrenner.gamefiles.Game;

import java.awt.*;

public class ExitButton extends Button{
    private Game game;

    public ExitButton(int x, int y, int width, int height, Color color, Color hoveredColor, Game game) {
        super(x, y, width, height, color, hoveredColor, false);
        this.game = game;
    }

    public ExitButton(int x, int y, int width, int height, Color color, Color hoveredColor, boolean visible, Game game) {
        super(x, y, width, height, color, hoveredColor, visible);
        this.game = game;
    }

    @Override
    public void build(Graphics2D g) {
        super.build(g);
    }

    @Override
    public void onClick() {
        game.setGameState(GameState.END);
    }
}
