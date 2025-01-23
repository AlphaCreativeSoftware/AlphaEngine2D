package org.alphacreative.gameobjects.flappy;

import java.awt.Graphics;
import org.alphacreative.time.Time;
import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.graphics.Assets;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;

public class ExplostionParticles extends GameEntity{
    float d_timer = 0f;

    float an_timer = 0f;
    int index = 0;

    Player_Flappy player;

    public ExplostionParticles(String name, Vector2D position, Vector2D scale) {
    super(name, position, scale, Assets.player_explosion[0]);
    player = (Player_Flappy) GameManager.SceneObjects().FindObjectWithName("Player");
	}
    public ExplostionParticles(String name,String tag, Vector2D position, Vector2D scale) {
		super(name, tag, position, scale, Assets.player_explosion[0]);
        player = (Player_Flappy) GameManager.SceneObjects().FindObjectWithName("Player");
	}
    @Override
    public void Update()
    {
        d_timer += Time.DeltaTime(); 
        if(d_timer > 1.5)
        {
            Destroy();
        }
        an_timer += Time.DeltaTime();
        if(an_timer > 0.03)
        {
            index++;
            if(index >= Assets.player_explosion.length)
            {
                index = Assets.player_explosion.length-1;
            }
            setTexture(Assets.player_explosion[index]);
            an_timer = 0f;
        }

        setPosition(player.getPosition().getX()-50, player.getPosition().getY()-120);
        //Follow((GameObject) player, 8, 0);
        this.setOpacity(0.9);
    }
    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }

}
