package org.alphacreative.gameobjects.particles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;
import org.alphacreative.util.Mathf;

public class Particle extends GameEntity{
    public float xMove = 0;
    public float yMove = 0;
    protected float startOpacity;

    protected BufferedImage[] textureAnim;
    protected int index = 0;
    protected float anim_timer = 0;

    protected float destroyVel = 0.5f;

    public Particle(String name, Vector2D position, Vector2D scale, BufferedImage texture,float startOpacity, float xMove, float yMove) {
		super(name, position, scale, texture);
        this.xMove = xMove;
        this.yMove = yMove;
        this.opacity = startOpacity;
        this.startOpacity = startOpacity;
	}
    public Particle(String name, Vector2D position, Vector2D scale, BufferedImage[] texture,float startOpacity, float xMove, float yMove) {
		super(name, position, scale, texture[0]);
        this.xMove = xMove;
        this.yMove = yMove;
        this.opacity = startOpacity;
        this.startOpacity = startOpacity;
        this.textureAnim = texture;
	}

    @Override
    public void Update()
    {
        super.Update();
        TranslateY(yMove*Time.DeltaTime());
        TranslateX(xMove*Time.DeltaTime());

        increaseOpacity(-destroyVel*Time.DeltaTime());

        if(textureAnim != null)
        {
            anim_timer += Time.DeltaTime();
            if(anim_timer > 0.03)
            {
                index++;
                if(index >= textureAnim.length)
                {
                    index = 0;
                }
                setTexture(textureAnim[index]);
                anim_timer = 0f;
            }
        }
        if(this.opacity <= 0.05)
        {
            Destroy();
        }
    }

    @Override
    public void Draw(Graphics g)
    {
        super.Draw(g);
    }

    public void RandomizeX(float min, float max)
    {
        xMove = Mathf.RandomRange(min, max);
    }

    public void RandomizeY(float min, float max)
    {
        yMove = Mathf.RandomRange(min, max);
    }
    public void SetDestroyVelocity(float value)
    {
        this.destroyVel = value;
    }

}
