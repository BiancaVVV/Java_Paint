package decorator;

import java.awt.Color;

import shapes.Point;
import shapes.Square;

public class RedSquareDecorator extends Square{

	private static final long serialVersionUID = 1L;

	public RedSquareDecorator() {}

    public RedSquareDecorator(Point upLeft, int side) {
        super(upLeft, side);
        setInteriorColor(Color.RED);
    }

    public RedSquareDecorator(Point upLeft, int side, Color edgeColor) {
        super(upLeft, side, edgeColor, Color.RED);
    }
    
}
