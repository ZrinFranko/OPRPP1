package hr.fer.oprpp1.hw08.jnotepadpp.model;

public interface MultipleDocumentListener {
	/**
	 * Function that notifies the listener of the changes made to the body of the document
	 * @param previousModel the previous version of the document
	 * @param currentModel the current version of the document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	/**
	 * Function that notifies listeners that a new document has been added to the app
	 * @param model the document added
	 */
	void documentAdded(SingleDocumentModel model);
	/**
	 * Function that notifies listeners that an opened document has been closed
	 * @param model the now closed document
	 */
	void documentRemoved(SingleDocumentModel model);
}
