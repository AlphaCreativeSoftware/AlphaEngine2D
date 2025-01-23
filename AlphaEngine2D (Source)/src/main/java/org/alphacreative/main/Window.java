package org.alphacreative.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import org.alphacreative.manager.GameManager;
import org.alphacreative.data.DataManager;
import org.alphacreative.graphics.Assets;
import org.alphacreative.input.KeyBoard;
import org.alphacreative.input.MouseInput;
import org.alphacreative.states.GameState;
import org.alphacreative.states.IntroScene;
import org.alphacreative.states.LoadingState;
import org.alphacreative.states.FlappyMenuScene;
import org.alphacreative.states.FlappyScene;

public class Window extends JFrame implements Runnable
{
    public static final int WIDTH=1280, HEIGHT=720;
    private Canvas canva;
    private Thread thread; //Hilo interno
    private boolean running; //Control del hilo interno
    private boolean paused = false; //Control de pausa del hilo interno (Por defecto false, ya que el juego inicia y ejecuta, excepto en grandees cargas)

    private BufferStrategy bs;
    private Graphics g;

    //FPS
    private final int FPS = 60;
    private double TARGETTIME = 1000000000/FPS;
    private double delta=0;
    private int AVERAGEFPS = FPS;
    
    // Clase organizada para entender mejor los metodos draw y update, gameState lo uso para crear la jerarquia de escenas
    private GameState[] gameState = new GameState[4];
    private int sceneIndex = 0;

    private DataManager data;

    private KeyBoard keyBoard;
    private MouseInput mouseInput;

    

    public static Window window = null;
    public Window()
    {
        setTitle("Antonio Run");
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Poder cerrar la ventana con la X ppor defecto de windows
        setResizable(true);//cambiar de tamaño una vez ejecutado
        setLocationRelativeTo(null); //Localizacion de ventana en el centro de pantalla
        setVisible(true); //Hacemos visible la ventana

        canva = new Canvas();

        keyBoard = new KeyBoard();
        mouseInput = new MouseInput();

        canva.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        canva.setMaximumSize(new Dimension(WIDTH,HEIGHT));
        canva.setMinimumSize(new Dimension(WIDTH,HEIGHT));
        canva.setFocusable(true); //Recibir entradas por parte de teclado, es decir que podamos focalizar la ventana al hacer click

        add(canva);
        canva.addKeyListener(keyBoard);
        canva.addMouseListener(mouseInput);
        canva.addMouseMotionListener(mouseInput);

        window=this;
    }
    //VARIABLES DE JUEGO//
    public static void main(String[] args)
    {
        new Window().start();
    }
    private void Update()
    {
        keyBoard.Update();
        gameState[sceneIndex].Update();
    }
    private void Draw()
    {
        bs = canva.getBufferStrategy();
        if(bs == null)
        {
            canva.createBufferStrategy(2);
            return;
        }
        g= bs.getDrawGraphics();
        //---LIMPIEZA DE PANTALLA---//
        g.clearRect(0,0,WIDTH,HEIGHT);
        //---DIBUJADO DE OBJETOS---//
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //GAME STATE
        gameState[sceneIndex].Draw(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(1, 1);
        g2d.setColor(Color.red);
        //----------------//
        g2d.dispose();
        bs.show();

    }
    private void init() 
    {
        Thread loadingThread = new Thread(new Runnable() {

            @Override
            public void run() {
                Assets.init();
            }
            
        });
        
        data = new DataManager("flappy.dat");
        System.out.println("--ASSETS SUCESSFULLY LOADED--"); //Mensaje debug personal
        GameManager.setWindow(this); //Asignamos nuestra ventana a la clase estática de GameManager
        System.out.println("--WINDOW SUCESFULLY OBATAINED--"); //Mensaje debug personal

        //INICIALIZAMOS LA JERARQUIA DE ESCENAS, ES UN SISTEMA BASADO EN LO APRENDIDO EN CLASE SOBRE CLASES ABSTRACTAS, PODRIA NO ESTAR HECHO DE LA MEJOR FORMA PERO DESDE LUEGO ES EFICIENTE
        //ChangeScene(3); //Método que inicializa las escenas de juego en el momento que se vayan a usar, empleado para reiniciarlas a su estado original
        StartLoadingScene(loadingThread);
        System.out.println("--SCENES SUCESSFULLY LOADED--"); //Mensaje debug personal

        
    }
    @Override
    public void run()
    {
        //Ciclo principal (El hilo esta creado mediante un bucle de llamadas internas)
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;//FPSaa
        long time = 0; //Tiempo
        init();
        while(running)
        {
            now = System.nanoTime();
            delta += (now-lastTime)/TARGETTIME;
            time += (now-lastTime);
            lastTime = now;

            if(delta >= 1 && !paused)
            {
                Update();
                Draw();
                delta --;
                frames ++;
            }
            if(time >= 1000000000)
            {
                AVERAGEFPS = frames;
                frames = 0;
                time = 0;
            }
            
        }
        stop();
    }
    private void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    private void stop()
    {
        try
        {
            thread.join();
            running = false;
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
    public void ChangeScene(int scene) //Metodo basico para cambiar de escena
    {
        switch(scene) //Creamos un switch ya que podriamos cargar todas las escenas de golpe en el init() pero esto asegura que se reinicien y que no hayan fugas de memoria, los datos de escena los guardaremos por separado
        {
            case 1:
            gameState[1] = new IntroScene();
            break;

            case 2:
            gameState[2] = new FlappyMenuScene();
            break;

            case 3:
            gameState[3] = new FlappyScene();
            break;
        }
        sceneIndex = scene;
    }
    public void StartLoadingScene(Thread loaginThread)
    {
        gameState[0] = new LoadingState(loaginThread);
        sceneIndex = 0;
    }

    //FUNCIONES DE CONTROL DE EJECUCIÓN DEL HILO INTERNO
    public void Pause(){paused = true;}
    public void Resume(){paused = false;}

    public Graphics getGraphics()
    {return g;}
    public int getFPS(){return AVERAGEFPS;}
    public int getTargetFPS(){return FPS;}
    public double getTargetTime()
    {return TARGETTIME;}
    public DataManager getDataManager()
    {return this.data;}
}
