package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

public class Enemies {
    Alien[] aliens;
    float tmpPosX=10000;
    float tmpPosY=10000;
    int With_aliens=11;
    int Height_aliens=5;
    int space=40; //nie mniej niz 40
    int alien_maxX;
    int alien_maxY;
    int alien_minX;
    int alien_minY;
    int alien_direct=1;
    float alien_speed = 100;//100;//100
    Vector2 alien_move;
    int alien_alive_amount;
    int currType=1;
    public int score=0;
    Texture[] textures = new Texture[4];
    private BitmapFont font;
    public List<Ammunition> ammunitions;

    public Enemies(Texture img)
    {
        alien_move = Vector2.Zero;
        aliens = new Alien[With_aliens*Height_aliens];
        alien_alive_amount = aliens.length;
        int i=0;
        ammunitions = new ArrayList<>();
        for(int y=0; y<Height_aliens;y++)
        {
            for(int x=0; x<With_aliens;x++)
            {
                Vector2 pos = new Vector2(x*space,y*space);
                pos.x+= (Gdx.graphics.getWidth()-(int)(0.2*Gdx.graphics.getWidth()))/2;
                pos.y+=Gdx.graphics.getHeight();
                pos.x-=(With_aliens/2)*space;
                pos.y-=(Height_aliens)*space;
                aliens[i] = new Alien(pos,img,1);
                i++;
            }
        }
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
    }
    void EnemyReinforcements(Texture img,int type)
    {
        int i=0;
        for(int y=0; y<Height_aliens;y++)
        {
            for(int x=0; x<With_aliens;x++)
            {
                Vector2 pos = new Vector2(x*space,y*space);
                pos.x+= (Gdx.graphics.getWidth()-(int)(0.2*Gdx.graphics.getWidth()))/2;
                pos.y+=Gdx.graphics.getHeight();
                pos.x-=(With_aliens/2)*space;
                pos.y-=(Height_aliens)*space;
                aliens[i] = new Alien(pos,img,type);
                i++;
            }
        }
    }
    void CheckCollision(Bullet bullet)
    {
        if(bullet.active==true)
            for(int i=0;i<aliens.length;i++)
            {
                if(aliens[i].alive==true)
                {
                    if(bullet.sprite.getBoundingRectangle().overlaps(aliens[i].sprite.getBoundingRectangle()))
                    {
                    /*for(int ii=0;ii<aliens.length;ii++) //zabijanie wszyskich
                        aliens[ii].alive=false;*/

                                /*Texture ammoTexture = new Texture("pierce_ammo.png");
                                Ammunition ammo = new Ammunition(ammoTexture, 1, aliens[i].pos);
                                ammunitions.add(ammo);*/
                        score++;
                        bulletKill(bullet,i);
/*                    bullet.active=false;
                    aliens[i].lives--;*/
                        if (aliens[i].lives <= 0) {
                            aliens[i].alive = false;
                        }
                    }
                }
            }
    }

    void bulletKill(Bullet bullet,int i)
    {
        switch(bullet.type) {
            case 1:
            {
                bullet.active=false;
                aliens[i].lives--;

                Texture ammoTexture = new Texture("pierce_ammo.png");
                Ammunition ammo = new Ammunition(ammoTexture, 1, aliens[i].pos);
                ammunitions.add(ammo);
            }
            break;
            case 2:
            {
                aliens[i].alive = false;
            }
            break;
            case 3:
            {
                aliens[i].alive=false;
                bullet.active=false;
                if((i-1)>0 && (i+1)<aliens.length)
                {
                    if(aliens[i].pos.y==aliens[i-1].pos.y)
                        aliens[i-1].alive=false;
                    if(aliens[i].pos.y==aliens[i+1].pos.y)
                        aliens[i+1].alive=false;
                }
            }
            break;
        }
    }
    void AlienMovement(float deltaTime,SpriteBatch batch)
    {
        alien_maxX=0;
        alien_maxY=0;
        alien_minX=10000;
        alien_minY=10000;
        alien_alive_amount=0;
        for(int i=0;i<aliens.length;i++)
        {
            if(aliens[i].alive)
            {
                int IndexX = i%With_aliens;
                int IndexY = i%With_aliens;//Height
                if(IndexX>alien_maxX)alien_maxX=IndexX;
                if(IndexX<alien_minX)alien_minX=IndexX;
                if(IndexY>alien_maxY)alien_maxY=IndexY;
                if(IndexY<alien_minY)alien_minY=IndexY;
                alien_alive_amount++;
            }
        }

        if(alien_alive_amount == 0)
        {
            alien_speed=100;
            alien_move = new Vector2(0, 0);
            for (int i = 0; i < aliens.length; i++) {
                aliens[i].pos = new Vector2(aliens[i].posInit.x + alien_move.x, aliens[i].posInit.y + alien_move.y);
            }
            for (int i = 0; i < aliens.length; i++) {
                aliens[i].alive = true;
            }
            currType++;
            if(currType>textures.length) {
                currType = 1;
            }
            EnemyReinforcements(textures[currType-1],currType);
            return;
        }

        alien_move.x+=alien_direct*deltaTime*alien_speed;

        if(aliens[alien_maxX].pos.x>=Gdx.graphics.getWidth()-(0.2*Gdx.graphics.getWidth()))
        {
            alien_direct=-1;
            alien_move.y-=aliens[0].sprite.getHeight()*aliens[0].sprite.getScaleY()*0.25f;
            alien_speed+=5;//nabiera predkosci
        }
        if(aliens[alien_minX].pos.x<=0)
        {
            alien_direct=1;
            alien_move.y-=aliens[0].sprite.getHeight()*aliens[0].sprite.getScaleY()*0.25f;
            alien_speed+=5;//nabiera predkosci
        }
        for(int i=0;i<aliens.length;i++){
            aliens[i].pos = new Vector2(aliens[i].posInit.x+alien_move.x,aliens[i].posInit.y+alien_move.y);
        }

    }
    public void Update(float deltaTime,SpriteBatch batch) {
        AlienMovement(deltaTime,batch);
        for (Ammunition ammunition : ammunitions) {
            ammunition.Update(deltaTime);
        }
        for (Ammunition ammunition : ammunitions) {
            ammunition.Update(deltaTime);
        }
    }
    public void Draw(SpriteBatch batch)
    {
        Update(Gdx.graphics.getDeltaTime(),batch);
        int i=0;
        for(int y=0; y<Height_aliens;y++)
        {
            for(int x=0; x<With_aliens;x++) {
                if(aliens[i].alive==true) {
                    aliens[i].Draw(batch);
                }
                i++;
            }
        }
        font.draw(batch, "Score: " + score, Gdx.graphics.getWidth()-(int)(Gdx.graphics.getWidth()*0.05), Gdx.graphics.getHeight()/2+(int)(Gdx.graphics.getHeight()*0.10), 0, Align.right, false);
        for (Ammunition ammunition : ammunitions) {
            if(ammunition.isactive==true)
                ammunition.Draw(batch);
        }
    }

}