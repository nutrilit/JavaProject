package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;

public class Help {
    private SpriteBatch batch;
    private BitmapFont font,font2;
    private OrthographicCamera camera;

    private Texture backgroundTexture;
    Texture En0, En1,En2,En3,amm1,amm2,amm3;
    public Sprite sprite;
    String filepath = "C:\\JavaProject\\core\\src\\com\\mygdx\\game\\Options.txt";
    public Help() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/myfont.fnt"), false);
        font.setColor(Color.WHITE);
        font.getData().setScale(0.1f*BufferedReader.getInstance(filepath).scale);
        font2 = new BitmapFont(Gdx.files.internal("font/myfont.fnt"), false);
        font2.setColor(Color.WHITE);
        font.getData().setScale(0.2f*BufferedReader.getInstance(filepath).scale);
        font2.getData().setScale(0.5f*BufferedReader.getInstance(filepath).scale);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        backgroundTexture = new Texture("optionsTlo.png");
        En0 = new Texture("alien.png");
        En1 = new Texture("reaver.png");
        En2 = new Texture("xenomorphv1.0.png");
        En3 = new  Texture("beholder.png");
        amm1 = new Texture("bullet1.png");
        amm2 = new Texture("pierce-shot.png");
        amm3 = new Texture("cluster-bullet.png");
    }

    public void obsluga_klaw() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameManager.getInstance().gameState = GameManager.GameState.MAIN_MENU;
        }
    }

    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float textY = screenHeight;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(new TextureRegion(backgroundTexture), 0, 0, screenWidth, screenHeight);
        font.getData().setLineHeight(BufferedReader.getInstance(filepath).scale); // Zwiększenie odstępu między liniami tekstu
        font2.getData().setLineHeight(BufferedReader.getInstance(filepath).scale); // Zwiększenie odstępu między liniami tekstu
        // font.draw(batch, "Help: ", screenWidth / 2, screenHeight * 0.8f, 0, Align.left, false);

        float LeftText = screenWidth * 1 / 4;
        Color lightBlueWithGlow = new Color(0.4f, 0.7f, 1, 0.5f);
        font2.setColor(lightBlueWithGlow);
        font2.draw(batch, "Controls", screenWidth / 2, screenHeight * 0.95f, 0, Align.center, false);

        font.setColor(Color.YELLOW);
        font.draw(batch, "For player one", LeftText, screenHeight * 0.90f, 0, Align.center, false);
        font.setColor(Color.WHITE);
        font.draw(batch, "Left: 'W'", LeftText, screenHeight *0.86f, 0, Align.center, false);
        font.draw(batch, "Right: 'D'", LeftText, screenHeight *0.82f, 0, Align.center, false);
        font.draw(batch, "Shoot: 'SPACE'", LeftText, screenHeight *0.78f, 0, Align.center, false);
        font.draw(batch, "Change bullet for player 1: 'Q'", LeftText, screenHeight *0.74f, 0, Align.center, false);

        float rightTextX = screenWidth * 3 / 4;
        font.setColor(Color.YELLOW);
        font.draw(batch, "For player two", rightTextX, screenHeight * 0.90f, 0, Align.center, false);
        font.setColor(Color.WHITE);
        font.draw(batch, "Left: 'LEFT'", rightTextX, screenHeight *0.86f, 0, Align.center, false);
        font.draw(batch, "Right: 'RIGHT'", rightTextX, screenHeight *0.82f, 0, Align.center, false);
        font.draw(batch, "Shoot: 'SHIFT RIGHT'", rightTextX, screenHeight *0.78f, 0, Align.center, false);
        font.draw(batch, "Change bullet for player 2: 'P'", rightTextX, screenHeight *0.74f, 0, Align.center, false);

        font.draw(batch, "Pause during gameplay: 'ESC' ", screenWidth / 2, screenHeight *0.68f, 0, Align.center, false);
        font.draw(batch, "Quit game: 'ESC'(double press)", screenWidth / 2, screenHeight *0.64f, 0, Align.center, false);
        font.draw(batch, "Muted music in game: 'ALT_RIGHT'", screenWidth / 2, screenHeight *0.60f, 0, Align.center, false);
        float LeftImg = screenWidth * 1 / 6;
        font.setColor(Color.YELLOW); // Font color for enemies
        font.draw(batch, "Enemy Types", LeftText, screenHeight *0.56f, 0, Align.center, false);
        font.setColor(Color.WHITE); // Reset font color
        float iconsize = 15*BufferedReader.getInstance(filepath).scale;
        font.draw(batch, "Lvl 1:", LeftText - iconsize, screenHeight *0.41f+ (iconsize*2/3), 0, Align.center, false);
        batch.draw(new TextureRegion(En0), LeftText, screenHeight *0.41f, iconsize, iconsize);

        font.draw(batch, "Lvl 2:", LeftText - iconsize, screenHeight *0.31f+ (iconsize*2/3), 0, Align.center, false);
        batch.draw(new TextureRegion(En1), LeftText, screenHeight *0.31f, iconsize, iconsize);

        font.draw(batch, "Lvl 3:", LeftText - iconsize, screenHeight *0.21f+ (iconsize*2/3), 0, Align.center, false);
        batch.draw(new TextureRegion(En2), LeftText, screenHeight *0.21f, iconsize, iconsize);

        font.draw(batch, "Lvl 4:", LeftText - iconsize, screenHeight *0.11f+ (iconsize*2/3), 0, Align.center, false);
        batch.draw(new TextureRegion(En3), LeftText, screenHeight *0.11f, iconsize, iconsize);

        font.setColor(Color.YELLOW);
        font.draw(batch, "Bullet Types:", rightTextX, screenHeight *0.56f, 0, Align.center, false);

        font.setColor(Color.WHITE); // Reset font color
        font.draw(batch, "Normal Bullet:", rightTextX - (iconsize*2), screenHeight *0.41f+ (iconsize*2/3), 0, Align.center, false);
        batch.draw(new TextureRegion(amm1), rightTextX, screenHeight *0.41f, iconsize, iconsize);

        font.draw(batch, "Piercing Bullet:", rightTextX -(iconsize*2), screenHeight *0.26f+ (iconsize*2/3), 0, Align.center, false);
        batch.draw(new TextureRegion(amm2), rightTextX, screenHeight *0.26f, iconsize, iconsize);

        font.draw(batch, "Cluster Bullet:", rightTextX -(iconsize*2), screenHeight *0.11f+ (iconsize*2/3), 0, Align.center, false);
        batch.draw(new TextureRegion(amm3), rightTextX, screenHeight *0.11f, iconsize, iconsize);

        batch.end();
        obsluga_klaw();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
