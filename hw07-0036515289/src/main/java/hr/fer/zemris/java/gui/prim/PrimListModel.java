package hr.fer.zemris.java.gui.prim;

import java.util.*;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer>{
	
	private List<Integer> prims;
	private List<ListDataListener> listeners;
	
	public PrimListModel() {
		prims = new ArrayList<>();
		listeners = new ArrayList<>();
		prims.add(1);
	}
	
	public void next() {
		int current = prims.get(prims.size()-1)+1;
		boolean foundPrim=true;
		while(true) {
			foundPrim = true;
			for(int i = 2 ; i <= (current/2) ; i++) {
				if(current%i == 0) {
					current++;
					foundPrim = false;
					break;
				}
			}
			if(foundPrim) {
				prims.add(current);
				ListDataEvent newEvent = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, current, current);
				listeners.forEach(l -> l.intervalAdded(newEvent));
				return;
			}
			
		}
	}
	
	public List<Integer> getPrims(){
		return prims;
	}

	@Override
	public int getSize() {
		return prims.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return prims.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		int index = -1;
		for(int i = 0; i < listeners.size() ; i++) {
			if(listeners.get(i).equals(l)) {
				index = i;
				break;
			}
		}
		if(index == -1) 
			throw new IllegalArgumentException("Listener does not exist in the list of listeners!");
		listeners.remove(index);
		
	}
	
}
