package interpreter;

public class RectangleArea implements IAreaInterpreter{

	private int width;
	private int side;
	
	public RectangleArea(int _width, int _side) {
		width = _width;
		side = _side;
	}
	
	@Override
	public double CalculateArea() {
		return width * side;
	}

}
