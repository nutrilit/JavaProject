package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;


public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerImg;
	Texture alienImg;
	Texture bulletImg;

	Player player;
	Player player2;
	Enemies enemies;

	//Alien a1;
	//public Vector2 pos

	@Override
	public void create () {
		//Vector2 pos = new Vector2(10,10);
		//Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//Gdx.graphics.setWindowedMode(800,600);
		//Gdx.graphics.setWindowedMode(1200,720);
		batch = new SpriteBatch();
		playerImg = new Texture("ship.png");
		alienImg = new Texture("alien.png");
		player = new Player(playerImg,1);
		player2 = new Player(playerImg,2);
		player2.pos.x+=20;
		//a1 = new Alien(pos,alienImg,1);
		enemies = new Enemies(alienImg);

		enemies.textures[0] = new Texture("alien.png");
		enemies.textures[1] = new Texture("reaver.png");
		enemies.textures[2] = new Texture("xenomorphv1.0.png");
		enemies.textures[3] = new Texture("beholder.png");

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		// Jeśli gra jeszcze nie została rozpoczęta, wyświetlaj menu
		if (!GameManager.getInstance().isGameStarted()) {
			GameManager.getInstance().render();
		} else {
			// Jeśli gra została rozpoczęta, renderuj grę
			batch.begin();
			player.Draw(batch);
			player.checkAmmoDrop(enemies.ammunitions);
			if (GameManager.getInstance().getNumberOfPlayers() == 2) {
				player2.Draw(batch);
				player2.checkAmmoDrop(enemies.ammunitions);
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
	}

	@Override
	public void dispose () {
		batch.dispose();
		playerImg.dispose();
		alienImg.dispose();
	}
}