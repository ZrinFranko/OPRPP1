package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Dimension;

@FunctionalInterface
public interface ComponentSize {
	
	public Dimension getSize(Component c);

}
