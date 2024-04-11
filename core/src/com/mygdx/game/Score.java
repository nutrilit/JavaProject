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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
public class Score {
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    private int currentResolutionIndex = 0;
    private Stage stage;
    private Texture backgroundTexture;

    public Score() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        backgroundTexture = new Texture("optionsTlo.png");
    }
    public void obsluga_klaw(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameManager.getInstance().gameState = GameManager.GameState.MAIN_MENU;
        }
    }
/*    public String odczytajWynikZPliku() {
        String wynikString = "0";
        String filePath = "wynik.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            wynikString = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wynikString;
    }*/
    public void render() {
        //String wynikString = odczytajWynikZPliku();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        float textX = screenWidth / 2; // Początkowe położenie tekstu X
        float textY = screenHeight * 0.9f; // Początkowe położenie tekstu Y

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(new TextureRegion(backgroundTexture), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        font.draw(batch, "Score: ", screenWidth / 2, screenHeight * 0.8f, 0, Align.left, false);
        //font.draw(batch, wynikString, screenWidth / 2, screenHeight * 0.7f, 0, Align.left, false);
        batch.end();
        obsluga_klaw();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}