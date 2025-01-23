package org.alphacreative.manager;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import org.alphacreative.data.DataManager;
import org.alphacreative.gameobjects.hierarchy.SceneObjects;
import org.alphacreative.main.Window; //Importamos el paquete de ventana para poder gestionar almacenar y obtener sus propiedades desde fuera
import org.alphacreative.math.Vector2D;
import org.alphacreative.states.GameState;
public class GameManager 
{
    private static Window window;
    private static GameState currentScene;
    public static SceneObjects currentSceneObjects;

    public static double worldX = 0;
    public static double worldY = 0;

    public static boolean isDebug = true;
    public static boolean showColliders = true;

    public static float global_volume = 4f;
    public static void setWindow(Window window) //Esta funcion solo se llamara una vez desde nuestra Window() para luego poder acceder a ella
    {GameManager.window = window;}

    public static Window getWindow() //Obtenemos la ventana (Usada internamente para acceder a ella)
    {return window;}

    public static void Pause(){window.Pause();} //Pausamos el juego (Hilo de Update y Draw)
    public static void Resume(){window.Resume();} //Reanudamos el juego (Hilo de Update y Draw)

    public static Vector2D getScreenSize() //Obtenemos las dimensiones de la ventana
    {return new Vector2D(Window.WIDTH,Window.HEIGHT);}

    public static void ChangeScene(int scene) //Unicamente para acceder al método cambiar escena sin tantos escalones y sea un código mas limpio
    {window.ChangeScene(scene);}

    public static int GetFPS()
    {return window.getFPS();} //Debuelve los fotogramas por segundo

    public static void setTitle(String title) //Método para ajustar el título de la ventana
    {window.setTitle(title);}

    public static DataManager DataManager(){return window.getDataManager();}

    public static void setCurrentScene(GameState scene)//Establecemos una escena actual
    {currentScene = scene;}

    public static GameState getCurrentScene() //Obtenemos la escena actual
    {return currentScene;}

    public static void setCurrentSceneObjects(SceneObjects sceneObjects) //Agregamos la lista de objetos en juego al esático gameManager para interactuar mejor con el
    {currentSceneObjects = sceneObjects;}
    
    public static SceneObjects SceneObjects()
    {return currentSceneObjects;}

    public static Vector2D getScreenCenter() { //Método para obtener el centro de la pantalla en vectores2D
        // Obtenemos las dimensiones de la pantalla
        Vector2D screenSize = getScreenSize();

        // Calculamos el centro de la pantalla
        double centerX = screenSize.getX() / 2;
        double centerY = screenSize.getY() / 2;

        return new Vector2D(centerX, centerY);
    }
    public static double WorldX(){return worldX;}
    public static double WorldY(){return worldY;}

    public static boolean IsDebug(){return isDebug;}
    public static void SetDebug(boolean debug){isDebug = debug;}

    public static boolean ShowColliders(){return showColliders;}
    public static void SetShowColliders(boolean showColliders){GameManager.showColliders = showColliders;}

    public static void SetVolume(float volume)
    {
        if (volume > -80f && volume <6f)
        {
            global_volume = volume;
        }
    }
    public static float GetVolume()
    {
        return global_volume;
    }

}
