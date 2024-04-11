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
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class OptionsScreen {
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    private String[] resolutions = {"800x600", "1024x768", "1280x720", "1900x1080"}; // Przykładowe rozdzielczości
    private String[] resolutions2 = {"800", "600", "1024", "768", "1280", "720", "1920", "1080"};
    private String[] scale = {"3","4","4","5"};
    private int currentResolutionIndex = 0;
    private Stage stage;
    private Texture backgroundTexture,mutedIcon, not;

    public OptionsScreen() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/myfont.fnt"), false);
        font.setColor(Color.WHITE);
        font.getData().setScale(1);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        backgroundTexture = new Texture("optionsTlo.png");
        mutedIcon = new Texture("mutedicon.png");
    }
    public void updateResolution(int width, int height) {
        Gdx.graphics.setWindowedMode(width, height);
    }
    private void saveResolutionToFile() {
        try {
            String filePath = "C:\\JavaProject\\core\\src\\com\\mygdx\\game\\Options.txt";
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(resolutions2[currentResolutionIndex * 2] + "\n" + resolutions2[currentResolutionIndex * 2 + 1]);
            bufferedWriter.write("\n"+scale[currentResolutionIndex]);
            System.out.println("Zapisano rozdzielczość: " + resolutions2[currentResolutionIndex * 2] + "x" + resolutions2[currentResolutionIndex * 2 + 1]);
            System.out.println("Zapisano skale: " + currentResolutionIndex );
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisu rozdzielczości do pliku.");
            e.printStackTrace();
        }
    }

    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(new TextureRegion(backgroundTexture), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Oblicz nowe położenie tekstu na podstawie aktualnych rozmiarów ekranu
        float textX = screenWidth / 2;
        float textY = screenHeight * 0.9f;

        font.draw(batch, "Press UP/DOWN to change resolution", screenWidth / 2, screenHeight * 0.9f, 0, Align.center, false);
        font.draw(batch, "Current Resolution: " + resolutions[currentResolutionIndex], screenWidth / 2, screenHeight * 0.8f, 0, Align.center, false);
        batch.draw(new TextureRegion(mutedIcon), screenWidth * 2 / 5 - 70 , screenHeight *0.6f, 70, 70);
        font.draw(batch, "Press M to muted music in menu ", screenWidth * 2 / 5, screenHeight * 0.66f, 0, Align.left, false);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameManager.getInstance().gameState = GameManager.GameState.MAIN_MENU;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            cycleResolution(true);
            saveResolutionToFile();
            //updateResolution(Integer.parseInt(resolutions2[currentResolutionIndex * 2]), Integer.parseInt(resolutions2[currentResolutionIndex * 2 + 1]));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            cycleResolution(false);
            saveResolutionToFile();
            //updateResolution(Integer.parseInt(resolutions2[currentResolutionIndex * 2]), Integer.parseInt(resolutions2[currentResolutionIndex * 2 + 1]));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            if (GameManager.getInstance().music.isPlaying()) {
                GameManager.getInstance().music.pause(); // Wycisz muzykę gry, jeśli jest odtwarzana
                GameManager.getInstance().musicMuted_in_lobby = true; // Ustaw flagę wyciszenia muzyki
            } else {
                GameManager.getInstance().music.play(); // Wznów odtwarzanie muzyki gry, jeśli jest wyciszona
                GameManager.getInstance().musicMuted_in_lobby = false; // Wyłącz flagę wyciszenia muzyki
            }
        }


    }



    private void cycleResolution(boolean increment) {
        if (increment) {
            currentResolutionIndex = (currentResolutionIndex + 1) % resolutions.length;
        } else {
            currentResolutionIndex = (currentResolutionIndex - 1 + resolutions.length) % resolutions.length;
        }
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}