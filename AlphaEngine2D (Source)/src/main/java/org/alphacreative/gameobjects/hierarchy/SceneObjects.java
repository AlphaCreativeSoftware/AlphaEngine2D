package org.alphacreative.gameobjects.hierarchy;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.alphacreative.gameobjects.GameEntity;
import org.alphacreative.gameobjects.GameObject;
import org.alphacreative.gameobjects.entities.Cloud;
import org.alphacreative.manager.GameManager;

public class SceneObjects {

    private ArrayList<GameObject> objects;
    private List<GameObject> objectsToAdd = new ArrayList<>(); //Esta arrayList esta creada por un error común que sucede cuando la lista se esta iterando y añado un objeto en escena, esto resultaria en un error de ejecución
    private List<GameObject> objectsToRemove = new ArrayList<>(); //Esta exactamente lo mismo que la anterior

    public SceneObjects()
    {
        objects = new ArrayList<GameObject>();
    }
    public ArrayList<GameObject> GameObjects()
    {
        return objects;
    }
    public ArrayList<GameEntity> GameEntities()
    {
        ArrayList<GameEntity> gameEntities = new ArrayList<GameEntity>();
        for (GameObject object : objects)
        {
            if (object instanceof GameEntity)
            {
                gameEntities.add((GameEntity)object);
            }
        }
        return gameEntities;
    }
    public void Update() //Actualizamos todos los GameObjects
    {
        for (GameObject object : objects)
        {
            object.Update();
        }

        // Añadir los objetos de la lista temporal después de la iteración
        if (!objectsToAdd.isEmpty()) { // Si hay objetos en la lista, añadimos a la array despues de iterar. Esto es para evitar un error de ejecución
            objects.addAll(objectsToAdd);
            objectsToAdd.clear();
        }

        // Eliminar los objetos de la lista temporal después de la iteración
        if (!objectsToRemove.isEmpty()) {
            objects.removeAll(objectsToRemove);//Si hay objetos en la lista, eliminamos de la array despues de iterar. Esto es para evitar un error de ejecución
            objectsToRemove.clear();
        }
    }
    public void Draw(Graphics g) //Dibujamos todos los GameObjects
    {
        ArrayList<GameObject> defaultObjects = FindObjectsWithNoTag("UI");
        ArrayList<GameObject> uiObjects = FindObjectsWithTag("UI");//Damos prioridad a los objetos UI para que se dibujen siempre despues de los objetos normales
        for (GameObject object : defaultObjects)
        {
            object.Draw(g);
        }

        for (GameObject object : uiObjects)
        {
            object.Draw(g);
        }
    }

    public GameObject AddObject(GameObject object)//Funcion simple que añade objetos a la escena
    {objectsToAdd.add(object); return object;}

    public GameObject FindObjectWithName(String name)
    {
        for (GameObject object : objects)
        {
            if (object.getName().equals(name))
            {
                return object;
            }
        }
        return null;
    }
    public GameObject EqualsObject(GameObject object)
    {
        for(GameObject gameObject : objects)
        {
            if (gameObject.equals(object))
            {
                return gameObject;
            }
        }
        return null;
    }
    public void RemoveObject(GameObject object)//Funcion simple que elimina objetos de la escena
    {objectsToRemove.add(object);}

    public ArrayList<GameObject> FindObjectsWithTag(String tag)//Devuelve una ArrayList con todos los objetos que si sean de tag "xx"
    {
        ArrayList<GameObject> objectsWithTag = new ArrayList<GameObject>();
        for (GameObject object : objects)
        {
            if (object.getTag().equals(tag))
            {
                objectsWithTag.add(object);
            }
        }
        return objectsWithTag;
    }
    public ArrayList<GameObject> FindObjectsWithNoTag(String tag) //Devuelve una ArrayList con todos los objetos que no sean de tag "xx"
    {
        ArrayList<GameObject> objectsWithTag = new ArrayList<GameObject>();
        for (GameObject object : objects)
        {
            if (!object.getTag().equals(tag))
            {
                objectsWithTag.add(object);
            }
        }
        return objectsWithTag;
    }
    public void TranslateAll_X(double xVelocity)
    {
        for (GameObject object : objects)
        {
            object.TranslateX(xVelocity);
        }
        GameManager.worldX+=xVelocity;
    }
    public void TranslateWorld_X(double xVelocity)//Traslada el "mundo entero", es decir todos los objetos en escena dando la sensación de camara
    {
        for (GameObject object : objects)
        {
            if(object.getTag() != "UI" && object.getTag() != "BG_STATIC" && object.getTag() != "BG")
            {
                object.TranslateX(xVelocity);
            }
        }
        GameManager.worldX+=xVelocity;
    }

    public void TranslateWorld_X_CLOUD(double xVelocity,double cloudFrequencyDivisor)//Traslada el "mundo entero" pero las nubes las mueve mas lentas, segun el parametro que le pasemos
    {
        for (GameObject object : objects)
        {
            if(object.getTag() != "UI" && object.getTag() != "BG_STATIC" && object.getTag() != "BG")
            {
                if(object.getTag() != "CLOUD_BG")
                {
                    object.TranslateX(xVelocity);
                }
                else
                {
                    object.TranslateX(xVelocity/cloudFrequencyDivisor);
                } 
            }
        }
        GameManager.worldX+=xVelocity;
    }
    public void TranslateWorld_X_CLOUD_HQ(double xVelocity)//Traslada en High Quality, es decir lo mismo que lo anterior pero trasladamos el mundo y cada nube por separado tiene su frecuencia de traslado, lo que daria como resultado un efecto 3D en las nubes
    {
        for (GameObject object : objects)
        {
            if(object.getTag() != "UI" && object.getTag() != "BG_STATIC" && object.getTag() != "BG")
            {
                if(object.getTag() != "CLOUD_BG")
                {
                    object.TranslateX(xVelocity);
                }
                else
                {
                    Cloud cloud = (Cloud)object;
                    object.TranslateX(xVelocity/cloud.CloudFrequencyTranslateDivisor());
                } 
            }
        }
        GameManager.worldX+=xVelocity;
    }
    public void moveObjectBehindBackground(String tag, String behindTag) {
        GameObject objectToMove = null;
        int backgroundIndex = -1;
    
        // Buscar el índice del objeto "Background" y el objeto a mover
        for (int i = 0; i < objects.size(); i++) {
            GameObject object = objects.get(i);
            if (object.getTag().equals(behindTag)) {
                backgroundIndex = i;
            } else if (object.getTag().equals(tag)) {
                objectToMove = object;
            }
        }
    
        // Si el objeto a mover y el "Background" existen
        if (objectToMove != null && backgroundIndex != -1) {
            // Remover el objeto a mover de su posición actual
            objects.remove(objectToMove);
    
            // Insertar el objeto a mover detrás del "Background"
            objects.add(backgroundIndex + 1, objectToMove);
        }
    }
}
