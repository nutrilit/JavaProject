package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;


public class MainMenu {
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private int selectedOptions = 1; // Default to 1 player
    private static final int number_of_options = 7;
    private Texture backgroundTexture;

    public MainMenu() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/myfont.fnt"), false);
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        backgroundTexture = new Texture("background.png"); // Adjust the path to your background image
    }
    public void opcje_menu() {
        Color lightBlueWithGlow = new Color(0.4f, 0.7f, 1, 0.5f);
        // Opcje menu
        for (int i = 1; i <= number_of_options; i++) {
            if (selectedOptions == i) {
                switch (i) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        font.setColor(Color.YELLOW);
                        break;
                }
            } else {
                font.setColor(lightBlueWithGlow);
            }
            // Wypisywanie opcji menu
            switch (i) {
                case 1:
                    font.draw(batch, "1 player", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * (8 - i) / 12f, 0, Align.center, false);
                    break;
                case 2:
                    font.draw(batch, "2 players", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * (8 - i) / 12f, 0, Align.center, false);
                    break;
                case 3:
                    font.draw(batch, "Options", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * (8 - i) / 12f, 0, Align.center, false);
                    break;
                case 4:
                    font.draw(batch, "Score", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * (8 - i) / 12f, 0, Align.center, false);
                    break;
                case 5:
                    font.draw(batch, "Quit", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * (8 - i) / 12f, 0, Align.center, false);
                    break;
                case 6:
                    font.draw(batch, "Help", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * (8 - i) / 12f, 0, Align.center, false);
                    break;
                case 7:
                    font.draw(batch, "Copyright", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * (8 - i) / 12f, 0, Align.center, false);
                    break;
            }
            font.setColor(Color.WHITE); // Domyślny kolor dla innych opcji
        }
    }
    public void obsluga_klaw(){
       //System.out.println(GameManager.getInstance().gameState);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selectedOptions) {
                case 1:
                case 2:
                    GameManager.getInstance().gameState= GameManager.GameState.STARTGAME;
                    GameManager.getInstance().startGame(selectedOptions);
                    break;
                case 3:
                    GameManager.getInstance().gameState = GameManager.GameState.OPTIONS;
                    break;
                case 4:
                    GameManager.getInstance().gameState = GameManager.GameState.SCORE;
                    break;
                case 5:
                    Gdx.app.exit(); // Zamknij aplikację, jeśli wybrano opcję Quit
                    break;
                case 6:
                    GameManager.getInstance().gameState = GameManager.GameState.HELP;
                    break;
                case 7:
                    GameManager.getInstance().gameState = GameManager.GameState.COPYRIGHT;
                    break;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOptions = (selectedOptions % number_of_options) + 1; // Przejście do kolejnej opcji, zachowując się cyklicznie
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOptions = ((selectedOptions - 2 + number_of_options) % number_of_options) + 1; // Przejście do poprzedniej opcji, zachowując się cyklicznie
        }
    }

    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(new TextureRegion(backgroundTexture), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //font.draw(batch, "Main Menu", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 9/10, 0, Align.center, false);
        //font.draw(batch, "Press ENTER to start the game", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 8/10, 0, Align.center, false);
        opcje_menu();
        batch.end();
        obsluga_klaw();

    }


    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
