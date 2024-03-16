package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public int playerType;
    public Vector2 pos;
    public Sprite sprite;
    public List<Bullet> bullets;
    public float speed = 500;
    public int ammoPierce = 5;
    public int ammoCluster = 5;
    public float fireRate = 0.2f; // czas (w sekundach) między kolejnymi strzałami
    private float fireTimer = 0; // licznik czasu od ostatniego strzału
    private int currentWeapon;

    public Player(Texture img,int type) {
        sprite = new Sprite(img);
        sprite.setScale(4);
        pos = new Vector2(Gdx.graphics.getWidth() / 2, sprite.getScaleY() * sprite.getHeight() / 2);
        bullets = new ArrayList<>(); // Initialize bullets list
        this.playerType=type;
        this.currentWeapon=1;
    }

    void playerMovement(float deltaTime) {
        if(playerType==1) {
            if (Gdx.input.isKeyPressed(Keys.A))
                pos.x -= deltaTime * speed;
            if (Gdx.input.isKeyPressed(Keys.D))
                pos.x += deltaTime * speed;
            if (pos.x - (sprite.getScaleX() * sprite.getWidth() / 2) <= 0)
                pos.x = (sprite.getScaleY() * sprite.getHeight() / 2);
            if (pos.x + (sprite.getScaleX() * sprite.getWidth() / 2) >= Gdx.graphics.getWidth()-(0.2*Gdx.graphics.getWidth()))
                pos.x = (Gdx.graphics.getWidth()-(int)(0.2*Gdx.graphics.getWidth())) - (sprite.getScaleY() * sprite.getHeight() / 2);

            if(Gdx.input.isKeyJustPressed(Keys.Q))
                currentWeapon++;
            if(currentWeapon>3)
                currentWeapon=1;

            // Strzelanie
            fireTimer += deltaTime;
            if (Gdx.input.isKeyPressed(Keys.SPACE) && fireTimer >= fireRate) {
                fire();
                fireTimer = 0;
            }
        }
        if(playerType==2) {
            if (Gdx.input.isKeyPressed(Keys.LEFT))
                pos.x -= deltaTime * speed;
            if (Gdx.input.isKeyPressed(Keys.RIGHT))
                pos.x += deltaTime * speed;
            if (pos.x - (sprite.getScaleX() * sprite.getWidth() / 2) <= 0)
                pos.x = (sprite.getScaleY() * sprite.getHeight() / 2);
            if (pos.x + (sprite.getScaleX() * sprite.getWidth() / 2) >= Gdx.graphics.getWidth()-(0.2*Gdx.graphics.getWidth()))
                pos.x = (Gdx.graphics.getWidth()-(int)(0.2*Gdx.graphics.getWidth())) - (sprite.getScaleY() * sprite.getHeight() / 2);

            if(Gdx.input.isKeyJustPressed(Keys.P))
                currentWeapon++;
            if(currentWeapon>3)
                currentWeapon=1;

            // Strzelanie
            fireTimer += deltaTime;
            if (Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT) && fireTimer >= fireRate) {
                fire();
                fireTimer = 0;
            }
        }
    }

    public void update(float deltaTime) {
        playerMovement(deltaTime);
        // Update bullet positions
        for (Bullet bullet : bullets) {
            bullet.update(deltaTime);
        }
    }
    public void removeBullets()
    {
        for (Bullet bullet : bullets) {
            bullet.active=false;
        }
    }
    public void Draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(pos.x, pos.y);
        sprite.draw(batch);
        // Draw bullets
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }
        //bullets.removeIf(bullet -> bullet.position.y < 0);
    }

    private void fire() {
        // Tworzenie nowego pocisku i dodawanie go do listy
        Texture bulletTexture = new Texture("bullet1.png");
        switch(currentWeapon){
            case 1:
                bulletTexture = new Texture("bullet1.png");
                break;
            case  2:
                bulletTexture = new Texture("pierce-shot.png");
                break;
            case 3:
                bulletTexture = new Texture("cluster-bullet.png");
                break;
        }
        Vector2 bulletPosition = new Vector2(pos.x, pos.y + sprite.getHeight());
        Bullet bullet = new Bullet(bulletTexture, bulletPosition,currentWeapon);
        bullets.add(bullet);
    }
}
