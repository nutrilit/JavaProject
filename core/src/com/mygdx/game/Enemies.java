package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import sun.jvm.hotspot.debugger.win32.coff.TestDebugInfo;
import java.util.Random;
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
    public List<EnemyBullet> bullets1;
    Texture[] textures = new Texture[4];
    private BitmapFont font;
    public List<Ammunition> ammunitions;
    public Music dyingSound;
    public Music explosion;
    private Texture tmptexture;
    boolean ammoType = true;
    public boolean isactive;
    private float enemyFireRate = 1f; // Czas (w sekundach) między kolejnymi strzałami wrogów
    private float enemyFireTimer = 0; // Licznik czasu od ostatniego strzału wrogów
    public Enemies(Texture img)
    {
        alien_move = Vector2.Zero;
        aliens = new Alien[With_aliens*Height_aliens];
        alien_alive_amount = aliens.length;
        tmptexture = new Texture("alien.png");
        bullets1 = new ArrayList<>();
        String filepath = "C:\\JavaProject\\core\\src\\com\\mygdx\\game\\Options.txt";
        space = 15*BufferedReader.getInstance(filepath).scale;

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
        font = new BitmapFont(Gdx.files.internal("font/myfont.fnt"), false);
        font.setColor(Color.WHITE);
        font.getData().setScale(0.2f*BufferedReader.getInstance(filepath).scale);
        dyingSound = Gdx.audio.newMusic(Gdx.files.internal("music/Something being hit - Sound Effect.mp3"));
        explosion = Gdx.audio.newMusic(Gdx.files.internal("music/mixkit-arcade-game-explosion-1699.wav"));
    }
    void resetEnemies()
    {
        alien_speed=100;
        alien_move = new Vector2(0, 0);
        for (int i = 0; i < aliens.length; i++) {
            aliens[i].pos = new Vector2(aliens[i].posInit.x + alien_move.x, aliens[i].posInit.y + alien_move.y);
        }
        for (int i = 0; i < aliens.length; i++) {
            aliens[i].alive = true;
        }
        currType=1;
        EnemyReinforcements(textures[0],currType);
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
    private void enemyFire(float deltaTime) {
        enemyFireTimer += deltaTime;
        if (enemyFireTimer >= enemyFireRate) {
            // Wybierz losowego wroga, który będzie strzelał
            int randomIndex = new Random().nextInt(aliens.length);
            if (aliens[randomIndex].alive) {
                // Stwórz nowy pocisk wroga i dodaj go do listy bullets1
                Texture enemyBulletTexture = new Texture("enemybullet.png");
                Vector2 enemyBulletPosition = new Vector2(aliens[randomIndex].pos.x, aliens[randomIndex].pos.y - aliens[randomIndex].sprite.getHeight());
                EnemyBullet enemyBullet = new EnemyBullet(enemyBulletTexture, enemyBulletPosition, 1);
                bullets1.add(enemyBullet);

                enemyFireTimer = 0;
            }
        }
    }
    void CheckPlayerCollision(Player player)
    {
        for(int i=0;i<aliens.length;i++)
        {
            if(aliens[i].alive==true) {
                if(player.sprite.getBoundingRectangle().overlaps(aliens[i].sprite.getBoundingRectangle()))
                {
                    resetEnemies();
                    player.reset();
                    GameManager.getInstance().gameState = GameManager.GameState.GAMEOVER;
                }
            }
        }
    }
    public void checkEnemyDrop(Player player) {
        for (EnemyBullet enemyBullet : bullets1) {
            if (!enemyBullet.collidedWithPlayer && player.sprite.getBoundingRectangle().overlaps(enemyBullet.sprite.getBoundingRectangle())) {
                // Perform actions when player collides with enemy bullet
                // For example, decrease player lives or reset the player
                enemyBullet.active = false;
                enemyBullet.collidedWithPlayer = true; // Ustaw flagę, że pocisk już kolidował z graczem w tej klatce gry
                player.lives--; // Zmniejsz liczbę żyć gracza o 1
                if(player.lives==0)
                {
                    GameManager.getInstance().gameState = GameManager.GameState.GAMEOVER;
                }
            }
        }
    }

    public void removeBullets()
    {
        List<EnemyBullet> bulletsToRemove = new ArrayList<>();

        for (EnemyBullet bullet : bullets1) {
            if (bullet.position.y >= Gdx.graphics.getWidth()) {
                bulletsToRemove.add(bullet);
            }
        }
        bullets1.removeAll(bulletsToRemove);
    }
    public void removeAllBullets()
    {
        List<EnemyBullet> bulletsToRemove = new ArrayList<>();

        for (EnemyBullet bullet : bullets1) {
            bulletsToRemove.add(bullet);

        }
        bullets1.removeAll(bulletsToRemove);
    }

    void CheckEndOfMap(Player player)
    {
        for(int i=0;i<aliens.length;i++)
        {
            if(aliens[i].alive==true && aliens[i].pos.y<0) {
                resetEnemies();
                player.reset();
                GameManager.getInstance().gameState = GameManager.GameState.GAMEOVER;
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
                            dyingSound.stop();
                            dyingSound.play();
                            GameManager.getInstance().wyciszenie();
                        }
                    }
                }
            }
    }


    public void removeAmmo()
    {
        List<Ammunition> ammoToRemove = new ArrayList<>();

        for (Ammunition ammunition : ammunitions) {
            if (ammunition.pos.y <= 0) {
                ammoToRemove.add(ammunition);
            }
        }
        ammunitions.removeAll(ammoToRemove);
    }
    public void removeallAmmo()
    {
        List<Ammunition> ammoToRemove = new ArrayList<>();

        for (Ammunition ammunition : ammunitions) {
                ammoToRemove.add(ammunition);
        }
        ammunitions.removeAll(ammoToRemove);
    }

    void bulletKill(Bullet bullet,int i)
    {
        switch(bullet.type) {
            case 1:
            {
                bullet.active=false;
                aliens[i].lives--;

                if(score%5==0) {
                    if(ammoType==true) {
                        Texture ammoTexture = new Texture("pierce_ammo.png");
                        Ammunition ammo = new Ammunition(ammoTexture, 1, aliens[i].pos);
                        ammoType=false;
                        ammunitions.add(ammo);
                    }
                    else
                    {
                        Texture ammoTexture = new Texture("cluster_ammo.png");
                        Ammunition ammo = new Ammunition(ammoTexture, 2, aliens[i].pos);
                        ammoType=true;
                        ammunitions.add(ammo);
                    }
                }
                if(score%7==0)
                {
                    Texture ammoTexture = new Texture("extra_life.png");
                    Ammunition ammo = new Ammunition(ammoTexture, 3, aliens[i].pos);
                    ammunitions.add(ammo);
                }
            }
            break;
            case 2:
            {
                aliens[i].alive = false;
                explosion.play();
                GameManager.getInstance().wyciszenie();
            }
            break;
            case 3:
            {
                explosion.stop();
                explosion.play();
                GameManager.getInstance().wyciszenie();
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

        if(aliens[alien_maxX].pos.x>=Gdx.graphics.getWidth()-(0.26*Gdx.graphics.getWidth()))
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
        enemyFire(deltaTime);
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
        for (EnemyBullet bullet : bullets1) {
            bullet.draw(batch);
        }
        font.draw(batch, "Score: " + score, Gdx.graphics.getWidth()-(int)(Gdx.graphics.getWidth()*0.05), Gdx.graphics.getHeight() - (int)(Gdx.graphics.getHeight()*0.05), 0, Align.right, false);
        for (Ammunition ammunition : ammunitions) {
            if(ammunition.isactive==true)
                ammunition.Draw(batch);
        }

    }


}