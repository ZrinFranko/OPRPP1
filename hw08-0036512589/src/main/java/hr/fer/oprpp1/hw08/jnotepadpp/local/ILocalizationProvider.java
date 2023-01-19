package hr.fer.oprpp1.hw08.jnotepadpp.local;
/**
 * Code showing the functions of the interface
 * @author zrin
 *
 */
public interface ILocalizationProvider {

	/**
	 * Function that adds a new localization listener to the array of listeners of the class
	 * @param listener listener that needs to be added
	 */
	public void addLocalizationListener(ILocalizationListener listener);
	/**
	 * Function that removes a specified  localization listener from the array of listeners of the class
	 * @param listener the listener that needs to be removed
	 */
	public void removeLocalizationListener(ILocalizationListener listener);
	/**
	 * Function that return the value of the text in a given language
	 * @param text the value of the text that needs to be translated
	 * @return the translated version of the text param
	 */
	public String getString(String text);
	/**
	 * Function that returns the current language of the provider
	 * @return the current active languge
	 */
	public String getCurrentLanguage();
}
