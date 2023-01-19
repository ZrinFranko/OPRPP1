package hr.fer.zemris.java.gui.calc;

public class FunctionsWithInverse {
	
	public static final ICalculatorFunction SINUS = (cModel) -> {cModel.setValue(Math.sin(cModel.getValue()));};
	public static final ICalculatorFunction ARCSINUS = (cModel) -> {cModel.setValue(Math.asin(cModel.getValue()));};
	public static final ICalculatorFunction COSINUS = (cModel) -> {cModel.setValue(Math.cos(cModel.getValue()));};
	public static final ICalculatorFunction ARCCOSINUS = (cModel) -> {cModel.setValue(Math.acos(cModel.getValue()));};
	public static final ICalculatorFunction TANGENS = (cModel) -> {cModel.setValue(Math.tan(cModel.getValue()));};
	public static final ICalculatorFunction ARCTANGENS = (cModel) -> {cModel.setValue(Math.atan(cModel.getValue()));};
	public static final ICalculatorFunction COTANGENS = (cModel) -> {cModel.setValue(1.0/Math.tan(cModel.getValue()));};
	public static final ICalculatorFunction ARCCOTANGENS = (cModel) -> {cModel.setValue(Math.atan(1.0/cModel.getValue()));};
	public static final ICalculatorFunction LOG = (cModel) -> {cModel.setValue(Math.log10(cModel.getValue()));};
	public static final ICalculatorFunction POW10 = (cModel) -> {cModel.setValue(Math.pow(10, cModel.getValue()));};
	public static final ICalculatorFunction LN = (cModel) -> {cModel.setValue(Math.log(cModel.getValue()));};
	public static final ICalculatorFunction POW_E = (cModel) -> {cModel.setValue(Math.pow(Math.E, cModel.getValue()));};
	public static final ICalculatorFunction POW = (cModel) -> {
		cModel.freezeValue(cModel.toString());
		cModel.setActiveOperand(cModel.getValue());
		cModel.setPendingBinaryOperation((a,b) -> Math.pow(a, b));
		cModel.clear();
	};
	public static final ICalculatorFunction POWINV = (cModel) -> {
		cModel.freezeValue(cModel.toString());
		cModel.setActiveOperand(cModel.getValue());
		cModel.setPendingBinaryOperation((a,b) -> Math.pow(a, 1.0/b));
		cModel.clear();
	};

}
