package iterator;

import java.util.ArrayList;

import shapes.Shape;

public class ShapeIterator implements IIterator{

	private ArrayList<Shape> shapes;
	private int index; 
	
	public ShapeIterator(ArrayList<Shape> _shapes)
	{
		shapes = _shapes;
		index = 0;
	}
	
	@Override
	public boolean hasNext() {
		if(index < shapes.size())
			return true;
		return false;
	}

	@Override
	public Object next() {
		Shape shape = shapes.get(index);
		if (hasNext())
			index++;
		return shape;
	}

	@Override
	public void add(Object object) {
		if(index < shapes.size() - 1)
			shapes.add(index, (Shape) object);	
		else
			shapes.add((Shape) object);
	}

	@Override
	public void first() {
		index = 0;
	}

	@Override
	public boolean done() {
		return index >= shapes.size();
	}

	@Override
	public Object get() {
		return shapes.get(index);
	}

	@Override
	public void remove() {
		shapes.remove(index);
	}

	@Override
	public int getIndex() {
		return index;
	}
}
