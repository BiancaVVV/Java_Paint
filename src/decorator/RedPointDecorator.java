package decorator;

import java.awt.Color;
import shapes.Point;

public class RedPointDecorator extends Point{

	private static final long serialVersionUID = 1L;

	 public RedPointDecorator() 
	 {
		setColor(Color.RED);
	 }

	 public RedPointDecorator(int xCoordinate, int yCoordinate) {
	        super(xCoordinate, yCoordinate);
	        setColor(Color.RED);
	    }
}
