package model;

import java.io.Serializable;
import java.util.ArrayList;

import iterator.ShapeIterator;
import shapes.Shape;

/**
 * Represent model in MVC architectural pattern. Contains application data.
 *
 */
public class DrawingModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Shape> shapes;
	private ShapeIterator iterator;
	
	public DrawingModel() {
		shapes = new ArrayList<Shape>();
		iterator = new ShapeIterator(shapes);
	}
	
	/**
	 * Add new shape.
	 * 
	 * @param shape Represent shape which will be added.
	 */
	public void add(Shape shape) {
		for (iterator.first(); !iterator.done(); iterator.next());
		iterator.add(shape);
	}
	
	/**
	 * Add new shape to specified index.
	 * @param index Represent index on which shape will be added.
	 * @param shape Represent shape which will be added.
	 */
	public void addToIndex(int index, Shape shape) {
		for (iterator.first(); !iterator.done(); iterator.next())
		{
			if(index == iterator.getIndex())
			{
				iterator.add(shape);
				break;
			}
		}
		
	}
	
	/**
	 * Add multiple shapes to list of shapes.
	 * 
	 * @param list Elements that are be added.
	 */
	public void addMultiple(ArrayList<Shape> _shapes) {
		for (iterator.first(); !iterator.done(); iterator.next());
		ShapeIterator it = new ShapeIterator(_shapes);
		
		for (it.first(); !it.done(); it.next())
			iterator.add(it.get());
		
	}
	
	/**
	 * Remove shape from list of shapes.
	 * 
	 * @param shape Shape to be removed.
	 */
	public void remove(Shape shape) {
		for (iterator.first(); !iterator.done(); iterator.next())
		{
			if (iterator.get() == shape)
			{
				iterator.remove();
				break;
			}
		}
	}
	
	/**
	 * Remove shape at specified index.
	 * 
	 * @param index Represent index of shape that will be removed.
	 */
	public void removeAtIndex(int index) {
		for (iterator.first(); !iterator.done(); iterator.next())
		{
			if (iterator.getIndex() == index)
			{
				iterator.remove();
				break;
			}
		}
	}
	
	/**
	 * Remove multiple shapes from list of shapes.
	 * 
	 * @param shapes Shapes to be removed.
	 */
	public void removeMultiple(ArrayList<Shape> shapes) {
		this.shapes.removeAll(shapes);
	}

	/**
	 * Remove all shapes from list of shapes.
	 */
	public void removeAll() {
		for (iterator.first(); !iterator.done(); iterator.next())
			iterator.remove();
	}
	
	public Shape getByIndex(int index) {
		for (iterator.first(); !iterator.done(); iterator.next())
		{
			if (iterator.getIndex() == index)
				return (Shape) iterator.get();
		}
		
		return null;
	}
	
	public int getIndexOf(Shape shape) {
		for (iterator.first(); !iterator.done(); iterator.next())
		{
			if (iterator.get() == shape)
				return iterator.getIndex();
		}
		
		return -1;
	}
	
	public ArrayList<Shape> getAll() {
		
		ArrayList<Shape> _shapes = new ArrayList<Shape>();
		for (iterator.first(); !iterator.done(); iterator.next())
			_shapes.add((Shape) iterator.get());

		return _shapes;
	
	}
}