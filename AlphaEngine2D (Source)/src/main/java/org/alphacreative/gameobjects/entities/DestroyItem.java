package org.alphacreative.gameobjects.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.alphacreative.gameobjects.GameObject;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;

public class DestroyItem extends GameObject {
    private double i_timer;

    public double destroyTime;
    public DestroyItem(String name, Vector2D position, Vector2D scale, BufferedImage texture, double destroyTime) {
		super(name, position, scale, texture);
        this.destroyTime = destroyTime;
	}
    public DestroyItem(String name,String tag, Vector2D position, Vector2D scale, BufferedImage texture, double destroyTime) {
		super(name, tag, position, scale, texture);
        this.destroyTime=destroyTime;
	}
    @Override
    public void Update(){
        i_timer += Time.DeltaTime();
        if (i_timer >= destroyTime){
            GameManager.SceneObjects().RemoveObject(this);
        }
    }
    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }

}
