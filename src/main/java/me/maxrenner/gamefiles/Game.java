package me.maxrenner.gamefiles;

import lombok.Setter;
import me.maxrenner.Hangman;
import me.maxrenner.enums.GameState;
import me.maxrenner.managers.FileManager;
import me.maxrenner.utils.CenterWord;
import me.maxrenner.ux.buttons.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends Canvas implements Runnable {

    private final Hangman hangman;

    public Game(Hangman hangman){
        this.hangman = hangman;
        addMouseListener(hangman.getMouseInputManager().createMouseAdapter());
        addMouseMotionListener(hangman.getMouseInputManager().createMouseMotionAdapter());
    }

    public void onEnable(){

        image = new BufferedImage(getPreferredSize().width, getPreferredSize().height, BufferedImage.TYPE_INT_RGB);

        hangman.getLogger().info("Started game thread.");
        hangman.getLogger().info("Starting main game loop...");
        gameState = GameState.TITLE;
        running = true;

        hangman.getUxButtonsManager().getButtons().add(new PauseButton(getPreferredSize().width - getPreferredSize().width/25,0,getPreferredSize().width/25,getPreferredSize().width/25,Color.GRAY, Color.DARK_GRAY,this));
        hangman.getUxButtonsManager().getButtons().add(new ExitButton(getPreferredSize().width/4, getPreferredSize().height-getPreferredSize().height/2, getPreferredSize().width/2, getPreferredSize().height/15, Color.GRAY, Color.DARK_GRAY, this));
        hangman.getUxButtonsManager().getButtons().add(new CloseButton(getPreferredSize().width - getPreferredSize().width/25,0,getPreferredSize().width/25,getPreferredSize().width/25,Color.GRAY, Color.DARK_GRAY,this));
        hangman.getUxButtonsManager().getButtons().add(new PlayAgainButton(getPreferredSize().width/4, getPreferredSize().height-getPreferredSize().height/2 - getPreferredSize().height/15, getPreferredSize().width/2, getPreferredSize().height/15, Color.GRAY, Color.DARK_GRAY, this));
        hangman.getUxButtonsManager().getButtons().add(new PlayButton(getPreferredSize().width/4, getPreferredSize().height-getPreferredSize().height/2, getPreferredSize().width/2, getPreferredSize().height/15, Color.GRAY, Color.DARK_GRAY, this));
    }

    @Override
    public void run() {
        onEnable();

        boolean logMsg = true;

        long lastTime = System.nanoTime(); // gets the current time in nano time
        double FPS = 60.0, deltaTime = 0, timeBetweenFrame = 1000000000/FPS;
        /*
        DeltaTime = a cumulative amount of time over each iteration - reset to 0 when delta = 1 and then run a game update
        TimeBetweenFrame = the amount of time in between each frame to make whatever set FPS
        Rendering is done every iteration while updates are done per FPS
         */

        while(running){
            if(logMsg) {
                hangman.getLogger().info("Started main game loop.");
                logMsg = false;
            }

            long now = System.nanoTime();
            deltaTime += (now-lastTime)/timeBetweenFrame;
            lastTime = now;

            while(deltaTime >= 1){
                update();
                deltaTime--;
            }

            render();
        }

        stop();
    }

    private void stop(){
        hangman.getLogger().error("Exiting game with error code 0");
        System.exit(0);
    }

    private String word;
    private ArrayList<Character> lettersChosen;
    private ArrayList<Character> wordLetterList;
    @Setter private GameState gameState;
    private BufferedImage image;
    private boolean running;

    private void update() {
        // do hangman logic

        // if new word = true                    done
        //    get new word                       done
        // output _ for each char in the word    done
        /// get user input
        // if user input == a letter in the word show all letters
        // new word
        // new game
        // end game

        switch (gameState){
            case NEW_GAME:
                ArrayList<String> words = getWordList();
                word = getRandomWord(words);
                guesses = 0;
                wordLetterList = new ArrayList<>();
                lettersChosen = new ArrayList<>();
                hangman.getAlphabetBlockManager().getLetterBlocks().forEach(b -> b.setVisible(true));
                for(char l : word.toCharArray()) wordLetterList.add(l);
                gameState = GameState.PLAYING;
                break;
            case END:
                // ask if they would like to play again
                running = false;
                gameState = GameState.NEW_GAME;
        }

        // check user input  done

        // have a-z letters displayed - done - OR have an option to open a textbox that allows u to type multiple letters

        // if user inputed into the textbox && input.length() > 1 && input != word YOU LOSE
        // if user input == a letter in the word show all letters of that letter
    }

    private void render(){
        // render graphics

        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        handleBufferedImage(g, image);

        // Scene Control
        switch(gameState){
            case TITLE:
                Font titleFont = new Font("Serif", Font.BOLD, 50);
                CenterWord.drawCenteredWordHorizontal(g, "Hangman", titleFont, Color.BLACK, 0, getPreferredSize().height/4, getPreferredSize().width);
                CenterWord.drawCenteredWordHorizontal(g, "\t\tBy Max Renner", new Font("Serif", Font.BOLD, 40), Color.BLACK, 0, getPreferredSize().height/4+2*g.getFontMetrics().getAscent(), getPreferredSize().width);
                hangman.getUxButtonsManager().getButtons().forEach(b -> b.setVisible(b instanceof PlayButton));
                break;
            case PLAYING:
                drawWord(g);
                hangman.getAlphabetBlockManager().drawBlocks(g);
                hangman.getUxButtonsManager().getButtons().forEach(b ->{
                    b.setVisible(b instanceof PauseButton);
                });

                g.setFont(new Font("Sans Serif", Font.PLAIN, 40));
                g.setColor(Color.BLACK);
                g.drawString("Guesses Left: " + (10-guesses), 0, g.getFontMetrics().getAscent());

                break;
            case PAUSED:
                hangman.getAlphabetBlockManager().drawBlocks(g);
                g.setColor(new Color(0,0,0, 0.8F));
                g.fillRect(0,0,getPreferredSize().width,getPreferredSize().height);
                hangman.getUxButtonsManager().getButtons().forEach(b -> b.setVisible(b instanceof ExitButton || b instanceof CloseButton));
                break;
            case WIN:
                CenterWord.drawCenteredWordHorizontal(g,"You Win!", new Font("Serif", Font.PLAIN, 30), Color.BLACK, 0, getPreferredSize().height/4, getPreferredSize().width);
                hangman.getUxButtonsManager().getButtons().forEach(b -> b.setVisible(b instanceof PlayAgainButton || b instanceof ExitButton));
                break;
            case LOSS:
                CenterWord.drawCenteredWordHorizontal(g,"You Lose!", new Font("Serif", Font.PLAIN, 30), Color.BLACK, 0, getPreferredSize().height/4, getPreferredSize().width);
                CenterWord.drawCenteredWordHorizontal(g,"Your word was " + word, new Font("Serif", Font.PLAIN, 30), Color.BLACK, 0, getPreferredSize().height/4+g.getFontMetrics().getAscent(), getPreferredSize().width);
                hangman.getUxButtonsManager().getButtons().forEach(b -> b.setVisible(b instanceof PlayAgainButton || b instanceof ExitButton));
                break;
        }
        hangman.getUxButtonsManager().buildAll(g);

        bs.show();
    }

    private ArrayList<String> getWordList(){
        return FileManager.readFile();
    }

    private String getRandomWord(ArrayList<String> words){
        return words.get(ThreadLocalRandom.current().nextInt(words.size()));
    }

    private int guesses;

    public void mouseClick(MouseEvent e){
        hangman.getUxButtonsManager().checkClicked(e.getX(), e.getY());

        if((hangman.getAlphabetBlockManager().contains(e.getX(), e.getY()))) {

            LetterBlock letterBlock = hangman.getAlphabetBlockManager().getBlockByCords(e.getX(), e.getY());
            char letter = letterBlock.getLetter();

            if (!lettersChosen.contains(letter) && letterBlock.isVisible()) {

                boolean correct = false;
                for (char l : wordLetterList) {
                    if (l == letter) {
                        // they chose a CORRECT LETTER
                        lettersChosen.add(letter);
                        letterBlock.destroy();
                        correct = true;

                        if (lettersChosen.containsAll(wordLetterList)) {
                            gameState = GameState.WIN;
                        }
                    }
                }
                if(!correct)
                    guesses++;
                    if(guesses >= 10)
                        gameState = GameState.LOSS;
            }

            for (char l : hangman.getAlphabetBlockManager().getAlphabet()) {
                if (l == letter) {
                    lettersChosen.add(letter);
                    letterBlock.destroy();
                }
            }
        }
    }

    public void mouseMove(MouseEvent e) {

        hangman.getUxButtonsManager().checkHovered(e.getX(), e.getY());

        if (!(hangman.getAlphabetBlockManager().contains(e.getX(), e.getY()))) {
            hangman.getAlphabetBlockManager().getLetterBlocks().forEach(b -> {
                if (b.isHovered()) {
                    b.setHovered(false);
                }
            });
        } else {

            LetterBlock block = hangman.getAlphabetBlockManager().getBlockByCords(e.getX(), e.getY());
            hangman.getAlphabetBlockManager().getLetterBlocks().forEach(b -> {
                if (b != block) {
                    b.setHovered(false);
                }
            });

            block.setHovered(true);
        }


    }

    private void handleBufferedImage(Graphics2D g, BufferedImage image){
        image.getGraphics().setColor(Color.WHITE);
        image.getGraphics().fillRect(0,0,image.getWidth(),image.getHeight());
        g.drawImage(image,0, 0, null);
    }

    private void drawWord(Graphics2D g){
        Font font = new Font("Serif", Font.PLAIN, 75);
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.1);
        StringBuilder builder = new StringBuilder();
        for(char letter : word.toCharArray()){
            if(lettersChosen.contains(letter)){
                builder.append(letter);
            } else {
                builder.append("_");
            }
        }

        CenterWord.drawCenteredWord(g, builder.toString(), font.deriveFont(attributes), Color.BLACK, 0,0,getPreferredSize().width, getPreferredSize().height);
    }
}
