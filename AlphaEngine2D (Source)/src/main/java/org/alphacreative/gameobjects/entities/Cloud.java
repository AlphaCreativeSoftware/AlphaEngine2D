package org.alphacreative.gameobjects.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.alphacreative.gameobjects.GameObject;
import org.alphacreative.math.Vector2D;

public class Cloud extends GameObject {
    private double cloudFrequencyTranslateDivisor;
    public Cloud(String name, Vector2D position, Vector2D scale, BufferedImage texture, double cloudFrequencyTranslateDivisor) {
        super(name, position, scale, texture);
        this.cloudFrequencyTranslateDivisor = cloudFrequencyTranslateDivisor;
        this.tag = "CLOUD_BG";
    }

    @Override
    public void Update() {
    }

    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }

    public double CloudFrequencyTranslateDivisor()
    {
        return cloudFrequencyTranslateDivisor;
    }
}