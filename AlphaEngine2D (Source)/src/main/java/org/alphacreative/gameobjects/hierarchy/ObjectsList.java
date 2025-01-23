package org.alphacreative.gameobjects.hierarchy;
/**
 * @author AlphaCreative (Mikael Rodriguez)
 * @description Clase para sustituir a los ArrayList de objetos de juego, unicamente llamada para almacenar y editar propiedades de determinados objetos
 * @usage Un ejemplo seria, necesito buscar los objetos en escena con tag "ground". Los almacenamos aqui y cambiamos el tag de todos a "suelo", sin tener que hacer un bucle for, ya que aqui se hace automaticamente
 */

import java.util.ArrayList;

import org.alphacreative.gameobjects.GameObject;

public class ObjectsList
{
    private ArrayList<GameObject> gameObjects;

    public ObjectsList(){gameObjects = new ArrayList<GameObject>();}

    //Getter
    public ArrayList<GameObject> GameObjects(){return gameObjects;}

    //Setter
    public void GameObjecs(ArrayList<GameObject> gameObjects){this.gameObjects = gameObjects;}

}
