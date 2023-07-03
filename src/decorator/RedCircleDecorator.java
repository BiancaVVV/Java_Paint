package decorator;

import java.awt.Color;

import shapes.Circle;
import shapes.Point;

public class RedCircleDecorator extends Circle{

	private static final long serialVersionUID = 1L;
	
	public RedCircleDecorator() 
	{
		setInteriorColor(Color.RED);
	}
	
	public RedCircleDecorator(Point center, int r, Color edgeColor)
	{
		super(center, r, edgeColor, Color.RED);
	}
}
