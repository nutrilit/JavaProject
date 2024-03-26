package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Align;

public class GameManager {
    private static GameManager instance;

    private MainMenu mainMenu;
    private OptionsScreen optionsScreen; // Dodane pole przechowujące ekran opcji

    private  Score score;
    private Help help;
    private CopyRight copyRight;
    private boolean gameStarted;
    public boolean gameMenu;
    private int numberOfPlayers;
    GameScreen gameScreen;
    private Music music;
    private Music gameMusic;
    public enum GameState {
        MAIN_MENU, OPTIONS, SCORE, QUIT, HELP, COPYRIGHT,STARTGAME,PAUSE;
    }
    public GameState gameState;


    private GameManager() {
        mainMenu = new MainMenu();
        optionsScreen = new OptionsScreen();
        help = new Help();
        copyRight = new CopyRight();
        score = new Score();
        gameStarted = false;
        gameMenu = true; // Początkowo ustawiamy na ekran menu
        gameState = GameState.MAIN_MENU;
        gameScreen = new GameScreen();
        //music = Gdx.audio.newMusic(Gdx.files.internal("music/Familiada.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music/Brunetki.mp3"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/WBA Free Track - Hackers.mp3"));
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void render() {
/*        if (gameMenu) {
            mainMenu.render();
        } else if (gameStarted) {
            // Renderuj grę tutaj
        } else {
            optionsScreen.render(); // Renderuj ekran opcji, gdy gameMenu jest false i gameStarted jest false
        }*/

        switch (gameState) {
            case MAIN_MENU:
                mainMenu.render();
                gameMusic.stop();
                music.play();
                break;
            case OPTIONS:
                optionsScreen.render();
                break;
            case SCORE:
                score.render();
                break;
            case QUIT:
                break;
            case HELP:
                help.render();
                break;
            case COPYRIGHT:
                copyRight.render();
                break;
            case STARTGAME:
                gameMusic.play();
                music.stop();
                gameScreen.render();
                gameScreen.obsluga_klaw();
                break;
        }
    }

    public void dispose() {
        mainMenu.dispose();
        optionsScreen.dispose(); // Dispose ekranu opcji
        music.dispose();
    }

    public void startGame(int numberOfPlayers) {
        //gameStarted = true;
        //gameMenu = false; // Po rozpoczęciu gry, nie chcemy być w menu
        gameState = GameState.STARTGAME;
        this.numberOfPlayers = numberOfPlayers;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

}

