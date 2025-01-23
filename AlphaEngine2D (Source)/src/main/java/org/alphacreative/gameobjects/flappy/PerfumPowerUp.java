package org.alphacreative.gameobjects.flappy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;

public class PerfumPowerUp extends GameEntity{

    private Player_Flappy player;

    private float change_direction_timer = 0;
    private float cycle_timer = 0;
    private boolean movingDown = true;

    private double yStartPos;
    private double yTargetPos;

    private boolean iscollidedWithPlayer = false;

    public PerfumPowerUp(String name, Vector2D position, Vector2D scale, BufferedImage texture) {
        super(name, position, scale, texture);
        player = (Player_Flappy) GameManager.SceneObjects().FindObjectWithName("Player");
        yStartPos = position.getY();
        yTargetPos = yStartPos + 50;
	}
    public PerfumPowerUp(String name,String tag, Vector2D position, Vector2D scale, BufferedImage texture) {
		super(name, tag, position, scale, texture);
        player = (Player_Flappy) GameManager.SceneObjects().FindObjectWithName("Player");
        yStartPos = position.getY();
        yTargetPos = yStartPos + 50;
	}

    @Override
    public void Update(){
        super.Update();

        // Calculate the deltaTime
        
        // Update the cycle timer
        cycle_timer += Time.DeltaTime();

        // Determine the duration of a full cycle (up and down)
        float cycleDuration = 2.0f; // 2 seconds for a full cycle

        // Calculate the normalized time within the current cycle
        float normalizedTime = (cycle_timer % cycleDuration) / cycleDuration;

        // Calculate the current position using a sine wave pattern
        double newY = yStartPos + (Math.sin(normalizedTime * Math.PI * 2) * 0.5 + 0.5) * (yTargetPos - yStartPos);

        // Set the new position
        this.setPosition(this.position.getX(), newY);

        // Check if we need to change direction
        if (change_direction_timer >= cycleDuration) {
            change_direction_timer = 0;
            movingDown = !movingDown;
        }
        if(!iscollidedWithPlayer)
        {
            if(isCollidingWithEntity2D(player))
            {
                iscollidedWithPlayer = true;
                player.flappy_scene.EnableTurbo();
                player.flappy_scene.day = false;
            }
        }
        if(iscollidedWithPlayer && this.getScale().getX() > 0.01)
        {
            this.setScale(this.scale.getX()-1.8*Time.DeltaTime(),this.scale.getY()-1.8*Time.DeltaTime());
            this.TranslateX(400*Time.DeltaTime());
            this.TranslateY(1900*Time.DeltaTime());
            this.setOpacity(this.getOpacity()-5*Time.DeltaTime());
        }
        if(this.getScaleX() < 0.04&&this.getScaleY()<0.04)
        {
            Destroy();
        }
    }

    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }
}
