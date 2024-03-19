package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ammunition {
    public Vector2 pos;
    public Sprite sprite;
    public float speed=500;
    int type;
    public boolean isactive;

    public Ammunition(Texture img, int type,Vector2 pos)
    {
        sprite = new Sprite(img);
        sprite.setScale(4);
        this.type = type;
        this.pos = pos;
        isactive = true;
    }
    public void setPos(float x,float y)
    {
        pos.x=x;
        pos.y=y;
    }

    public void Update(float deltaTime) { pos.y-=deltaTime*speed; }

    public void Draw(SpriteBatch batch)
    {
        pos.y += speed * Gdx.graphics.getDeltaTime(); // Aktualizacja pozycji pocisku
        sprite.setPosition(pos.x, pos.y);
        sprite.draw(batch);
    }
}
