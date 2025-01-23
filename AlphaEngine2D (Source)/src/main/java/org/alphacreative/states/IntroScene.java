package org.alphacreative.states;

import java.awt.Graphics;

import javax.sound.sampled.Clip;

import org.alphacreative.manager.GameManager;
import org.alphacreative.gameobjects.ui.Sprite;
import org.alphacreative.graphics.Assets;
import org.alphacreative.graphics.Sound;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;

public class IntroScene extends GameState{
    private double time = 0.000;

    private double showTimer = 0;
    private Sprite splashBG;
    private Sprite splashScreen;

    private Sound introSound;
    public IntroScene()
    {
        super();
        GameManager.setTitle("Alpha Engine");
        GameManager.SetShowColliders(false);
        GameManager.SetDebug(false);

        splashBG = (Sprite) sceneObjects.AddObject(new Sprite("SPLASH",new Vector2D(1,1), new Vector2D(1,1), Assets.introBg,new Vector2D(0,0)));
        splashScreen = (Sprite) sceneObjects.AddObject(new Sprite("SPLASH",new Vector2D(1,1), new Vector2D(1,1), Assets.introImage,new Vector2D(0,0)));
        splashScreen.setOpacity(0);
        splashScreen.setScale(0.75,0.75);

        introSound = new Sound(Assets.intro_clip);
        introSound.ChangeVolume(-10);
        introSound.Play();
    }
    @Override
    public void Update()
    {
        sceneObjects.Update();
        showTimer += Time.DeltaTime();
        if(showTimer >= 1.5)
        {
            time += Time.DeltaTime(); //Aumentamos un segundo ya que el DeltaTime() calcula lo tardado en renderizar el fotograma anterior para asi normalizar movimientos y funciones en su tiempo real y no en tiempo de CPU


            splashScreen.setScale(splashScreen.getScaleX()-0.00012*Time.SyncTime(), splashScreen.getScaleY()-0.00012*Time.SyncTime());
    
            if(time < 5)
            {
                splashScreen.increaseOpacity(0.25*Time.DeltaTime());
            }
                else if (time < 7)
                {
                    splashScreen.decreaseOpacity(0.5*Time.DeltaTime());
                }
                    else
                    {
                        GameManager.ChangeScene(2);
                        introSound.Stop();
                    }
        }
    }

    @Override
    public void Draw(Graphics g)
    {
        sceneObjects.Draw(g);
    }
}
