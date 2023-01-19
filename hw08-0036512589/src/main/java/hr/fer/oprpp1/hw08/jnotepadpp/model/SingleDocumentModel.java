package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;

import javax.swing.JTextArea;

public interface SingleDocumentModel {
	/**
	 * Function that returns the visual component of the document representing it's body
	 * @return the text area containing the text written in the document
	 */
	JTextArea getTextComponent();
	/**
	 * Function that returns the path to the document
	 * @return the path to the document
	 */
	Path getFilePath();
	/**
	 * Function that sets a new path to the document
	 * @param path the new path of the document
	 */
	void setFilePath(Path path);
	/**
	 * Function that returns the value of the modify status of the document
	 * @return true if the document has been modified, false otherwise
	 */
	boolean isModified();
	/**
	 * Function that sets the current modify status of the document
	 * @param modified the new status of the document
	 */
	void setModified(boolean modified);
	/**
	 * Function that add a new listener to the array of listeners of the class
	 * @param l listeners that is being added
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	/**
	 * Function that removes a currently existing listener from the array of listeners of the class
	 * @param l listener that needs to be removed
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
