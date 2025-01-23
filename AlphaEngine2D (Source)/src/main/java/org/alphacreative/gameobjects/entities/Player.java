package org.alphacreative.gameobjects.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.gameobjects.GameObject;
import org.alphacreative.input.KeyBoard;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;
import org.alphacreative.util.Mathf;

public class Player extends GameEntity{
	protected float velocity;
	public Player(String name, Vector2D position, float velocity, Vector2D scale, BufferedImage texture) {
		super(name, position, scale, texture);
		this.velocity = velocity;

		SetColliderLocalPositionY(0);
		SetColliderLocalScaleY(0.95);
		SetColliderLocalScaleX(0.8);
	}
	float gravityForce = 1f; //Fuerza gravedad incremental
	float jumpAddForce = 16f;
	boolean grounded = false;

	boolean walking_anim = false;
	private double angleIncrement = 0.015; // Ajusta este valor para controlar la velocidad de la animación
    private double angleLimit = 0.1;

	public GameObject collidingObject = null;
	public double groundHeight = 400;

	private ArrayList<GameObject> groundObjects;
	@Override
	public void Update() {
		super.Update();
		// WALKING
		double velX;
		if(KeyBoard.SHIFT) {
			velX = (velocity * 1.8);
		} else {
			velX = (velocity);
		}
		if (KeyBoard.D && position.getX() < GameManager.getScreenSize().getX()-texture.getWidth()) {
			TranslateX(velX);
		}
		if (KeyBoard.A  && position.getX() > 0 ) {
			TranslateX(-velX);
		}
		if((KeyBoard.A || KeyBoard.D)) {
			walking_anim = true;
		} else {
			walking_anim = false;
		}


		//cosas nuevas
		if(KeyBoard.CTRL)
		{
			SetColliderLocalPositionY(115);
			SetColliderLocalScaleY(0.6);
		}
		else
		{
			SetColliderLocalPositionY(0);
			SetColliderLocalScaleY(0.95);
			SetColliderLocalScaleX(0.8);
		}
	
		// Aplicar gravedad
		if (!grounded) {
			position.setY(position.getY() + gravityForce);
			gravityForce += 0.75f; // Incremento de la gravedad
		}
	
		// Salto
	    // Salto
	    if (KeyBoard.SPACE && grounded) {
			position.setY(position.getY()-1);
	        gravityForce = -jumpAddForce; // Aplicar fuerza hacia arriba al saltar
	        grounded = false; // El jugador ya no está en el suelo
	    }
	
		// Limitar la velocidad de caída máxima
		if (gravityForce > 8) {
			gravityForce = 8;
		}

		groundObjects = GameManager.SceneObjects().FindObjectsWithTag("Ground");

		for(int i=0; i<groundObjects.size(); i++)
		{
			GameObject collidingObject_t = groundObjects.get(i);
			if(isCollidingWithTop(collidingObject_t))
			{
				collidingObject = collidingObject_t;
				//groundHeight = collidingObject_t.getPosition().getY() - collidingObject_t.getTexture().getHeight() * collidingObject_t.getScaleY();
				grounded = true;
				gravityForce = 0;
				TranslateY(-2*Time.SyncTime());
				break;
			}
		}
		if(collidingObject != null)
		{
			if(hasExitedCollision(collidingObject))
			{
				TranslateY(3*Time.SyncTime());
				grounded = false;
				collidingObject = null;
			}
		}
		//System.out.println(collidingObject);
	    // Limitar la posición del jugador para que no se caiga fuera de la pantalla
	    if (position.getY() > groundHeight) {
	        position.setY(groundHeight);
	        grounded = true; // El jugador está en el suelo
	        gravityForce = 0; // Detener la fuerza de la gravedad
	    }
	
		if (walking_anim) {
			Rotate(angleIncrement);
			if (angle >= angleLimit || angle <= -angleLimit) {
				angleIncrement = -angleIncrement; // Cambia la dirección cuando alcanza los límites
			}
		} else {
			// Si no está caminando, establece el ángulo en 0
			//angle = 0.0;
			angle = Mathf.Lerp(angle, 0, 0.2);
		}
	}

	@Override
	public void Draw(Graphics g) {
		super.Draw(g);
		Graphics2D g2d = (Graphics2D) g;
		// Guardar la configuración de transformación original
		AffineTransform originalTransform = g2d.getTransform();

		g2d.setColor(Color.RED);
		
		// Dibujar el texto "Antonio" con la transformación aplicada
		g2d.drawString(this.name, (int) Math.round(position.getX()), (int) Math.round(position.getY()));

		g2d.setColor(Color.MAGENTA); //CENTER DRAW
		g2d.drawString("⥦", (int) Math.round(this.getCenter().getX()), (int) Math.round(this.getCenter().getY()));

		g2d.setColor(Color.BLUE); //TOP DRAW
		g2d.drawString("↑", (int) Math.round(this.getTop().getX()), (int) Math.round(this.getTop().getY()));

		g2d.setColor(Color.BLUE); //BOTTOM DRAW
		g2d.drawString("↓", (int) Math.round(this.getBottom().getX()), (int) Math.round(this.getBottom().getY()));

		g2d.setColor(Color.BLUE); //LEFT DRAW
		g2d.drawString("←", (int) Math.round(this.getLeft().getX()), (int) Math.round(this.getLeft().getY()));

		g2d.setColor(Color.BLUE); //RIGHT DRAW
		g2d.drawString("→", (int) Math.round(this.getRight().getX()), (int) Math.round(this.getRight().getY()));
		
		// Restaurar la configuración de transformación original
		g2d.setTransform(originalTransform);
	}
}