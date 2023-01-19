package hr.fer.zemris.math;

import java.util.*;

public class Complex {

	private double rValue;
	private double iValue;

	public static final Complex ZERO = new Complex(0.0,0.0);
	public static final Complex ONE = new Complex(1.0,0.0);
	public static final Complex ONE_NEG = new Complex(-1.0,0.0);
	public static final Complex IM = new Complex(0.0,1.0);
	public static final Complex IM_NEG = new Complex(0.0,-1.0);


	public Complex() {
		this.rValue = 0;
		this.iValue = 0;
	}
	public Complex(double re, double im) {
		this.rValue = re;
		this.iValue = im;
	}
	/**
	 * Function that calculates the module of the complex number
	 * @return the module of the complex number
	 */
	public double module() {
		return Math.sqrt(this.getReal()*this.getReal() + this.getImaginary()*this.getImaginary());
	}
	/**
	 * Function that calculates a new complex number generated as a multiplication of two complex numbers
	 * @param c the value of the complex number with which the current complex number is multiplied
	 * @return new complex number that is a multiplication of the current and the given complex numbers
	 */
	public Complex multiply(Complex c) {
		double newReal = (c.getReal() * rValue) - (c.getImaginary() * iValue);
		double newIm = (rValue * c.getImaginary()) + (c.getReal() * iValue);
		return new Complex(newReal,newIm);
	}

	/**
	 * Function that calculates a new complex number generated as a division of two complex numbers
	 * @param c the value of the complex number with which the current complex number is divided
	 * @return new complex number that is a division of the current and the given complex numbers
	 */
	public Complex divide(Complex c) {
		double denominator = c.getReal()*c.getReal() + c.getImaginary()*c.getImaginary();
		double newReal = (c.getReal()*this.getReal()) + (this.getImaginary()* c.getImaginary());
		double newIm = (this.getReal() * c.getImaginary()) + (c.getReal() * this.getImaginary());
		return new Complex(newReal,newIm);
	}

	/**
	 * Function that adds the given complex number to the current one
	 * @param c the complex number that will be added to the current one
	 * @return a new complex number generated as an addition of the current and given numbers
	 */
	public Complex add(Complex c) { 
		return new Complex(c.getReal()+this.rValue, c.getImaginary() + this.iValue);
	}

	/**
	 * Function that subtracts the given complex number from the current one
	 * @param c the complex number that will be subtracted from the current one 
	 * @return a new complex number generated as a subtraction of the given one from the current one
	 */
	public Complex sub(Complex c) {
		return new Complex(this.rValue - c.getReal(), this.iValue - c.getImaginary());
	}

	/**
	 * Function that returns a new complex number that is a negation of the current one
	 * @return the negated number of the current one
	 */
	public Complex negate() {
		return new Complex(-1 * this.getReal(), -1 * this.getImaginary());
	}

	/**
	 * Function that returns a new complex number that is the current one to the power of n
	 * @param n the power to whose we need to get the current number
	 * @return the newly generated number that is the current one to the power of n
	 */
	public Complex power(int n) {
		if(n < 0) throw new IllegalArgumentException("You can only power complex numbers to the positive power");
		if(n == 0) return this.ONE;
		if(n == 1) return this;
		Complex temp = this;
		for(int i = n ; i > 0 ; i--)
			temp = temp.multiply(this);
		return temp;
	}

	/**
	 * Function that returns the nth root of the current complex number
	 * @param n the nth root that needs to be found
	 * @return a list of n nth roots of the complex number
	 */
	public List<Complex> root(int n) {
		if(n <= 0) throw new IllegalArgumentException("Can only find the positive n-th root of a complex number");
		if(n == 1) return List.of(this);
		List<Complex> temp = new ArrayList<>();
		double kut = Math.atan(this.getImaginary() / this.getReal());
		double offset = (2 * Math.PI) / n;
		double module = this.module();
		module = findNthRootOfModule(module,n);
		for(int i = 0; i < n ; i++) {
			temp.add(new Complex(module * Math.cos(kut + (i*offset)),module * Math.sin(kut+ (i*offset))));
		}
		return temp;
	}

