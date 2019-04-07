package application.model;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * @author Gabriel Morales (woc797)
 *
 * Class ColorTransition extends Transition from javafx animation package.
 * Uses the Node inheritance tree to change the nodes that are "skinnable".
 * Interpolates colors from one to another and applies it to a
 * specified javafx style.
 *
 */
public class ColorTransition extends Transition {

	
	private Node myNode;
	private Color from;
	private Color to;
	private String style;
	
	
	/**
	 * Constructs a new ColorTransition object
	 * 
	 * @param duration Duration - Duration object for length of keyframs.
	 * @param myNode Node - The object to perform the transition on.
	 * @param from Color - The Color to transition from.
	 * @param to Color - The Color to transition to.
	 * @param style String - The javafx property to modify.
	 */
	public ColorTransition (Duration duration, Node myNode, Color from, Color to, String style)
	{
		this.myNode = myNode;
		this.from = from;
		this.to = to;
		this.style = style;
		
		setCycleDuration(duration);
		
	}
	
	/**
	 * Overriden interpolate method (internal only)
	 * 
	 * @param range from 0 (start) to 1 (end)
	 */
	@Override
	protected void interpolate(double val) {
		
		Color lerpColor = this.getFrom().interpolate(this.getTo(), val);
			
		String color = lerpColor.toString().substring(2);
		
		this.getMyNode().setStyle(style + "#" + color);
		
		
	}
	
	/**
	 * Gets the node being modified.
	 * 
	 * @return Node - node being modified
	 */
	public Node getMyNode() {
		return myNode;
	}

	/**
	 * Set the node being modified.
	 * 
	 * @param myNode Node - node being modified
	 */
	public void setMyNode(Node myNode) {
		this.myNode = myNode;
	}

	/**
	 * Get color to transition from.
	 * 
	 * @return Color - color being transitioned from.
	 */
	public Color getFrom() {
		return from;
	}

	/**
	 * Set color being transitioned from.
	 * 
	 * @param from Color - color being transitioned from.
	 */
	public void setFrom(Color from) {
		this.from = from;
	}

	/**
	 * Get color being transitioned to.
	 * 
	 * @return Color - color transitioned to
	 */
	public Color getTo() {
		return to;
	}

	/**
	 * Set color being transitioned to.
	 * 
	 * @param to Color - color to transition to
	 */
	public void setTo(Color to) {
		this.to = to;
	}
	
	
	

}
