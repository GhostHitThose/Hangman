package me.maxrenner.ux.managers;

import lombok.Getter;
import me.maxrenner.Hangman;
import me.maxrenner.gamefiles.LetterBlock;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

public class AlphabetBlockManager {
    @Getter private final ArrayList<Character> alphabet = new ArrayList<>(Arrays.asList(
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z'));

    @Getter private final ArrayList<LetterBlock> letterBlocks;

    public AlphabetBlockManager(int width, int height){
        letterBlocks = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                //400 440 480
                if(alphabet.size()-1 < i*9+j) continue;
                LetterBlock letterBlock = new LetterBlock(j*((width-(2*width/8))/9)+width/8, i*((height-(2*height/8))/9) + 2*height/3, (width-(2*width/8))/9, (height-(2*height/8))/9,
                        alphabet.get(i*9+j));
                letterBlocks.add(letterBlock);
            }
        }
    }

    public void drawBlocks(Graphics2D g) {

        g.setFont(new Font("Sans", Font.PLAIN, 38));
        try {
            for (LetterBlock letterBlock : letterBlocks) {
                letterBlock.draw(g);
            }
        } catch(ConcurrentModificationException ignored){}
    }

    public boolean contains(int x, int y){
        for(LetterBlock lb : letterBlocks){
            if(lb.contains(x,y))
                return true;
        }

        return false;
    }

    public LetterBlock getBlockByCords(int x, int y){
        for(LetterBlock lb : letterBlocks){
            if(lb.contains(x,y))
                return lb;
        }

        return null;
    }
}
