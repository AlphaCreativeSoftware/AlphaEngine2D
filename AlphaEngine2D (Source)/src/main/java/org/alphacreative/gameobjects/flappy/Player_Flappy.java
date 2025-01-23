package org.alphacreative.gameobjects.flappy;

import java.awt.Graphics;
import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.gameobjects.particles.Particle;
import org.alphacreative.graphics.Assets;
import org.alphacreative.graphics.Sound;
import org.alphacreative.input.KeyBoard;
import org.alphacreative.input.MouseInput;
import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;
import org.alphacreative.states.FlappyScene;
import org.alphacreative.time.Time;
import org.alphacreative.util.Mathf;

public class Player_Flappy extends GameEntity {
    public FlappyScene flappy_scene;

    private float jump_timer = 0;
    private float shoot_timer = 0;
    private boolean isDead = false;
    public boolean isJumping = false;
    public float velocityY = 0;
    float gravityForce = 3.3f; //Fuerza gravedad incremental
	float jumpAddForce = 16f;

    public int index = 0;

    public float particle_timer;

    //SOUND
    public Sound fly_sound;
    public Sound hit_sound;
    public Player_Flappy(String name, Vector2D position, Vector2D scale, int index) {
		super(name, position, scale, Assets.player_flappy[0]);
        this.index = index;
        this.setTexture(Assets.player_flappy[index]);
        this.fly_sound = new Sound(Assets.fly_sound);
        this.hit_sound = new Sound(Assets.kick_sound);
        flappy_scene = (FlappyScene) GameManager.getCurrentScene();//Obtenemos la escena flappy aprovechando el polimorfismo de la herencoa
	}
    @Override
    public void Update(){
        super.Update();
        shoot_timer += Time.DeltaTime();
        Mathf.Clamp(shoot_timer, 0, 50);
        if(shoot_timer > 0.2)
        {
            if(KeyBoard.E)
            {
                GameManager.SceneObjects().AddObject(new LaserBeam("Laser", new Vector2D(Collider().getRight(), this.getRight().getY()), new Vector2D(0.1, 0.1), Assets.laser_beam[0]));
                shoot_timer = 0;
            }

        }

        if(!isJumping)
        {
            //this.SetRotation(Mathf.Lerp(this.GetRotation(), 0, 0.1f*Time.SyncTime()));
            TranslateY(gravityForce*Time.SyncTime());
			gravityForce += 18*Time.DeltaTime(); // Incremento de la gravedad
            velocityY = Mathf.Lerp(velocityY, gravityForce, 0.15f*(float)Time.SyncTime());
            if(gravityForce > 12)
            {
                gravityForce = 12;
            }
        }
        

        if((KeyBoard.SPACE||MouseInput.LEFT) && !isJumping && !isDead)
        {
                isJumping = true;
                PlayFlySound();
        }
        //System.out.println(jump_timer);

        if(isJumping)
        {
            jump_timer += Time.DeltaTime();
            /*if(GetRotation() > -0.5)
            Rotate(-0.1f*Time.SyncTime());*/ 
            jumpAddForce -= 1*Time.SyncTime();
            TranslateY(-jumpAddForce*Time.SyncTime());
            velocityY = Mathf.Lerp(velocityY, -jumpAddForce, 0.15f*(float)Time.SyncTime());

            if(jump_timer >= 0.2)  
            {
                isJumping = false;
                gravityForce = 3.3f;
                jumpAddForce = 16f;
                jump_timer = 0;
            }
        }
        if(this.getPosition().getY() <= 0)
        {
            this.setPosition(this.getPosition().getX(),0);
        }
        if(this.getPosition().getY() >= GameManager.getScreenSize().getY()-this.getRealHeight())
        {
            this.setPosition(this.getPosition().getX(),GameManager.getScreenSize().getY()-this.getRealHeight());
        }

        float angleDir = Mathf.NormalizeValue(velocityY, -10, 8, -0.4f, 0.3f);
        SetRotation(angleDir);

        particle_timer += Time.DeltaTime();

        if(particle_timer > 0.05&&this.isAlive()&index==2)
        {
            for (int i = 0; i<= 8; i++)
            {
                float randOp = (float) Mathf.RandomRange(0.2, 0.5);
                double randScale = Mathf.RandomRange(0.1, 0.24);
                Particle part = (Particle) GameManager.SceneObjects().AddObject(new Particle("Particle", new Vector2D(this.getBottom().getX()-70,this.getBottom().getY()-60) ,new Vector2D(randScale,randScale), Assets.fireAnim, randOp, 2, 2));
                part.SetDestroyVelocity(1);
                part.SetRotationEuler(180);
                part.RandomizeX(-100,100);
                part.RandomizeY(650,900);
                
            }
            particle_timer = 0;
        }
    }
    @Override
    public void Draw(Graphics g) {
        /*BufferedImage shine = Assets.player_flappy_light[0];
        g2d = (Graphics2D)g;
        at = AffineTransform.getTranslateInstance(position.getX()-125, position.getY()-125);
        //at.rotate(angle,texture.getWidth()*scale.getX()/2, texture.getHeight()*scale.getY()/2);
        at.scale(scale.getX(), scale.getY()); 
        g.setColor(Color.BLACK);
        //g.drawRect( (int) Math.round(position.getX()), (int) Math.round(position.getY()), (int) Math.round(this.getRealWidth()), (int) Math.round(this.getRealHeight()));
        if(this.getRight().getX() > -100 && this.getLeft().getX() < screenSize.getX()+100)
        {
            g2d.drawImage(Assets.player_flappy_light[0],at,null);
            g2d.setColor(new Color(2, 2, 2, 255));
        }*/
        super.Draw(g);
    }


    public void Kill()
    {
        setTexture(Assets.player_flappy_damaged[index]);
        PlayDeadSound();
        isJumping = false;
        isDead = true;
    }

    public boolean isAlive()
    {
        return !isDead;
    }

    public void PlayFlySound()
    {
        fly_sound.ChangeVolume(GameManager.GetVolume());

        fly_sound.Stop();
        fly_sound.Play();
    }

    public void PlayDeadSound()
    {
        hit_sound.ChangeVolume(GameManager.GetVolume());
        hit_sound.Stop();
        hit_sound.Play();
    }
}
