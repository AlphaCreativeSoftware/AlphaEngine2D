package org.alphacreative.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.alphacreative.gameobjects.GameObject;
import org.alphacreative.gameobjects.hierarchy.SceneObjects;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;

public abstract class GameState 
{
	protected Vector2D screenSize;
	public SceneObjects sceneObjects; //Lista de objetos en juego, la mandaremos posteriormente a GameManager. Nuestro gestor principal
	public GameState() 
	{
		screenSize = GameManager.getScreenSize();
		GameManager.setCurrentScene(this);

		sceneObjects = new SceneObjects(); //Inicializamos la lista de objetos en juego, donde podremos gestionar uno por uno buscar, editar y eliminar
		GameManager.setCurrentSceneObjects(sceneObjects); //Mandamos nuestros objetos actuales a GameManager para que los asigne a su lista de objetos en escena
	}
	
	public abstract void Update();
	public abstract void Draw(Graphics g);

	public void DrawSceneHierarchy(Graphics g)
    {
		if(GameManager.IsDebug())
		{
			Graphics2D g2d = (Graphics2D)g;


			g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, (float)1));//Restablecemos la opacidad en el método
			String objectsString = "";
			int pos_counter = 50;
			g2d.drawString(""+GameManager.GetFPS(),5,15);
			g2d.setColor(Color.blue);
			g2d.drawString(""+(int)GameManager.getScreenSize().getX()+ "X" + (int)GameManager.getScreenSize().getY(),30,15);
			g2d.setColor(Color.red);
			g2d.drawString("--(OBJETOS EN ESCENA)--", 9, pos_counter-15);
			for(GameObject obj : sceneObjects.GameObjects())
			{
				if(obj.getTag() != "CLOUD_BG")
				{
					objectsString = ("NAME: {" + obj.getName()+ "}" + " TAG: {" + obj.getTag() + "}");
					g2d.drawString(objectsString, 9, pos_counter);
					g2d.drawString("-----------------------------------------------------", 9, pos_counter+8);
					pos_counter += 25;
				}
			}
		}
		else
		{
			Graphics2D g2d = (Graphics2D)g;
			g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, (float)1));
			int pos_counter = 50;
			g2d.drawString(""+GameManager.GetFPS(),5,15);
			g2d.setColor(Color.blue);
			g2d.drawString(""+(int)GameManager.getScreenSize().getX()+ "X" + (int)GameManager.getScreenSize().getY(),30,15);
			g2d.setColor(Color.red);
			g2d.drawString("--(PRESS ('D') TO SHOW DEBUG)--", 9, pos_counter-15);
			g2d.drawString("--(PRESS ('C') TO SHOW COLLIDERS)--", 9, pos_counter);

		}
    }
}
