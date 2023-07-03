package model;

import java.awt.Color;

import decorator.RedCircleDecorator;
import decorator.RedLineDecorator;
import decorator.RedPointDecorator;
import decorator.RedRectangleDecorator;
import decorator.RedSquareDecorator;
import decorator.RedTriangleDecorator;
import shapes.*; 

public interface IAbstractFactory {
	
	public Circle createCircle();
	public Circle createCircle(Point center, int r, Color edgeColor, Color interiorColor);
	public RedCircleDecorator createRedCircle();
	public RedCircleDecorator createRedCircle(Point center, int r, Color edgeColor);
	public Line createLine();
	public Line createLine(Point initial, Point last);
	public Line createLine(Point initial, Point last, Color color);
	public RedLineDecorator createRedLine();
	public RedLineDecorator createRedLine(Point initial, Point last);
	public Point createPoint();
	public Point createPoint(int xCoordinate, int yCoordinate);
	public Point createPoint(int xCoordinate, int yCoordinate, Color color);
	public RedPointDecorator createRedPoint();
	public RedPointDecorator createRedPoint(int xCoordinate, int yCoordinate);
	public Rectangle createRectangle();
	public Rectangle createRectangle(Point upLeft, int width, int height, Color edgeColor, Color interiorColor);
	public RedRectangleDecorator createRedRectangle();
	public RedRectangleDecorator createRedRectangle(Point upLeft, int width, int height, Color edgeColor);
	public Square createSquare();
	public Square createSquare(Point upLeft, int side);
	public Square createSquare(Point upLeft, int side, Color edgeColor, Color interiorColor);
	public RedSquareDecorator createRedSquare();
	public RedSquareDecorator createRedSquare(Point upLeft, int side);
	public RedSquareDecorator createRedSquare(Point upLeft, int side, Color edgeColor);
	public Triangle createTriangle();
	public Triangle createTriangle(Point upPoint, int width, int height);
	public Triangle createTriangle(Point upPoint, int width, int height, Color edgeColor, Color interiorColor);
	public RedTriangleDecorator createRedTriangle();
	public RedTriangleDecorator createRedTriangle(Point upPoint, int width, int height);
	public RedTriangleDecorator createRedTriangle(Point upPoint, int width, int height, Color edgeColor);
	    
	
}


