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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class GameOver {
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    private int currentResolutionIndex = 0;
    private TextField nameField;
    private Stage stage;
    private Texture backgroundTexture;
    GameOver()
    {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/myfont.fnt"), false);
        font.setColor(Color.WHITE);
        String filepath = "C:\\JavaProject\\core\\src\\com\\mygdx\\game\\Options.txt";
        font.getData().setScale(0.3f*BufferedReader.getInstance(filepath).scale);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        backgroundTexture = new Texture("optionsTlo.png");

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;

        TextureRegionDrawable textFieldBackground = new TextureRegionDrawable(new TextureRegion(new Texture("TextFieldBackground.png")));
        textFieldStyle.background = textFieldBackground;

/*        TextureRegionDrawable cursor = new TextureRegionDrawable(new TextureRegion(new Texture("textFieldSelection.png")));
        textFieldStyle.cursor = cursor;*/

        TextureRegionDrawable selection = new TextureRegionDrawable(new TextureRegion(new Texture("yellowTextFieldBackground.png")));
        textFieldStyle.selection = selection;

        nameField = new TextField("", textFieldStyle);
        nameField.setAlignment(Align.center);
        nameField.setSize(50f*BufferedReader.getInstance(filepath).scale, 15f*BufferedReader.getInstance(filepath).scale); // Ustawienie szerokości i wysokości pola tekstowego
        nameField.setPosition((Gdx.graphics.getWidth() - nameField.getWidth()) / 2, Gdx.graphics.getHeight() / 2);
        nameField.setMaxLength(5);
        stage.addActor(nameField);
    }
    public void obsluga_klaw(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameManager.getInstance().gameState = GameManager.GameState.MAIN_MENU;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            GameManager.getInstance().gameState = GameManager.GameState.STARTGAME;
            String playerName = nameField.getText();
            zapiszNazweDoPliku(playerName);
        }

    }
/*    public void zapiszWynik(int wynik) {
        String filePath = "C:\\JavaProject\\core\\src\\com\\mygdx\\game\\wynik.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(Integer.toString(wynik));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
private void zapiszNazweDoPliku(String playerName) {
    String filePath = "C:\\JavaProject\\core\\src\\com\\mygdx\\game\\nazwa_gracza.txt";
    try {
        int linesCount = countLines(filePath);
        if (linesCount >= 15) {
            removeFirstLine(filePath);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(playerName + "    " + GameManager.getInstance().gameScreen.enemies.score);
        writer.close();

        GameManager.getInstance().gameScreen.enemies.score = 0;
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private int countLines(String filePath) throws IOException {
        java.io.BufferedReader reader = new java.io.BufferedReader(new FileReader(filePath));
        int linesCount = 0;
        while (reader.readLine() != null) {
            linesCount++;
        }
        reader.close();
        return linesCount;
    }

    private void removeFirstLine(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);

        lines.remove(0);

        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
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
        batch.draw(new TextureRegion(backgroundTexture), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, "Game Over", screenWidth / 2, screenHeight * 0.8f, 0, Align.center, false);
        font.draw(batch, "Click on the text field to enter your nickname", screenWidth / 2, screenHeight * 0.7f, 0, Align.center, false);

        batch.end();
        obsluga_klaw();
        stage.draw();
    }
}
