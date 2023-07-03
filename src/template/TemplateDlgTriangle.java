package template;

import java.awt.event.MouseEvent;

import dialogs.DlgTriangle;
import frame.DrawingFrame;
import shapes.Triangle;

public class TemplateDlgTriangle {

	public TemplateDlgTriangle(DlgTriangle _dlgTriangle)
	{
		dlgTriangle = _dlgTriangle;
	}
	
	public void add(MouseEvent click, DrawingFrame frame)
	{
		dlgTriangle.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgTriangle.deleteButtons();
		dlgTriangle.setVisible(true);
	}
	
	public void update(Triangle oldTriangle, DrawingFrame frame)
	{
		dlgTriangle.fillUp(oldTriangle, frame.getView().getWidth(), frame.getView().getHeight());
		dlgTriangle.setVisible(true);
	}
	
	
	private DlgTriangle dlgTriangle;
}
