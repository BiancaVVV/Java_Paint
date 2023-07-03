package commands;

import shapes.Triangle;

public class CmdUpdateTriangle implements Command {
	
	private Triangle oldState;
	private Triangle newState;
	private Triangle originalState;
	
	public CmdUpdateTriangle(Triangle oldState, Triangle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	/**
	 * Update Triangle.
	 */
	@Override
	public void execute() {
		originalState = oldState.clone();
		oldState.setUpPoint(newState.getUpPoint().clone());
		oldState.setHeight(newState.getHeight());
		oldState.setWidth(newState.getWidth());
		oldState.setColor(newState.getColor());
		oldState.setInteriorColor(newState.getInteriorColor());
		oldState.generatePoints();
	}

	/**
	 * Undo previous updating and return Triangle to the original values.
	 */
	@Override
	public void unexecute() {
		oldState.setUpPoint(originalState.getUpPoint());
		oldState.setHeight(originalState.getHeight());
		oldState.setWidth(originalState.getWidth());
		oldState.setColor(originalState.getColor());
		oldState.setInteriorColor(originalState.getInteriorColor());
		oldState.generatePoints();
	}
}