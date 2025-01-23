package org.alphacreative.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;

public class Text {
	public static void DrawText(String text, Vector2D pos, boolean center, Color color, Font font,float fontSize) {
        Graphics2D g = (Graphics2D) GameManager.getWindow().getGraphics();

		g.setColor(color);
        Font newFont = font.deriveFont((float) fontSize);
		g.setFont(newFont);
		Vector2D position = new Vector2D(pos.getX(), pos.getY());
		
		if(center) {
			FontMetrics fm = g.getFontMetrics();
			position.setX(position.getX() - fm.stringWidth(text) / 2);
			position.setY(position.getY() - fm.getHeight() / 2);
		}
		g.drawString(text, (int)position.getX(), (int)position.getY());
	}

	public static void DrawText(String text, Vector2D pos, boolean center, Color color, Color outlineColor,float outlineGrosor, Font font, float fontSize) {
        Graphics2D g = (Graphics2D) GameManager.getWindow().getGraphics();
        
        Font newFont = font.deriveFont(fontSize);
        g.setFont(newFont);
        FontMetrics fm = g.getFontMetrics();
        
        Vector2D position = new Vector2D(pos.getX(), pos.getY());
        if (center) {
            position.setX(position.getX() - fm.stringWidth(text) / 2);
            position.setY(position.getY() - fm.getAscent() / 2);
        }
        
        // Draw outline
        g.setColor(outlineColor);
        g.setStroke(new BasicStroke(outlineGrosor)); // Grosor del borde
        g.draw(new java.awt.geom.AffineTransform().createTransformedShape(g.getFont().createGlyphVector(fm.getFontRenderContext(), text).getOutline((float) position.getX(), (float) position.getY())));
        
        // Draw text
        g.setColor(color);
        g.drawString(text, (int) position.getX(), (int) position.getY());
    }

	
}