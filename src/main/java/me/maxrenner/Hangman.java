package me.maxrenner;

/*

A player will guess a secret word letter-by-letter.
A secret word will be picked at random from a list of words. - wordbank.yml
There will be a maximum number of turns a player has to guess the word. - 2x the length of the word
Correct guesses do not count towards the player's maximum number of turns; only incorrect guesses. - dont discount lives based on a second correct guess. just reprompt
The incorrect guesses should be displayed and subsequent guesses of these letters should not count against the player.
The secret word should be masked with underscores (space these out so it is obvious how many letters there are). Correctly guessed letters should be unmasked.
The main game loop will play the game until the user correctly guesses all of the letters or they run out of attempts.
JavaDocs & well-commented code are required.
To expand upon your Hangman game, try the following (extra credit will be awarded based on difficulty):

Ask the player if they want to play again after the game is over.
Add phrases to the word bank (spaces and symbols should not have to be guessed and should be visible from the start).
Create an external document that will contain all of the words and phrases used for the word bank.
Allow the player to guess the entire word or phrase at one time. However, if they guess wrong, they lose.
Allow the user to add words/phrases to the word bank.
Anything else you would like to add to your game.


*****
Make frame
Put _ at string length
Put a hanger where hangman hangs -- add a body part each time a false letter was guessed
Put a word bank at the bottom of the screen.

 */

import lombok.Getter;
import me.maxrenner.gamefiles.Game;
import me.maxrenner.gamefiles.managers.MouseInputManager;
import me.maxrenner.managers.FileManager;
import me.maxrenner.managers.LoggingManager;
import me.maxrenner.managers.WindowManager;
import me.maxrenner.ux.managers.AlphabetBlockManager;
import me.maxrenner.ux.managers.UXButtonsManager;

public class Hangman {

    @Getter private static Hangman instance;
    @Getter private final Game game;
    @Getter private final LoggingManager logger;
    @Getter private final WindowManager windowManager;
    @Getter private final MouseInputManager mouseInputManager;
    @Getter private final AlphabetBlockManager alphabetBlockManager;
    @Getter private final UXButtonsManager uxButtonsManager;

    public Hangman(){
        instance = this;

        loadDefaultWordFile();

        mouseInputManager = new MouseInputManager();
        logger = new LoggingManager();
        windowManager = new WindowManager();
        game = new Game(this);
        uxButtonsManager = new UXButtonsManager();

        windowManager.build("Hangman - Max Renner", 1280,720, game);

        alphabetBlockManager = new AlphabetBlockManager(game.getPreferredSize().width, game.getPreferredSize().height);

        logger.info("Starting game thread...");
        Thread thread = new Thread(game);
        thread.start();
    }

    private void loadDefaultWordFile(){
        FileManager.loadFile(this.getClass().getResourceAsStream("/wordbank.yml"));
    }

    public static void main(String[] args){
        new Hangman();
    }
}
