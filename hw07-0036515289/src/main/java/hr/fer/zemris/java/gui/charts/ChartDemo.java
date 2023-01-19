package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class ChartDemo extends JFrame{

	
	private static final long serialVersionUID = 1L;

	public ChartDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChartDemo");
		setSize(700, 550);
		setLocation(10,10);
		initGUI();
		//pack();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setBackground(Color.WHITE);
		
		
	}

	public static void main(String[] args) {
		BarChart bc = new BarChart(
				Arrays.asList(
						new XYValue(1,8), new XYValue(2,20), new XYValue(3,22),
						new XYValue(4,10), new XYValue(5,4)
						),
				"Number of people in the car",
				"Frequency",
				0, // y-os kreće od 0
				22, // y-os ide do 22
				2
				);
		BarChartComponent bcc = new BarChartComponent(bc);
		bcc.setPreferredSize(new Dimension(500,500));
		ChartDemo bcd = new ChartDemo();
		bcd.getContentPane().add(bcc);
		
		SwingUtilities.invokeLater(()->{
			bcd.setVisible(true);
		});
	}
}
