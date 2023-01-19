package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;

import javax.swing.JComponent;

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Function that returns the visual component of the class
	 * @return the class itself, since it extends a visual component
	 */
	JComponent getVisualComponent();
	/**
	 * Function that is used to create a new document and append a new tab to the window
	 * @return a newly formed blank document
	 */
	SingleDocumentModel createNewDocument();
	/**
	 * Function that is used to return the document in a currently active tab
	 * @return the document in the active tab
	 */
	SingleDocumentModel getCurrentDocument();
	/**
	 * Function that is used to load an existing document into the app
	 * @param path the path to the document
	 * @return a new tab representing the newly opened document
	 */
	SingleDocumentModel loadDocument(Path path);
	/**
	 * Function that is used to save a document in the currently active tab
	 * @param model the document that will be saved
	 * @param newPath the path of where the document will be saved
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	/**
	 * Function that is used to close the currently active tab 
	 * @param model the document in an opened tab that needs to be closed
	 */
	void closeDocument(SingleDocumentModel model);
	/**
	 * Function that adds a multiple document listener to the array of listeners
	 * @param l the listener that will be appended to the array of listeners of the class
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	/**
	 * Function that removes a listener from the array of listeners of the class
	 * @param l listener that needs to be removed
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	/**
	 * Returns the number of currently opened docuemnts of the app
	 * @return the number of opened documents
	 */
	int getNumberOfDocuments();
	/**
	 * Function that returns a document in a currently active tab
	 * @param index the index of the tab and document that needs to be returned
	 * @return the document in the currently selected tab
	 */
	SingleDocumentModel getDocument(int index);
	/**
	 * Function that returns the docuemtn from the array of documents of the app found by its path
	 * @param path the path of the searched document
	 * @return the document found in the array or null if no such model exists
	 */
	SingleDocumentModel findForPath(Path path); //null, if no such model exists
	/**
	 * Function that returns the index of the document in the array 
	 * @param doc The searched document
	 * @return the index of the document in the array or -1 if the document is not currently opened
	 */
	int getIndexOfDocument(SingleDocumentModel doc); //-1 if not present
}
