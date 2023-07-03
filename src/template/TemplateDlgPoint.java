package template;

import dialogs.DlgPoint;
import frame.DrawingFrame;
import shapes.Point;

public class TemplateDlgPoint {

	public TemplateDlgPoint(DlgPoint _dlgPoint)
	{
		dlgPoint = _dlgPoint;
	}
	
	public void update(Point oldPoint, DrawingFrame frame)
	{
		dlgPoint.write(oldPoint, frame.getView().getWidth(), frame.getView().getHeight());
		dlgPoint.setVisible(true);
	}
	
	
	private DlgPoint dlgPoint;
}
