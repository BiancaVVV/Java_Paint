package template;

import dialogs.DlgLine;
import shapes.Line;

public class TemplateDlgLine {

	public TemplateDlgLine(DlgLine _dlgLine)
	{
		dlgLine = _dlgLine;
	}
	
	public void update(Line oldLine)
	{
		dlgLine.write(oldLine);
		dlgLine.setVisible(true);
	}
	
	
	private DlgLine dlgLine;
}
