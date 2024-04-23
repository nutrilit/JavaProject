package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    public Sprite sprite;
    public Vector2 position;
    public boolean active;
    private float speed = 250; // Prędkość pocisku
    public int type;
    int scale = 2;

    public Bullet(Texture texture, Vector2 position,int type) {
        this.sprite = new Sprite(texture);
        String filepath = "C:\\JavaProject\\core\\src\\com\\mygdx\\game\\Options.txt";
        sprite.setScale(0.20f*BufferedReader.getInstance(filepath).scale);
        this.position = position;
        this.active = true;
        this.type=type;
    }
    public void draw(SpriteBatch batch) {
        if(this.active==true) {
            position.y += speed * Gdx.graphics.getDeltaTime(); // Aktualizacja pozycji pocisku
            sprite.setPosition(position.x, position.y);
            sprite.draw(batch);
        }
    }
    public void update(float deltaTime) {
        position.y += speed * deltaTime; // Aktualizacja pozycji pocisku
    }

}
