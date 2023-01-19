package hr.fer.zemris.java.gui.calc;

import javax.swing.JButton;

public class OperationButton extends JButton{

	private static final long serialVersionUID = 1L;
	private String currentName;
	private String regularName;
	private String invName;

	private ICalculatorFunction currentFunction;
	private ICalculatorFunction function;
	private ICalculatorFunction invFunction;
	private CalcModelImpl calcModel;

	public OperationButton(String regularName, String inverseName, ICalculatorFunction function,
			ICalculatorFunction invFunction, CalcModelImpl calcModel) {
		super(regularName);
		this.regularName = regularName;
		this.invName = inverseName;
		this.function = function;
		this.invFunction = invFunction;
		this.calcModel = calcModel;

		this.currentFunction = function;
		this.currentName = regularName;
		this.setText(regularName);
		this.addActionListener(f -> {
			currentFunction.doOperation(calcModel);});

	}

	public void inverseValues() {
		if(currentFunction.equals(function)) {
			this.removeActionListener(f -> {
				currentFunction.doOperation(calcModel);});
			currentFunction = invFunction;
			this.addActionListener(f -> {
				currentFunction.doOperation(calcModel);});
		}
		else {
			this.removeActionListener(f -> {
				currentFunction.doOperation(calcModel);});
			currentFunction = function;
			this.addActionListener(f -> {
				currentFunction.doOperation(calcModel);});
		}		
		if(currentName.equals(regularName)) {
			currentName = invName;
			this.setText(currentName);
			
		}else {
			currentName = regularName;
			this.setText(currentName);
		}
	}


}
