package me.maxrenner.gamefiles.managers;

import me.maxrenner.Hangman;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseInputManager {
    public MouseAdapter createMouseAdapter(){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Hangman.getInstance().getGame().mouseClick(e);
            }
        };
    }

    public MouseMotionAdapter createMouseMotionAdapter(){
        return new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e){
                Hangman.getInstance().getGame().mouseMove(e);
            }
        };
    }
}
