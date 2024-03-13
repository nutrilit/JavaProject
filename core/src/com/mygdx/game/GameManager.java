package com.mygdx.game;

public class GameManager {
    private static GameManager instance;
    private MainMenu mainMenu;
    private OptionsScreen optionsScreen; // Dodane pole przechowujące ekran opcji
    private boolean gameStarted;
    public boolean gameMenu;
    private int numberOfPlayers;

    private GameManager() {
        mainMenu = new MainMenu();
        optionsScreen = new OptionsScreen(); // Inicjalizacja ekranu opcji
        gameStarted = false;
        gameMenu = true; // Początkowo ustawiamy na ekran menu
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void render() {
        if (gameMenu) {
            mainMenu.render();
        } else if (gameStarted) {
            // Renderuj grę tutaj
        } else {
            optionsScreen.render(); // Renderuj ekran opcji, gdy gameMenu jest false i gameStarted jest false
        }
    }

    public void dispose() {
        mainMenu.dispose();
        optionsScreen.dispose(); // Dispose ekranu opcji
    }

    public void startGame(int numberOfPlayers) {
        gameStarted = true;
        gameMenu = false; // Po rozpoczęciu gry, nie chcemy być w menu
        this.numberOfPlayers = numberOfPlayers;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    // Dodane metody do obsługi przejścia do ekranu opcji
    public void goToOptionsScreen() {
        gameMenu = false; // Ustawienie gameMenu na false, aby przeskoczyć do ekranu opcji
    }

    public void goToMainMenu() {
        gameMenu = true; // Ustawienie gameMenu na true, aby powrócić do menu głównego
    }
}