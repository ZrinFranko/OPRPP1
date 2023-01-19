package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.*;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * Code showing the implementation of the SingleDocumentModel interface
 * @author zrin
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel{

	private Path file;
	private JTextArea textArea;
	private boolean isModified;
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Constructor of the class 
	 * @param file the path to a new file
	 * @param text the text contained in the file
	 */
	public DefaultSingleDocumentModel(Path file,String text){
		this.file = file;
		textArea = new JTextArea(text);
		isModified = false;
		listeners = new ArrayList<>();
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}

	
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return this.file;
	}

	@Override
	public void setFilePath(Path path) {
		this.file = path;
		notifyListenersPath();
	}

	@Override
	public boolean isModified() {
		return this.isModified;
	}

	@Override
	public void setModified(boolean modified) {
		this.isModified = modified;
		notifyListenersStatus();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		int index = -1;
		for(int i = 0; i < listeners.size() ; i++) {
			if(listeners.get(i).equals(l)) {
				index = i;
				break;
			}
		}
		if(index == -1)
			throw new NoSuchElementException("Specified listener is not in the registered listener array!");
		listeners.remove(index);
	}
	
	/**
	 * Auxiliary function that notifies listeners that the status of the document has been changed
	 */
	private void notifyListenersStatus() {
		listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}
	
	/**
	 * Auxiliary function that notifies listeners that the path to the file has ben changed
	 */
	private void notifyListenersPath() {
		listeners.forEach(l -> l.documentFilePathUpdated(this));
	}

}
