package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Calculator extends JFrame{

	private static final long serialVersionUID = 1L;
	public CalcModelImpl calcModel;


	public Calculator() {
		setLocation(100, 100);
		setSize(700,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		calcModel = new CalcModelImpl();
		initGUI();
		
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
		List<OperationButton> opButtons = createButtons();
		
		//prvi red
		cp.add(display(calcModel.toString()),new RCPosition(1, 1));
		cp.add(button("=",calcModel -> {
			double result = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue());
			calcModel.setValue(result);
			calcModel.clearActiveOperand();
			calcModel.setPendingBinaryOperation(null);
		}),new RCPosition(1, 6));
		cp.add(button("clr",calcModel -> calcModel.clear()),new RCPosition(1, 7));
		
		//Brojke
		cp.add(numberButton("9",calcModel -> calcModel.insertDigit(9)), new RCPosition(2,5));
		cp.add(numberButton("8",calcModel -> calcModel.insertDigit(8)), new RCPosition(2,4));
		cp.add(numberButton("7",calcModel -> calcModel.insertDigit(7)), new RCPosition(2,3));
		
		cp.add(numberButton("6",calcModel -> calcModel.insertDigit(6)), new RCPosition(3,5));
		cp.add(numberButton("5",calcModel -> calcModel.insertDigit(5)), new RCPosition(3,4));
		cp.add(numberButton("4",calcModel -> calcModel.insertDigit(4)), new RCPosition(3,3));
		
		cp.add(numberButton("3",calcModel -> calcModel.insertDigit(3)), new RCPosition(4,5));
		cp.add(numberButton("2",calcModel -> calcModel.insertDigit(2)), new RCPosition(4,4));
		cp.add(numberButton("1",calcModel -> calcModel.insertDigit(1)), new RCPosition(4,3));
		
		cp.add(numberButton("0",calcModel -> calcModel.insertDigit(0)), new RCPosition(5,3));
		cp.add(button("+/-",calcModel -> calcModel.swapSign()),new RCPosition(5, 4));
		cp.add(button(".",calcModel -> calcModel.insertDecimalPoint()),new RCPosition(5, 5));
		
		//Funkcije
		cp.add(button("1/x",calcModel -> {calcModel.setValue(1.0/calcModel.getValue());}),new RCPosition(2,1));
		cp.add(opButtons.get(0),new RCPosition(2,2));
		cp.add(opButtons.get(1),new RCPosition(3,1));
		cp.add(opButtons.get(2),new RCPosition(3,2));
		cp.add(opButtons.get(3),new RCPosition(4,1));
		cp.add(opButtons.get(4),new RCPosition(4,2));
		cp.add(opButtons.get(5),new RCPosition(5,1));
		cp.add(opButtons.get(6),new RCPosition(5,2));
		
		cp.add(button("reset",calcModel -> calcModel.clearAll()),new RCPosition(2,7));
		cp.add(button("push",calcModel -> calcModel.addToValueStack(calcModel.getValue())),new RCPosition(3,7));
		cp.add(button("pop",calcModel -> {
			if(calcModel.getValueStack().isEmpty()) {
				calcModel.freezeValue("Stack is empty!");
				calcModel.clear();
			}
			calcModel.setValue(calcModel.removeFromValueStack());
		}),new RCPosition(4,7));
		
		//Operacije
		cp.add(button("+",calcModel -> {
			if(calcModel.isActiveOperandSet()) {
				calcModel.setActiveOperand(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue()));
				calcModel.setPendingBinaryOperation((a,b) -> a+b);
				calcModel.freezeValue(""+calcModel.getActiveOperand());
				calcModel.clear();
			}else {
				calcModel.setActiveOperand(calcModel.getValue());
				calcModel.setPendingBinaryOperation((a,b) -> a+b);
				calcModel.freezeValue(""+calcModel.getActiveOperand());
				calcModel.clear();
			}
			
		}),new RCPosition(5,6));
		cp.add(button("-",calcModel -> {
			if(calcModel.isActiveOperandSet()) {
				calcModel.setActiveOperand(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue()));
				calcModel.setPendingBinaryOperation((a,b) -> a-b);
				calcModel.freezeValue(""+calcModel.getActiveOperand());
				calcModel.clear();
			}else {
				calcModel.setActiveOperand(calcModel.getValue());
				calcModel.setPendingBinaryOperation((a,b) -> a-b);
				calcModel.freezeValue(""+calcModel.getActiveOperand());
				calcModel.clear();
			}
			
		}),new RCPosition(4,6));
		cp.add(button("*",calcModel -> {
			if(calcModel.isActiveOperandSet()) {
				calcModel.setActiveOperand(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue()));
				calcModel.setPendingBinaryOperation((a,b) -> a*b);
				calcModel.freezeValue(""+calcModel.getActiveOperand());
				calcModel.clear();
			}else {
				calcModel.setActiveOperand(calcModel.getValue());
				calcModel.setPendingBinaryOperation((a,b) -> a*b);
				calcModel.freezeValue(""+calcModel.getActiveOperand());
				calcModel.clear();
			}
			
		}),new RCPosition(3,6));
		cp.add(button("/",calcModel -> {
			if(calcModel.isActiveOperandSet()) {
				calcModel.setActiveOperand(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue()));
				calcModel.setPendingBinaryOperation((a,b) -> a/b);
				calcModel.freezeValue(""+calcModel.getActiveOperand());
				calcModel.clear();
			}else {
				calcModel.setActiveOperand(calcModel.getValue());
				calcModel.setPendingBinaryOperation((a,b) -> a/b);
				calcModel.freezeValue(""+calcModel.getActiveOperand());
				calcModel.clear();
			}
			
		}),new RCPosition(2,6));
		
		JCheckBox checkBox = new JCheckBox("Inv");
		checkBox.addItemListener(l -> {
			opButtons.forEach(b -> b.inverseValues());
		});
		cp.add(checkBox, new RCPosition(5,7));
		
		
	}
	
	private JLabel display(String text) {
		JLabel label = new JLabel(text);
		label.setBackground(Color.yellow);
		label.setOpaque(true);
		Border border = BorderFactory.createLineBorder(Color.black);
		label.setBorder(border);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		calcModel.addCalcValueListener(calcModel -> label.setText(calcModel.toString()));
		return label;
	}
	
	private List<OperationButton> createButtons() {
		List<OperationButton> temp = new ArrayList<OperationButton>();
		OperationButton sin = new OperationButton("sin","arcsin",FunctionsWithInverse.SINUS,FunctionsWithInverse.ARCSINUS,calcModel);
		temp.add(sin);
		OperationButton log = new OperationButton("log","10^x",FunctionsWithInverse.LOG,FunctionsWithInverse.POW10,calcModel);
		temp.add(log);
		OperationButton cos = new OperationButton("cos","arccos",FunctionsWithInverse.COSINUS,FunctionsWithInverse.ARCCOSINUS,calcModel);
		temp.add(cos);
		OperationButton ln = new OperationButton("ln","e^x",FunctionsWithInverse.LN,FunctionsWithInverse.POW_E,calcModel);
		temp.add(ln);
		OperationButton tan = new OperationButton("tan","arctan",FunctionsWithInverse.TANGENS,FunctionsWithInverse.ARCTANGENS,calcModel);
		temp.add(tan);
		OperationButton pow = new OperationButton("x^n","x^(1/n)",FunctionsWithInverse.POW,FunctionsWithInverse.POWINV,calcModel);
		temp.add(pow);
		OperationButton ctg = new OperationButton("ctg","arcctg",FunctionsWithInverse.COTANGENS,FunctionsWithInverse.ARCCOTANGENS,calcModel);
		temp.add(ctg);
		return temp;
		
	}
	
	private JButton button(String name,ICalculatorFunction function) {
		JButton button = new JButton(name);
		button.setOpaque(true);
		Border border = BorderFactory.createLineBorder(Color.black);
		button.setBorder(border);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setBackground(new Color(222, 226, 228));
		button.addActionListener(f -> {
			function.doOperation(calcModel);});
		return button;
	}
	
	private JButton numberButton(String name,ICalculatorFunction function) {
		JButton button = new JButton(name);
		button.setOpaque(true);
		Border border = BorderFactory.createLineBorder(Color.black);
		button.setBorder(border);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setBackground(new Color(222, 226, 228));
		button.setFont(button.getFont().deriveFont(30f));
		button.addActionListener(f -> {
			function.doOperation(calcModel);});
		return button;
	}
	
	private JButton operationButton(String name,String invName,ICalculatorFunction function,ICalculatorFunction invFunction) {
		JButton button = new JButton(name);
		button.setOpaque(true);
		Border border = BorderFactory.createLineBorder(Color.black);
		button.setBorder(border);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setBackground(new Color(222, 226, 228));
		button.addActionListener(f -> {
			function.doOperation(calcModel);});
		return button;
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}
}
