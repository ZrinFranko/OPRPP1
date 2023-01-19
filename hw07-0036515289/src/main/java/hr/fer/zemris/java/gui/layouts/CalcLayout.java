package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.*;

public class CalcLayout implements LayoutManager2{

	private static final int MAX_ROWS = 5;
	private static final int MAX_COLUMNS = 7;

	private int offset;
	private Map<RCPosition,Component> components;

	public CalcLayout() {
		offset = 0;
		components = new HashMap<>();
	}
	public CalcLayout(int offset) {
		this.offset = offset;
		components = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void removeLayoutComponent(Component comp) {

		RCPosition removingValue = null;

		for(RCPosition rcp : components.keySet()) {
			if(components.get(rcp).equals(comp)) { 
				removingValue = rcp;
				break;
			}
		}

		components.remove(removingValue);	

	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(s -> s.getPreferredSize(),parent);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(s -> s.getMinimumSize(),parent);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets(); //borderi

		//visina i sirina prozora sa kojim mogu radit
		int windowsWidth = parent.getWidth() - insets.left - insets.right;
		int windowsHeight = parent.getHeight() - insets.top - insets.bottom;

		//visina i sirina pojedinacnih komponenti
		int singleComponentWidth = (windowsWidth - (MAX_COLUMNS-1) * offset) / MAX_COLUMNS;
		int singleComponentHeight = (windowsHeight - (MAX_ROWS-1) * offset)/ MAX_ROWS;

		for(RCPosition rcp : components.keySet()) {
			int x;
			int y;		

			if(rcp.equals(new RCPosition(1, 1))) {
				x = insets.top+offset;
				y = insets.left;
				int width = singleComponentWidth*5 + offset*4;
				components.get(rcp).setBounds(x, y, width, singleComponentHeight);
			}
			else {
				x = offset + insets.top + ((rcp.getColumn()-1)*singleComponentWidth) + (offset*(rcp.getColumn()-1));
				y = insets.left + ((rcp.getRow()-1)*singleComponentHeight) + (offset*(rcp.getRow()-1));
				components.get(rcp).setBounds(x, y, singleComponentWidth, singleComponentHeight);
			}
		}

	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition position = null;

		if(constraints instanceof String) 
			position = RCPosition.parse((String)constraints);
		else if(constraints instanceof RCPosition)
			position = (RCPosition) constraints;
		else if(constraints == null)
			throw new NullPointerException("Constraints cannot be null!");
		else
			throw new IllegalArgumentException("Constraint can only be a string representation of an RCPosition or an RCPosition!");

		if(comp == null)
			throw new NullPointerException("Component must not be null!");

		if(components.containsKey(position))
			throw new CalcLayoutException("There already exists a component on the specified position!");

		components.put(position, comp);

	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize(s -> s.getMaximumSize(),target);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
	
	private Dimension getLayoutSize(ComponentSize parentDimensions,Container parent) {
		int maximumWidth = 0;
		int maximumHeight = 0;
		
		Insets insets = parent.getInsets();
		
		for(RCPosition rcp : components.keySet()) {
			if(rcp.equals(new RCPosition(1, 1))) {
				if(maximumWidth < (components.get(rcp).getWidth()/5-4*offset))
					maximumWidth = components.get(rcp).getWidth();
				if(maximumHeight < components.get(rcp).getHeight())
					maximumHeight = components.get(rcp).getHeight();
			}
			if(maximumWidth < parentDimensions.getSize(components.get(rcp)).width)
				maximumWidth = parentDimensions.getSize(components.get(rcp)).width;
			if(maximumHeight < parentDimensions.getSize(components.get(rcp)).height)
				maximumHeight = parentDimensions.getSize(components.get(rcp)).height;
		}
		int windowWidth = maximumWidth*MAX_COLUMNS + insets.left + insets.right + (MAX_COLUMNS-1)*offset;
		int windowHeight = maximumHeight*MAX_ROWS + insets.top + insets.bottom + (MAX_ROWS-1)*offset;
		return new Dimension(windowWidth,windowHeight);
	}

}
