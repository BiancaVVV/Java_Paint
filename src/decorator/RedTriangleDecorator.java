package decorator;

import java.awt.Color;

import shapes.Point;
import shapes.Triangle;

public class RedTriangleDecorator extends Triangle{

	private static final long serialVersionUID = 1L;

	
	 public RedTriangleDecorator() {
		 setInteriorColor(Color.RED);
	 }

	    public RedTriangleDecorator(Point _upPoint, int _width, int _height) {
	        super(_upPoint, _width, _height);
	        setInteriorColor(Color.RED);
	    }

	    public RedTriangleDecorator (Point _upPoint, int _width, int _height, Color edgeColor) {
	        super(_upPoint, _width, _height, edgeColor, Color.RED);
	    }
}
