package me.maxrenner.ux.buttons;

import me.maxrenner.enums.GameState;
import me.maxrenner.gamefiles.Game;

import java.awt.*;

public class PlayButton extends Button {

    private final Game game;

    public PlayButton(int x, int y, int width, int height, Color color, Color hoveredColor, boolean visible, Game game) {
        super(x, y, width, height, color, hoveredColor, visible);
        this.game = game;
    }
    public PlayButton(int x, int y, int width, int height, Color color, Color hoveredColor, Game game) {
        super(x, y, width, height, color, hoveredColor, false);
        this.game = game;
    }

    @Override
    public void onClick() {
        if(!isVisible()) return;
        game.setGameState(GameState.NEW_GAME);
    }

    @Override
    public void build(Graphics2D g){
        if(!isVisible()) return;
        super.build(g);
    }
}
