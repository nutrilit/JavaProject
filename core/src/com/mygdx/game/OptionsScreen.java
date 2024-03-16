package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private String[] resolutions = {"800x600", "1024x768", "1280x720"}; // Przykładowe rozdzielczości
    private String[] resolutions2 = {"800", "600", "1024", "768", "1280", "720"};
    private int currentResolutionIndex = 0;
    private Stage stage;

    public OptionsScreen() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
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
            System.out.println("Zapisano rozdzielczość: " + resolutions2[currentResolutionIndex * 2] + "x" + resolutions2[currentResolutionIndex * 2 + 1]);

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

        float textX = screenWidth / 2; // Początkowe położenie tekstu X
        float textY = screenHeight * 0.9f; // Początkowe położenie tekstu Y

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "Current Resolution: " + screenWidth + "x" + screenHeight, screenWidth / 2, screenHeight * 0.8f, 0, Align.center, false);
        font.draw(batch, "Options Screen", textX, textY, 0, Align.center, false);
        font.draw(batch, "Press ENTER to go back to main menu", screenWidth / 2, screenHeight * 0.7f, 0, Align.center, false);
        font.draw(batch, "Press UP/DOWN to change resolution: " + resolutions[currentResolutionIndex], screenWidth / 2, screenHeight * 0.6f, 0, Align.center, false);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            GameManager.getInstance().gameMenu = true; // Powrót do menu głównego po naciśnięciu ENTER
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            cycleResolution(true);
            saveResolutionToFile(); // Zapisz nową rozdzielczość po zmianie
            updateResolution(Integer.parseInt(resolutions2[currentResolutionIndex * 2]), Integer.parseInt(resolutions2[currentResolutionIndex * 2 + 1]));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            cycleResolution(false);
            saveResolutionToFile(); // Zapisz nową rozdzielczość po zmianie
            updateResolution(Integer.parseInt(resolutions2[currentResolutionIndex * 2]), Integer.parseInt(resolutions2[currentResolutionIndex * 2 + 1]));
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