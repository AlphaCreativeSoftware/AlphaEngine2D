package org.alphacreative.states;

import java.awt.Graphics;

import org.alphacreative.gameobjects.entities.Enemy;
import org.alphacreative.gameobjects.entities.Player;
import org.alphacreative.graphics.Assets;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;

public class GameScene extends GameState
{
	private Player player;

	private Enemy enemy1;

    public GameScene()
    {
        super();
		GameManager.setTitle("Game");
		//Objetos
		//player = new Player("Player",new Vector2D(0,0),4,new Vector2D(1,1),Assets.player);

		//enemy1 = new Enemy("Enemy",new Vector2D(900,0), new Vector2D(1,1), Assets.player, player);
    }

    @Override
    public void Update()
	{
		//Objetos Update
		player.Update();
		enemy1.Update();
	}
    @Override
	public void Draw(Graphics g) 
	{
		//Objetos draw
		player.Draw(g);

		enemy1.Draw(g);
	}




}
