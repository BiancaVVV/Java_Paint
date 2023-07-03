package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import commands.CmdAddShape;
import commands.CmdBringToBack;
import commands.CmdBringToFront;
import commands.CmdRemoveShape;
import commands.CmdSelectShape;
import commands.CmdToBack;
import commands.CmdToFront;
import commands.CmdUpdateCircle;
import commands.CmdUpdateLine;
import commands.CmdUpdatePoint;
import commands.CmdUpdateRectangle;
import commands.CmdUpdateSquare;
import commands.CmdUpdateTriangle;
import commands.Command;
import dialogs.DlgCircle;
import dialogs.DlgLine;
import dialogs.DlgPoint;
import dialogs.DlgRectangle;
import dialogs.DlgSquare;
import dialogs.DlgTriangle;
import facade.ShapeFacade;
import frame.DrawingFrame;
import memento.Memento;
import model.DrawingModel;
import observer.DrawingObserver;
import shapes.Circle;
import shapes.Square;
import shapes.Triangle;
import strategy.FileDraw;
import strategy.FileLog;
import strategy.FileManager;
import strategy.FilePicture;
import template.TemplateDlgCircle;
import template.TemplateDlgLine;
import template.TemplateDlgPoint;
import template.TemplateDlgRectangle;
import template.TemplateDlgSquare;
import template.TemplateDlgTriangle;
import view.DrawingView;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;
import model.ShapeFactory;

/**
 * <h2>Class that represent controller in MVC architectural pattern.</h2>
 * 
 * Called by the {@link DrawingView} when user click something and act depending on the command (usually update {@link DrawingModel}).
 */
