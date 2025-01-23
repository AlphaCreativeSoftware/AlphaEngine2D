package org.alphacreative.math;

public class Vector2D {
	private double x,y;
	
	public Vector2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2D()
	{
		x = 0;
		y = 0;
	}
	
	public double getMagnitude()
	{
		return Math.sqrt(x*x + y*y);
	}
	
	public Vector2D setDirection(double angle)
	{
		return new Vector2D(Math.cos(angle)*getMagnitude(), Math.sin(angle)*getMagnitude());
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	public void increaseX(double x_increment)
	{
		this. x = this.x + x_increment;
	}
	public void decreaseX(double x_decrement)
	{
		this. x = this.x - x_decrement;
	}


	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	public void increaseY(double y_increment)
	{
		this. y = this.y + y_increment;
	}
	public void decreaseY(double y_decrement)
	{
		this. y = this.y - y_decrement;
	}
	public double distanceTo(Vector2D other) {
        double dx = this.x - other.getX();
        double dy = this.y - other.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
	public double distanceToY(Vector2D other)
	{
		double dy = this.y - other.getY();
		return Math.sqrt(dy * dy);
	}
	public double distanceToX(Vector2D other)
	{
		double dx = this.x - other.getX();
		return Math.sqrt(dx * dx);
	}
	public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.getX(), this.y - other.getY());
    }
	public Vector2D normalize() {
        double magnitude = Math.sqrt(x * x + y * y);
        if (magnitude == 0) {
            return new Vector2D(0, 0);
        }
        return new Vector2D(x / magnitude, y / magnitude);
    }
}