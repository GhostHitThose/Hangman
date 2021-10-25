package me.maxrenner.ux.managers;

import lombok.Getter;
import me.maxrenner.ux.buttons.Button;

import java.awt.*;
import java.util.ArrayList;

public class UXButtonsManager {

    @Getter private final ArrayList<Button> buttons;

    public UXButtonsManager(){
        buttons = new ArrayList<>();
    }

    public void buildAll(Graphics2D g){
        for(Button button : buttons){
            button.build(g);
        }
    }

    public void checkHovered(int x, int y){
        for(Button button : buttons){
            button.setHovered(x,y);
        }
    }

    public void checkClicked(int x, int y){
        for(Button button : buttons){
            button.setClicked(x,y);
        }
    }
}
