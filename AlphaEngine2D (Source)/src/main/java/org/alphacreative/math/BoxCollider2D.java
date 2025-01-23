package org.alphacreative.math;

public class BoxCollider2D {

    private Vector2D position;
    private Vector2D scale;

    public BoxCollider2D(Vector2D position, Vector2D scale) {
        this.position = position;
        this.scale = scale;
    }

    // Getters y setters para position
    public Vector2D getPosition() {
        return position;
    }
    public double getPositionX()
    {
        return position.getX();
    }
    public double getPositionY()
    {
        return position.getY();
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setPosition(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    // Getters y setters para scale
    public Vector2D getScale() {
        return scale;
    }

    public double getScaleX() {
        return scale.getX();
    }

    public double getScaleY() {
        return scale.getY();
    }

    public void setScale(Vector2D scale) {
        this.scale = scale;
    }

    public void setScale(double x, double y) {
        this.scale.setX(x);
        this.scale.setY(y);
    }
    public double getLeft() {
        return position.getX();
    }

    public double getRight() {
        return position.getX() + scale.getX();
    }

    public double getTop() {
        return position.getY();
    }

    public double getBottom() {
        return position.getY() + scale.getY();
    }

    // Método para verificar colisión con otro BoxCollider2D
    public boolean isCollidingWith(BoxCollider2D other) {
        return position.getX() < other.getPosition().getX() + other.getScaleX() &&
               position.getX() + scale.getX() > other.getPosition().getX() &&
               position.getY() < other.getPosition().getY() + other.getScaleY() &&
               position.getY() + scale.getY() > other.getPosition().getY();
    }

    // Método para verificar colisión con un punto
    public boolean isCollidingWith(double x, double y) {
        return x >= position.getX() &&
               x <= position.getX() + scale.getX() &&
               y >= position.getY() &&
               y <= position.getY() + scale.getY();
    }

    @Override
    public String toString() {
        return "BoxCollider2D{" +
                "position=" + position +
                ", scale=" + scale +
                '}';
    }
}
