package decorator;

import java.awt.Graphics;

import shapes.Shape;

public class ShapeDecorator extends Shape {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Shape decoratedShape;
	
	public ShapeDecorator(Shape _decoratedShape)
	{
		decoratedShape = _decoratedShape;
	}
	
	@Override
	public void draw(Graphics graphics) {
	}

	@Override
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Shape o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void selected(Graphics graphics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsClick(int xCoordinate, int yCoordinate) {
		// TODO Auto-generated method stub
		return false;
	}
}
