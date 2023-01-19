package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
/**
 * Code showing the implementation of the actions in the app
 * @author zrin
 *
 */
public class NotepadActions {
	
	private String textToUse;
	private JNotepadPP appFrame;
	private ILocalizationProvider provider;
	private MultipleDocumentModel model;
	
	public Action newA;
	public Action open;
	public Action save;
	public Action saveAs;
	public Action close;
	public Action cut;
	public Action copy;
	public Action paste;
	public Action stats;
	public Action upper;
	public Action lower;
	public Action invert;
	public Action asc;
	public Action desc;
	public Action de;
	public Action it;
	public Action en;
	public Action hr;
	public Action unique;
	public Action exit;	
	
	/**
	 * Constructor for the NotepadActions class
	 * @param frame the frame on which these actions will be active
	 * @param provider the language provider for the app
	 * @param model tabs shown in the app 
	 */
	public NotepadActions(JNotepadPP frame,ILocalizationProvider provider,MultipleDocumentModel model) {
		if(frame == null) throw new NullPointerException("Frame cannot be null");
		this.appFrame = frame;
		this.provider = provider;
		this.model = model;
		initializeActions();
		initializeShortcuts();
	}
	/**
	 * An auxiliary function that initializes all of the actions to a certain value
	 */
	private void initializeActions() {
		newA = new LocalizableAction("new",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
			}
		};
		open = new LocalizableAction("open",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Open file");
				if (chooser.showOpenDialog(appFrame) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				Path selectedFile = chooser.getSelectedFile().toPath();
				if(Files.isReadable(selectedFile)) {
					model.loadDocument(selectedFile);
				}else {
					JOptionPane.showMessageDialog(appFrame, "File " + selectedFile.getFileName().toString() + " doesn't exist!","Error!",JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		save = new LocalizableAction("save",provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Path path = model.getCurrentDocument().getFilePath();

				if(path == null) {
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("Save document");
					if(chooser.showOpenDialog(appFrame) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(appFrame,"Saving was unsuccesful",
								"Notice", JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					path = chooser.getSelectedFile().toPath();
					String name = chooser.getSelectedFile().getName();
					model.getCurrentDocument().setFilePath(path);
					if(Files.exists(path)) {
						int response = JOptionPane.showConfirmDialog(appFrame, "File with the name " + path.getFileName().toString() + 
								" \nDo you wish to overwrite it?","Notice",JOptionPane.YES_NO_OPTION);
						if(response != JOptionPane.YES_OPTION)
							return;
					}
				}
				model.saveDocument(model.getCurrentDocument(), path);
			}
		};
		saveAs = new LocalizableAction("saveAs",provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Path path = null;
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Save document as: ");
				if(chooser.showOpenDialog(appFrame) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(appFrame,"Saving was unsuccesful",
							"Notice", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				path = chooser.getSelectedFile().toPath();
				if(Files.exists(path)) {
					int response = JOptionPane.showConfirmDialog(appFrame, "File with the name " + path.getFileName().toString() + 
							" \nDo you wish to overwrite it?","Notice",JOptionPane.YES_NO_OPTION);
					if(response != JOptionPane.YES_OPTION)
						return;
				}
				model.getCurrentDocument().setFilePath(path);
				model.saveDocument(model.getCurrentDocument(), path);
			}
		};
		close = new LocalizableAction("close",provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel currentModel = model.getCurrentDocument();
				if(currentModel.isModified()) {
					int response = JOptionPane.showConfirmDialog(appFrame,"File has unsaved changes.\n Do you wish to continue?","Notice",JOptionPane.YES_NO_OPTION);
					if(response == JOptionPane.NO_OPTION)
						return;
				}
				model.closeDocument(currentModel);
			}
		};
		cut = new LocalizableAction("cut",provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int lengthOfCut = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				int whereToStartCutting = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				try {
					textToUse = textArea.getDocument().getText(whereToStartCutting, lengthOfCut);
					textArea.getDocument().remove(whereToStartCutting, lengthOfCut);	
				} catch (BadLocationException e1) {
					System.out.println("Unable to cut at this position " + whereToStartCutting + " for length " + lengthOfCut);
				}

			}
		};
		copy = new LocalizableAction("copy",provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int lengthOfCopy = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				int whereToStartCopying = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				try {
					textToUse = textArea.getDocument().getText(whereToStartCopying, lengthOfCopy).toString();
				} catch (BadLocationException e1) {
					System.out.println("Unable to copy at this position " + whereToStartCopying + " for length " + lengthOfCopy);
				}

			}
		};
		paste = new LocalizableAction("paste",provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				try {
					if(textToUse == null) {
						JOptionPane.showMessageDialog(appFrame, "Unable to paste when no text was previously selected!","Notice",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					textArea.getDocument().insertString(textArea.getCaret().getDot(),textToUse,null);
				} catch (BadLocationException e1) {
					System.out.println("Unable to paste because the position is invalid!");
				}

			}
		};
		stats = new LocalizableAction("stats",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int length = textArea.getText().length();
				int chars = textArea.getText().replaceAll("\\s", "").length();
				long lines = textArea.getText().lines().count();
				JOptionPane.showMessageDialog(appFrame, "Current document is " + length + " long, has "
						+ chars + " characters, and " + lines + " lines.","Satatistics of document",JOptionPane.INFORMATION_MESSAGE);
			}
		};
		upper = new LocalizableAction("upper",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int lengthOfCaseChange = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				int whereToStartChanging = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				try {
					String changedText = textArea.getDocument().getText(whereToStartChanging, lengthOfCaseChange).toUpperCase();
					textArea.getDocument().remove(whereToStartChanging, lengthOfCaseChange);
					textArea.getDocument().insertString(whereToStartChanging, changedText, null);
				}catch(BadLocationException e1) {

				}

			}
		};
		lower = new LocalizableAction("lower",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int lengthOfCaseChange = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				int whereToStartChanging = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				try {
					String changedText = textArea.getDocument().getText(whereToStartChanging, lengthOfCaseChange).toLowerCase();
					textArea.getDocument().remove(whereToStartChanging, lengthOfCaseChange);
					textArea.getDocument().insertString(whereToStartChanging, changedText, null);
				}catch(BadLocationException e1) {

				}

			}
		};
		invert = new LocalizableAction("invert",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int lengthOfCaseChange = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				int whereToStartChanging = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				try {
					char[] changingText = textArea.getDocument().getText(whereToStartChanging, lengthOfCaseChange).toCharArray();
					StringBuilder sb = new StringBuilder();
					for(int i = 0; i < changingText.length ; i++) {
						if(Character.isLetter(changingText[i])) {
							if(Character.isUpperCase(changingText[i]))
								sb.append(Character.toLowerCase(changingText[i]));
							if(Character.isLowerCase(changingText[i]))
								sb.append(Character.toUpperCase(changingText[i]));
						}
						else {
							sb.append(changingText[i]);
						}
					}
					textArea.getDocument().remove(whereToStartChanging, lengthOfCaseChange);
					textArea.getDocument().insertString(whereToStartChanging, sb.toString(), null);
				}catch(BadLocationException e1) {

				}

			}
		};
		asc = new LocalizableAction("asc",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int firstEl;
				int lastEl;
				if(textArea.getCaret().getDot() != textArea.getCaret().getMark()) {
					firstEl = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
					lastEl = Math.max(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				}else {
					return;
				}
				try {
					int firstLine = textArea.getLineOfOffset(firstEl);
					int lastLine = textArea.getLineOfOffset(lastEl);
					int length = Math.abs(lastLine-firstLine);
					List<String> textToSort = new ArrayList<String>(List.of(textArea.getDocument().getText(firstLine, length).split("\n")));
					textToSort.sort(Collator.getInstance(new Locale(provider.getCurrentLanguage())));
					StringBuilder sb = new StringBuilder();
					for(String line : textToSort)
						sb.append(line + "\n");
					
					textArea.getDocument().remove(firstEl, length);
					textArea.getDocument().insertString(firstEl, sb.toString(), null);
				} catch (BadLocationException e1) {
					JOptionPane.showMessageDialog(appFrame, "Something went wrong!","Notice",JOptionPane.INFORMATION_MESSAGE);
				}

			}
		};
		desc = new LocalizableAction("desc",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int firstEl;
				int lastEl;
				if(textArea.getCaret().getDot() != textArea.getCaret().getMark()) {
					firstEl = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
					lastEl = Math.max(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				}else {
					return;
				}
				try {
					int firstLine = textArea.getLineOfOffset(firstEl);
					int lastLine = textArea.getLineOfOffset(lastEl);
					int length = Math.abs(lastLine-firstLine);
					List<String> textToSort = new ArrayList<String>(List.of(textArea.getDocument().getText(firstLine, length).split("\n")));
					textToSort.sort(Collator.getInstance(new Locale(provider.getCurrentLanguage())).reversed());
					
					StringBuilder sb = new StringBuilder();
					for(String line : textToSort)
						sb.append(line + "\n");
					
					textArea.getDocument().remove(firstEl, length);
					textArea.getDocument().insertString(firstEl, sb.toString(), null);
				} catch (BadLocationException e1) {
					JOptionPane.showMessageDialog(appFrame, "Something went wrong!","Notice",JOptionPane.INFORMATION_MESSAGE);
				}

			}
		};
		hr = new LocalizableAction("hr",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};
		en = new LocalizableAction("en",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};
		it = new LocalizableAction("it",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("it");
			}
		};
		de = new LocalizableAction("de",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		};
		unique = new LocalizableAction("unique",provider) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int firstEl;
				int lastEl;
				if(textArea.getCaret().getDot() != textArea.getCaret().getMark()) {
					firstEl = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
					lastEl = Math.max(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				}else {
					return;
				}
				try {
					int firstLine = textArea.getLineOfOffset(firstEl);
					int lastLine = textArea.getLineOfOffset(lastEl);
					int length = Math.abs(lastLine-firstLine);
					List<String> removingDuplicates = new ArrayList<String>(List.of(textArea.getDocument().getText(firstLine, length).split("\n")));
					List<String> noDuplicates = new ArrayList<>();
					
					for(String line : removingDuplicates) {
						if(!noDuplicates.contains(line))
							noDuplicates.add(line);
					}
					
					StringBuilder sb = new StringBuilder();
					for(String line : noDuplicates)
						sb.append(line + "\n");
					
					textArea.getDocument().remove(firstEl, length);
					textArea.getDocument().insertString(firstEl, sb.toString(), null);
				}catch(BadLocationException e1) {
					JOptionPane.showMessageDialog(appFrame, "Something went wrong!","Notice",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		exit = new LocalizableAction("exit",provider) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				Iterator<SingleDocumentModel> iterator = model.iterator();	
				while(iterator.hasNext()) {
					SingleDocumentModel m = iterator.next();
					if(m.isModified()) {
						String nameOfFile = "";
						if(m.getFilePath() == null)
							nameOfFile = "unnamed";
						else
							nameOfFile = m.getFilePath().getFileName().toString();
						int response = JOptionPane.showConfirmDialog(appFrame, "File " + nameOfFile + " has unsaved changes\n"
								+"Do you wish to save changes before exiting application?","Warning",JOptionPane.YES_NO_CANCEL_OPTION);
						if(response == JOptionPane.YES_OPTION) {
							save.actionPerformed(e);
						}
					}
				}
				NotepadActions.this.appFrame.dispose();
				
			}
		};
	}
	
	/**
	 * An auxiliary function that gives the actions shortcuts 
	 */
	private void initializeShortcuts() {

		newA.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newA.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newA.putValue(Action.SHORT_DESCRIPTION, "Used to create new file.");
		
		open.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		open.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		open.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk.");

		save.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		save.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		save.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk.");
		
		saveAs.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveAs.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAs.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk as a user specified format.");
		
		close.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		close.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		close.putValue(Action.SHORT_DESCRIPTION, "Used to close the current tab.");
		
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cut.putValue(Action.SHORT_DESCRIPTION, "Used to cut the selected text.");
		
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copy.putValue(Action.SHORT_DESCRIPTION, "Used to copy the selected text.");
		
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		paste.putValue(Action.SHORT_DESCRIPTION, "Used to paste the previously selected text.");
		
		stats.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		stats.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		stats.putValue(Action.SHORT_DESCRIPTION, "Used to show the statistics of the text.");
		
		upper.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		upper.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		upper.putValue(Action.SHORT_DESCRIPTION, "Used to change the case to Uppercase of the selected text.");
		
		lower.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		lower.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		lower.putValue(Action.SHORT_DESCRIPTION, "Used to change the case to Lowercase of the selected text.");
		
		invert.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift I"));
		invert.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		invert.putValue(Action.SHORT_DESCRIPTION, "Used to invert the case of the selected text.");
		
		asc.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));
		asc.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		asc.putValue(Action.SHORT_DESCRIPTION, "Used to sort the lines in an ascending order.");
		
		desc.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift D"));
		desc.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		desc.putValue(Action.SHORT_DESCRIPTION, "Used to sort the lines in a descending order.");
		
		unique.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		unique.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		unique.putValue(Action.SHORT_DESCRIPTION, "Used to remove duplicate lines from the document.");
		
		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exit.putValue(Action.SHORT_DESCRIPTION, "Used to exit the application.");
	}
	
}
