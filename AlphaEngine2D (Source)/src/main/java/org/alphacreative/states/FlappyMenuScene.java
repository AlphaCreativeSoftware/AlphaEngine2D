package org.alphacreative.states;


import java.awt.Color;
import java.awt.Graphics;
import org.alphacreative.manager.GameManager;
import org.alphacreative.gameobjects.DefaultObject;
import org.alphacreative.gameobjects.GameObject;
import org.alphacreative.gameobjects.entities.Cloud;
import org.alphacreative.gameobjects.ui.ButtonUI;
import org.alphacreative.gameobjects.ui.Sprite;
import org.alphacreative.gameobjects.ui.TextUI;
import org.alphacreative.graphics.Assets;
import org.alphacreative.graphics.Sound;
import org.alphacreative.input.KeyBoard;
import org.alphacreative.input.MouseInput;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;
import org.alphacreative.util.Mathf;


public class FlappyMenuScene extends GameState{
    public GameObject bg;
    public Sprite logo;
    public float load_bg_timer = 0;

    public float start_game_timer = 0;
    //BUTTONS
    public ButtonUI play_button;
    public boolean clicked_playButton = false;

    //PLAYER SELECTOR
    public Sprite beige_tab;
    public Sprite red_tab;
    public Sprite volume_tab;
    public Sprite player_sprite;

    public ButtonUI left_arrow;
    public ButtonUI right_arrow;
    public float arrow_timer;

    public int playerIndex = 0;

    public float particle_tiemr = 0;

    public float inpt_timer = 0;

    //AUDIO SETTING
    public Sprite slider_bg;
    public Sprite slider_button;

    public Sound bg_music;

    public FlappyMenuScene() {
        super();
        GameManager.setTitle("Menu");
        //GameManager.SetShowColliders(false);
        bg = sceneObjects.AddObject(new DefaultObject("Background","BG_STATIC",new Vector2D(), new Vector2D(1,1), Assets.backGround[0]));
        playerIndex = GameManager.DataManager().getIndex();



        //SPRITES SELECTOR INTIALIZE
        beige_tab = (Sprite) sceneObjects.AddObject(new Sprite("BEIGE_TAB",new Vector2D(1,1), new Vector2D(0.23,0.18), Assets.beige_tab,new Vector2D(0,120)));
        red_tab = (Sprite) sceneObjects.AddObject(new Sprite("RED_TAB",new Vector2D(1,1), new Vector2D(0.16,0.16), Assets.red_tab,new Vector2D(-460,120)));
        volume_tab = (Sprite) sceneObjects.AddObject(new Sprite("VOLUME_TAB",new Vector2D(1,1), new Vector2D(0.16,0.16), Assets.green_tab,new Vector2D(410,120)));

        slider_bg = (Sprite) sceneObjects.AddObject(new Sprite("SLIDER_BG",new Vector2D(1,1), new Vector2D(0.3,0.3), Assets.silder_bg,new Vector2D(410,60)));
        slider_button = (Sprite) sceneObjects.AddObject(new Sprite("SLIDER_BUT",new Vector2D(1,1), new Vector2D(0.11,0.11), Assets.slider_button,new Vector2D(490,60)));
        float positionValue = Mathf.NormalizeValue(GameManager.GetVolume(), -79, 0f, 960f, 1110f);
        slider_button.setPosition(positionValue,405);
        slider_button.DisableRealPosition();
        slider_button.SetColliderLocalScaleX(1.6);
        slider_button.SetColliderLocalScaleY(1.1);
        sceneObjects.AddObject(new TextUI("RECORD_UI", new Vector2D(GameManager.getScreenCenter().getX()+410,GameManager.getScreenCenter().getY()+40), "VOLUME", Assets.font_fruitpunch, Color.decode("#FFF699"), true, 33,7,Color.decode("#009500")));
        bg_music = new Sound(Assets.bg_music);
        bg_music.Loop();

        //sceneObjects.AddObject(new TextUI("SELECT_TEXT", new Vector2D(GameManager.getScreenCenter().getX(),GameManager.getScreenCenter().getY()-100), "SELECT", Assets.font_fruitpunch, Color.WHITE, true, 34,7,Color.decode("#8E8251")));
        player_sprite = (Sprite) sceneObjects.AddObject(new Sprite("PLAYER_SPRITE",new Vector2D(), new Vector2D(0.5,0.5), Assets.player_flappy[playerIndex],new Vector2D(0,40)));

        left_arrow = (ButtonUI) sceneObjects.AddObject(new ButtonUI("LEFT_ARROW",new Vector2D(), new Vector2D(0.24,0.24), Assets.left_arrow_button,new Vector2D(-140,20)));
        left_arrow.setIncreaseFactor(0.05);

        right_arrow = (ButtonUI) sceneObjects.AddObject(new ButtonUI("RIGHT_ARROW",new Vector2D(), new Vector2D(0.24,0.24), Assets.right_arrow_button,new Vector2D(140,20)));
        right_arrow.setIncreaseFactor(0.05);

        //BUTTONS INTIALIZE
        play_button = (ButtonUI) sceneObjects.AddObject(new ButtonUI("Background",new Vector2D(), new Vector2D(0.16,0.16), Assets.play_button,new Vector2D(0,200)));
        play_button.SetColliderLocalScale(0.9,0.91);
        play_button.setIncreaseFactor(0.02);

        logo = (Sprite) sceneObjects.AddObject(new Sprite("BEIGE_TAB",new Vector2D(1,1), new Vector2D(0.2,0.2), Assets.logo,new Vector2D(0,-200)));

        sceneObjects.AddObject(new TextUI("Texto", new Vector2D(GameManager.getScreenCenter().getX()-460,GameManager.getScreenCenter().getY()+40), "TOP SCORE", Assets.font_fruitpunch, Color.WHITE, true, 34,7,Color.decode("#900C3F")));
        TextUI recordUI = (TextUI) sceneObjects.AddObject(new TextUI("RECORD_UI", new Vector2D(GameManager.getScreenCenter().getX()-460,GameManager.getScreenCenter().getY()+115), "0", Assets.font_fruitpunch, Color.decode("#FFF37A"), true, 46,7,Color.decode("#AB9E2B")));
        recordUI.SetText(GameManager.DataManager().getRecord());

        sceneObjects.AddObject(new TextUI("Texto", new Vector2D(GameManager.getScreenCenter().getX()-460,GameManager.getScreenCenter().getY()+170), "LAST SCORE", Assets.font_fruitpunch, Color.WHITE, true, 26,7,Color.decode("#900C3F")));
        TextUI lastScoreUI = (TextUI) sceneObjects.AddObject(new TextUI("RECORD_UI", new Vector2D(GameManager.getScreenCenter().getX()-460,GameManager.getScreenCenter().getY()+220), "0", Assets.font_fruitpunch, Color.decode("#FFF699"), true, 33,7,Color.decode("#900C3F")));
        lastScoreUI.SetText(GameManager.DataManager().getLastScore());


        for (int i = 0; i <= 50; i++) //CLOUD GENERATOR
        {
            double randX = Mathf.RandomRange(-100, 10000);
            double randY = Mathf.RandomRange(0, 150);
            double randScale = Mathf.RandomRange(0.1, 0.25);
            double randOpacity = Mathf.RandomRange(0.5,0.7);
            double randTranslatorFrequency = Mathf.NormalizeValue((float)randScale, 0.1f, 0.25f, 18f, 10f);
            GameObject cl = sceneObjects.AddObject(new Cloud("Cloud", new Vector2D(randX,randY) , new Vector2D(randScale,randScale), Assets.cloud[0],randTranslatorFrequency));
            //El trasladodr divisor es lo que hace en las nubes del mapa un efecto tridimensional, generando un numero atleatorio para la escala de la nube del cual luego normalizaremos para saber si las nubes estan cera o lejos
            cl.setOpacity(randOpacity);
        }
    }

