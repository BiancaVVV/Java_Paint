package facade;

import java.awt.Graphics;

import shapes.Shape;

public class ShapeFacade {

	private Shape shape;
	
	public ShapeFacade(Shape _shape)
	{
		shape = _shape;
	}
	
	public void draw(Graphics g)
	{
		shape.draw(g);
	}
	
	public double calculateArea()
	{
		return shape.getArea();
	}
	
	public String serialize()
	{
		return shape.toString();
	}
	
}
