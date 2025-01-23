package org.alphacreative.gameobjects.flappy;

import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
public class LaserBeam extends GameEntity{

    private float destroy_timer = 0;

    public LaserBeam(String name, Vector2D position, Vector2D scale, BufferedImage texture) {
        super(name, position, scale, texture);
	}
    public LaserBeam(String name,String tag, Vector2D position, Vector2D scale, BufferedImage texture) {
		super(name, tag, position, scale, texture);
	}
    @Override
    public void Update(){
        super.Update();
        TranslateX(40*Time.SyncTime());

        destroy_timer += Time.DeltaTime();
        if(destroy_timer > 2)
        {
            Destroy();
        }
    }
    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }

}
