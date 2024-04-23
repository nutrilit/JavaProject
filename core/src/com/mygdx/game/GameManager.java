package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;

public class GameManager {
    private static GameManager instance;

    private MainMenu mainMenu;
    private OptionsScreen optionsScreen; // Dodane pole przechowujące ekran opcji

    private  Score score;
    private Player player;
    private Help help;
    private Pause pause;
    private GameOver gameOver;
    private CopyRight copyRight;
    private boolean gameStarted;
    public boolean gameMenu;
    private int numberOfPlayers;
    GameScreen gameScreen;
    public Music music;
    public Music gameMusic;
    public boolean musicMuted_in_lobby_and_game = false;
    public enum GameState {
        MAIN_MENU, OPTIONS, SCORE, QUIT, HELP, COPYRIGHT,STARTGAME,PAUSE,GAMEOVER;
    }
    public GameState gameState;


    private GameManager() {
        mainMenu = new MainMenu();
        optionsScreen = new OptionsScreen();
        help = new Help();
        copyRight = new CopyRight();
        score = new Score();
        pause = new Pause();
        gameOver = new GameOver();
        gameStarted = false;
        gameMenu = true; // Początkowo ustawiamy na ekran menu
        gameState = GameState.MAIN_MENU;
        gameScreen = new GameScreen();
        //music = Gdx.audio.newMusic(Gdx.files.internal("music/Familiada.mp3"));
        //music = Gdx.audio.newMusic(Gdx.files.internal("music/Brunetki.mp3"));
        //music = Gdx.audio.newMusic(Gdx.files.internal("music/cygan.mp3"));
        //music = Gdx.audio.newMusic(Gdx.files.internal("music/jessica.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music/mainMusic.mp3"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/WBA Free Track - Hackers.mp3"));
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    public void wyciszenie(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ALT_RIGHT)) {
            musicMuted_in_lobby_and_game = !musicMuted_in_lobby_and_game;
        }
        if (musicMuted_in_lobby_and_game) {
            gameMusic.setVolume(0);
            music.setVolume(0);
            GameManager.getInstance().gameScreen.player.shoot.setVolume(0);
            GameManager.getInstance().gameScreen.player2.shoot.setVolume(0);
            GameManager.getInstance().gameScreen.enemies.explosion.setVolume(0);
            GameManager.getInstance().gameScreen.enemies.dyingSound.setVolume(0);
        } else {
            gameMusic.setVolume(1);
            music.setVolume(1);
            GameManager.getInstance().gameScreen.player.shoot.setVolume(1);
            GameManager.getInstance().gameScreen.player2.shoot.setVolume(1);
            GameManager.getInstance().gameScreen.enemies.explosion.setVolume(1);
            GameManager.getInstance().gameScreen.enemies.dyingSound.setVolume(1);
        }
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
                music.play();
                gameMusic.stop();
                wyciszenie();
                break;
            case OPTIONS:
                optionsScreen.render();
                wyciszenie();
                break;
            case SCORE:
                score.render();
                wyciszenie();
                break;
            case QUIT:
                break;
            case HELP:
                help.render();
                wyciszenie();
                break;
            case COPYRIGHT:
                copyRight.render();
                wyciszenie();
                break;
            case STARTGAME:
                gameMusic.play();
                music.stop();
                gameScreen.render();
                gameScreen.obsluga_klaw();
                wyciszenie();
                break;
            case PAUSE:
                pause.render();
                wyciszenie();
                break;
            case GAMEOVER:
                gameOver.render();
                gameScreen.player.removeAllBullets();
                gameScreen.enemies.removeallAmmo();
                wyciszenie();
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