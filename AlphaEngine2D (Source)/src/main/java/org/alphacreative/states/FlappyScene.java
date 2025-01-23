package org.alphacreative.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import org.alphacreative.gameobjects.GameObject;
import org.alphacreative.gameobjects.DefaultObject;
import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.gameobjects.entities.Cloud;
import org.alphacreative.gameobjects.flappy.ExplostionParticles;
import org.alphacreative.gameobjects.flappy.FlappyPipeTrigger;
import org.alphacreative.gameobjects.flappy.PerfumPowerUp;
import org.alphacreative.gameobjects.flappy.Player_Flappy;
import org.alphacreative.gameobjects.ui.ButtonUI;
import org.alphacreative.gameobjects.ui.Sprite;
import org.alphacreative.gameobjects.ui.TextUI;
import org.alphacreative.graphics.Assets;
import org.alphacreative.graphics.Sound;
import org.alphacreative.input.KeyBoard;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;
import org.alphacreative.util.Mathf;
public class FlappyScene extends GameState
{
    public float load_timer = 0;

    private int record = 0;
    private int score = 0;

    public int stage = 0;

    public TextUI scoreUI;
    public TextUI recordUI;
    private GameObject newRecordUI;

    private Player_Flappy player;
    private int playerIndex = 0;
    private GameObject DAY_BG;
    private GameObject NIGHT_BG;


    private double day_value = 0;
    public boolean day = true;


    public double timer;
    public double destroy_timer;

    private GameObject actualFloor_r;

    public double game_velocity = 400;
    public double game_velocity_multi = 1;

    public boolean turboMode = false;
    public float turbo_timer = 0;

    public boolean inmortal = false;
    public boolean gameover = false;


    public float inpt_timer = 0;

    public float k_key_timer = 0; //Para pulsar teclas
    public float i_key_timer = 0; //Para pulsar teclas

    public float internal_jobs_timer = 0;

    public float restart_game_timer = 0;


    //GAMEOVER Objects
    public Sprite black_bg;
    public Sprite beige_tab;
    public TextUI finalScoreUI;
    public TextUI finalRecordUI;
    public ButtonUI retry_button;
    public ButtonUI menu_button;

    public boolean hasCliked = false;
    public float loadSceneTimer = 0;

