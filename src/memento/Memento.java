package memento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import shapes.Shape;

public class Memento {

	public Memento()
	{
		map = new HashMap<>();
	}
	
	public void addState(Shape shape)
	{
		if (map.get(shape.getKey()) == null)    
		    map.put(shape.getKey(), new ArrayList<String>());  
		
		map.get(shape.getKey()).add(shape.toString());  
	}
	
	public Shape getState(Shape shape, int index)
	{
		return shape;
		
	}
	
	public String getFirstState(Shape shape)
	{
		String first = map.get(shape.getKey()).get(0);
		System.out.println(first.toString());
		
		return first;
	}
	
	private Map<String, ArrayList<String>> map;   
}