    @Override
    public void Update()
	{
        sceneObjects.Update();

        GameManager.SceneObjects().TranslateWorld_X_CLOUD_HQ(-(200)*Time.DeltaTime());

        //SOUND
        if(slider_button.isClickingOn())
        slider_button.setPosition(MouseInput.X-slider_button.getRealWidth()/2,405);

        float volumeValue = Mathf.NormalizeValue((float)slider_button.getPosition().getX(), 960f, 1110f, -79f, 0f);
        
        slider_button.setPosition(Mathf.Clamp(slider_button.getPosition().getX(), 960, 1110),405);
        //slider_button.setRealPosition(Mathf.Clamp(slider_button.getRealPosition().getX(), 400, 490),Mathf.Clamp(slider_button.getRealPosition().getY(), 60, 60));
        bg_music.ChangeVolume(GameManager.GetVolume());
        GameManager.SetVolume(volumeValue);
        arrow_timer += Time.DeltaTime();
        arrow_timer = Mathf.Clamp(arrow_timer, 0, 5);
        if(arrow_timer >= 0.15)
        {
            if(left_arrow.isClickingOn())
            {
                ChangePlayer(-1);
                arrow_timer = 0;
            }

            if(right_arrow.isClickingOn())
            {
                ChangePlayer(1);
                arrow_timer = 0;
            }

        }

        if(play_button.isClickingOn()&&!clicked_playButton)
        {
            System.out.println("Listo para jugar");

            clicked_playButton = true;
        }
        if(clicked_playButton)
        {
            start_game_timer += Time.DeltaTime();
            if(start_game_timer >= 0.5)
            {
                GameManager.ChangeScene(3);
                bg_music.Stop();
                start_game_timer = 0;
            }
        }
        inpt_timer += Time.DeltaTime();
        inpt_timer = Mathf.Clamp(inpt_timer, 0, 10);
        if(KeyBoard.D && inpt_timer >= 0.2) //PULSA LA LETRA K PARA DESACTIVAR/ACTIVAR EL MODOD DE DEBUG
        {
            GameManager.SetDebug(!GameManager.IsDebug());
            inpt_timer = 0;
        }

        if(KeyBoard.C && inpt_timer >= 0.2) //PULSA LA LETRA I PARA VER/DEJAR DE VER LOS HITBOX DE LOS OBJETOS
        {
            GameManager.SetShowColliders(!GameManager.ShowColliders());
            inpt_timer = 0;
        }
            
	}
    @Override
	public void Draw(Graphics g)
	{
        sceneObjects.Draw(g);
        DrawSceneHierarchy(g);
		//Objetos draw
	}


    public void ChangePlayer(int indexIncrease)
    {
        playerIndex = GameManager.DataManager().getIndex();
        playerIndex += indexIncrease;
        if(playerIndex > Assets.player_flappy.length-1)
        {
            playerIndex = 0;
        }
        else if (playerIndex < 0)
        {
            playerIndex = Assets.player_flappy.length-1;
        }
        System.out.println("Current Player: "+ playerIndex);
        player_sprite.setTexture(Assets.player_flappy[playerIndex]);
        GameManager.DataManager().saveIndex(playerIndex);

    }
}
