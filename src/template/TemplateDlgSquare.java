package template;

import java.awt.event.MouseEvent;

import dialogs.DlgSquare;
import frame.DrawingFrame;
import shapes.Square;

public class TemplateDlgSquare {

	public TemplateDlgSquare(DlgSquare _dlgSquare)
	{
		dlgSquare = _dlgSquare;
	}
	
	public void add(MouseEvent click, DrawingFrame frame)
	{
		dlgSquare.write(click.getX(), click.getY(), frame.getView().getWidth(), frame.getView().getHeight());
		dlgSquare.deleteButtons();
		dlgSquare.setVisible(true);
	}
	
	public void update(Square oldSquare, DrawingFrame frame)
	{
		dlgSquare.fillUp(oldSquare, frame.getView().getWidth(), frame.getView().getHeight());
		dlgSquare.setVisible(true);
	}
	
	
	private DlgSquare dlgSquare;
}
