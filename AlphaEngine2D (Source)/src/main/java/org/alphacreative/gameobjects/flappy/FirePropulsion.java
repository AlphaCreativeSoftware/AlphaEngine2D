package org.alphacreative.gameobjects.flappy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.alphacreative.gameobjects.GameObject;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;

public class FirePropulsion extends GameObject{
    private BufferedImage[] textureAnimated;

    float an_timer = 0f;
    int index = 0;
    public FirePropulsion(String name, Vector2D position, Vector2D scale, BufferedImage[] texture) {
        super(name, position, scale, texture[0]);
        this.textureAnimated = texture;
	}
    public FirePropulsion(String name,String tag, Vector2D position, Vector2D scale, BufferedImage[] texture) {
		super(name, tag, position, scale, texture[0]);
        this.textureAnimated = texture;
	}
    @Override
    public void Update(){
        an_timer += Time.DeltaTime();
        if(an_timer > 0.03)
        {
            index++;
            if(index >= textureAnimated.length)
            {
                index = 0;
            }
            setTexture(textureAnimated[index]);
            an_timer = 0f;
        }
    }
    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }

}
