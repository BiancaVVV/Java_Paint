package template;

import java.awt.event.MouseEvent;

import dialogs.DlgCircle;
import frame.DrawingFrame;
import shapes.Circle;

public class TemplateDlgCircle {

	public TemplateDlgCircle(DlgCircle _dlgCircle)
	{
		dlgCircle = _dlgCircle;
	}
	
	public void add(MouseEvent click, DrawingFrame frame)
	{
		dlgCircle.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgCircle.deleteButtons();
		dlgCircle.setVisible(true);
	}
	
	public void update(Circle oldCircle, DrawingFrame frame)
	{
		dlgCircle.fillUp(oldCircle, frame.getView().getWidth(), frame.getView().getHeight());
		dlgCircle.setVisible(true);
	}
	
	
	private DlgCircle dlgCircle;
}
