package interpreter;

public class SquareArea implements IAreaInterpreter{

	private int side;
	
	public SquareArea(int _side){
		side = _side;
	}
	
	@Override
	public double CalculateArea() {
		return side * side;
	}

}
