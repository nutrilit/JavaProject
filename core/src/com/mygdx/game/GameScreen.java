package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Adler32;

public class GameScreen {
    SpriteBatch batch;
    Texture playerImg;
    Texture alienImg;
    Texture asteroidImg;
    Texture bulletImg;

    Player player;
    Player player2;
    Enemies enemies;
    Asteroid asteroid;
    Asteroid asteroid2;
    Asteroid asteroid3;
    public List<Asteroid> asteroids = new ArrayList<>();
    private Texture backgroundTexture;
    private ShapeRenderer shapeRenderer;
    public GameScreen()
    {
        batch = new SpriteBatch();
        playerImg = new Texture("ship.png");
        alienImg = new Texture("alien.png");
        asteroidImg = new Texture("beholder.png");
        player = new Player(playerImg,1);
        player2 = new Player(playerImg,2);
        asteroid = new Asteroid(asteroidImg,true,Gdx.graphics.getHeight() * 0.5f);
        asteroid2=new Asteroid(asteroidImg,true,Gdx.graphics.getHeight() * 0.75f);
        asteroid3=new Asteroid(asteroidImg,false,Gdx.graphics.getHeight() * 1f);
        player2.pos.x+=20;
        //a1 = new Alien(pos,alienImg,1);
        asteroids.add(asteroid);
        asteroids.add(asteroid2);
        asteroids.add(asteroid3);

        enemies = new Enemies(alienImg);

        enemies.textures[0] = new Texture("alien.png");
        enemies.textures[1] = new Texture("reaver.png");
        enemies.textures[2] = new Texture("xenomorphv1.0.png");
        enemies.textures[3] = new Texture("beholder.png");
        backgroundTexture = new Texture("spaceBackground.png");
        shapeRenderer = new ShapeRenderer();
    }
    public void render() {
        batch.begin();
        batch.draw(new TextureRegion(backgroundTexture), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.Draw(batch);
        player.checkAmmoDrop(enemies.ammunitions);
        enemies.CheckPlayerCollision(player);
        enemies.CheckEndOfMap(player);
        player.removeBullets();
        enemies.removeAmmo();
        asteroid.draw(batch);
        asteroid2.draw(batch);
        asteroid3.draw(batch);
        if (GameManager.getInstance().getNumberOfPlayers() == 2) {
            player2.Draw(batch);
            player2.checkAmmoDrop(enemies.ammunitions);
            enemies.CheckPlayerCollision(player2);
            enemies.CheckEndOfMap(player2);
        }
        System.out.println(player.ammoPierce);
        enemies.Draw(batch);
        for (Bullet bullet : player.bullets) {
				/*if(enemies.alien_alive_amount==0)
					bullet.active=false;*/
            enemies.CheckCollision(bullet);
            for (Asteroid asteroid : asteroids) {
                asteroid.CheckCollision(bullet);
            }

        }
        for (Bullet bullet : player2.bullets) {
				/*if(enemies.alien_alive_amount==0)
					bullet.active=false;*/
            enemies.CheckCollision(bullet);
            asteroid.CheckCollision(bullet);
            asteroid2.CheckCollision(bullet);
            asteroid3.CheckCollision(bullet);
        }

        //a1.Draw(batch);
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.line(Gdx.graphics.getWidth() * 0.76f, 0, Gdx.graphics.getWidth() * 0.76f, Gdx.graphics.getHeight());
        shapeRenderer.end();
    }
    public void obsluga_klaw(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameManager.getInstance().gameState = GameManager.GameState.PAUSE;
        }
    }
}
