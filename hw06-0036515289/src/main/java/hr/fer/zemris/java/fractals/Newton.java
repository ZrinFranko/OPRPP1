package hr.fer.zemris.java.fractals;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.*;
import hr.fer.zemris.math.*;

public class Newton {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);	
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		List<Complex> temp = new ArrayList<>();
		try {
			while(true) {
				String currentLine = "";
				System.out.print("Root" + (temp.size()+1) + "> ");
				currentLine = reader.nextLine();
				if(currentLine.equalsIgnoreCase("done")) {
					if(temp.size() < 2) {
						System.out.println("I need at least two roots to be able to run properly!");
						continue;
					}else
						break;
				}
				Complex temporaryComplex = Complex.generateFromString(currentLine);
				temp.add(temporaryComplex);	
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		reader.close();
		System.out.println(temp);
		System.out.println("Image of fractal will appear shortly. Thank you.");
		Complex[] tempArray = new Complex[temp.size()];
		for(int i = 0; i < temp.size() ; i++) {
			tempArray[i] = temp.get(i);
		}
		FractalViewer.show(new MojProducer(tempArray));

	}
	
	public static class MojProducer implements IFractalProducer {

		private static final int ITERATIONS = 16*16*16;
		private ComplexRootedPolynomial crp;
		
		public MojProducer(Complex... roots) {
			this.crp = new ComplexRootedPolynomial(Complex.ONE_NEG, roots);
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");

			int m = 16*16*16;
			short[] data = new short[width * height];
			int offset = 0;
			ComplexPolynomial cp = crp.toComplexPolynom();
			
			for(int y = 0; y < height; y++) {
				//if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					
					
					Complex zn = new Complex(cre, cim);
					Complex znold;
					int iter = 0;
					do {
						Complex numerator = cp.apply(zn);
						Complex denominator = cp.derive().apply(zn);
						znold = new Complex(zn.getReal(), zn.getImaginary());
						zn = zn.sub(numerator.divide(denominator));
						iter++;
					} while((znold.sub(zn).module()) > 0.001 && iter < m);
					int index = crp.indexOfClosestRootFor(zn, 0.002);
					data[offset++] = (short)(index+1);
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(cp.order()+1), requestNo);
		
		}
		
	}
	
}
