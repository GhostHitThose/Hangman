package me.maxrenner.ux.buttons;

import me.maxrenner.enums.GameState;
import me.maxrenner.gamefiles.Game;
import me.maxrenner.utils.CenterWord;

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
        if(!isVisible()) return;
        super.build(g);
        g.setColor(Color.BLACK);
        CenterWord.drawCenteredWord(g, "EXIT", new Font("Impact", Font.PLAIN, 40), Color.BLACK, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void onClick() {
        if(!isVisible()) return;
        game.setGameState(GameState.END);
    }
}
