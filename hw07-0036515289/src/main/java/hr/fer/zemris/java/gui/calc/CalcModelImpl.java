package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;
import java.util.*;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class CalcModelImpl implements CalcModel{

	private boolean isEditable;
	private boolean isNegative;
	private String enteredDigits;
	private double currentValue;
	private String frozenValue;
	private OptionalDouble activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private List<CalcValueListener> listeners;
	private List<Double> valueStack;
	
	public CalcModelImpl() {
		isEditable = true;
		isNegative = false;
		enteredDigits = "";
		currentValue = 0;
		frozenValue = null;
		activeOperand = OptionalDouble.empty();
		pendingOperation = null;
		listeners = new ArrayList<>();	
		valueStack = new ArrayList<>();	
	}
	
	public List<Double> getValueStack() {
		return valueStack;
	}

	public void addToValueStack(Double value) {
		valueStack.add(value);
	}
	public Double removeFromValueStack() {
		return valueStack.get(valueStack.size()-1);
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return currentValue;
	}

	@Override
	public void setValue(double value) {
		currentValue = value;
		enteredDigits = ((Double)value).toString();
		isEditable = false;
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		isNegative = false;
		currentValue = 0;
		enteredDigits = "";
		isEditable = true;
		notifyListeners();
		
	}

	@Override
	public void clearAll() {
		clear();
		frozenValue =null;
		notifyListeners();
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable()) throw new CalculatorInputException("Currently unable to process input!");
		
		currentValue *= -1; 
		if(isNegative)
			enteredDigits = enteredDigits.substring(1);
		else
			enteredDigits = "-" + enteredDigits;
		isNegative = !isNegative;
		freezeValue(null);
		notifyListeners();
		
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable()) throw new CalculatorInputException("Currently cannot input a decimal point!");
		if(enteredDigits.contains(".")) throw new CalculatorInputException("Decimal point already exists");
		if(enteredDigits.isEmpty())
			currentValue = 0;
		enteredDigits += ".";
		freezeValue(null);
		notifyListeners();
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable()) throw new CalculatorInputException("Cannot input new digits currently!");
		enteredDigits += digit;
		try {
			currentValue = Double.parseDouble(enteredDigits);
		}catch(NumberFormatException e) {
			throw new CalculatorInputException("Input must be a digit!");
		}
		freezeValue(null);
		notifyListeners();
		
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand.isPresent();
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(isActiveOperandSet()) {
			return activeOperand.getAsDouble();
		}
		throw new IllegalStateException();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = OptionalDouble.of(activeOperand);
		isEditable = true;
		notifyListeners();
		
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = OptionalDouble.empty();
		isEditable = true;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
		isEditable = true;
		notifyListeners();
	}
	
	
	public void freezeValue(String value) {
		frozenValue = value;
	} 
	
	private void notifyListeners() {
		for(CalcValueListener cvl : listeners) {
			cvl.valueChanged(this);
		}
	}
	
	@Override
	public String toString() {
		if(frozenValue == null) {
			if(enteredDigits.isEmpty()) {
				if(isNegative)
					return "-0";
				return "0";
			}
			return enteredDigits;
		}
		return frozenValue;
	}

}
