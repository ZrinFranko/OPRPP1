package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Code showing the implementation of the LocalizationProviderBridge that is extending the AbstractLocalizationProvider class
 * @author zrin
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{

	private ILocalizationProvider provider;
	private boolean connected;
	private ILocalizationListener listener;
	
	/**
	 * Constructor of the class
	 * @param provider the provider that will be bridged
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		if(provider == null) throw new NullPointerException("Provider must not be null!");
		this.provider = provider;
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				LocalizationProviderBridge.this.notifyListeners();
				
			}
		};
	}
	/**
	 * Function that disconnect the class from the app ie. removes a listener from the app
	 */
	public void disconnect() {
		if (!connected) {
			return;
		}
		this.removeLocalizationListener(listener);
		this.connected = false;
	}

	/**
	 * Function that connects the class to the app ie. adds a listener to the app
	 */
	public void connect() {
		if (connected) {
			return;
		}
		this.addLocalizationListener(listener);
		this.connected = true;
	}
	
	@Override
	public String getString(String text) {
		return provider.getString(text);
	}

	@Override
	public String getCurrentLanguage() {
		return provider.getCurrentLanguage();
	}

}
