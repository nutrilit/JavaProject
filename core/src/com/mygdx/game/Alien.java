package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Alien {
    public Vector2 pos;
    public Vector2 posInit;
    public Sprite sprite;
    public Boolean alive;
    public int lives;
    public int type;
    public Alien(Vector2 _position, Texture alien, int type)
    {
        pos = _position;
        posInit = pos;
        sprite = new Sprite(alien);
        sprite.setScale(4);
        this.alive = true;
        type=type;
        switch (type){
            case 1: this.lives=1; break;
            case 2:this.lives=2; break;
            case 3:this.lives=3; break;
            case 4:this.lives=4; break;
        }
    }
    public void MoveCoordinates(int nx, int ny){
        pos.x = nx;
        pos.y = ny;
    }
    public void Draw(SpriteBatch batch)
    {
        sprite.setPosition(pos.x, pos.y);
        sprite.draw(batch);
    }
}