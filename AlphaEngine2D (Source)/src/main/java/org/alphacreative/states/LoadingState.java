package org.alphacreative.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.alphacreative.graphics.Assets;
import org.alphacreative.manager.GameManager;

public class LoadingState extends GameState{
    private Thread loadingThread;

    public LoadingState(Thread loadingThread)
    {
        super();
        this.loadingThread=loadingThread;
        this.loadingThread.start();
        GameManager.setTitle("Loading...");
    }


    @Override
    public void Update()
    {
        if(Assets.loaded)
        {
            System.out.println("Numero de Assets cargados: " + Assets.count);
            GameManager.ChangeScene(1);
        }
    }

    @Override
    public void Draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 1280, 720);
    }

}
