package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import interpreter.TriangleArea;

public class Triangle extends SurfaceShape{
	private static final long serialVersionUID = 1L;	
	
	private Point upPoint;
	private int[] xPoints, yPoints;
	private int width;
	private int height;
	

    public Triangle() {}

    public Triangle(Point _upPoint, int _width, int _height) {
        this.upPoint = _upPoint;
        this.width = _width;
        this.height = _height;
    }

    public Triangle(Point _upPoint, int _width, int _height, Color edgeColor, Color interiorColor) {
        this(_upPoint, _width, _height);
        setColor(edgeColor);
        setInteriorColor(interiorColor);
    }
    
    public void draw(Graphics graphics) {
        graphics.setColor(getColor());
        generatePoints();
        graphics.drawPolygon(xPoints, yPoints, 3);
        fillUpShape(graphics);
        if (isSelected()) selected(graphics);
    }
    

    @Override
	public boolean equals(Object obj) {
		if (obj instanceof Triangle) {
			Triangle triangle = (Triangle) obj;
			return upPoint.equals(triangle.upPoint) && width == triangle.width && height == triangle.height;
		}
		return false;
	}
    
   
    @Override
	public int compareTo(Shape shape) {
		if (shape instanceof Triangle) return surface() - ((Triangle) shape).surface();
		return 0;
	}

    @Override
    public String toString() {
    	return "Triangle: x=" + upPoint.getXcoordinate() + "; y=" + upPoint.getYcoordinate() + "; height=" + height + "; width=" + width + "; edge color=" + getColor().toString().substring(14).replace('=', '-') + "; area color=" + getInteriorColor().toString().substring(14).replace('=', '-');
    }

    public void moveTo(int x, int y) {
        upPoint.moveTo(x, y);
    }
    
  
    @Override
    public void selected(Graphics graphics) {
    	graphics.setColor(Color.BLUE);
        new Line(getUpPoint(), new Point(upPoint.getXcoordinate() + width/2, upPoint.getYcoordinate() + height/2)).selected(graphics);
        new Line(getUpPoint(), new Point(upPoint.getXcoordinate() - width/2, upPoint.getYcoordinate() + height/2)).selected(graphics);
        new Line(new Point(upPoint.getXcoordinate() + width/2, upPoint.getYcoordinate() + height/2), new Point(upPoint.getXcoordinate() - width/2, upPoint.getYcoordinate() + height/2)).selected(graphics);
       
    }
    
    @Override
    public boolean containsClick(int xCoordinate, int yCoordinate) {
    	int minX = Arrays.stream(xPoints).min().getAsInt();
        int minY = Arrays.stream(yPoints).min().getAsInt();
        int maxX = Arrays.stream(xPoints).max().getAsInt();
        int maxY = Arrays.stream(yPoints).max().getAsInt();
        if (xCoordinate < minX || xCoordinate > maxX || yCoordinate < minY || yCoordinate > maxY) {
            return false;
        }

      
        double alpha = ((yPoints[1] - yPoints[2]) * (xCoordinate - xPoints[2]) +
                        (xPoints[2] - xPoints[1]) * (yCoordinate - yPoints[2])) /
                       ((yPoints[1] - yPoints[2]) * (xPoints[0] - xPoints[2]) +
                        (xPoints[2] - xPoints[1]) * (yPoints[0] - yPoints[2]));
        double beta = ((yPoints[2] - yPoints[0]) * (xCoordinate - xPoints[2]) +
                       (xPoints[0] - xPoints[2]) * (yCoordinate - yPoints[2])) /
                      ((yPoints[1] - yPoints[2]) * (xPoints[0] - xPoints[2]) +
                       (xPoints[2] - xPoints[1]) * (yPoints[0] - yPoints[2]));
        double gamma = 1.0 - alpha - beta;

        return alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1 && gamma >= 0 && gamma <= 1;
    }

    
   
    public Triangle clone() {
    	return new Triangle(upPoint.clone(), width, height, getColor(), getInteriorColor());
    }
    
   
    public void fillUpShape(Graphics graphics) {
        graphics.setColor(getInteriorColor());
        graphics.fillPolygon(xPoints, yPoints, 3);
    }
    
    public int surface() {
        return (height * width) / 2;
    }

    
    public Point getUpPoint() {
        return upPoint;
    }

    public void setUpPoint(Point _upPoint) {
        this.upPoint = _upPoint;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }

    public void setHeight(int _height) {
        this.height = _height;
    }
    
    public void generatePoints()
    {
    	xPoints = new int[3];
    	yPoints = new int[3];
    	
    	xPoints[0] = upPoint.getXcoordinate();
    	xPoints[1] = upPoint.getXcoordinate() + width/2;
    	xPoints[2] = upPoint.getXcoordinate() - width/2;
    	
    	
    	yPoints[0] = upPoint.getYcoordinate();
    	yPoints[1] = upPoint.getYcoordinate() + height/2;
    	yPoints[2] = upPoint.getYcoordinate() + height/2;
    }
    
    public double getArea()
    {
    	TriangleArea area = new TriangleArea(width, height);
    	return area.CalculateArea();
    }
}
