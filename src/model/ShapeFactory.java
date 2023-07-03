package model;

import java.awt.Color;

import decorator.RedCircleDecorator;
import decorator.RedLineDecorator;
import decorator.RedPointDecorator;
import decorator.RedRectangleDecorator;
import decorator.RedSquareDecorator;
import decorator.RedTriangleDecorator;
import shapes.*;

public class ShapeFactory implements IAbstractFactory {

	@Override
	public Circle createCircle() {
		return new Circle();
	}

	@Override
	public Circle createCircle(Point center, int r, Color edgeColor, Color interiorColor) {
		return new Circle(center, r, edgeColor, interiorColor);
	}
	
	@Override
	public RedCircleDecorator createRedCircle(Point center, int r, Color edgeColor) {
		return new RedCircleDecorator(center, r, edgeColor);
	}

	@Override
	public Line createLine() {
		return new Line();
	}

	@Override
	public Line createLine(Point initial, Point last) {
		return new Line(initial, last);
	}

	@Override
	public Line createLine(Point initial, Point last, Color color) {
		return new Line(initial, last, color);
	}

	@Override
	public Point createPoint() {
		return new Point();
	}

	@Override
	public Point createPoint(int xCoordinate, int yCoordinate) {
		return new Point(xCoordinate, yCoordinate);
	}

	@Override
	public Point createPoint(int xCoordinate, int yCoordinate, Color color) {
		return new Point(xCoordinate, yCoordinate, color);
	}

	@Override
	public Rectangle createRectangle() {
		return new Rectangle();
	}

	@Override
	public Rectangle createRectangle(Point upLeft, int width, int height, Color edgeColor, Color interiorColor) {
		return new Rectangle(upLeft, width, height, edgeColor, interiorColor);
	}

	@Override
	public Square createSquare() {
		return new Square();
	}

	@Override
	public Square createSquare(Point upLeft, int side) {
		return new Square(upLeft, side);
	}

	@Override
	public Square createSquare(Point upLeft, int side, Color edgeColor, Color interiorColor) {
		return new Square(upLeft, side, edgeColor, interiorColor);
	}

	

	@Override
	public Triangle createTriangle() {
		return new Triangle();
	}

	@Override
	public Triangle createTriangle(Point upPoint, int width, int height) {
		return new Triangle(upPoint, width, height);
	}

	@Override
	public Triangle createTriangle(Point upPoint, int width, int height, Color edgeColor, Color interiorColor) {
		return new Triangle(upPoint, width, height, edgeColor, interiorColor);
	}

	@Override
	public RedCircleDecorator createRedCircle() {
		return new RedCircleDecorator();
	}

	@Override
	public RedLineDecorator createRedLine() {
		return new RedLineDecorator();
	}

	@Override
	public RedLineDecorator createRedLine(Point initial, Point last) {
		return new RedLineDecorator(initial, last);
	}

	@Override
	public RedPointDecorator createRedPoint() {
		return new RedPointDecorator();
	}

	@Override
	public RedPointDecorator createRedPoint(int xCoordinate, int yCoordinate) {
		return new RedPointDecorator(xCoordinate, yCoordinate);
	}

	@Override
	public RedRectangleDecorator createRedRectangle() {
		return new RedRectangleDecorator();
	}

	@Override
	public RedRectangleDecorator createRedRectangle(Point upLeft, int width, int height, Color edgeColor) {
		return new RedRectangleDecorator(upLeft, width, height, edgeColor);
	}

	@Override
	public RedSquareDecorator createRedSquare() {
		return new RedSquareDecorator();
	}

	@Override
	public RedSquareDecorator createRedSquare(Point upLeft, int side) {
		return new RedSquareDecorator(upLeft, side);
	}

	@Override
	public RedSquareDecorator createRedSquare(Point upLeft, int side, Color edgeColor) {
		return new RedSquareDecorator(upLeft, side, edgeColor);
	}



	@Override
	public RedTriangleDecorator createRedTriangle() {
		return new RedTriangleDecorator();
	}

	@Override
	public RedTriangleDecorator createRedTriangle(Point upPoint, int width, int height) {
		return new RedTriangleDecorator(upPoint, width, height);
	}

	@Override
	public RedTriangleDecorator createRedTriangle(Point upPoint, int width, int height, Color edgeColor) {
		return new RedTriangleDecorator(upPoint, width, height, edgeColor);
	}
	
}
