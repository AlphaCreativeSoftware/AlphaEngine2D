package org.alphacreative.gameobjects.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.graphics.Assets;
import org.alphacreative.graphics.Text;
import org.alphacreative.math.Vector2D;

public class TextUI extends GameEntity{

        private String text;
        private Color color;
        private boolean center;
        private Font font;
        private float fontscale;

        private float outlineGrosor = 0;
        private Color outlineColor;
        public TextUI(String name, Vector2D position,String text, Font font, Color color, boolean center, float fontscale)
        {
            super(name, position, new Vector2D(1,1), Assets.box);
            this.tag = "UI";
            this.text = text;
            this.font = font;
            this.color = color;
            this.center = center;
            this.fontscale = fontscale;
	    }
        public TextUI(String name, Vector2D position,String text, Font font, Color color, boolean center, float fontscale, float outlineGrosor, Color outlineColor)
        {
            super(name, position, new Vector2D(1,1), Assets.box);
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
        Stroke originaStroke = g2d.getStroke();
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) opacity));

        if(this.outlineGrosor > 0)
        {
            Text.DrawText(text, position, center, color,outlineColor,outlineGrosor, font, fontscale);
        }
        else
        {
            Text.DrawText(text, position, center, color, font, fontscale);
        }
        g2d.setFont(originalFont);
        g2d.setColor(originalColor);
        g2d.setComposite(originalComposite);
        g2d.setStroke(originaStroke);
	    }

        public void SetText(Object text)
        {
            String textStr = text.toString();
            this.text = textStr;
        }
        public String GetText()
        {
            return this.text;
        }


}
