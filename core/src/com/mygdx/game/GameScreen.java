package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;

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
        enemies = new Enemies(alienImg);

        enemies.textures[0] = new Texture("alien.png");
        enemies.textures[1] = new Texture("reaver.png");
        enemies.textures[2] = new Texture("xenomorphv1.0.png");
        enemies.textures[3] = new Texture("beholder.png");
    }
    public void render() {
        batch.begin();
        player.Draw(batch);
        player.checkAmmoDrop(enemies.ammunitions);
        enemies.CheckPlayerCollision(player);
        enemies.CheckEndOfMap();
        asteroid.draw(batch);
        asteroid2.draw(batch);
        asteroid3.draw(batch);
        if (GameManager.getInstance().getNumberOfPlayers() == 2) {
            player2.Draw(batch);
            player2.checkAmmoDrop(enemies.ammunitions);
            enemies.CheckPlayerCollision(player2);
        }
        System.out.println(player.ammoPierce);
        enemies.Draw(batch);
        for (Bullet bullet : player.bullets) {
				/*if(enemies.alien_alive_amount==0)
					bullet.active=false;*/
            enemies.CheckCollision(bullet);
        }
        for (Bullet bullet : player2.bullets) {
				/*if(enemies.alien_alive_amount==0)
					bullet.active=false;*/
            enemies.CheckCollision(bullet);
        }
        //a1.Draw(batch);
        batch.end();
    }
    public void obsluga_klaw(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameManager.getInstance().gameState = GameManager.GameState.MAIN_MENU;
        }
    }
}
