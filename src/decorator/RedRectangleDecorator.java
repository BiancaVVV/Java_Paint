package decorator;

import java.awt.Color;

import shapes.Rectangle;
import shapes.Point;

public class RedRectangleDecorator extends Rectangle {

	private static final long serialVersionUID = 1L;

	public RedRectangleDecorator() 
	{
		setInteriorColor(Color.RED);
	}
	
	public RedRectangleDecorator(Point upLeft, int width, int height, Color edgeColor)
	{
		super(upLeft, width, height, edgeColor, Color.RED);
	}
}
