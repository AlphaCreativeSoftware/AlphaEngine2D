package org.alphacreative.graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;

public class Assets {
    public static boolean loaded = false;
    public static float count = 0;
    public static float MAX_COUNT = 97;

    public static BufferedImage introImage;
    public static BufferedImage introBg;

    public static BufferedImage flappy_menu_BG;

    
    public static BufferedImage[] powerups;
    public static BufferedImage[] player_flappy;
    public static BufferedImage[] player_flappy_damaged;
    public static BufferedImage[] player_explosion;

    public static BufferedImage[] fireAnim;
    public static BufferedImage[] laser_beam;

    public static BufferedImage background_prado;
    public static BufferedImage trees_bg;


    public static BufferedImage[] cloud;

    public static BufferedImage[] backGround;

    public static BufferedImage[] floor;
    public static BufferedImage box;
    public static BufferedImage wood_box;

    public static BufferedImage[] pipe_up;
    public static BufferedImage[] pipe_down;

    




    //UI
    public static BufferedImage logo;
    public static BufferedImage black_bg;
    public static BufferedImage beige_tab;
    public static BufferedImage red_tab;
    public static BufferedImage green_tab;
    public static BufferedImage green_ribbon;
    public static BufferedImage new_record;
    public static BufferedImage wood_ui;

    //UI MENU
    public static BufferedImage[] play_button;
    public static BufferedImage[] retry_button;
    public static BufferedImage[] menu_button;
    public static BufferedImage[] right_arrow_button;
    public static BufferedImage[] left_arrow_button;

    public static BufferedImage silder_bg;
    public static BufferedImage slider_button;

    //FONTS
    public static Font font_guarane;
    public static Font font_boohong;
    public static Font font_fruitpunch;

    //SOUNDS
    public static Clip intro_clip; 
    public static Clip bg_music;

    public static Clip bell_sound;
    public static Clip fly_sound;
    public static Clip kick_sound;
    public static void init()
    {
        introImage = LoadImage("/alphaengine/splashHD.png");
        introBg = LoadImage("/alphaengine/bg.png");

        //player = LoadImage("/player/antonio_2.png");

        powerups = new BufferedImage[1];
        for(int i = 0; i < powerups.length; i++)
        {
            powerups[i] = LoadImage("/powerups/"+(i)+".png");
        }
        fireAnim = new BufferedImage[10];
        for(int i = 0; i < fireAnim.length; i++)
        {
            fireAnim[i] = LoadImage("/player/fire/"+(i)+".png");
        }


        player_flappy = new BufferedImage[4];
        for(int i = 0; i < player_flappy.length; i++)
        {
            player_flappy[i] = LoadImage("/player/spaceship/"+(i)+".png");
        }

        player_flappy_damaged = new BufferedImage[4];
        for(int i = 0; i < player_flappy.length; i++)
        {
            player_flappy_damaged[i] = LoadImage("/player/spaceship_damaged/"+(i)+".png");
        }

        //PLAYER EXPLODE PARTICLES
        player_explosion = new BufferedImage[26];
        for(int i = 0; i < player_explosion.length; i++)
        {
            player_explosion[i] = LoadImage("/player/player_explosion/"+(i)+".png");
        }

        background_prado = LoadImage("/background/bg1.png");
        trees_bg = LoadImage("/background/trees_bg.png");
        cloud = new BufferedImage[1];

        cloud[0] = LoadImage("/background/cloud.png");

        backGround = new BufferedImage[2];
        backGround[0] = LoadImage("/background/SKY.png");
        backGround[1] = LoadImage("/background/SKY_NIGHT.png");

        floor = new BufferedImage[1];
        floor[0] = LoadImage("/background/floor_street.png");

        box = LoadImage("/basic/box.png");
        wood_box = LoadImage("/basic/wood_box.png");

        pipe_up = new BufferedImage[1];
        pipe_up[0] = LoadImage("/obstacles/pipe0_up.png");

        pipe_down = new BufferedImage[1];
        pipe_down[0] = LoadImage("/obstacles/pipe0_down.png");

        laser_beam = new BufferedImage[1];
        laser_beam[0] = LoadImage("/ammu/laser_beam.png");

        logo = LoadImage("/ui/logo.png");
        black_bg = LoadImage("/ui/black_bg.png");
        beige_tab = LoadImage("/ui/tabs/beige_tab.png");
        red_tab = LoadImage("/ui/tabs/red_tab.png");
        green_tab = LoadImage("/ui/tabs/green_tab.png");
        green_ribbon = LoadImage("/ui/green_ribbon.png");
        new_record = LoadImage("/ui/new_record.png");
        wood_ui = LoadImage("/ui/wood_ui.png");


        //FLAPPY MENU
        play_button = new BufferedImage[2];
        for(int i = 0; i < play_button.length; i++)
        {
            play_button[i] = LoadImage("/ui/buttons/play_buttons/"+(i)+".png");
        }

        retry_button = new BufferedImage[2];
        for(int i = 0; i < retry_button.length; i++)
        {
            retry_button[i] = LoadImage("/ui/buttons/retry_buttons/"+(i)+".png");
        }

        menu_button = new BufferedImage[2];
        for(int i = 0; i < menu_button.length; i++)
        {
            menu_button[i] = LoadImage("/ui/buttons/menu_buttons/"+(i)+".png");
        }

        right_arrow_button = new BufferedImage[2];
        for(int i = 0; i < right_arrow_button.length; i++)
        {
            right_arrow_button[i] = LoadImage("/ui/buttons/arrows/right/"+(i)+".png");
        }

        left_arrow_button = new BufferedImage[2];
        for(int i = 0; i < left_arrow_button.length; i++)
        {
            left_arrow_button[i] = LoadImage("/ui/buttons/arrows/left/"+(i)+".png");
        }

        silder_bg = LoadImage("/ui/buttons/slider/slider_bg.png");
        slider_button = LoadImage("/ui/buttons/slider/slider_button.png");

        //FONTS
        font_guarane = LoadFont("/fonts/gGuarantee.ttf", 42);
        font_boohong = LoadFont("/fonts/Boohong.ttf", 42);
        font_fruitpunch = LoadFont("/fonts/Fruitpunch.ttf", 42);

        //INTRO CLIP
        intro_clip = LoadSound("/sounds/intro_scene.wav");
        bg_music = LoadSound("/sounds/bg_music_low.wav");
        bell_sound = LoadSound("/sounds/bell.wav");
        fly_sound = LoadSound("/sounds/fly.wav");
        kick_sound = LoadSound("/sounds/kick.wav");
        loaded = true;
    }


    public static BufferedImage LoadImage(String path)
    {
        count ++;
        return Loader.ImageLoader(path);
    }
    public static Font LoadFont(String path, int size)
    {
        count ++;
        return Loader.FontLoader(path,size);
    }

    public static Clip LoadSound(String path)
    {
        count ++;
        return Loader.SoundLoader(path);
    }

    public static float getCount()
    {
        return count;
    }
}
