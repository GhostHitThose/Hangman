package me.maxrenner.gamefiles;

import lombok.Setter;
import me.maxrenner.Hangman;
import me.maxrenner.enums.GameState;
import me.maxrenner.managers.FileManager;
import me.maxrenner.utils.CenterWord;
import me.maxrenner.ux.buttons.PauseButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
        gameState = GameState.NEW_GAME;
        lettersChosen = new ArrayList<>();
        word = "";
        running = true;

        hangman.getUxButtonsManager().getButtons().add(new PauseButton(600-35,0,30,30,Color.GRAY, Color.DARK_GRAY, true, this));
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
                wordLetterList = new ArrayList<>();
                for(char l : word.toCharArray()) wordLetterList.add(l);

                gameState = GameState.START;
                break;
            case START:
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

        // Game rendering
        drawWord(g);

        hangman.getAlphabetBlockManager().drawBlocks(g);
        hangman.getUxButtonsManager().buildAll(g);

        bs.show();
    }

    private ArrayList<String> getWordList(){
        return FileManager.readFile(hangman.getWordFile());
    }

    private String getRandomWord(ArrayList<String> words){
        return words.get(ThreadLocalRandom.current().nextInt(words.size()));
    }

    public void mouseClick(MouseEvent e){
        if(gameState == GameState.PLAYING) {
            hangman.getUxButtonsManager().checkClicked(e.getX(), e.getY());

            if((hangman.getAlphabetBlockManager().contains(e.getX(), e.getY()))) {

                LetterBlock letterBlock = hangman.getAlphabetBlockManager().getBlockByCords(e.getX(), e.getY());
                char letter = letterBlock.getLetter();

                if (!lettersChosen.contains(letter)) {

                    for (char l : wordLetterList) {
                        if (l == letter) {
                            // they chose a CORRECT LETTER
                            lettersChosen.add(letter);
                            letterBlock.delete();

                            if (lettersChosen.containsAll(wordLetterList)) {
                                gameState = GameState.END;
                            }
                        }
                    }
                }

                for (char l : hangman.getAlphabetBlockManager().getAlphabet()) {
                    if (l == letter) {
                        lettersChosen.add(letter);
                        letterBlock.delete();
                    }
                }
            }
        }
    }

    public void mouseMove(MouseEvent e) {
        if(gameState == GameState.PLAYING) {

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

        CenterWord.drawCenteredWord(g, builder.toString(), font.deriveFont(attributes), Color.BLACK, (Double) attributes.get(TextAttribute.TRACKING), 0,0,getPreferredSize().width, getPreferredSize().height);
    }
}
