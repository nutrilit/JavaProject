package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public int playerType;
    private BitmapFont font;
    public Vector2 pos;
    public Sprite sprite;
    public List<Bullet> bullets;
    public float speed = 500;
    public int ammoPierce = 5;
    public int ammoCluster = 5;
    public float fireRate = 0.2f; // czas (w sekundach) między kolejnymi strzałami
    private float fireTimer = 0; // licznik czasu od ostatniego strzału
    private int currentWeapon;
    private int lives=1;
    private  int score;
    private boolean isAlive;
    private Music shoot;

    public Player(Texture img,int type) {
        font = new BitmapFont(Gdx.files.internal("font/myfont.fnt"), false);
        font.setColor(Color.WHITE);
        font.getData().setScale(1);
        sprite = new Sprite(img);
        sprite.setScale(4);
        pos = new Vector2(Gdx.graphics.getWidth() / 2, sprite.getScaleY() * sprite.getHeight() / 2);
        bullets = new ArrayList<>(); // Initialize bullets list
        this.playerType=type;
        this.currentWeapon=1;
        this.lives=3;
        this.isAlive=true;
        this.score=0;
        shoot = Gdx.audio.newMusic(Gdx.files.internal("music/12-Gauge-Pump-Action-Shotgun-Close-Gunshot-A-www.fesliyanstudios.com.mp3"));
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

            if(Gdx.input.isKeyJustPressed(Keys.L))
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
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (bullet.position.y >= Gdx.graphics.getWidth()) {
                bulletsToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletsToRemove);
    }
    public void removeAllBullets()
    {
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
                bulletsToRemove.add(bullet);

        }
        bullets.removeAll(bulletsToRemove);
    }
    public void checkAmmoDrop(List<Ammunition> ammunitions)
    {
        for(Ammunition ammunition: ammunitions)
        {
            if(ammunition.sprite.getBoundingRectangle().overlaps(this.sprite.getBoundingRectangle()))
            {
                if(ammunition.isactive==true) {
                    switch (ammunition.type) {
                        case 1:
                            this.ammoPierce++;
                            break;
                        case 2:
                            this.ammoCluster++;
                            break;
                    }
                }
                ammunition.isactive=false;
            }
        }
    }
    public void Draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(pos.x, pos.y);
        sprite.draw(batch);
        //font.draw(batch, "Type:"+this.currentWeapon, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 9/10, 0, Align.center, false);
        // Draw bullets
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }
        //bullets.removeIf(bullet -> bullet.position.y < 0);
        if (playerType == 1) {
            font.draw(batch, "Lives Player1: " + lives, Gdx.graphics.getWidth() - (int)(Gdx.graphics.getWidth() * 0.05), Gdx.graphics.getHeight() - (int)(Gdx.graphics.getHeight() * 0.15), 0, Align.right, false);
            font.draw(batch, "Ammo1 PLayer1: " + ammoPierce, Gdx.graphics.getWidth() - (int)(Gdx.graphics.getWidth() * 0.05), Gdx.graphics.getHeight() - (int)(Gdx.graphics.getHeight() * 0.20), 0, Align.right, false);
            font.draw(batch, "Ammo2 PLayer1: " + ammoCluster, Gdx.graphics.getWidth() - (int)(Gdx.graphics.getWidth() * 0.05), Gdx.graphics.getHeight() - (int)(Gdx.graphics.getHeight() * 0.25), 0, Align.right, false);
            font.draw(batch, "PLayer1 Weapown: " + currentWeapon, Gdx.graphics.getWidth() - (int)(Gdx.graphics.getWidth() * 0.05), Gdx.graphics.getHeight() - (int)(Gdx.graphics.getHeight() * 0.30), 0, Align.right, false);
        } else {
            font.draw(batch, "Lives Player2: " + lives, Gdx.graphics.getWidth() - (int)(Gdx.graphics.getWidth() * 0.05), Gdx.graphics.getHeight() - (int)(Gdx.graphics.getHeight() * 0.40), 0, Align.right, false);
            font.draw(batch, "Ammo Player2: " + ammoPierce, Gdx.graphics.getWidth() - (int)(Gdx.graphics.getWidth() * 0.05), Gdx.graphics.getHeight() - (int)(Gdx.graphics.getHeight() * 0.45), 0, Align.right, false);
            font.draw(batch, "Ammo2 PLayer2: " + ammoCluster, Gdx.graphics.getWidth() - (int)(Gdx.graphics.getWidth() * 0.05), Gdx.graphics.getHeight() - (int)(Gdx.graphics.getHeight() * 0.50), 0, Align.right, false);
            font.draw(batch, "PLayer2 Weapown: " + currentWeapon, Gdx.graphics.getWidth() - (int)(Gdx.graphics.getWidth() * 0.05), Gdx.graphics.getHeight() - (int)(Gdx.graphics.getHeight() * 0.55), 0, Align.right, false);
        }

    }

    private void fire() {
        // Tworzenie nowego pocisku i dodawanie go do listy
        Texture bulletTexture = new Texture("bullet1.png");
        shoot.stop();
        shoot.play();
        switch(currentWeapon){
            case 1:
                bulletTexture = new Texture("bullet1.png");
                break;
            case  2:
                if(this.ammoPierce>0) {
                    bulletTexture = new Texture("pierce-shot.png");
                    this.ammoPierce--;
                    break;
                }
                else currentWeapon = 1;
                break;
            case 3:
                if(this.ammoCluster>0) {
                    bulletTexture = new Texture("cluster-bullet.png");
                    this.ammoCluster--;
                    break;
                }
                else currentWeapon = 1;
                break;
        }
        Vector2 bulletPosition = new Vector2(pos.x, pos.y + sprite.getHeight());
        Bullet bullet = new Bullet(bulletTexture, bulletPosition,currentWeapon);
        bullets.add(bullet);
    }
}