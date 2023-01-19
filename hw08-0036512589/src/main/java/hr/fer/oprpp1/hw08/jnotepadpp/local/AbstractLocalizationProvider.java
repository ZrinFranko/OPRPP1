package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.*;
/**
 * Code showing the implementation of the AbstractLocalizationProvider that implements ILocalizationProvider interface
 * @author zrin
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	private List<ILocalizationListener> listeners;

	/**
	 * Constructor of the class
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Auxiliary function that notifies the listeners that a localization change took place
	 */
	public void notifyListeners() {
		for (var l : listeners) {
			l.localizationChanged();
		}
	}
}
