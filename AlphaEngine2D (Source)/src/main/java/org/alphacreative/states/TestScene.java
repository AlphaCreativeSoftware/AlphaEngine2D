package org.alphacreative.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import org.alphacreative.gameobjects.GameObject;
import org.alphacreative.gameobjects.DefaultObject;
import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.gameobjects.entities.GroundObject;
import org.alphacreative.gameobjects.entities.Player;
import org.alphacreative.graphics.Assets;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;
import org.alphacreative.util.Mathf;
public class TestScene extends GameState
{
    private Player player;
    private GameObject backGround;
    private ArrayList <GameObject> boxes;
    public double timer;
    public double destroy_timer;

    private GameObject actualFloor_r;
    public TestScene()
    {
        super();
        GameManager.setTitle("Antonio");

        
        //SCENE HIERARCHY ORDER BY DRAW CALL
        sceneObjects.AddObject(new DefaultObject("BackGround",new Vector2D(0,0), new Vector2D(1,1), Assets.backGround[2])).setTag("BG_STATIC");
        backGround = sceneObjects.FindObjectWithName("BackGround");

        for (int i = 0; i <= 150; i++) //CLOUD GENERATOR
        {
            double randX = Mathf.RandomRange(-12000, 12000);
            double randY = Mathf.RandomRange(0, 150);
            double randScale = Mathf.RandomRange(0.1, 0.22);
            double randOpacity = Mathf.RandomRange(0.5,0.7);
            GameObject cl = sceneObjects.AddObject(new DefaultObject("Cloud 1", new Vector2D(randX,randY) , new Vector2D(randScale,randScale), Assets.cloud[0]));
            cl.setTag("CLOUD_BG");
            cl.setOpacity(randOpacity);
        }

        actualFloor_r = sceneObjects.AddObject(new DefaultObject("Floor", new Vector2D(0,0),new Vector2D(1,1) , Assets.floor[0]));
        actualFloor_r.setTag("Floor");

        //sceneObjects.AddObject(new Player("Player",new Vector2D(0,500),4,new Vector2D(0.35,0.35),Assets.player)).setTag("Player");
        player = (Player) sceneObjects.FindObjectWithName("Player"); //Ejemplo de buscar al jugador por nombre de objeto


        boxes = GameManager.SceneObjects().FindObjectsWithTag("Ground"); //Ejemplo de buscar objetos por tag

        GameObject objeto = sceneObjects.AddObject(new GameEntity("Entidad1", new Vector2D(0, 540), new Vector2D(0.2, 0.2), Assets.box));

    }

