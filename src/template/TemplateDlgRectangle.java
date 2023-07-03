package template;

import java.awt.event.MouseEvent;

import dialogs.DlgRectangle;
import frame.DrawingFrame;
import shapes.Rectangle;

public class TemplateDlgRectangle {

	public TemplateDlgRectangle(DlgRectangle _dlgRectangle)
	{
		dlgRectangle = _dlgRectangle;
	}
	
	public void add(MouseEvent click, DrawingFrame frame)
	{
		dlgRectangle.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgRectangle.deleteButtons();
		dlgRectangle.setVisible(true);
	}
	
	public void update(Rectangle oldRectangle, DrawingFrame frame)
	{
		dlgRectangle.fillUp(oldRectangle, frame.getView().getWidth(), frame.getView().getHeight());
		dlgRectangle.setVisible(true);
	}
	
	
	private DlgRectangle dlgRectangle;
}