    public Sound sound;
    public FlappyScene()
    {
        super();
        GameManager.setTitle("Flappy");
        record = GameManager.DataManager().getRecord();
        playerIndex = GameManager.DataManager().getIndex();
        GameManager.DataManager().saveLastScore(0);


        //SCENE HIERARCHY ORDER BY DRAW CALL
        DAY_BG = (GameObject) sceneObjects.AddObject(new DefaultObject("DAY_BACKGROUND",new Vector2D(0,0), new Vector2D(1,1), Assets.backGround[0]));
        NIGHT_BG = sceneObjects.AddObject(new DefaultObject("NIGHT_BACKGROUND",new Vector2D(0,0), new Vector2D(1,1), Assets.backGround[1]));
        DAY_BG.setTag("BG_STATIC");
        NIGHT_BG.setTag("BG_STATIC");
        NIGHT_BG.setOpacity(0);

        //sceneObjects.AddObject(new Player("Player",new Vector2D(0,500),4,new Vector2D(0.35,0.35),Assets.player)).setTag("Player");
        //player = (Player) sceneObjects.FindObjectWithName("Player"); //Ejemplo de buscar al jugador por nombre de objeto

        for (int i = 0; i <= 400; i++) //CLOUD GENERATOR
        {
            double randX = Mathf.RandomRange(-100, 50000);
            double randY = Mathf.RandomRange(0, 150);
            double randScale = Mathf.RandomRange(0.1, 0.25);
            double randOpacity = Mathf.RandomRange(0.5,0.7);
            double randTranslatorFrequency = Mathf.NormalizeValue((float)randScale, 0.1f, 0.25f, 18f, 10f);
            GameObject cl = sceneObjects.AddObject(new Cloud("Cloud", new Vector2D(randX,randY) , new Vector2D(randScale,randScale), Assets.cloud[0],randTranslatorFrequency));
            //El trasladodr divisor es lo que hace en las nubes del mapa un efecto tridimensional, generando un numero atleatorio para la escala de la nube del cual luego normalizaremos para saber si las nubes estan cera o lejos
            cl.setOpacity(randOpacity);
        }
        actualFloor_r = sceneObjects.AddObject(new DefaultObject("Floor", new Vector2D(0,-30),new Vector2D(1,1) , Assets.floor[0]));
        actualFloor_r.setTag("Floor");
        actualFloor_r.setOpacity(0);
        
        //Instanciamos el jugador y ajustamos su trigger2d(Hitbox)
        player = (Player_Flappy) sceneObjects.AddObject(new Player_Flappy("Player",new Vector2D(210,300),new Vector2D(0.48,0.48),playerIndex));
        player.setTag("Player");
        player.SetColliderLocalPositionY(-25);
        player.SetColliderLocalScaleY(0.48);
        player.SetColliderLocalScaleX(0.62);


        GameObject scoreBG = sceneObjects.AddObject(new DefaultObject("SCORE_BG",new Vector2D(0,0), new Vector2D(0.4,0.6), Assets.green_ribbon));
        scoreBG.setPosition(GameManager.getScreenCenter().getX()-scoreBG.getRight().getX()/2,10);
        scoreBG.setTag("UI");
        newRecordUI = sceneObjects.AddObject(new DefaultObject("NEW_RECORD",new Vector2D(0,0), new Vector2D(0.16,0.16), Assets.new_record));
        newRecordUI.setPosition((GameManager.getScreenCenter().getX()-newRecordUI.getRight().getX()/2)+85,80);
        newRecordUI.setTag("UI");
        HideNewRecord();

        scoreUI = (TextUI) sceneObjects.AddObject(new TextUI("Texto Prueba", new Vector2D(GameManager.getScreenCenter().getX(),GameManager.getScreenCenter().getY()-285), "0", Assets.font_fruitpunch, Color.WHITE, true, 34,7,Color.decode("#355E3B")));

        record = GameManager.DataManager().getRecord();
        recordUI = (TextUI) sceneObjects.AddObject(new TextUI("Texto Prueba", new Vector2D(GameManager.getScreenCenter().getX(),GameManager.getScreenCenter().getY()-260), "0", Assets.font_fruitpunch, Color.decode("#EBFF90"), true, 20,7,Color.decode("#355E3B")));


        //GameOver Objects
        black_bg = (Sprite) sceneObjects.AddObject(new Sprite("Background",new Vector2D(), new Vector2D(1.1,1.1), Assets.black_bg,new Vector2D(0,0)));
        black_bg.setOpacity(0);

        beige_tab = (Sprite) sceneObjects.AddObject(new Sprite("Background",new Vector2D(), new Vector2D(0,0), Assets.beige_tab,new Vector2D(0,0)));
        beige_tab.setOpacity(0);

        finalScoreUI = (TextUI) sceneObjects.AddObject(new TextUI("FINAL_SCORE_UI", new Vector2D(GameManager.getScreenCenter().getX(),GameManager.getScreenCenter().getY()-120), "0", Assets.font_fruitpunch, Color.WHITE, true, 65,10,Color.decode("#8E8251")));
        finalScoreUI.setOpacity(0);

        finalRecordUI = (TextUI) sceneObjects.AddObject(new TextUI("FINAL_RECORD_UI", new Vector2D(GameManager.getScreenCenter().getX(),GameManager.getScreenCenter().getY()), "0", Assets.font_fruitpunch, Color.decode("#FFD31D"), true, 75,10,Color.decode("#BD9E1D")));
        finalRecordUI.setOpacity(0);

        retry_button = (ButtonUI) sceneObjects.AddObject(new ButtonUI("Background",new Vector2D(), new Vector2D(0.12,0.12), Assets.retry_button,new Vector2D(0,200)));
        retry_button.SetColliderLocalScaleY(0.9);
        retry_button.setIncreaseFactor(0.025);
        retry_button.setOpacity(0);

        menu_button = (ButtonUI) sceneObjects.AddObject(new ButtonUI("Background",new Vector2D(), new Vector2D(0.14,0.14), Assets.menu_button,new Vector2D(0,90)));
        menu_button.SetColliderLocalScaleY(0.9);
        menu_button.setIncreaseFactor(0.025);
        menu_button.setOpacity(0);

        //SOUNDS
        sound = new Sound(Assets.bell_sound);

        System.out.println("Record: "+record);
    }
    @Override
    public void Update()
    {
        
        
        sceneObjects.Update();
        timer += Time.DeltaTime();
        inpt_timer += Time.DeltaTime();
        internal_jobs_timer += Time.DeltaTime();
        Mathf.Clamp(internal_jobs_timer, 0, 10);
        Mathf.Clamp(timer,0,10);
        UpdateDayNight();
        GameInternalJobs();
        GameManager.SceneObjects().TranslateWorld_X_CLOUD_HQ(-(game_velocity*game_velocity_multi)*Time.DeltaTime());
        player.TranslateX((game_velocity*game_velocity_multi)*Time.DeltaTime());
        if(player.isAlive())
        {
            game_velocity += 0.1*Time.SyncTime();
            game_velocity = Mathf.Clamp(game_velocity, 50, 850);
        }
        else
        {
            game_velocity = Mathf.Lerp(game_velocity, 0, 0.05*Time.SyncTime());
        }

        //System.out.println(game_velocity);
        //System.out.println(GameManager.WorldX());

        //Los parametros indican la altura maxima y minima, y el espacio minimo y máximo que debe tener el jugador para pasar
        SpawnPipes(-380,-110,255,380);// Llamada a funcíon "Generar Tuberias", Se generan cuando la parte izquierda de la imagen transparente de fondo ha sobrepasado eje 0 de la pantalla
        

        ArrayList<GameObject> floors = GameManager.SceneObjects().FindObjectsWithTag("Floor");
        ArrayList<GameObject> pipes = GameManager.SceneObjects().FindObjectsWithTag("Obstacle");
        for (GameObject floor : floors)
        {
            if(floor.getRight().getX() < 5)
            {
                floor.Destroy();
            }
        } 
        for (GameObject pipe : pipes)
        {
            if(pipe.getRight().getX() < -100)
            {
                pipe.Destroy();
            }
            if(player.isCollidingWithEntity2D((GameEntity)pipe) && player.isAlive()) 
            {
                if(!inmortal)
                {
                    sceneObjects.AddObject(new ExplostionParticles("Explosion_Particles", new Vector2D(player.getPosition().getX(),player.getPosition().getY()-40),new Vector2D(1.4,1.4)));
                    player.Kill();
                    gameover = true;
                }
            }
            GameEntity ge_pipe = (GameEntity)pipe;
            if(turboMode)
            {
                if(player.Collider().getRight() > ge_pipe.Collider().getLeft()-200)
                {
                    if(pipe.getName().equals("Pipe(DOWN)")&& pipe.GetRotationEuler()< 19)
                    {
                        pipe.SetRotationEuler(pipe.GetRotationEuler() + (10)*Time.DeltaTime());
                        ge_pipe.SetColliderLocalScale(1, 0.9);
                        ge_pipe.TranslateY(-75*Time.DeltaTime());
                    }
                    else if (pipe.getName().equals("Pipe(UP)") && pipe.GetRotationEuler()> -19)
                    { 
                        pipe.SetRotationEuler(pipe.GetRotationEuler() - (10)*Time.DeltaTime());
                        ge_pipe.SetColliderLocalScale(1, 0.9);
                        ge_pipe.TranslateY(75*Time.DeltaTime());
                    }
                }
            }
            else
            {
                //codigo
            }
            if(ge_pipe.isCollidingWithEntity2D(player))
            {
                if(pipe.getName().equals("Pipe(DOWN)"))
                {
                    if(ge_pipe.isCollidingFromRight2D(player, 70))
                    {
                        if(ge_pipe.GetRotationEuler() < 25)
                        pipe.Rotate(0.04*Time.SyncTime());
                    }
                    else if (ge_pipe.isCollidingFromBelow2D(player, 70))
                    {
                        if(ge_pipe.GetRotationEuler() < 25)
                        pipe.Rotate(0.020*Time.SyncTime());
                    }
                    
                }
                else if(pipe.getName().equals("Pipe(UP)"))
                {
                    if(ge_pipe.isCollidingFromRight2D(player, 70))
                    {
                        if(ge_pipe.GetRotationEuler() > -25)
                        pipe.Rotate(-0.04*Time.SyncTime());
                    }
                    
                    else if (ge_pipe.isCollidingFromAbove2D(player, 70))
                    {
                        if(ge_pipe.GetRotationEuler() > -25)
                        pipe.Rotate(-0.015*Time.SyncTime());
                    }
                    
                }
            }

        }

        /*if(KeyBoard.O && inpt_timer >= 0.5)
        {
            day = !day;
            inpt_timer = 0;
        }
        if(KeyBoard.J && inpt_timer >= 0.5)
        {
            turboMode = !turboMode;
            inpt_timer = 0;
        }*/
        k_key_timer += Time.DeltaTime();
        i_key_timer += Time.DeltaTime();
        Mathf.Clamp(k_key_timer, 0, 10);
        Mathf.Clamp(i_key_timer, 0, 10);
        if(KeyBoard.D && k_key_timer >= 0.2) //PULSA LA LETRA K PARA DESACTIVAR/ACTIVAR EL MODOD DE DEBUG
        {
            GameManager.SetDebug(!GameManager.IsDebug());
            k_key_timer = 0;
        }

        if(KeyBoard.C && i_key_timer >= 0.2) //PULSA LA LETRA I PARA VER/DEJAR DE VER LOS HITBOX DE LOS OBJETOS
        {
            GameManager.SetShowColliders(!GameManager.ShowColliders());
            i_key_timer = 0;
        }

        //TURBO
        if(turboMode)
        {
            turbo_timer += Time.DeltaTime();
            game_velocity_multi = Mathf.Lerp(game_velocity_multi, 1.7, 0.7*Time.DeltaTime());
            if(turbo_timer >= 12)
            {
                turboMode = false;
                day = true;
                turbo_timer = 0;
            }
        }
        else
        {
            game_velocity_multi = Mathf.Lerp(game_velocity_multi, 1, 0.7*Time.DeltaTime());
        }

        if(!player.isAlive())
        {
            restart_game_timer += Time.DeltaTime();
            finalScoreUI.SetText(score);
            finalRecordUI.SetText(record);
            if (restart_game_timer >= 0.2)
            {
                if(beige_tab.getOpacity() < 1)
                {
                    beige_tab.increaseOpacity(1*Time.DeltaTime());
                }
                if(beige_tab.getScaleX()< 0.25 && beige_tab.getScaleY()<0.25)
                {
                    beige_tab.setScale(beige_tab.getScaleX()+0.8*Time.DeltaTime(), beige_tab.getScaleY()+0.8*Time.DeltaTime());
                }
            }

            if(restart_game_timer >= 0.5)
            {
                if(gameover)
                {
                    if(black_bg.getOpacity()< 0.5)
                    {
                        black_bg.increaseOpacity(1*Time.DeltaTime());
                    }

                    if(retry_button.getOpacity()<1)
                    {
                        retry_button.increaseOpacity(1*Time.DeltaTime());
                    }
                    if(menu_button.getOpacity()<1)
                    {
                        menu_button.increaseOpacity(1*Time.DeltaTime());
                    }
                    if(finalScoreUI.getOpacity()<1)
                    {
                        finalScoreUI.increaseOpacity(1*Time.DeltaTime());
                    }
                    if(finalRecordUI.getOpacity()<1)
                    {
                        finalRecordUI.increaseOpacity(1*Time.DeltaTime());
                    }
                    if(retry_button.isClickingOn())
                    {
                        GameManager.ChangeScene(3);
                    }
                    if(menu_button.isClickingOn())
                    {
                        GameManager.ChangeScene(2);
                    }
                    
        
                }
            }
        }
 
        scoreUI.SetText(score);
        recordUI.SetText(record);
    }
    @Override
    public void Draw(Graphics g)
    {
        sceneObjects.Draw(g);
        g.setColor(Color.red);
        DrawSceneHierarchy(g);
    }

