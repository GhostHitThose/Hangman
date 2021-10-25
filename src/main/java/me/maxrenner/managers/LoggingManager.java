package me.maxrenner.managers;

public class LoggingManager {
    public void info(String msg){
        System.out.println("[Hangman] " + msg);
    }

    public void error(String msg){
        System.err.println("[Hangman] " + msg);
    }
}