	/**
	 * Auxiliary function that calculates the nth root of a double type number
	 * @param moduleValue the double value
	 * @param root tha nth root that needs to be calculated
	 * @return the value of the nth root of the given number
	 */
	private double findNthRootOfModule(double moduleValue,int root) {
		double p = 0.0001;

		if(moduleValue < 0 && root %2 == 0) {
			throw new IllegalArgumentException("Value of module can be negative only if the root is odd");
		}
		if(moduleValue == 0)
			return 0;

		double x1 = moduleValue;
		double x2 = moduleValue/root;

		while((x1-x2) > p || (x1-x2) < -p) {
			x1 = x2;
			x2 = ((root - 1.0) * x2 + (moduleValue / Math.pow(x2, root - 1.0))) / root;
		}

		return x2;

	}
	@Override
	public String toString() {
		return this.getReal() + (this.getImaginary() < 0 ? (" - i" + (-1*this.getImaginary()) ): (" + i" + this.getImaginary()));
	}

	/**
	 * Getter of the real part of the complex number
	 * @return the value of the real part of the complex number
	 */
	public double getReal() {
		return rValue;
	}

	/**
	 * Getter of the imaginary part of the complex number
	 * @return the value of the imaginary part of the complex number
	 */
	public double getImaginary() {
		return iValue;
	}

	/**
	 * Function that generates a new Complex number from a string
	 * @param line the string which contains a Complex number
	 * @return the newly generated Complex number
	 */
	public static Complex generateFromString(String line) {
		if(line == null) throw new NullPointerException("Line must not be null");
		// -1 + i0

		String temp = line;
		
		char[] inputData = temp.trim().toCharArray();
		int startOfIm = 0;
		boolean startedNegative = false;
		
		if(inputData[0] == '-' || inputData[0] == '+') {
			if(inputData[0] == '-')
				startedNegative = true;
			inputData = removeFirst(inputData);
		}
		
		if(inputData.length == 1) {
			if(inputData[0] == 'i') {
				if(startedNegative)
					return new Complex(0,-1);
				else 
					return new Complex(0,1);
			}
			if(startedNegative) {
				return new Complex(-1*Double.parseDouble(temp.substring(1)),0);
			}
			return new Complex(Double.parseDouble(temp),0);
		}else {
			for(int i = 0; i < inputData.length ; i++) {
				if(inputData[i] == 'i') {
					startOfIm = i;
					break;
				}
			}
			if(startOfIm == (inputData.length-1)) {
				if(startedNegative) {
					if(inputData[startOfIm-1] == '-') {
						return new Complex(-1*Double.parseDouble(temp.substring(0,startOfIm-1)) , -1);
					}else
						return new Complex(-1*Double.parseDouble(temp.substring(0,startOfIm-1)) , 1);
				}else {
					if(inputData[startOfIm-1] == '-') {
						return new Complex(Double.parseDouble(temp.substring(0,startOfIm-1)) ,  -1);
					}else
						return new Complex(Double.parseDouble(temp.substring(0,startOfIm-1)) , 1);
				}	
			}else {
				if(startOfIm == 0) {
					if(startedNegative)
						return new Complex(0,-1*Double.parseDouble(temp.substring(1)));
					return new Complex(0,Double.parseDouble(temp.substring(1)));
				}else {
					if(startedNegative) {
						if(inputData[startOfIm-1] == '-') {
							return new Complex(-1*Double.parseDouble(temp.substring(1,startOfIm)) , -1*Double.parseDouble(temp.substring(startOfIm+2)));
						}else
							return new Complex(-1*Double.parseDouble(temp.substring(1,startOfIm)) , Double.parseDouble(temp.substring(startOfIm+2)));
					}else {
						if(inputData[startOfIm-1] == '-') {
							return new Complex(Double.parseDouble(temp.substring(0,startOfIm-1)) ,  -1*Double.parseDouble(temp.substring(startOfIm+1)));
						}else
							return new Complex(Double.parseDouble(temp.substring(0,startOfIm-1)) , Double.parseDouble(temp.substring(startOfIm+1)));
					}
				}
				
			}
		}
	}

	private static char[] removeFirst(char[] oldArray) {
		char[] newArray = new char[oldArray.length-1];
		for(int i = 0 ; i < newArray.length ; i++) {
			newArray[i] = oldArray[i+1];
		}
		return newArray;
	}

}
