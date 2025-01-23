package org.alphacreative.gameobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import org.alphacreative.manager.GameManager;
import org.alphacreative.math.BoxCollider2D;
import org.alphacreative.math.Vector2D;

public class GameEntity extends GameObject{
    private BoxCollider2D boxCollider2D;
    private Vector2D colliderScale;
    private Vector2D colliderLocalPosition;
    public GameEntity(String name, Vector2D position, Vector2D scale, BufferedImage texture) {
		super(name, position, scale, texture);
        colliderScale = new Vector2D(1,1);
        colliderLocalPosition = new Vector2D(0,0);
        boxCollider2D = new BoxCollider2D(this.position, new Vector2D(getRealWidth()*colliderScale.getX(),getRealHeight()*colliderScale.getY()));
	}
    public GameEntity(String name,String tag, Vector2D position, Vector2D scale, BufferedImage texture) {
		super(name, tag, position, scale, texture);
        colliderScale = new Vector2D(1,1);
        colliderLocalPosition = new Vector2D(0,0);
        boxCollider2D = new BoxCollider2D(this.position, new Vector2D(getRealWidth()*colliderScale.getX(),getRealHeight()*colliderScale.getY()));
	}
    @Override
    public void Update(){ 
        boxCollider2D.setScale(getRealWidth()*colliderScale.getX(),getRealHeight()*colliderScale.getY());
        double colliderX = (this.position.getX()+colliderLocalPosition.getX()*this.scale.getX()) + (getRealWidth() - boxCollider2D.getScaleX()) / 2; //Mantenemos centrado el eje X
        double colliderY = (this.position.getY()+colliderLocalPosition.getY()*this.scale.getY()) + (getRealHeight() - boxCollider2D.getScaleY()) / 2; //Mantenemos centrado el eje Y
        boxCollider2D.setPosition(new Vector2D(colliderX,colliderY));
        if(this.scale.getX() <= 0)
        {
            this.scale.setX(0);
        }
        if(this.scale.getY() <= 0)
        {
            this.scale.setY(0);
        }
    }
    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED); // Puedes cambiar el color según tus necesidades

        if(GameManager.ShowColliders())
        g2d.drawRect((int)boxCollider2D.getPositionX(), (int)boxCollider2D.getPositionY(), (int)boxCollider2D.getScaleX(),(int) boxCollider2D.getScaleY());
        
    }

    public BoxCollider2D Collider()
    {
        return this.boxCollider2D;
    }
    public Vector2D GetColliderLocalScale()
    {
        return this.colliderScale;
    }
    public double GetColliderLocalScaleX()
    {
        return this.colliderScale.getX();
    }
    public double GetColliderLocalScaleY()
    {
        return this.colliderScale.getY();
    }
    public void SetColliderLocalScale(double x, double y)
    {
        this.colliderScale.setX(x);
        this.colliderScale.setY(y);
    }
    public void SetColliderLocalScaleX(double x)
    {
        this.colliderScale.setX(x);
    }
    public void SetColliderLocalScaleY(double y)
    {
        this.colliderScale.setY(y);
    }
    public Vector2D GetColliderLocalPosition()
    {
        return this.colliderLocalPosition;
    }
    public double GetColliderLocalPositionX()
    {
        return this.colliderLocalPosition.getX();
    }
    public double GetColliderLocalPositionY()
    {
        return this.colliderLocalPosition.getY();
    }
    public void SetColliderLocalPosition(double x, double y)
    {
        this.colliderLocalPosition.setX(x);
        this.colliderLocalPosition.setY(y);
    }
    public void SetColliderLocalPositionX(double x)
    {
        this.colliderLocalPosition.setX(x);
    }
    public void SetColliderLocalPositionY(double y)
    {
        this.colliderLocalPosition.setY(y);
    }

    //MÉTODOS ESPECIALES DE GAMEENTITY
    public boolean isCollidingWithEntity2D(GameEntity other) {
        BoxCollider2D otherCollider = other.Collider();
    
        boolean isColliding = this.boxCollider2D.getRight() > otherCollider.getLeft() &&
                              this.boxCollider2D.getLeft() < otherCollider.getRight() &&
                              this.boxCollider2D.getBottom() > otherCollider.getTop() &&
                              this.boxCollider2D.getTop() < otherCollider.getBottom();
    
        //System.out.println("Is Colliding: " + isColliding);
        return isColliding;
    }

    public boolean isCollidingFromAbove2D(GameEntity other, double margin) {
        BoxCollider2D otherCollider = other.Collider();
    
        return this.isCollidingWithEntity2D(other) &&
               this.boxCollider2D.getBottom() <= otherCollider.getTop() + margin &&
               this.boxCollider2D.getBottom() >= otherCollider.getTop();
    }
    
    public boolean isCollidingFromBelow2D(GameEntity other, double margin) {
        BoxCollider2D otherCollider = other.Collider();
    
        return this.isCollidingWithEntity2D(other) &&
               this.boxCollider2D.getTop() >= otherCollider.getBottom() - margin &&
               this.boxCollider2D.getTop() <= otherCollider.getBottom();
    }
    
    public boolean isCollidingFromLeft2D(GameEntity other, double margin) {
        BoxCollider2D otherCollider = other.Collider();
    
        return this.isCollidingWithEntity2D(other) &&
               this.boxCollider2D.getRight() <= otherCollider.getLeft() + margin &&
               this.boxCollider2D.getRight() >= otherCollider.getLeft();
    }
    
    public boolean isCollidingFromRight2D(GameEntity other, double margin) {
        BoxCollider2D otherCollider = other.Collider();
    
        return this.isCollidingWithEntity2D(other) &&
               this.boxCollider2D.getLeft() >= otherCollider.getRight() - margin &&
               this.boxCollider2D.getLeft() <= otherCollider.getRight();
    }
}
