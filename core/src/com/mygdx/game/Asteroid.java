package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;
public class Asteroid {
    private Vector2 position;
    private Texture texture;
    public Sprite sprite;
    private float width;
    private float height;
    private float speed = 100; // Prędkość ruchu asteroidy
    private float respawnTimeMin = 2; // Minimalny czas oczekiwania przed ponownym pojawieniem się
    private float respawnTimeMax = 5; // Maksymalny czas oczekiwania przed ponownym pojawieniem się
    private float timeUntilRespawn = 0;
    private Random random;

    public Asteroid(Texture texture, boolean isRightAligned,float initialHeight) {
        this.texture = texture;
        this.sprite =  new Sprite(texture);
        String filepath = "C:\\JavaProject\\core\\src\\com\\mygdx\\game\\Options.txt";
        sprite.setScale(0.5f*BufferedReader.getInstance(filepath).scale);
        width = 50;
        height = 50;

        if (isRightAligned) {
            position = new Vector2(Gdx.graphics.getWidth()+(sprite.getWidth()), (initialHeight - height) / 2);
            speed = -speed;
        } else {
            position = new Vector2(-width, (initialHeight - height) / 2);
        }

        random = new Random();
        timeUntilRespawn = generateRandomRespawnTime();
    }

    public void update(float deltaTime) {
        if (timeUntilRespawn > 0) {
            timeUntilRespawn -= deltaTime;
            if (timeUntilRespawn <= 0) {
                if (speed > 0) {
                    position.x = -width;
                } else {
                    position.x = 0.74f * Gdx.graphics.getWidth();
                }
            }
            return;
        }
        position.x += speed * deltaTime * 3;


        if ((speed > 0 && position.x >= 0.74f * Gdx.graphics.getWidth())) {
            position.x = -width;
            timeUntilRespawn = generateRandomRespawnTime();
        }
        if((speed < 0 && position.x < -width))
        {
            position.x = -width;
            timeUntilRespawn = generateRandomRespawnTime();
        }
    }

    private float generateRandomRespawnTime() {
        return random.nextFloat() * (respawnTimeMax - respawnTimeMin) + respawnTimeMin;
    }
    public void draw(SpriteBatch batch) {
       // batch.draw(texture, position.x, position.y, width, height);
        update(Gdx.graphics.getDeltaTime());

        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
    void CheckCollision(Bullet bullet) {
        if (bullet.active == true)
        {
            if(this.sprite.getBoundingRectangle().overlaps(bullet.sprite.getBoundingRectangle()))
            {
                bullet.active = false;
            }
        }
    }

}