    public void SpawnPipes(int minUp, int maxUp, int playerMinSpace, int playerMaxSpace)
    {
        if(actualFloor_r.getRight().getX() < GameManager.getScreenSize().getX())
        {
            

            GameObject t_floor = sceneObjects.AddObject(new DefaultObject("Floor", new Vector2D(actualFloor_r.getRight().getX(),actualFloor_r.getPosition().getY()),new Vector2D(1,1) , Assets.floor[0]));
            t_floor.setTag("Floor");
            t_floor.setOpacity(actualFloor_r.getOpacity());
            actualFloor_r = t_floor;
            stage += 1;
            System.out.println("Generando Tuberias, Stage: " + stage);
            GameManager.SceneObjects().moveObjectBehindBackground("Floor","BG_STATIC"); //Esta parte mueve los indices de la array de los objetos de tag floor(suelo) hasta llegar a un objeto de tag BG_STATIC para asi evitar que se camufle con el fondo

            float randomUp_1 = Mathf.RandomRange(minUp, maxUp);
            GameEntity objeto_tub_0 = (GameEntity)sceneObjects.AddObject(new GameEntity("Pipe(UP)", new Vector2D(t_floor.getPosition().getX()+300, randomUp_1), new Vector2D(0.7, 0.7), Assets.pipe_down[0]));
            objeto_tub_0.setTag("Obstacle");
            objeto_tub_0.SetColliderLocalScaleX(0.9);
            //objeto_tub_0.SetRotationEuler(180);
            GameManager.SceneObjects().moveObjectBehindBackground("Obstacle","CLOUD_BG");
            //System.out.println("TUBERIA 1(UP): " + randomUp_1);
            //System.out.println("TUBERIA 1(UP) (BOTTOM) " + objeto_tub_0.Collider().getBottom());

            

            float randomDown_1 = Mathf.RandomRange((float)objeto_tub_0.Collider().getBottom()+playerMinSpace, (float) objeto_tub_0.Collider().getBottom()+playerMaxSpace); //Esto genera un numero atleatorio partiendo de la base del collider de la tuberia de arriba, asi siempre habra espacio para el jugador
            GameEntity objeto_tub_1 = (GameEntity)sceneObjects.AddObject(new GameEntity("Pipe(DOWN)", new Vector2D(t_floor.getPosition().getX()+300, randomDown_1), new Vector2D(0.7, 0.7), Assets.pipe_up[0]));
            objeto_tub_1.setTag("Obstacle");
            objeto_tub_1.SetColliderLocalScaleX(0.9);
            GameManager.SceneObjects().moveObjectBehindBackground("Obstacle","CLOUD_BG");

            GameObject trigger_box = sceneObjects.AddObject(new FlappyPipeTrigger("TRIGGER_BOX", new Vector2D(objeto_tub_0.Collider().getLeft()+160,0),new Vector2D(0.1,5) , Assets.box));
            trigger_box.setOpacity(0);

            if(!turboMode)
            {
                if((Mathf.RandomBool(0.2f) && stage >= 8) || stage == 1)
                {
                    PerfumPowerUp perf = (PerfumPowerUp)sceneObjects.AddObject(new PerfumPowerUp("PERFUME_SQL",new Vector2D(objeto_tub_0.getPosition().getX(),randomDown_1-270), new Vector2D(0.38,0.38), Assets.powerups[0]));
                    perf.setTag("PERFUME_SQL");
                    System.out.println(perf.Collider().getScaleX());
                    System.out.println(perf.Collider().getScaleY());
                }
                else
                {
                    System.out.println("no generado perfume sql");
                }
                
            }

            float randomUp_2 = Mathf.RandomRange(minUp, maxUp);
            GameEntity objeto_tub_2 = (GameEntity)sceneObjects.AddObject(new GameEntity("Pipe(UP)", new Vector2D(t_floor.getPosition().getX()+950, randomUp_2), new Vector2D(0.7, 0.7), Assets.pipe_down[0]));
            objeto_tub_2.setTag("Obstacle");
            objeto_tub_2.SetColliderLocalScaleX(0.9);
            //objeto_tub_2.SetRotationEuler(180);
            GameManager.SceneObjects().moveObjectBehindBackground("Obstacle","CLOUD_BG");

            float randomDown_2 = Mathf.RandomRange((float)objeto_tub_2.Collider().getBottom()+playerMinSpace, (float) objeto_tub_2.Collider().getBottom()+playerMaxSpace);
            GameEntity objeto_tub_3 = (GameEntity)sceneObjects.AddObject(new GameEntity("Pipe(DOWN)", new Vector2D(t_floor.getPosition().getX()+950, randomDown_2), new Vector2D(0.7, 0.7), Assets.pipe_up[0]));
            objeto_tub_3.setTag("Obstacle");
            objeto_tub_3.SetColliderLocalScaleX(0.9);
            GameManager.SceneObjects().moveObjectBehindBackground("Obstacle","CLOUD_BG");

            GameObject trigger_box_2 = sceneObjects.AddObject(new FlappyPipeTrigger("TRIGGER_BOX", new Vector2D(objeto_tub_2.Collider().getLeft()+160,0),new Vector2D(0.1,5) , Assets.box));
            trigger_box_2.setOpacity(0);
           
        } 

        
    }

