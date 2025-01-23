package org.alphacreative.gameobjects.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.input.MouseInput;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;

public class Sprite extends GameEntity{
    
    protected Vector2D targetScale;
    protected Vector2D realPosition;

    private boolean useRealposition = true;

    public Sprite(String name, Vector2D position, Vector2D scale, BufferedImage texture,Vector2D realPosition) {
        super(name, position, scale, texture);
        this.tag = "UI";
        this.targetScale = scale;
        this.realPosition = realPosition;
	}

    @Override
    public void Update()
    {
        super.Update();
        if(useRealposition)
        setPosition((GameManager.getScreenCenter().getX()-this.getRealWidth()/2)+realPosition.getX(),(GameManager.getScreenCenter().getY()-this.getRealHeight()/2)+realPosition.getY());
    }

    @Override
    public void Draw(Graphics g)
    {
        super.Draw(g);
    }

    public boolean isMouseOn()
    {
        return Collider().isCollidingWith(MouseInput.X, MouseInput.Y);
    }

    public boolean isClickingOn()
    {
        if(Collider().isCollidingWith(MouseInput.X, MouseInput.Y))
        {
            if(MouseInput.LEFT)
            {
                return true;
            }
        }
        return false;
    }

    public Vector2D getRealPosition()
    {
        return this.realPosition;
    }
    public void setRealPosition(Vector2D pos)
    {
        this.realPosition = pos;
    }
    public void setRealPosition(double x, double y)
    {
        this.realPosition = new Vector2D(x,y);
    }

    public void DisableRealPosition()
    {
        useRealposition = false;
    }
}
