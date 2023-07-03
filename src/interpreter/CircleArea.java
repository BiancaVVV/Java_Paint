package interpreter;

public class CircleArea implements IAreaInterpreter{

	private int radius;
	
	public CircleArea(int _radius) {
		radius = _radius;
	}
	@Override
	public double CalculateArea() {
		return (float) (Math.PI * radius * radius);
	}

}