    public void GameInternalJobs() 
    {
        if(internal_jobs_timer >=0.7)
        {
            GameManager.SceneObjects().moveObjectBehindBackground("Floor","BG_STATIC");
            GameManager.SceneObjects().moveObjectBehindBackground("Obstacle","CLOUD_BG");
            internal_jobs_timer = 0;
        }
        for (GameObject object : sceneObjects.GameObjects())
        {
            if(object.getPosition().getX()+object.getTexture().getWidth() < -200) //Hola Alberto, esta funcion está dentro de los trabajos internos del juego que elimina aquellos objetos no visibles en pantalla, para limpiar memoria, generalmente deberian limpiarse ya que asi lo he programado anteriormente pero por seguridad llamo a esta funcion para asegurar que no hayan fugas de memoria
            {
                object.Destroy();
            }
        }

    }

    public void UpdateDayNight()
    {
        NIGHT_BG.setOpacity(day_value);

        if(day)
        {
            day_value=Mathf.Lerp(day_value, 0, 0.005f);
        }
        else
        {
            day_value=Mathf.Lerp(day_value, 1, 0.005f);
        }
    }

    public void EnableTurbo()
    {
        turboMode = true;
    }

    public void DisableTurbo()
    {
        turboMode = false;
    }
    public void ShowNewRecord()
    {
        newRecordUI.setOpacity(1);
    }
    public void HideNewRecord()
    {
        newRecordUI.setOpacity(0);
    }
    public int getScore()
    {
        return this.score;
    }
    public void setScore(int newScore)
    {
        this.score = newScore;
    }
    public int getRecord()
    {
        return this.record;
    }
    public void setRecord(int newRecord)
    {
        this.record = newRecord;
    }
    public void IncreaseScore()
    {
        score += 1;
    }

    //SOUNDS
    public void SetSoundVolume()
    {
        sound.ChangeVolume(GameManager.GetVolume());
    }
    public void PlayBellSound()
    {
        SetSoundVolume();
        sound.Stop();
        sound.Play();
    }
}