public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	private Point initialPointOfLine;
	private Color edgeColor = Color.BLACK;
	private Color interiorColor = Color.WHITE;
	private Color choosenEdgeColor;
	private Color choosenInteriorColor;
	private PropertyChangeSupport propertyChangeSupport;
	private int counterOfSelectedShapes = 0;
	private FileManager fileManager;
	private DefaultListModel<String> log;
	private Stack<String> undoCommandsLog;
	private Stack<Command> commands;
	private Stack<Command> undoCommands;
	private ShapeFactory factory;
	private ShapeFacade shapeFacade;
	private Memento shapeMemento;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		initialPointOfLine = null;
		propertyChangeSupport = new PropertyChangeSupport(this);
		log = frame.getList();
		commands = new Stack<>();
		undoCommands = new Stack<>();
		undoCommandsLog = new Stack<String>();
		shapeMemento = new Memento();
	}
	
	/**
	 * <h3>Add listener that will listen (observe) to the changes in this class.</h3>
	 * 
	 * @param propertyChangeListener Represent {@link DrawingObserver} that listen to the changes.
	 */
	public void addPropertyChangedListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}
	
	/**
	 * User clicked to choose edge color, show {@inheritDoc JColorChooser}.
	 * 
	 * @return Color that user choose.
	 */
	public Color btnEdgeColorClicked() {
		choosenEdgeColor = JColorChooser.showDialog(null, "Colors pallete", edgeColor);
		if (choosenEdgeColor != null) {
			if (choosenEdgeColor.equals(Color.WHITE)) {
				JOptionPane.showMessageDialog(null, "Background is white :D");
				return null;
			}
			edgeColor = choosenEdgeColor;
			return edgeColor;
		}
		return choosenEdgeColor;
	}
	
	/**
	 * <h3>User clicked to choose area color, show {@inheritDoc JColorChooser}.</h3>
	 * 
	 * @return Color that user choose.
	 */
	public Color btnInteriorColorClicked() {
		choosenInteriorColor = JColorChooser.showDialog(null, "Colors pallete", interiorColor);
		if (choosenInteriorColor != null) {
			interiorColor = choosenInteriorColor;
			return interiorColor;
		}
		return choosenEdgeColor;
	}
	
	public void btnSaveStateClicked() {
		Shape shape = getSelectedShape();
		shapeMemento.addState(shape);
	}
	
	public void btnGetFirstStateClicked() {
		FileLog fileLog = new FileLog(frame, model, null);
		Shape shape = getSelectedShape();
		String command = shapeMemento.getFirstState(shape);
		String[] commands1 = command.split(":");
		Shape shape1 = fileLog.parseShape(commands1[0], commands1[1]);
		DrawingController controller = new DrawingController(model, frame);
		controller.executeCommand(new CmdAddShape(shape1, model));
		controller.executeCommand(new CmdRemoveShape(shape, model));
	}
	
	
	/**
	 * <h3>Called when user click to add new {@link Point}.</h3>
	 * <p>
	 * Create {@link CmdAddShape}, add {@link Point} to that command and execute it.</br>
	 * Fire change that now shape exist, repaint {@link DrawingView} and add command to the list of commands.
	 * </p>
	 * @param click Represent place where user clicked.
	 */
	public void btnAddPointClicked(MouseEvent click) {
		factory = new ShapeFactory();
		Point point = factory.createRedPoint(click.getX(), click.getY());
		shapeFacade = new ShapeFacade(point);
		executeCommand(new CmdAddShape(point, model));
		log.addElement("Added->" + shapeFacade.serialize());
	}
	
	/**
	 * <h3>Called when user click to add new {@link Line}.</h3>
	 * <p>
	 * Create {@link CmdAddShape}, add {@link Line} to that command and execute it.</br>
	 * Fire change that now shape exist, repaint {@link DrawingView} and add command to the list of commands.
	 * </p>
	 * @param click Represent place where user clicked.
	 */
	public void btnAddLineClicked(MouseEvent click) {
		factory = new ShapeFactory();
		if(initialPointOfLine == null) initialPointOfLine = factory.createPoint(click.getX(), click.getY(), edgeColor);
		else {
			Line line = factory.createRedLine(initialPointOfLine, factory.createPoint(click.getX(), click.getY()));
			shapeFacade = new ShapeFacade(line);
			executeCommand(new CmdAddShape(line, model));
			log.addElement("Added->" + shapeFacade.serialize());
			initialPointOfLine = null;
		}
	}
	
	/**
	 * <h3>Called when user click to add new {@link Square}.</h3>
	 * <p>
	 * Create {@link CmdAddShape}, add {@link Square} to that command and execute it, ensuring that {@link Square} don't go out of draw.</br>
	 * Fire change that now shape exist, repaint {@link DrawingView} and add command to the list of commands.
	 * </p>
	 * @param click Represent place where user clicked.
	 */
	public void btnAddSquareClicked(MouseEvent click) {
		factory = new ShapeFactory();
		DlgSquare dlgSquare = new DlgSquare();
		TemplateDlgSquare templateDlgSquare = new TemplateDlgSquare(dlgSquare);
		templateDlgSquare.add(click, frame);
		if (dlgSquare.isConfirmed()) {
			Square square = factory.createRedSquare(factory.createPoint(click.getX(), click.getY()), dlgSquare.getSideLength(), edgeColor);
			shapeFacade = new ShapeFacade(square);
			executeCommand(new CmdAddShape(square, model));
			log.addElement("Added->" + shapeFacade.serialize());
		}
	}
	

	
	public void btnAddTriangleClicked(MouseEvent click) {
		factory = new ShapeFactory();
		DlgTriangle dlgTriangle = new DlgTriangle();
		TemplateDlgTriangle templateDlgTriangle = new TemplateDlgTriangle(dlgTriangle);
		templateDlgTriangle.add(click, frame);
		if (dlgTriangle.isConfirmed()) {
			Triangle triangle = factory.createRedTriangle(factory.createPoint(click.getX(), click.getY()), dlgTriangle.getTriangleWidth(), dlgTriangle.getTriangleHeight(), edgeColor);
			shapeFacade = new ShapeFacade(triangle);
			executeCommand(new CmdAddShape(triangle, model));
			log.addElement("Added->" + shapeFacade.serialize());
		}
	}
	
	/**
	 * <h3>Called when user click to add new {@link Rectangle}.</h3>
	 * <p>
	 * Create {@link CmdAddShape}, add {@link Rectangle} to that command and execute it, ensuring that {@link Rectangle} don't go out of draw.</br>
	 * Fire change that now shape exist, repaint {@link DrawingView} and 
	 * add command to the list of commands.
	 * </p>
	 * @param click Represent place where user clicked.
	 */
	public void btnAddRectangleClicked(MouseEvent click) {
		factory = new ShapeFactory();
		DlgRectangle dlgRectangle = new DlgRectangle();
		TemplateDlgRectangle templateDlgRectangle = new TemplateDlgRectangle(dlgRectangle);
		templateDlgRectangle.add(click, frame);
		if (dlgRectangle.isConfirmed()) {
			Rectangle rectangle = factory.createRedRectangle(factory.createPoint(click.getX(), click.getY()), dlgRectangle.getRectangleWidth(), dlgRectangle.getRectangleHeight(), edgeColor);
			shapeFacade = new ShapeFacade(rectangle);
			executeCommand(new CmdAddShape(rectangle, model));
			log.addElement("Added->" + shapeFacade.serialize());
		}
	}
	
	
	public void btnAddCircleClicked(MouseEvent click) {
		factory = new ShapeFactory();
		DlgCircle dlgCircle = new DlgCircle();
		TemplateDlgCircle templateDlgCircle = new TemplateDlgCircle(dlgCircle);
		templateDlgCircle.add(click, frame);
		if (dlgCircle.isConfirmed()) {
			Circle circle = factory.createRedCircle(factory.createPoint(click.getX(), click.getY()), dlgCircle.getRadiusLength(), edgeColor);
			shapeFacade = new ShapeFacade(circle);
			executeCommand(new CmdAddShape(circle, model));
			log.addElement("Added->" + shapeFacade.serialize());
		}
	}
	
	/**
	 * <h3>Called when user click to add new {@link Circle}.</h3>
	 * <p>
	 * Create {@link CmdAddShape}, add {@link Circle} to that command and execute it, ensuring that {@link Circle} don't go out of draw.</br>
	 * Fire change that now shape exist, repaint {@link DrawingView} and 
	 * add command to the list of commands.
	 *</p>
	 * @param click Represent place where user clicked.
	 */

	
	
	/**
	 * <h3>Called when user select some shape on a draw. </h3>
	 * <p>
	 * Multiple shapes can be selected and deleted.</br>
	 * One selected shape can be modified, deleted, and moved by Z coordinate ({@link CmdBringToBack}, {@link CmdBringToFront}, {@link CmdToBack} and {@link CmdToFront}).</br>
	 * When user click on some part on draw where no shape, all selected shapes are unselected.</br>
	 * When user click on some selected shape only that shape is unselected.</br>
	 * </p>
	 * @param click Represent place where user click.
	 */
	public void btnSelectShapeClicked(MouseEvent click) {
		Iterator <Shape> iterator = model.getAll().iterator();	
		ArrayList<Integer> tempListOfShapes = new ArrayList<>();
		
		while(iterator.hasNext()) {
			Shape shapeForSelection = iterator.next();
			if(shapeForSelection.containsClick(click.getX(), click.getY())) tempListOfShapes.add(model.getIndexOf(shapeForSelection));
		}
		
		if (!tempListOfShapes.isEmpty()) {
			Shape shape = model.getByIndex(Collections.max(tempListOfShapes));
			shapeFacade = new ShapeFacade(shape);

			if (!shape.isSelected()) {
				++counterOfSelectedShapes;
				executeCommand(new CmdSelectShape(shape, true));
				log.addElement("Selected->" + shapeFacade.serialize());
			}
			else {
				--counterOfSelectedShapes;
				executeCommand(new CmdSelectShape(shape, false));
				log.addElement("Unselected->" + shapeFacade.serialize());
			}
			
			handleSelectButtons();
		}
		
		frame.getView().repaint();	
	}
	
	/**
	 * Count how many shapes are selected.
	 * This method is called by undo command, redo command and log parser.
	 * 
	 * @param s Undo or redo command.
	 * @param command Selection or unselection.
	 */
	public void handleSelect(String s, String command) {
		if (command.equals("redo")) {
			if (s.equals("Selected")) ++counterOfSelectedShapes;
			else --counterOfSelectedShapes;
			handleSelectButtons();
		} else if (command.equals("undo")) {
			if (s.equals("Selected")) --counterOfSelectedShapes;
			else ++counterOfSelectedShapes;
			handleSelectButtons();
		} else if (command.equals("parser")) {
			if (s.equals("Selected")) ++counterOfSelectedShapes;
			else --counterOfSelectedShapes;
		}
	}
	
	/**
	 * Handle buttons state depend on number of selected shapes.
	 */
	public void handleSelectButtons() {
		if (counterOfSelectedShapes == 0) propertyChangeSupport.firePropertyChange("shape unselected", false, true);
		else if (counterOfSelectedShapes == 1)  {
			propertyChangeSupport.firePropertyChange("update/move turn on", false, true);
			propertyChangeSupport.firePropertyChange("shape selected", false, true);
		}  
		else if (counterOfSelectedShapes > 1) propertyChangeSupport.firePropertyChange("update/move turn off", false, true);
	}
	
	/**
	 * <h3>Method that is called when user choose to update some shape.</h3>
	 * 
	 * Determines instance of selected shape and call appropriate method forwarding casted type of shape.
	 */ 
	public void updateShapeClicked() {
		Shape shape = getSelectedShape();
		if (shape instanceof Point) btnUpdatePointClicked((Point) shape);
		else if (shape instanceof Line) btnUpdateLineClicked((Line) shape);
		else if (shape instanceof Rectangle) btnUpdateRectangleClicked((Rectangle) shape);
		else if (shape instanceof Square) btnUpdateSquareClicked((Square) shape);
		else if (shape instanceof Circle) btnUpdateCircleClicked((Circle) shape);
		else if (shape instanceof Triangle) btnUpdateTriangleClicked((Triangle) shape);
	}
	
	

	/**
	 * <h3>Method is called when user want to update some existing {@link Point} on draw.</h3>
	 * <p>
	 * {@link DlgPoint} is presented for user to enter new values for point.</br>
	 * Then {@link CmdUpdatePoint} is executed if user confirm input.
	 * </p>
	 * @param point Point that user want to update.
	 */
	public void btnUpdatePointClicked(Point oldPoint) {
		factory = new ShapeFactory();
		DlgPoint dlgPoint = new DlgPoint();
		TemplateDlgPoint templateDlgPoint = new TemplateDlgPoint(dlgPoint);
		templateDlgPoint.update(oldPoint, frame);
		if(dlgPoint.isConfirmed()) {
			Point newPoint = factory.createPoint(dlgPoint.getXcoordinate(), dlgPoint.getYcoordinate(), dlgPoint.getColor());
			shapeFacade = new ShapeFacade(newPoint);
			ShapeFacade oldShapeFacade = new ShapeFacade(oldPoint);
			executeCommand(new CmdUpdatePoint(oldPoint, newPoint));
			log.addElement("Updated->" + oldShapeFacade.serialize() + "->" + shapeFacade.serialize());
		}
	}
	
	/**
	 * <h3>Method is called when user want to update some existing {@link Line} on draw.</h3>
	 * <p>
	 * {@link DlgLine} is presented for user to enter new values for line.
	 * Then {@link CmdUpdateLine} is executed if user confirm input.
	 * </p>
	 * @param line Line that user want to update.
	 */
	public void btnUpdateLineClicked(Line oldLine) {
		factory = new ShapeFactory();
		DlgLine dlgLine = new DlgLine();
		TemplateDlgLine templateDlgLine = new TemplateDlgLine(dlgLine);
		templateDlgLine.update(oldLine);
		if(dlgLine.isConfirmed()) {
			Line newLine =  factory.createLine(factory.createPoint(dlgLine.getXcoordinateInitial(), dlgLine.getYcoordinateInitial()), factory.createPoint(dlgLine.getXcoordinateLast(), dlgLine.getYcoordinateLast()), dlgLine.getColor());
			shapeFacade = new ShapeFacade(newLine);
			ShapeFacade oldShapeFacade = new ShapeFacade(oldLine);
			executeCommand(new CmdUpdateLine(oldLine, newLine));
			log.addElement("Updated->" + oldShapeFacade.serialize() + "->" + shapeFacade.serialize());
		}
	}
	
	/**
	 * <h3>Method is called when user want to update some existing {@link Rectangle} on draw.</h3>
	 * <p>
	 * {@link DlgRectangle} is presented for user to enter new values for rectangle.</br>
	 * Then {@link CmdUpdateRectangle} is executed if user confirm input.
	 * </p>
	 * @param rectangle Rectangle that user want to update.
	 */
	public void btnUpdateRectangleClicked(Rectangle oldRectangle) {
		factory = new ShapeFactory();
		DlgRectangle dlgRectangle = new DlgRectangle();
		TemplateDlgRectangle templateDlgRectangle = new TemplateDlgRectangle(dlgRectangle);
		templateDlgRectangle.update(oldRectangle, frame);
		if(dlgRectangle.isConfirmed()) {
			Rectangle newRectangle = factory.createRectangle(factory.createPoint(dlgRectangle.getXcoordinate(), dlgRectangle.getYcoordinate()), dlgRectangle.getRectangleWidth(), dlgRectangle.getRectangleHeight(), dlgRectangle.getEdgeColor(), dlgRectangle.getInteriorColor());
			shapeFacade = new ShapeFacade(newRectangle);
			ShapeFacade oldShapeFacade = new ShapeFacade(oldRectangle);
			executeCommand(new CmdUpdateRectangle(oldRectangle, newRectangle));
			log.addElement("Updated->" + oldShapeFacade.serialize() + "->" + shapeFacade.serialize());
		}
	}


	
	public void btnUpdateTriangleClicked(Triangle oldTriangle) {
		factory = new ShapeFactory();
		DlgTriangle dlgTriangle = new DlgTriangle();
		TemplateDlgTriangle templateDlgTriangle = new TemplateDlgTriangle(dlgTriangle);
		templateDlgTriangle.update(oldTriangle, frame);
		if(dlgTriangle.isConfirmed()) {
			Triangle newTriangle = factory.createTriangle(factory.createPoint(dlgTriangle.getXcoordinate(), dlgTriangle.getYcoordinate()), dlgTriangle.getTriangleWidth(), dlgTriangle.getTriangleHeight(), dlgTriangle.getEdgeColor(), dlgTriangle.getInteriorColor());
			shapeFacade = new ShapeFacade(newTriangle);
			ShapeFacade oldShapeFacade = new ShapeFacade(oldTriangle);
			executeCommand(new CmdUpdateTriangle(oldTriangle, newTriangle));
			log.addElement("Updated->" + oldShapeFacade.serialize() + "->" + shapeFacade.serialize());
		}
	}
	
	/**
	 * <h3>Method is called when user want to update some existing {@link Square} on draw.</h3>
	 * <p>
	 * {@link DlgSquare} is presented for user to enter new values for square.</br>
	 * Then {@link CmdUpdateSquare} is executed if user confirm input.
	 * </p>
	 * @param square Square that user want to update.
	 */
	public void btnUpdateSquareClicked(Square oldSquare) {
		factory = new ShapeFactory();
		DlgSquare dlgSquare = new DlgSquare();
		TemplateDlgSquare templateDlgSquare = new TemplateDlgSquare(dlgSquare);
		templateDlgSquare.update(oldSquare, frame);
		if(dlgSquare.isConfirmed()) {
			Square newSquare = factory.createSquare(factory.createPoint(dlgSquare.getXcoordinate(), dlgSquare.getYcoordinate()), dlgSquare.getSideLength(), dlgSquare.getEdgeColor(), dlgSquare.getInteriorColor());
			shapeFacade = new ShapeFacade(newSquare);
			ShapeFacade oldShapeFacade = new ShapeFacade(oldSquare);
			executeCommand(new CmdUpdateSquare(oldSquare, newSquare));
			log.addElement("Updated->" + oldShapeFacade.serialize() + "->" + shapeFacade.serialize());
		}
	}
	
	/**
	 * <h3>Method is called when user want to update some existing {@link Circle} on a draw.</h3>
	 * <p>
	 * {@link DlgCircle} is presented for user to enter new values for circle.</br>
	 * Then {@link CmdUpdateCircle} is executed if user confirm input.
	 * </p>
	 * @param circle Circle that user want to update.
	 */
	public void btnUpdateCircleClicked(Circle oldCircle) {
		factory = new ShapeFactory();
		DlgCircle dlgCircle = new DlgCircle();
		TemplateDlgCircle templateDlgCircle = new TemplateDlgCircle(dlgCircle);
		templateDlgCircle.update(oldCircle, frame);
		if(dlgCircle.isConfirmed()) {
			Circle newCircle = factory.createCircle(factory.createPoint(dlgCircle.getXcoordinateOfCenter(), dlgCircle.getYcoordinateOfCenter()), dlgCircle.getRadiusLength(), dlgCircle.getEdgeColor(), dlgCircle.getInteriorColor());
			shapeFacade = new ShapeFacade(newCircle);
			ShapeFacade oldShapeFacade = new ShapeFacade(oldCircle);
			executeCommand(new CmdUpdateCircle(oldCircle, newCircle));
			log.addElement("Updated->" + oldShapeFacade.serialize() + "->" + shapeFacade.serialize());
		}
	}
	
	/**
	 * <h3>Method that call command {@link CmdToFront} which move some shape one position forward in the list of shapes if shape is not already at last position.</h3>
	 */ 
	public void toFront() {
		Shape shape = getSelectedShape();
		shapeFacade = new ShapeFacade(shape);
		if (model.getIndexOf(shape) == model.getAll().size() - 1) JOptionPane.showMessageDialog(null, "Shape is already on top!");
		else {
			executeCommand(new CmdToFront(model, shape));
			log.addElement("Moved to front->" + shapeFacade.serialize());
		}
	}

	/**
	 * <h3>Method that call command {@link CmdBringToFront} which bring some shape at the end of the list of shapes if shape is not already at last position.</h3>
	 */ 
	public void bringToFront() {
		Shape shape = getSelectedShape();
		shapeFacade = new ShapeFacade(shape);
		if (model.getIndexOf(shape) == model.getAll().size() - 1) JOptionPane.showMessageDialog(null, "Shape is already on top!");
		else {
			executeCommand(new CmdBringToFront(model, shape, model.getAll().size() - 1));
			log.addElement("Bringed to front->" + shapeFacade.serialize());
		}
	}

	/**
	 * <h3>Method that call command {@link CmdToBack} which move some shape one position backward in the list of shapes if shape is not already at first position.</h3>
	 */ 
	public void toBack() {
		Shape shape = getSelectedShape();
		shapeFacade = new ShapeFacade(shape);
		if (model.getIndexOf(shape) == 0) JOptionPane.showMessageDialog(null, "Shape is already on bottom!");
		else {
			executeCommand(new CmdToBack(model, shape));
			log.addElement("Moved to back->" + shapeFacade.serialize());
		}
	}

	/**
	 * <h3>Method that call command {@link CmdBringToBack} which bring some shape at the beginnig of the list of shapes if shape is not already at first position.</h3>
	 */ 
	public void bringToBack() {
		Shape shape = getSelectedShape();
		shapeFacade = new ShapeFacade(shape);
		if (model.getIndexOf(shape) == 0) JOptionPane.showMessageDialog(null, "Shape is already on bottom!");
		else {
			executeCommand(new CmdBringToBack(model, shape));
			log.addElement("Bringed to back->" + shapeFacade.serialize());
		}
	}
	
	/**
	 * <h3>Method that returns currently selected shape.</h3>
	 * 
	 * @return Shape that is selected.
	 */
	public Shape getSelectedShape() {
		Iterator<Shape> iterator = model.getAll().iterator();
		while(iterator.hasNext()) {
			Shape shapeForModification = iterator.next();
			if(shapeForModification.isSelected()) return shapeForModification;
		}
		return null;
	}

	/**
	 * <h3>Method is called when user want to delete some shape(s).</h3>
	 * 
	 * {@link CmdRemoveShape} is executed.
	 */
	public void btnDeleteShapeClicked() {
		if(JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete selected shape?", "Warning!", JOptionPane.YES_NO_OPTION) == 0) {	
			Iterator<Shape> it = model.getAll().iterator();
			ArrayList<Shape> shapesForDeletion = new ArrayList<Shape>();
			
			while (it.hasNext()) {
				Shape shape = it.next();
				if(shape.isSelected()) {
					shapesForDeletion.add(shape);
					counterOfSelectedShapes--;
					shapeFacade = new ShapeFacade(shape);
					log.addElement("Deleted->" + shapeFacade.serialize());
				}	
			}
			
			executeCommand(new CmdRemoveShape(shapesForDeletion, model));
			handleSelectButtons();
		}
	}
	
	/**
	 * <h3>Method that execute some command.</h3>
	 * 
	 * Fire changes from {@link DrawingModel} to Observer {@link DrawingFrame} that updates buttons. 
	 * 
	 * @param command Command that need to be executed.
	 */
	public void executeCommand(Command command) {
		command.execute();
		commands.push(command);
	
		if (!undoCommands.isEmpty()) {
			undoCommands.removeAllElements();
			propertyChangeSupport.firePropertyChange("redo turn off", false, true);
		}
		
		if (model.getAll().isEmpty()) propertyChangeSupport.firePropertyChange("shape don't exist", false, true);
		else if (model.getAll().size() == 1) propertyChangeSupport.firePropertyChange("shape exist", false, true);
		
		if (commands.isEmpty()) propertyChangeSupport.firePropertyChange("draw is empty", false, true);
		else if (commands.size() == 1) propertyChangeSupport.firePropertyChange("draw is not empty", false, true);
		frame.getView().repaint();
	}
	
	/**
	 * <h3> Method that unexecute (undo) some command. </h3>
	 * 
	 * Fire changes from {@link DrawingModel} to Observer {@link DrawingFrame} that updates buttons. 
	 * 
	 * @param command Command that need to be unexecuted.
	 */
	public void undo() {
		commands.peek().unexecute();
		if (commands.peek() instanceof CmdRemoveShape) {
			int i = ((CmdRemoveShape) commands.peek()).getSize();
			for (int j = 0; j < i; j++) undoCommandsLog.add(log.remove(log.size() - 1));
		}
		else undoCommandsLog.add(log.remove(log.size() - 1));
		if (commands.peek() instanceof CmdSelectShape) handleSelect((log.get(log.size() - 1)).split("->")[0], "undo");
		undoCommands.push(commands.pop());
		if (log.isEmpty()) propertyChangeSupport.firePropertyChange("log turn off", false, true);
		if (undoCommands.size() == 1) propertyChangeSupport.firePropertyChange("redo turn on", false, true);
		if (commands.isEmpty()) propertyChangeSupport.firePropertyChange("draw is empty", false, true);
		frame.getView().repaint();
	}
	
	/**
	 * <h3> Method that execute previously unexecuted command. </h3>
	 * 
	 * Fire changes from {@link DrawingModel} to Observer {@link DrawingFrame} that updates buttons. 
	 * <br>
	 * @param command Command that need to be executed again.
	 */
	public void redo() {		
		undoCommands.peek().execute();
		if (undoCommands.peek() instanceof CmdRemoveShape) {
			int i = ((CmdRemoveShape) undoCommands.peek()).getSize();
			for (int j = 0; j < i; j++) log.addElement(undoCommandsLog.pop());
		}
		else log.addElement(undoCommandsLog.pop());
		if (undoCommands.peek() instanceof CmdSelectShape) handleSelect((log.get(log.size() - 1)).split("->")[0], "redo");
		commands.push((undoCommands.pop()));
		if (undoCommands.isEmpty()) propertyChangeSupport.firePropertyChange("redo turn off", false, true);
		if (commands.size() == 1) {
			propertyChangeSupport.firePropertyChange("shape exist", false, true);
			propertyChangeSupport.firePropertyChange("draw is not empty", false, true);
			propertyChangeSupport.firePropertyChange("log turn on", false, true);
		}
		frame.getView().repaint();
	}
	
	/**
	 * <h3>Method that is obligate for displaying {@ JFileChooser} for user to choose where to save draw as serialized file, log file with executed commands or picture (screenshot) of draw.</h3>
	 * 
	 * Then called appropriate {@link FileManager} to save {@docRoot File}.
	 */ 
	public void save() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
	    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
		chooser.enableInputMethods(false);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileHidingEnabled(false);
		chooser.setEnabled(true);
		chooser.setDialogTitle("Save");
		chooser.setAcceptAllFileFilterUsed(false);
		if (!model.getAll().isEmpty()) {
			chooser.setFileFilter(new FileNameExtensionFilter("Serialized draw", "ser"));
			chooser.setFileFilter(new FileNameExtensionFilter("Picture", "jpeg"));
		}
		if (!commands.isEmpty()) chooser.setFileFilter(new FileNameExtensionFilter("Commands log", "log"));
		if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			if (chooser.getFileFilter().getDescription().equals("Serialized draw")) fileManager = new FileManager(new FileDraw(model));
			else if (chooser.getFileFilter().getDescription().equals("Commands log")) fileManager = new FileManager(new FileLog(frame, model, this));
			else fileManager = new FileManager(new FilePicture(frame));
			fileManager.save(chooser.getSelectedFile());
		}
		chooser.setVisible(false);
	}

	/**
	 * <h3>Method that is obligate for displaying {@ JFileChooser} for user to choose file to open.</h3>
	 * 
	 * Supported formats are serialized file and log file with executed commands.
	 * 
	 * Then called appropriate {@link FileManager} to open {@docRoot File}.
	 */ 
	public void open() {
		JFileChooser chooser = new JFileChooser();
		chooser.enableInputMethods(true);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileHidingEnabled(false);
		chooser.setEnabled(true);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
		chooser.setFileFilter(new FileNameExtensionFilter("Serialized draw", "ser"));
		chooser.setFileFilter(new FileNameExtensionFilter("Commands log", "log"));
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			model.removeAll();
			log.removeAllElements();
			undoCommands.clear();
			undoCommandsLog.clear();
			commands.clear();
			frame.getView().repaint();
			if (chooser.getFileFilter().getDescription().equals("Serialized draw")) {
				fileManager = new FileManager(new FileDraw(model));
				propertyChangeSupport.firePropertyChange("serialized draw opened", false, true);
			}
			else if (chooser.getFileFilter().getDescription().equals("Commands log")) fileManager = new FileManager(new FileLog(frame, model, this));
			fileManager.open(chooser.getSelectedFile());
		}	
		chooser.setVisible(false);
	}

	/**
	 * <h3>Method that create new draw if draw is not already empty removing all executed shapes and comands.</h3>
	 */ 
	public void newDraw() {
		if(JOptionPane.showConfirmDialog(null, "Are you sure that you want to start new draw?", "Warning!", JOptionPane.YES_NO_OPTION) == 0) {	
			model.removeAll();
			log.removeAllElements();
			undoCommands.clear();
			undoCommandsLog.clear();
			commands.clear();
			propertyChangeSupport.firePropertyChange("draw is empty", false, true);
			frame.getView().repaint();
		}
	}
}