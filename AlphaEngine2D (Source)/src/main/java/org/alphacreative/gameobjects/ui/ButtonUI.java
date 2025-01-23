package org.alphacreative.gameobjects.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;
import org.alphacreative.util.Mathf;

public class ButtonUI extends Sprite{
    protected BufferedImage[] textureArray;
    public double increaseFactor = 0.2;
    public ButtonUI(String name, Vector2D position, Vector2D scale, BufferedImage[] texture, Vector2D realPosition) {
        super(name, position, scale, texture[0],realPosition);
        this.textureArray = texture;
	}

    @Override
    public void Update(){
        super.Update();
        if(isMouseOn()&&this.getOpacity()>0)
        {
            this.setScale(Mathf.Lerp(this.getScaleX(), targetScale.getX()+increaseFactor, 8*Time.DeltaTime()),Mathf.Lerp(this.getScaleY(), targetScale.getY()+increaseFactor, 8*Time.DeltaTime()));
            if(isClickingOn())
            {
                texture = textureArray[1];
            }
            else
            {
                texture = textureArray[0];
            }
        }
        else
        {
            this.setScale(Mathf.Lerp(this.getScaleX(), targetScale.getX(), 9*Time.DeltaTime()),Mathf.Lerp(this.getScaleY(), targetScale.getY(), 9*Time.DeltaTime()));
        }

    }
    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }

    public double getIncreaseFactor() {
        return increaseFactor;
    }

    public void setIncreaseFactor(double increaseFactor) {
        this.increaseFactor = increaseFactor;
    }

}
