package org.alphacreative.gameobjects.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.alphacreative.gameobjects.GameObject;
import org.alphacreative.math.Vector2D;

public class Enemy extends GameObject{
    Player player;
    public Enemy(String name, Vector2D position,Vector2D scale, BufferedImage texture, Player player) {
		super(name, position, scale, texture);
        this.player = player;
	}


    @Override
    public void Update()
    {
        // Interpola la posición del jugador suavemente hacia la nueva posición
        /*double newX = Mathf.Lerp(this.position.getX(), player.position.getX(),0.01);
        double newY = Mathf.Lerp(this.position.getY(), player.position.getY(), 0.01);

        position.setX(newX);
        position.setY(newY);*/
        FollowX(player,3f,5f);
        FollowY(player,3f,350f);
    }

    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }

}
