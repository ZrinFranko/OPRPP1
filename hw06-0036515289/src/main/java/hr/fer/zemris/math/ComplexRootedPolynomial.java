package hr.fer.zemris.math;

import java.util.*;

public class ComplexRootedPolynomial {
	
	private Complex constant;
	private List<Complex> roots;
	// ...
	// constructor
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = new ArrayList<>();
		for(Complex root : roots) {
			this.roots.add(root);
		}
	}
	
	/**
	 * Function that computes the polynomial value at given point z
	 * @param z the given point
	 * @return the polynomial value of z
	 */
	public Complex apply(Complex z) {
		Complex temp = getConstant();
		for(Complex c : getRoots()) {
			temp = temp.multiply(z.sub(c));
		}
		return temp;
	}
	// converts this representation to ComplexPolynomial type
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial temp = new ComplexPolynomial(this.constant);
		
		for(Complex root : roots) 
			temp = temp.multiply(new ComplexPolynomial(Complex.ONE,root.negate()));
		
		return temp;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("f(z)= ").append(constant);
		for(Complex c : roots) {
			sb.append(" * (z - (" + c.toString() + "))");
		}
		return sb.toString();
	}
	// finds index of closest root for given complex number z that is within
	// treshold; if there is no such root, returns -1
	// first root has index 0, second index 1, etc
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int currentClosest = -1;
		double currentSmallestDifference = treshold;
		
		for(int i = 0 ; i < roots.size()-1; i++) {
			if(z.sub(roots.get(i)).module() < currentSmallestDifference) {
				currentSmallestDifference = z.sub(roots.get(i)).module();
				currentClosest = i;
			}
		}
		
		return currentClosest;
	}
	
	public Complex getConstant() {
		return constant;
	}
	public List<Complex> getRoots() {
		return roots;
	}
}
