package decorator;

import java.awt.Color;

import shapes.Line;
import shapes.Point;

public class RedLineDecorator extends Line{

	private static final long serialVersionUID = 1L;

	public RedLineDecorator() 
	{
		setColor(Color.RED);
	}

    public RedLineDecorator (Point initial, Point last) {
       super(initial, last);
       setColor(Color.RED);
    }
}
