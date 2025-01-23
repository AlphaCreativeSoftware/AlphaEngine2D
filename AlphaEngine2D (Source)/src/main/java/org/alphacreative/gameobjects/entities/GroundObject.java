package org.alphacreative.gameobjects.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.alphacreative.gameobjects.GameObject;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;

public class GroundObject extends GameObject{
    protected Player player;
    public GroundObject(String name, Vector2D position, Vector2D scale, BufferedImage texture) {
		super(name, position, scale, texture);
        this.tag = "Ground";
        player = (Player) GameManager.SceneObjects().FindObjectWithName("Player");
	}
    @Override
    public void Update() {
    }
    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
        g2d.setColor(Color.MAGENTA);
        g2d.drawString("X", (int) Math.round(this.getCenter().getX()), (int) Math.round(this.getCenter().getY()));

        g2d.setColor(Color.BLUE); //TOP DRAW
		g2d.drawString("↑", (int) Math.round(this.getTop().getX()), (int) Math.round(this.getTop().getY()));

		g2d.setColor(Color.BLUE); //BOTTOM DRAW
		g2d.drawString("↓", (int) Math.round(this.getBottom().getX()), (int) Math.round(this.getBottom().getY()));

		g2d.setColor(Color.BLUE); //LEFT DRAW
		g2d.drawString("←", (int) Math.round(this.getLeft().getX()), (int) Math.round(this.getLeft().getY()));

		g2d.setColor(Color.BLUE); //RIGHT DRAW
		g2d.drawString("→", (int) Math.round(this.getRight().getX()), (int) Math.round(this.getRight().getY()));

        g2d.setColor(Color.black);
        g.drawRect( (int) Math.round(position.getX()), (int) Math.round(position.getY()), (int) Math.round(this.getRealWidth()), (int) Math.round(this.getRealHeight()));
    }

}
