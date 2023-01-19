package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JLabel;
/**
 * Code showing the implementation of the LocalizationJLabel class that extends the JLabel by giving it a way to work with languages
 * @author zrin
 *
 */
public class LocalizationJLabel extends JLabel {

	private static final long serialVersionUID = 2448752721846249975L;
	private String key;	
	private ILocalizationProvider providerL;
	/**
	 * Constructor of the class
	 * @param key
	 * @param provider
	 */
	public LocalizationJLabel(String key,ILocalizationProvider provider) {
		if(key == null || provider == null) throw new NullPointerException("All arguments must be given!");
		this.key = key;
		setText(provider.getString(key));
		this.providerL = provider;
		providerL.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(getKey());
				
			}
		});
	}
	
	/**
	 * Getter of the key variable of the class
	 * @return the value of the key variable ran through the language provider
	 */
	public String getKey() {
		return this.providerL.getString(this.key);
	} 

}
