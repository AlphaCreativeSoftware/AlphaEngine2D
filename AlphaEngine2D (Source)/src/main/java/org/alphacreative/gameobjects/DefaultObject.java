package org.alphacreative.gameobjects;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.alphacreative.math.Vector2D;
public class DefaultObject extends GameObject //Clase extendida de GameObject sin ningun atributo especial, solo usado para instancias simples
{
    public DefaultObject(String name, Vector2D position, Vector2D scale, BufferedImage texture) {
		super(name, position, scale, texture);
	}
    public DefaultObject(String name,String tag, Vector2D position, Vector2D scale, BufferedImage texture) {
		super(name, tag, position, scale, texture);
	}
    @Override
    public void Update(){ 
    }
    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }
}
