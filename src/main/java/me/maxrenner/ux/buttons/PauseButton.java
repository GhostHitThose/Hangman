package me.maxrenner.ux.buttons;

import me.maxrenner.enums.GameState;
import me.maxrenner.gamefiles.Game;
import me.maxrenner.utils.CenterWord;

import java.awt.*;

public class PauseButton extends Button {

    private final Game game;

    public PauseButton(int x, int y, int width, int height, Color color, Color hoveredColor, boolean visible, Game game) {
        super(x, y, width, height, color, hoveredColor, visible);
        this.game = game;
    }

    public PauseButton(int x, int y, int width, int height, Color color, Color hoveredColor, Game game) {
        super(x, y, width, height, color, hoveredColor, false);
        this.game = game;
    }

    @Override
    public void build(Graphics2D g) {
        super.build(g);
        CenterWord.drawCenteredWord(g, "||", new Font("Sans", Font.PLAIN, 25), Color.BLACK, 0, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void onClick() {
        setHovered(0,0);
        game.setGameState(GameState.PAUSED);
    }
}
