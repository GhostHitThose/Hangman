package me.maxrenner.gamefiles;

import lombok.Getter;
import lombok.Setter;
import me.maxrenner.Hangman;

import java.awt.*;

public class LetterBlock {

    @Getter private final int x, y, width = 40, height = 40;
    @Getter private final char letter;
    @Getter @Setter private boolean hovered;

    public LetterBlock(int x, int y, char letter) {
        this.x = x;
        this.y = y;
        this.letter = letter;
    }

    public void draw(Graphics2D g) {
        if(isHovered()){
            g.setColor(Color.darkGray);
        } else {
            g.setColor(Color.LIGHT_GRAY);
        }
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        // draw string
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(String.valueOf(letter), x + width / 2 - metrics.stringWidth(String.valueOf(letter)) / 2, y + 35 + height / 2 - metrics.getHeight() / 2);
    }

    public boolean contains(int x, int y) {
        return(this.x <=x &&this.x+width >=x)&&(this.y <=y &&this.y +height >=y);
    }

    public void delete(){
        Hangman.getInstance().getAlphabetBlockManager().getLetterBlocks().remove(this);
    }
}
