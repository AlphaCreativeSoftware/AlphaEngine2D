package org.alphacreative.gameobjects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.alphacreative.manager.GameManager;
import org.alphacreative.math.Vector2D;
import org.alphacreative.time.Time;
import org.alphacreative.util.Mathf;

import java.awt.Graphics2D;
/**
 * @author AlphaCreative (Mikael Rodriguez)
 * @info Clase avanzada del objeto de juego
 * @details Contiene un dibujado diferente en draw usando la libreria Graphics2D y metodos adicionales para facilitar rotaciones, escalas y traslaciones, permite la creación y manipulación mas específica de objetos
 */
public abstract class GameObject{
	protected long id;

	protected String name;
	protected String tag;

	protected BufferedImage texture;
	protected Vector2D position;
	protected Vector2D screenSize;	

	protected Vector2D scale;
	public AffineTransform at;
    protected Graphics2D g2d;
	protected double angle;
	protected double opacity;
	public GameObject(String name, Vector2D position, Vector2D scale, BufferedImage texture) {
		this.screenSize = GameManager.getScreenSize();

		this.position = position;
		this.texture = texture;
		this.name = name;
		this.tag = "Default";
		this.scale = scale;
		angle = 0;
		opacity = 1;
	}
	public GameObject(String name,String tag, Vector2D position, Vector2D scale, BufferedImage texture) {
		this.screenSize = GameManager.getScreenSize();

		this.position = position;
		this.texture = texture;
		this.name = name;
		this.tag = tag;
		this.scale = scale;
		angle = 0;
		opacity = 1;
	}
	public void Draw(Graphics g)
	{
		if(this.texture != null) //Control de que la textura no sea null (Podria generar una excepcion si un usuario crea un objecto sin textura)
		{
			g2d = (Graphics2D)g;
			at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
			at.rotate(angle,texture.getWidth()*scale.getX()/2, texture.getHeight()*scale.getY()/2);
			at.scale(scale.getX(), scale.getY());
			g.setColor(Color.BLACK);
			//g.drawRect( (int) Math.round(position.getX()), (int) Math.round(position.getY()), (int) Math.round(this.getRealWidth()), (int) Math.round(this.getRealHeight()));
				if(this.getRight().getX() > -100 && this.getLeft().getX() < screenSize.getX()+100)
				{
					g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, (float)opacity));
					g2d.drawImage(texture,at,null);
					g2d.setColor(new Color(2, 2, 2, 255));
				}
		}
	}

	public abstract void Update();


    //Métodos internos para facilitar uso y comprension

	//GETTERS AND SETTERS
	public String getName()
	{return this.name;}
	public void setName(String name)
	{this.name = name;}

	public String getTag()
	{return this.tag;}
	public void setTag(String tag)
	{this.tag = tag;}
	public Vector2D getPosition() {
		return position;
	}
	public void setPosition(double x, double y) {
		this.position = new Vector2D(x,y);
	}
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	public BufferedImage getTexture() {
		return texture;
	}
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	//METODOS PARA OBTENER COORDENADAS DE SU TEXTURA
	public Vector2D getCenter(){return new Vector2D(position.getX() + getRealWidth()/2, position.getY() + getRealHeight()/2);}
	public Vector2D getTop(){return new Vector2D(position.getX() + getRealWidth()/2, position.getY());}
	public Vector2D getBottom(){return new Vector2D(position.getX() + getRealWidth()/2, position.getY() + getRealHeight());}
	public Vector2D getLeft(){return new Vector2D(position.getX(), position.getY() + getRealHeight()/2);}
	public Vector2D getRight(){return new Vector2D(position.getX() + getRealWidth(), position.getY() + getRealHeight()/2);}

	public void Destroy()
	{
		GameManager.SceneObjects().RemoveObject(this);
	}
	
	public double getRealWidth()
	{	if(texture != null)
		return texture.getWidth()*scale.getX();
		else
		return 0;
	}
	public double getRealHeight()
	{
		if (texture != null)
		return texture.getHeight()*scale.getY();
		else
		return 0;
	}
	public Vector2D getScale()
	{
		return scale;
	}
	public double getScaleX()
	{
		return scale.getX();
	}
	public double getScaleY()
	{
		return scale.getY();
	}
	public void setScale(double x, double y)
	{
		this.scale = new Vector2D(x,y);
	}
	public void setScale(Vector2D scale)
	{
		this.scale = scale;
	}
	public void scaleCentered(double scaleX, double scaleY) {
		int screenWidth = (int) GameManager.getScreenSize().getX();
		int screenHeight = (int) GameManager.getScreenSize().getY();
		// Calcula las coordenadas del centro de la pantalla
		double centerX = screenWidth / 2.0;
		double centerY = screenHeight / 2.0;
		
		// Calcula el cambio en el tamaño
		double deltaX = (texture.getWidth() * scaleX) - texture.getWidth();
		double deltaY = (texture.getHeight() * scaleY) - texture.getHeight();
		
		// Ajusta la posición para mantener el centro
		position.setX(position.getX() - (deltaX / 2));
		position.setY(position.getY() - (deltaY / 2));
		
		// Ajusta la posición para mantener el centro de pantalla
		position.setX(centerX - (texture.getWidth() * scaleX / 2.0));
		position.setY(centerY - (texture.getHeight() * scaleY / 2.0));
		
		// Aplica la escala
		scale.setX(scale.getX() * scaleX);
		scale.setY(scale.getY() * scaleY);
	}
	public void TranslateX(double xVelocity)
	{
		position.setX(position.getX()+xVelocity);
	}
	public void TranslateY(double yVelocity)
	{
		position.setY(position.getY()+yVelocity);
	}

	public double GetRotation()
	{
		return this.angle;
	}
	public double GetRotationEuler()
	{
		return Math.toDegrees(this.angle);
	}
	public void Rotate(double velocity)
	{
		this.angle += velocity;
	}
	public void SetRotation(double l_angle)
	{
		this.angle = l_angle;
	}
	public void SetRotationEuler(double euler)
	{
		double l_angle = Math.toRadians(euler);
		this.angle = l_angle;
	}
	public void setOpacity(double opacity)
	{
		this.opacity = Mathf.Clamp(opacity,0,1);
	}
	public void decreaseOpacity(double decreaseFactor)
	{
		this.opacity -= decreaseFactor;
		this.opacity = Mathf.Clamp(opacity,0,1);
	}
	public void increaseOpacity(double increaseFactor)
	{
		this.opacity += increaseFactor;
		this.opacity = Mathf.Clamp(opacity,0,1);
	}
	public double getOpacity()
	{
		return this.opacity;
	}
	public boolean isCollidingWith(GameObject other) {
		// Verifica si hay colisión entre el jugador y otro objeto
		return position.getX() < other.getPosition().getX() + other.getTexture().getWidth() * other.getScaleX() &&
				position.getX() + texture.getWidth() * scale.getX() > other.getPosition().getX() &&
				position.getY() < other.getPosition().getY() + other.getTexture().getHeight() * other.getScaleY() &&
				position.getY() + texture.getHeight() * scale.getY() > other.getPosition().getY();
	}
	public boolean isCollidingWithTop(GameObject other) {
		// Verifica si hay colisión entre el jugador y otro objeto
		return position.getX() < other.getPosition().getX() + other.getTexture().getWidth()*0.9 * other.getScaleX() &&
				position.getX() + texture.getWidth() * scale.getX() > other.getPosition().getX()*1.1 &&
				position.getY() < other.getPosition().getY() + other.getTexture().getHeight()*0.1 * other.getScaleY() &&
				position.getY() + texture.getHeight() * scale.getY() > other.getPosition().getY();
	}
	public boolean detectLateralCollision(GameObject other) {
		// Obtén los límites de colisión de ambos objetos
		double thisLeft = position.getX();
		double thisRight = position.getX() + texture.getWidth() * scale.getX();
		double thisTop = position.getY() + texture.getHeight() * scale.getY(); // 10% menos sobre Y
		double thisBottom = position.getY() + texture.getHeight() * scale.getY(); // Parte inferior del objeto
		
		double otherLeft = other.getPosition().getX();
		double otherRight = other.getPosition().getX() + other.getTexture().getWidth() * other.getScaleX();
		double otherTop = other.getPosition().getY(); // Parte superior del otro objeto
		double otherBottom = other.getPosition().getY() + other.getTexture().getHeight() * other.getScaleY(); // Parte inferior del otro objeto
	
		// Comprueba si hay colisión lateral y si el objeto actual está en la misma altura
		return (thisRight >= otherLeft && thisLeft <= otherLeft) || (thisLeft <= otherRight && thisRight >= otherRight)
				&& !(thisBottom <= otherTop || thisTop >= otherBottom); // Verifica que no haya colisión en el eje Y
	}
	public void checkLateralCollisionAndAdjust(GameObject other) {
		// Calcula los límites de colisión de ambos objetos
		double thisLeft = position.getX();
		double thisRight = position.getX() + texture.getWidth() * scale.getX();
		double thisTop = position.getY() + texture.getHeight() * scale.getY() * 0.1; // 10% debajo de la parte superior del objeto
		double thisBottom = position.getY() + texture.getHeight() * scale.getY(); // Ajustamos aquí
		
		double otherLeft = other.getPosition().getX();
		double otherRight = other.getPosition().getX() + other.getTexture().getWidth() * other.getScaleX();
		double otherTop = other.getPosition().getY(); // Ajustamos aquí
		double otherBottom = other.getPosition().getY() + other.getTexture().getHeight() * other.getScaleY(); // Ajustamos aquí
	
		// Comprueba si hay colisión lateral
		if ((thisRight >= otherLeft && thisLeft <= otherLeft) || (thisLeft <= otherRight && thisRight >= otherRight)) {
			// Comprueba si hay colisión en el eje Y (considera si este objeto está encima del otro)
			if (!(thisBottom <= otherTop || thisTop >= otherBottom)) { // Cambiamos la condición para no colisionar en el eje Y
	
				// Si hay colisión, ajusta la posición de este objeto hacia atrás
				if (thisRight >= otherLeft && thisLeft <= otherLeft) {
					// Colisión en el lado izquierdo de 'other', mueve este objeto hacia la izquierda
					position.setX(otherLeft - texture.getWidth() * scale.getX());
				} else {
					// Colisión en el lado derecho de 'other', mueve este objeto hacia la derecha
					position.setX(otherRight);
				}
			}
		}
	}
	
	public void checkLateralCollisionAndAdjustFix(GameObject other) {
		// Calcula los límites de colisión de ambos objetos
		double thisLeft = position.getX();
		double thisRight = position.getX() + texture.getWidth() * scale.getX();
		double thisTop = position.getY() + texture.getHeight() * scale.getY() * 0.1; // 10% debajo de la parte superior del objeto
		double thisBottom = position.getY() + texture.getHeight() * scale.getY(); // Ajustamos aquí
		
		double otherLeft = other.getPosition().getX();
		double otherRight = other.getPosition().getX() + other.getTexture().getWidth() * other.getScaleX();
		double otherTop = other.getPosition().getY(); // Ajustamos aquí
		double otherBottom = other.getPosition().getY() + other.getTexture().getHeight() * other.getScaleY(); // Ajustamos aquí
	
		// Comprueba si hay colisión lateral
		if ((thisRight >= otherLeft && thisLeft <= otherLeft) || (thisLeft <= otherRight && thisRight >= otherRight)) {
			// Comprueba si hay colisión en el eje Y (considera si este objeto está encima del otro)
			if (!(thisBottom <= otherTop || thisTop >= otherBottom)) { // Cambiamos la condición para no colisionar en el eje Y
	
				// Comprueba si el objeto está colisionando desde arriba
				if (thisTop < otherTop) {
					// Ajusta la posición del objeto a la parte superior del otro objeto
					position.setY(otherTop - texture.getHeight() * scale.getY());
				} else {
					// Si hay colisión lateral, ajusta la posición de este objeto hacia atrás
					if (thisRight >= otherLeft && thisLeft <= otherLeft) {
						// Colisión en el lado izquierdo de 'other', mueve este objeto hacia la izquierda
						position.setX(otherLeft - texture.getWidth() * scale.getX());
					} else {
						// Colisión en el lado derecho de 'other', mueve este objeto hacia la derecha
						position.setX(otherRight);
					}
				}
			}
		}
	}
	public void handleCollisionAndStacking(GameObject other) {
		// Calcula los límites de colisión de ambos objetos
		double thisLeft = position.getX();
		double thisRight = position.getX() + texture.getWidth() * scale.getX();
		double thisTop = position.getY() + texture.getHeight() * scale.getY() * 0.1; // 10% debajo de la parte superior del objeto
		double thisBottom = position.getY() + texture.getHeight() * scale.getY(); // Ajustamos aquí
		
		double otherLeft = other.getPosition().getX();
		double otherRight = other.getPosition().getX() + other.getTexture().getWidth() * other.getScaleX();
		double otherTop = other.getPosition().getY(); // Ajustamos aquí
		double otherBottom = other.getPosition().getY() + other.getTexture().getHeight() * other.getScaleY(); // Ajustamos aquí
	
		// Comprueba si hay colisión lateral
		if ((thisRight >= otherLeft && thisLeft <= otherLeft) || (thisLeft <= otherRight && thisRight >= otherRight)) {
			// Comprueba si hay colisión en el eje Y (considera si este objeto está encima del otro)
			if (!(thisBottom <= otherTop || thisTop >= otherBottom)) { // Cambiamos la condición para no colisionar en el eje Y
	
				// Si hay colisión lateral, ajusta la posición de este objeto hacia atrás
				if (thisRight >= otherLeft && thisLeft <= otherLeft) {
					// Colisión en el lado izquierdo de 'other', mueve este objeto hacia la izquierda
					position.setX(otherLeft - texture.getWidth() * scale.getX());
				} else {
					// Colisión en el lado derecho de 'other', mueve este objeto hacia la derecha
					position.setX(otherRight);
				}
			} else {
				// Si hay colisión en el eje Y, apila este objeto encima del otro
				double overlap = Math.min(thisBottom - otherTop, otherBottom - thisTop);
				position.setY(other.getPosition().getY() - overlap - texture.getHeight() * scale.getY());
			}
		}
	}
	public void handleVerticalCollisionAndGravity(GameObject other) {
		double thisBottom = position.getY() + texture.getHeight() * scale.getY(); // Obtener la parte inferior de este objeto
		double otherTop = other.getPosition().getY(); // Obtener la parte superior del otro objeto
		
		// Comprobar si este objeto está por encima del otro objeto
		if (thisBottom <= otherTop) {
			// Si no hay colisión en el eje Y, aplicar gravedad moviendo el objeto hacia abajo
			TranslateY(Time.DeltaTime()); // Ajusta la velocidad de gravedad según tus necesidades
		} else {
			// Si hay colisión en el eje Y, detener el movimiento hacia abajo y ajustar la posición
			position.setY(otherTop - texture.getHeight() * scale.getY()); // Ajusta la posición para que el objeto quede justo encima del otro
		}
	}
	public boolean hasExitedCollision(GameObject other) {
        // Verifica si este objeto ha salido de la colisión con otro objeto
        return !isCollidingWith(other);
    }
    public void Follow(GameObject object,float followSpeed, float stopDistance) {
        double distanceToObject = position.distanceTo(object.getPosition());
        
        if (distanceToObject > stopDistance) {
            // Calcular la dirección hacia el jugador
            Vector2D direction = object.getPosition().subtract(position).normalize();

            // Calcular el movimiento basado en la dirección y la velocidad del enemigo
            double moveX = direction.getX() * followSpeed;
            double moveY = direction.getY() * followSpeed;

            // Actualizar la posición del enemigo
            position.setX(position.getX() + moveX);
            position.setY(position.getY() + moveY);
        }
    }
    public void FollowX(GameObject object,float followSpeed, float stopDistance) {
        double distanceToObject = position.distanceToX(object.getPosition());
        
        if (distanceToObject > stopDistance) {
            // Calcular la dirección hacia el jugador
            Vector2D direction = object.getPosition().subtract(position).normalize();

            // Calcular el movimiento basado en la dirección y la velocidad del enemigo
            double moveX = direction.getX() * followSpeed;

            // Actualizar la posición del enemigo
            position.setX(position.getX() + moveX);
        }
    }
    public void FollowY(GameObject object,float followSpeed, float stopDistance) {
        double distanceToObject = position.distanceToY(object.getPosition());
        
        if (distanceToObject > stopDistance) {
            // Calcular la dirección hacia el jugador
            Vector2D direction = object.getPosition().subtract(position).normalize();

            // Calcular el movimiento basado en la dirección y la velocidad del enemigo
            double moveY = direction.getY() * followSpeed;

            // Actualizar la posición del enemigo
            position.setY(position.getY() + moveY);
        }
    }
}
