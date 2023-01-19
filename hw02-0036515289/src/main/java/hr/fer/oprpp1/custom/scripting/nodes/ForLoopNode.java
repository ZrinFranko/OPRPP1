package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

/**
 * Code showing the implementation of the ForLoopNode class 
 * @author zrin
 *
 */
public class ForLoopNode extends Node{
	
	/**
	 * Variable of the for loop
	 */
	private ElementVariable variable;
	/**
	 * the expression at which the loop starts
	 */
	private Element startExpression;
	/**
	 * The expression at which the loop ends
	 */
	private Element endExpression;
	/**
	 * The expression showing how the start variable changes in order to get to the end of the loop
	 */
	private Element stepExpression;
	
	/**
	 * Public construct of the ForLoopNode class
	 * @param variable variable which we are iterating on
	 * @param startExpression the starting value of the variable
	 * @param endExpression the ending value of the variable
	 * @param stepExpression the expression defining how we are iterating over the variable
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Function that returns the value of the variable
	 * @return value of the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Function that returns the value of the start expression
	 * @return the value of the start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Function that returns the value of the end expression
	 * @return value of the end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Function that returns the value of the step expression
	 * @return value of the step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	

}
