package view;

import java.awt.Graphics;
import java.util.Iterator;
import javax.swing.JPanel;
import controller.DrawingController;
import facade.ShapeFacade;
import model.DrawingModel;
import shapes.Shape;

/**
 * Represent view in MVC architectural pattern.
 */
public class DrawingView extends JPanel {
	private static final long serialVersionUID = 1L;
	private DrawingModel model;
	private ShapeFacade shapeFacade;
	
	public DrawingView() {}

	/**
	 * When {@link DrawingModel} change, paint changes triggered by {@link DrawingController} to draw.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (model != null) {
			Iterator<Shape> it = model.getAll().iterator();
			while (it.hasNext())
			{
				shapeFacade = new ShapeFacade(it.next());
				shapeFacade.draw(g);
			}
		}
	}
	
	public void setModel(DrawingModel model) {
		this.model = model;
	}
}