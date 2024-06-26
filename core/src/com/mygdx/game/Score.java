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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Score {
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    private Stage stage;
    private Texture backgroundTexture;

    public Score() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/myfont.fnt"), false);
        font.setColor(Color.WHITE);
        String filepath = "C:\\JavaProject\\core\\src\\com\\mygdx\\game\\Options.txt";
        font.getData().setScale(0.3f* com.mygdx.game.BufferedReader.getInstance(filepath).scale);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        backgroundTexture = new Texture("optionsTlo.png");
    }

    public void obsluga_klaw(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameManager.getInstance().gameState = GameManager.GameState.MAIN_MENU;
        }
    }

    public List<PlayerResult> odczytajWynikiZPliku() {
        List<PlayerResult> wyniki = new ArrayList<>();
        String filePath = "C:\\JavaProject\\core\\src\\com\\mygdx\\game\\nazwa_gracza.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    String playerName = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    wyniki.add(new PlayerResult(playerName, score));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        Collections.sort(wyniki); // Sortowanie listy wyników
        return wyniki;
    }


    public void render() {
        List<PlayerResult> wyniki = odczytajWynikiZPliku();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        float textX = screenWidth / 2; // Początkowe położenie tekstu X
        float textY = screenHeight * 0.95f; // Początkowe położenie tekstu Y

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(new TextureRegion(backgroundTexture), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(batch, "Scoreboard: ", screenWidth / 2, screenHeight * 0.95f, 0, Align.center, false);
        float yPosition = screenHeight * 0.85f;
        int numeracja = 1;
        for (PlayerResult wynik : wyniki) {
            font.draw(batch, numeracja + ". " + wynik.getPlayerName() + "    " + wynik.getScore(), screenWidth /2, yPosition, 0, Align.center, false);
            yPosition -= font.getLineHeight();
            numeracja++;
        }
        batch.end();
        obsluga_klaw();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
