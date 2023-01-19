package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
/**
 * Code showing the implementation of the LocalizableAction class that extends the AbstractAction class
 * @author zrin
 *
 */
public class LocalizableAction extends AbstractAction{

	private static final long serialVersionUID = -3080640892057198939L;
	private String key;
	private ILocalizationProvider provider;
	/**
	 * Contructor of the class
	 * @param key the name of the action
	 * @param provider the language provider of the action
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		if(key == null || provider == null) throw new NullPointerException("All arguments must be given!");
		this.key = key;
		putValue(Action.NAME, provider.getString(key));
		this.provider = provider;
		this.provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(Action.NAME,LocalizableAction.this.provider.getString(key));
			}
		});
		
	}
	/**
	 * Function that returns the current value of the key
	 * @return the value of the key ran through the language provider
	 */
	public String getKey() {
		return this.provider.getString(this.key);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
