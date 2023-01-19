package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class BarChartDemo extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public BarChartDemo(BarChartComponent chartComp) {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("BarChartDemo");
		initGUI(chartComp);
	}
	
	private void initGUI(BarChartComponent chartComp) {
		Container cp = this.getContentPane();
		cp.setBackground(Color.WHITE);
		cp.add(chartComp);
	}
	

	private static List<XYValue> generateValues(String line) {
		String[] elems = line.split(" ");
		List<XYValue> temp = new ArrayList<>();
		for(String el : elems) {
			try {
				temp.add(new XYValue(Integer.parseInt(el.split(",")[0]), Integer.parseInt(el.split(",")[1])));
			}catch(NumberFormatException e) {
				System.out.println("Value of x and y may only be a number!");
				System.exit(0);
			}
		}
		return temp;
	}
	
	public static void main(String[] args) {
		
		if(args.length != 1) throw new IllegalArgumentException("Path to the file must be specified and only the path to the file!");
		BarChart chart = null;
		try {
			List<String> lines = Files.readAllLines(Path.of(args[0]),StandardCharsets.UTF_8);
			if(lines.size() != 6) throw new IllegalArgumentException();
			String xOpis = lines.get(0);
			String yOpis = lines.get(1);
			List<XYValue> values = generateValues(lines.get(2));
			int minY = Integer.parseInt(lines.get(3));
			int maxY = Integer.parseInt(lines.get(3));
			int diffY = Integer.parseInt(lines.get(3));
			chart = new BarChart(values,xOpis,yOpis,minY,maxY,diffY);
		}catch(IOException e) {
			System.out.println("Unable to read file!");
			return;
		}catch(IllegalArgumentException e) {
			System.out.println("File must only contains lines that give information about the chart");
			return;
		}
		BarChartComponent chartComp = new BarChartComponent(chart);
		BarChartDemo demo = new BarChartDemo(chartComp);
		SwingUtilities.invokeLater(()->{
			demo.setVisible(true);
		});
	}

}
