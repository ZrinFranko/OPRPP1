package hr.fer.oprpp1.custom.collections.demo;

import java.util.regex.Pattern;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		/**
		 * checking if the argument array has the correct number of arguments (1)
		 */

		if(args.length != 1) {
			System.out.println("Wrong number of arguments");
			return;
		}
		/**
		 * Using a function checks if only numbers and mathematical operands are in the given string argument
		 */
		if(!isExpressionValid(args[0])) {
			System.out.println("Expression is invalid. It must only contain numbers or basic mathematical operators ( +, - * / %) !");
			return;
		}
		ObjectStack stack = new ObjectStack();
		String[] expression = args[0].split(" ");
		for(String s : expression) {
			if(isNumber(s)) {
				stack.push(Integer.parseInt(s));
			}else if(s.equals("")){
				continue;
			}else {
				int secondOperand = (int) stack.pop();
				int firstOperand = (int) stack.pop();
				if(secondOperand == 0 && s.equals("/") || s.equals("%")) {
					System.out.println("Division with 0 is strongly prohibited!");
					return;
				}
				int operationResult = doOperation(firstOperand,secondOperand, s);
				stack.push(operationResult);
				if(s.equalsIgnoreCase("cubed")) {
					stack.push(firstOperand);
				}
			}
		}
		if(stack.size() != 1) {
			System.out.println("Stack size is invalid!");
		}else {
			System.out.println("Expression evaluates to " + stack.pop());
		}
	}

	/**
	 * Private function that does a mathematical function depending on the kind of operand sent into it
	 * @param firstOperand first operator of the equation
	 * @param secondOperand second operator of the equation
	 * @param operator type of operation that needs to be done on the operands
	 * @return the value gotten after the operation is complete
	 */
	private static int doOperation(int firstOperand, int secondOperand, String operator) {
		switch(operator) {
		case "+":
			return firstOperand + secondOperand;
		case "-":
			return firstOperand - secondOperand;
		case "*":
			return firstOperand * secondOperand;
		case "/":
			return firstOperand / secondOperand;
		case "bigger":
			return firstOperand > secondOperand ? firstOperand : secondOperand;
		case "cubed":
			return secondOperand*secondOperand*secondOperand;
		default:
			return firstOperand % secondOperand;

		}
	}

	/**
	 * Function that uses regex to check if the string contains only numbers and a set of operators (+ - * / %)
	 * @param string string that needs to be checked
	 * @return returns true if the string is valid and false otherwise
	 */
	private static boolean isExpressionValid(String string) {
		String regex = "^((-?\\d+\\s+)+([\\+\\-\\*\\/\\%(?:bigger)(?:cubbed)]\\s*)+)+";
		return Pattern.matches(regex, string);
	}

	/**
	 * Function that uses regex to check if the sent string is a number be it positive or negative
	 * @param s string that needs to be checked 
	 * @return returns true if the string is a number and false otherwise
	 */
	private static boolean isNumber(String s) {
		String regex = "^-?\\d+$";
		return Pattern.matches(regex, s);
	}

}
