package hr.fer.zemris.math;

import java.util.*;

public class ComplexPolynomial {
	
	private List<Complex> factors;
	// ...
	// constructor
	public ComplexPolynomial(Complex ...factors) {
		this.factors = new ArrayList<>();
		for(Complex factor : factors) {
			this.factors.add(factor);
		}
	}
	
	/**
	 * Function that returns the order of the polynomial
	 * @return the order of the polynomial
	 */
	public short order() {
		if(factors.isEmpty() || factors.size() == 1) return 0;
		return (short) (factors.size()-1);
	}
	// computes a new polynomial this*p
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] tempFactors = new Complex[this.order()+p.order()+1];
		
		for(int i = 0 ; i <= this.order() ; i++) {
			for(int j = 0; j <= p.order() ; j++) {
				Complex temp = this.factors.get(i).multiply(p.factors.get(j));
				if(tempFactors[i+j] == null) 
					tempFactors[i+j] = temp;
				else
					tempFactors[i+j] = tempFactors[i+j].add(temp);
				
			}
		}
		
		return new ComplexPolynomial(tempFactors);
	}
	

	/**
	 * Function that derives the given polynomial one degree 	
	 * @return a new polynomial that is the first derivative of the current polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] tempFactors = new Complex[order()];
		
		for(int i = 0 ; i < tempFactors.length ; i++) {
			double real = (factors.get(i+1).getReal()) * (i+1);
			double im = (factors.get(i+1).getImaginary()) * (i+1);
			tempFactors[i] = new Complex(real,im);
		}
		
		return new ComplexPolynomial(tempFactors);
	}
	
	/**
	 * Function that returns the value of the polynomial at a specified z
	 * @param z the specified z of which we need the value of the polynomial
	 * @return the value of the polynomial at z
	 */
	public Complex apply(Complex z) {
		Complex temp = factors.get(0);
		for(int i = 1 ; i < factors.size(); i++) {
			temp = temp.add(this.factors.get(i).multiply(z.power(i))); 
		}
		return temp;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = factors.size()-1 ; i >= 0 ; i--) {
			sb.append("( " + factors.get(i) + " )*z^" + i + "+");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
