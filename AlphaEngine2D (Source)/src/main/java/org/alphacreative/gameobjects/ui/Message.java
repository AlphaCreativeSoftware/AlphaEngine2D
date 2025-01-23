package org.alphacreative.gameobjects.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.graphics.Assets;
import org.alphacreative.graphics.Text;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;

public class Message extends GameEntity{
        private String text;
        private Color color;
        private boolean center;
        private boolean fade = false;
        private Font font;
        private float fontscale;

        private float alpha;

        private float outlineGrosor = 0;
        private Color outlineColor;

        private float vel = 1;
        public Message(String name, Vector2D position,String text, Font font, Color color, boolean center, float fontscale)
        {
        super(name, position, new Vector2D(1,1), Assets.box);
        this.opacity = 0;
        this.tag = "UI";
        this.text = text;
        this.font = font;
        this.color = color;
        this.center = center;
        this.fontscale = fontscale;
	    }
        public Message(String name, Vector2D position,String text, Font font, Color color, boolean center, float fontscale,float outlineGrosor, Color outlineColor)
        {
        super(name, position, new Vector2D(1,1), Assets.box);
        this.opacity = 0;
        this.tag = "UI";
        this.text = text;
        this.font = font;
        this.color = color;
        this.center = center;
        this.fontscale = fontscale;
        this.outlineGrosor = outlineGrosor;
        this.outlineColor = outlineColor;
	    }

    @Override
    public void Update()
    {

    }

    @Override
    public void Draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Font originalFont = g2d.getFont();
        Color originalColor = g2d.getColor();
        Composite originalComposite = g2d.getComposite();
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        if(this.outlineGrosor > 0)
        {
            Text.DrawText(text, position, center, color,outlineColor,outlineGrosor, font, fontscale);
        }
        else
        {
            Text.DrawText(text, position, center, color, font, fontscale);
        }
		
		position.setY(position.getY() - (40*vel)*Time.DeltaTime());
		
		if(fade)
			alpha -= (1*vel)*Time.DeltaTime();
		else
			alpha += (1*vel)*Time.DeltaTime();
		
		if(fade && alpha < 0) {
			Destroy();
		}
		
		if(!fade && alpha > 1) {
			fade = true;
			alpha = 1;
		}
        g2d.setFont(originalFont);
        g2d.setColor(originalColor);
        g2d.setComposite(originalComposite);
		
	}

    public void SetVelocity(float vel)
    {
        this.vel = vel;
    }

}
