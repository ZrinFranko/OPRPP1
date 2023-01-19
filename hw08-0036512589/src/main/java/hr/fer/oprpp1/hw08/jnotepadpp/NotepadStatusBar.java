package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * Code showing the implementation of the status bar of the app
 * @author zrin
 *
 */
public class NotepadStatusBar extends JPanel{

	private static final long serialVersionUID = 1726684018130849442L;
	private MultipleDocumentModel model;
	private FormLocalizationProvider provider;
	private JLabel lengthInfo;
	private JPanel editorInfo;
	private JLabel timeInfo;
	private boolean editSelected;
	/**
	 * Constructor of the status bar 
	 * @param model Tabs on the app that are being listened to
	 * @param provider the language provider of the app
	 */
	public NotepadStatusBar(MultipleDocumentModel model,FormLocalizationProvider provider) {
		if(model == null || provider == null) throw new NullPointerException("Both arguments must be given!");
		this.model = model;
		this.provider = provider;
		this.lengthInfo = new JLabel(provider.getString("length") + ": 0");
		this.editorInfo = new JPanel();
		this.timeInfo = new JLabel();
		this.editSelected = false;
		initialize();
	}
	/**
	 * Function that returns the visual component of the class
	 * @return the class itself because it is extending the JPanel class
	 */
	public JComponent getVisualComponent() {
		return this;
	}
	/**
	 * Auxiliary function that initializes the status bar and it's listeners
	 */
	private void initialize() {
		this.setLayout(new GridLayout(1,3));
		System.out.println("Current provider is: " + provider.getCurrentLanguage());
		this.add(lengthInfo);
		editorInfo.add(new JLabel(provider.getString("ln") + ": 1"));
		editorInfo.add(new JLabel(provider.getString("col") + ": 1"));
		editorInfo.add(new JLabel(provider.getString("sel") + ": 0"));
		Timer timer = new Timer(1000, l -> {
			this.timeInfo.setText(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
		});
		timer.start();
		this.add(editorInfo);
		this.add(timeInfo);
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				if(model == null)
					return;
				model.getTextComponent().getCaret().addChangeListener(l -> {
					updateInfo(model);
				});
				
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (currentModel != null) {
					currentModel.getTextComponent().getCaret().addChangeListener(l -> {
						updateInfo(currentModel);
					});
				}
				updateInfo(currentModel);
			}
		});
		
		
	}
	/**
	 * Auxiliary function that updates the status bar depending on the caret position of the dot and mark
	 * @param model
	 */
	private void updateInfo(SingleDocumentModel model) {
		int ln = 1;
		int col = 1;
		int sel = 0;
		int length = 0;
		JLabel lineInfo = (JLabel)editorInfo.getComponent(0);
		JLabel columnInfo = (JLabel)editorInfo.getComponent(1);
		JLabel selectInfo = (JLabel)editorInfo.getComponent(2);
		if(model == null) {
			lengthInfo.setText(provider.getString("length") + ": " + length);
			lineInfo.setText(provider.getString("ln") + ": 1");
			columnInfo.setText(provider.getString("col") + ": 1");
			selectInfo.setText(provider.getString("sel") + ": 0");
			return;
		}
		JTextArea textArea = model.getTextComponent();
		try {
			ln += textArea.getLineOfOffset(textArea.getCaretPosition());
			col += textArea.getCaretPosition();
			sel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			if(sel > 0)
				editSelected = true;
		} catch (BadLocationException e) {
			System.out.println("Something went wrong!");
		}
		
		lengthInfo.setText(provider.getString("length") + ": " + textArea.getText().length());
		lineInfo.setText(provider.getString("ln") + ": " + ln);
		columnInfo.setText(provider.getString("col") + ": " + col);
		selectInfo.setText(provider.getString("sel") + ": " + sel);
	
	}
	
	/**
	 * Function that shows if a group of characters has been selected by the user
	 * @return true if the selection is made, false otherwise
	 */
	public boolean isEditSelected() {
		return this.editSelected;
	}

}
