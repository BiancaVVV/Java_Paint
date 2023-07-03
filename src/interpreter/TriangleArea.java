package interpreter;

public class TriangleArea implements IAreaInterpreter {

	private int width;
	private int height;
	
	public TriangleArea(int _width, int _height) {
		width = _width;
		height = _height;
	}
	@Override
	public double CalculateArea() {
		return (width * height)/2;
	}

}
