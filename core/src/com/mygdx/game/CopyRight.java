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

public class CopyRight {
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    private int currentResolutionIndex = 0;
    private Stage stage;
    private Texture backgroundTexture,copyrightIcon;

    public CopyRight() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/myfont.fnt"), false);
        font.setColor(Color.WHITE);
        String filepath = "C:\\JavaProject\\core\\src\\com\\mygdx\\game\\Options.txt";
        font.getData().setScale(0.25f*BufferedReader.getInstance(filepath).scale);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        backgroundTexture = new Texture("optionsTlo.png");
        copyrightIcon = new Texture("copyright.png");
    }
    public void obsluga_klaw(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameManager.getInstance().gameState = GameManager.GameState.MAIN_MENU;
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
        batch.draw(new TextureRegion(backgroundTexture), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //batch.draw(new TextureRegion(copyrightIcon), screenWidth*1/4, screenHeight *0.80f, 70, 70);
        font.draw(batch, "© 2024 MKP Corporation. All rights reserved.", screenWidth /2, screenHeight * 0.9f, 0, Align.center, false);
        font.draw(batch, "Mariusz S", screenWidth / 2, screenHeight * 0.80f, 0, Align.center, false);
        font.draw(batch, "Patryk W", screenWidth / 2, screenHeight * 0.75f, 0, Align.center, false);
        font.draw(batch, "Jakub J", screenWidth / 2, screenHeight * 0.70f, 0, Align.center, false);

        batch.end();
        obsluga_klaw();
    }




    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}