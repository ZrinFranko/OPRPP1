package hr.fer.oprpp1.hw08.jnotepadpp.model;

public interface SingleDocumentListener {
	/**
	 * Function that notifies the listeners that a document has been updated
	 * @param model the document that has been updated
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	/**
	 * Function that notifies listeners that a path of document has been changed
	 * @param model the document whose path has been updated
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
