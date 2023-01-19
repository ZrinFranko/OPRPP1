package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;

public class BarChartComponent extends JComponent{

	private static final long serialVersionUID = 1L;
	private BarChart chart;
	private static final int SPACING = 10;

	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d  = (Graphics2D)g;
		//Dobivanje rotiranog teksta za opis y
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		
		//stavljanje opisa
		g2d.setTransform(at);
		g2d.drawString(chart.getyOpis(), -this.getHeight()/2-g2d.getFontMetrics().stringWidth(chart.getyOpis())/2, g2d.getFontMetrics().getHeight());
		at.rotate(Math.PI/2);
		g2d.setTransform(at);
		g2d.drawString(chart.getxOpis(), this.getWidth()/2-g2d.getFontMetrics().stringWidth(chart.getxOpis())/2, this.getHeight()-SPACING);
		
		//Dodavanje brojeva i okvir
		int horizontalOffset = g2d.getFontMetrics().getHeight()+g2d.getFontMetrics().stringWidth("" + chart.getMaxY())+2*SPACING;
		int verticalOffset = g2d.getFontMetrics().getHeight()*2 + SPACING;
		
		//Strelice
		g2d.setColor(Color.BLACK);
		g2d.drawLine(horizontalOffset+SPACING, this.getInsets().top, horizontalOffset+SPACING-3, this.getInsets().top+SPACING);
		g2d.drawLine(horizontalOffset+SPACING, this.getInsets().top, horizontalOffset+SPACING+3, this.getInsets().top+SPACING);
		g2d.drawLine(horizontalOffset+SPACING-3, this.getInsets().top+SPACING, horizontalOffset+SPACING+3, this.getInsets().top+SPACING);
		
		g2d.drawLine(this.getWidth(), this.getHeight()-verticalOffset, this.getWidth()-SPACING, this.getHeight()-verticalOffset-3);
		g2d.drawLine(this.getWidth(), this.getHeight()-verticalOffset, this.getWidth()-SPACING, this.getHeight()-verticalOffset+3);
		g2d.drawLine(this.getWidth()-SPACING, this.getHeight()-verticalOffset-3, this.getWidth()-SPACING, this.getHeight()-verticalOffset+3);
		
		//crtanje linija
		int numberOfVerticalLines = getMaxXValue();
		g2d.drawLine(horizontalOffset+SPACING,this.getHeight()-verticalOffset ,horizontalOffset+SPACING , this.getInsets().bottom+SPACING);
		g2d.drawString("1", horizontalOffset+SPACING + (this.getWidth()/(numberOfVerticalLines*2)), this.getHeight()-g2d.getFontMetrics().getHeight()-SPACING);
		for(int i = 1; i < numberOfVerticalLines ; i++) {
			g2d.setColor(Color.BLACK);
			int startX =horizontalOffset+i*((this.getWidth()-horizontalOffset)/numberOfVerticalLines)+SPACING;
			int startY = this.getHeight();
			g2d.drawString(""+(i+1), startX + (this.getWidth()/(numberOfVerticalLines*2)), startY-g2d.getFontMetrics().getHeight()-SPACING);
			g2d.setColor(Color.ORANGE);
			g2d.drawLine(startX, startY-verticalOffset, startX, this.getInsets().bottom+SPACING);
		}
		int numberOfHorizontalLines = chart.getMaxY()/chart.getDiffY();
		g2d.setColor(Color.BLACK);
		g2d.drawLine(horizontalOffset+SPACING, this.getHeight()-verticalOffset, this.getWidth(), this.getHeight()-verticalOffset);
		int startFirstX = g2d.getFontMetrics().getHeight()+g2d.getFontMetrics().stringWidth("" + chart.getMaxY());
		int startFirstY = this.getInsets().top+SPACING + (this.getHeight()-horizontalOffset)+ (g2d.getFontMetrics().getHeight()/2);
		g2d.drawString(""+chart.getMinY(), startFirstX, startFirstY);
		for(int i = numberOfHorizontalLines-1 ; i >=0 ; i--) {
			g2d.setColor(Color.BLACK);
			int startX = horizontalOffset+SPACING;
			int textX = g2d.getFontMetrics().getHeight()+g2d.getFontMetrics().stringWidth("" + chart.getMaxY());
			int startY = this.getInsets().top+SPACING + i*((this.getHeight()-horizontalOffset)/numberOfHorizontalLines);
			int textY = startY + (g2d.getFontMetrics().getHeight()/2);
			g2d.drawString(""+(chart.getDiffY()*(numberOfHorizontalLines-i)), textX, textY);
			g2d.setColor(Color.ORANGE);
			g2d.drawLine(startX, startY, this.getWidth()-2*SPACING, startY);
		}	
		
		for(XYValue value : chart.getValues()) {
			int recWidth = (this.getWidth()-horizontalOffset)/numberOfVerticalLines;
			int recHeight = (this.getHeight()-horizontalOffset)/chart.getMaxY();
			int startX = horizontalOffset+((value.getX()-1)*((this.getWidth()-horizontalOffset)/numberOfVerticalLines))+SPACING;
			int takingSpace = (this.getHeight()-horizontalOffset)/chart.getMaxY();
			int startY = this.getHeight()-verticalOffset-takingSpace*(value.getY());
			g2d.fillRect(startX, startY, recWidth, recHeight*value.getY());
		}
	}

	private int getMaxXValue() {
		int max = 0;
		for(XYValue value : chart.getValues()) {
			if(max < value.getX())
				max = value.getX();
		}
		return max;
	}

}


