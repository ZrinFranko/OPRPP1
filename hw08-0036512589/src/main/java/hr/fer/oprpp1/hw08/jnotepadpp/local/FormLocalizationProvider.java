package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
/**
 * Code showing the implementation of the FormLocalizationProvider
 * @author zrin
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{

	/**
	 * Constructor of the class
	 * @param provider the language provider used
	 * @param frame the app frame on which the provider is active
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				FormLocalizationProvider.this.connect();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				FormLocalizationProvider.this.disconnect();
			}
			
		});
	}

}
