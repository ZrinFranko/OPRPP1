package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Code showing the implementation of teh LocalizationProvider class that extends the AbstractLocalizationProvider
 * @author zrin
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

	private String currentLanguage;
	private static LocalizationProvider instance = new LocalizationProvider();
	private ResourceBundle bundle;
	
	/**
	 * Constructor of the class
	 */
	private LocalizationProvider() {
		super();
		currentLanguage = "eng";
		Locale local = Locale.forLanguageTag(currentLanguage);
		bundle = ResourceBundle.getBundle("hr/fer/oprpp1/hw08/jnotepadpp/local/prijevodi", local);
	}
	
	/**
	 * Function that returns the current instance of the language provider
	 * @return the instance of the provider
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	/**
	 * Function that sets a language the provider is working with
	 * @param language the language that needs to be set as active
	 */
	public void setLanguage(String language) {
		currentLanguage = language;
		bundle = ResourceBundle.getBundle("hr/fer/oprpp1/hw08/jnotepadpp/local/prijevodi", Locale.forLanguageTag(currentLanguage));
		this.notifyListeners();
	}
	
	@Override
	public String getString(String text) {
		return LocalizationProvider.getInstance().bundle.getString(text);
	}

	@Override
	public String getCurrentLanguage() {
		return currentLanguage;
	}

}