    private double startX = 600;
    private double startY = 450;
    @Override
    public void Update()
    {
        sceneObjects.Update();
        timer += Time.DeltaTime();
        Mathf.Clamp(timer,0,10);
        destroy_timer += Time.DeltaTime();

        if(player.getPosition().getX() >= GameManager.getScreenSize().getX()-650)
        {
            GameManager.SceneObjects().TranslateWorld_X_CLOUD(-600*Time.DeltaTime(),15);
        }
        else if (player.getPosition().getX() <= 350)
        {
            GameManager.SceneObjects().TranslateWorld_X_CLOUD(+600*Time.DeltaTime(),15);
        }

        if(timer >= 0.1 && (GameManager.WorldX()+startX < GameManager.getScreenSize().getX()))
        {
            GameObject box = sceneObjects.AddObject(new GroundObject("BOX1", new Vector2D(GameManager.WorldX()+startX,startY) , new Vector2D(0.25,0.25), Assets.wood_box));
            boxes.add(box);

            startX += Mathf.RandomRange(100, 1050);
            startY = Mathf.RandomRange(0, 200);

            for(GameObject boxObj : boxes)
            {
                if(boxObj.getPosition().getX() < -10)
                {
                    //sceneObjects.RemoveObject(boxObj);
                }
            }
            timer = 0;
        }
        for(GameObject boxObj : boxes)
        {
            //System.out.println("BOX POS: " + (boxObj.getPosition().getX() + GameManager.WorldX()));
            boxObj.checkLateralCollisionAndAdjustFix(player);

            //System.out.println("TOP: " + thisTop + " BOTTOM: " + thisBottom);
        }
        for (int i=0;i<boxes.size();i++) //BUCLE PARA DETECCION ENTRE SUS PROPIOS
        {
            for (int j=0;j < boxes.size();j++)
            {
                if(j != i)
                {
                    boxes.get(i).checkLateralCollisionAndAdjustFix(boxes.get(j));
                }
            }
            if(boxes.get(i).getPosition().getY() < 500)
            {
                boxes.get(i).TranslateY(5*Time.SyncTime());
            }
        }
        //System.out.println("PLAYER WORLD POS: " + (player.getPosition().getX() + GameManager.WorldX()));
        //System.out.println("world pos: " + (GameManager.WorldX()));
        //System.out.println("PLAYER SCREEN POS: " + (player.getPosition().getX()));

        if(actualFloor_r.getRight().getX() < GameManager.getScreenSize().getX()-190)
        {
            GameObject t_floor = sceneObjects.AddObject(new DefaultObject("Floor", new Vector2D(actualFloor_r.getRight().getX(),0),new Vector2D(1,1) , Assets.floor[0]));
            t_floor.setTag("Floor");
            actualFloor_r = t_floor;
            System.out.println("Llegado a la derecha");
            GameManager.SceneObjects().moveObjectBehindBackground("Floor","BG_STATIC"); //Esta parte mueve los indices de la array de los objetos de tag floor(suelo) hasta llegar a un objeto de tag BG_STATIC para asi evitar que se camufle con el fondo
        }
        ArrayList<GameObject> floors = GameManager.SceneObjects().FindObjectsWithTag("Floor");
        for (GameObject floor : floors)
        {
            if(floor.getRight().getX() < 30)
            {
                floor.Destroy();
            }
        }
        GameEntity entidad1 = (GameEntity) sceneObjects.FindObjectWithName("Entidad1");
        //entidad1.setScale(entidad1.getScaleX()+0.001*Time.SyncTime(), entidad1.getScaleY()+0.001*Time.SyncTime());

        for (GameEntity entity : sceneObjects.GameEntities())
        {
            if(entity != player)

                    if(entity.isCollidingFromAbove2D(player, 5))
                    {
                        System.out.println("Colisionando from abajo");
                    }
                    if(entity.isCollidingFromRight2D(player, 10))
                    {
                        player.setPosition(new Vector2D(entity.Collider().getLeft()-player.Collider().getScaleX()-21,player.getPosition().getY()));
                    }
                    if(entity.isCollidingFromLeft2D(player, 10))
                    {
                        player.setPosition(new Vector2D(entity.Collider().getRight()-16,player.getPosition().getY()));
                    }
                    if(entity.isCollidingFromBelow2D(player, 5))
                    {
                        //player.setPosition(new Vector2D(player.getPosition().getX(), entity.Collider().getTop() - player.Collider().getBottom()));
                        System.out.println("Colisionando from arriba");
                    }

            }
        }
    

    @Override
    public void Draw(Graphics g)
    {
        sceneObjects.Draw(g);

        g.setColor(Color.red);
        DrawSceneHierarchy(g);
    }

    public void DrawSceneHierarchy(Graphics g)
    {
        
        String objectsString = "";
        int pos_counter = 45;
        for(GameObject obj : sceneObjects.GameObjects())
        {
            if(obj.getTag() != "CLOUD")
            {
                objectsString = ("NAME: {" + obj.getName()+ "}" + " TAG: {" + obj.getTag() + "}");
                g.drawString(objectsString, 9, pos_counter);
                g.drawString("-----------------------------------------------------", 9, pos_counter+8);
                pos_counter += 25;
            }
        }

    }

}
