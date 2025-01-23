package org.alphacreative.gameobjects.flappy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.gameobjects.ui.Message;
import org.alphacreative.graphics.Assets;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;
import org.alphacreative.states.FlappyScene;

public class FlappyPipeTrigger extends GameEntity{
    public FlappyScene flappy_scene;
    public Player_Flappy player;
    private boolean hasTriggered = false;
        public FlappyPipeTrigger(String name, Vector2D position, Vector2D scale, BufferedImage texture) {
		super(name, position, scale, texture);
        player = (Player_Flappy) GameManager.SceneObjects().FindObjectWithName("Player");
        flappy_scene = (FlappyScene) GameManager.getCurrentScene();//Obtenemos la escena flappy aprovechando el polimorfismo de la herencoa
	}
    public FlappyPipeTrigger(String name,String tag, Vector2D position, Vector2D scale, BufferedImage texture) {
		super(name, tag, position, scale, texture);
        player = (Player_Flappy) GameManager.SceneObjects().FindObjectWithName("Player");
        flappy_scene = (FlappyScene) GameManager.getCurrentScene();
	}
    @Override
    public void Update(){ 
        if(isCollidingWithEntity2D(player) && !hasTriggered)
        {
            flappy_scene.IncreaseScore();
            if(flappy_scene.getScore() >= flappy_scene.getRecord())
            {
                flappy_scene.setRecord(flappy_scene.getScore());
                flappy_scene.ShowNewRecord();
                GameManager.DataManager().saveRecord(flappy_scene.getRecord());
                
            }
            else
            {
                flappy_scene.HideNewRecord();
            }
            GameManager.DataManager().saveLastScore(flappy_scene.getScore());
            Message message = (Message) GameManager.SceneObjects().AddObject(new Message("Mensahe", new Vector2D(GameManager.getScreenCenter().getX()+100,GameManager.getScreenCenter().getY()-280), "+1", Assets.font_boohong, Color.WHITE, true, 35,5,Color.BLACK));
            message.SetVelocity(2);
            flappy_scene.PlayBellSound();
            hasTriggered = true;
            Destroy();
        }
    }
    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }

}
