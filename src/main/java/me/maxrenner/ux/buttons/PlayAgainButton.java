package me.maxrenner.ux.buttons;

import me.maxrenner.enums.GameState;
import me.maxrenner.gamefiles.Game;
import me.maxrenner.utils.CenterWord;

import java.awt.*;

public class PlayAgainButton extends Button {

    private final Game game;

    public PlayAgainButton(int x, int y, int width, int height, Color color, Color hoveredColor, Game game) {
        super(x, y, width, height, color, hoveredColor, false);
        this.game = game;
    }
    public PlayAgainButton(int x, int y, int width, int height, Color color, Color hoveredColor, boolean visible, Game game) {
        super(x, y, width, height, color, hoveredColor, visible);
        this.game = game;
    }

    @Override
    public void build(Graphics2D g) {
        if(!isVisible()) return;
        super.build(g);
        CenterWord.drawCenteredWord(g, "Play Again", new Font("Impact", Font.BOLD, 30), Color.BLACK, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void onClick() {
        if(!isVisible()) return;
        game.setGameState(GameState.NEW_GAME);
    }
}
