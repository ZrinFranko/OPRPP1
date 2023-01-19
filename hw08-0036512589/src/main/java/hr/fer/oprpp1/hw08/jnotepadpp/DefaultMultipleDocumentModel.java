package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * Code showing the implementation of the MultipleDocumentModel interface
 * @author zrin
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	private static final long serialVersionUID = -1905720030701383300L;
	private List<SingleDocumentModel> opens;
	private SingleDocumentModel currentOpen;
	private List<MultipleDocumentListener> listeners;

	/**
	 * Constructor of the class
	 */
	public DefaultMultipleDocumentModel() {
		super();
		opens = new ArrayList<>();
		currentOpen = null;
		listeners = new ArrayList<>();

		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel previous = getCurrentDocument();
				if(getSelectedIndex() != 1)
					currentOpen = getDocument(getSelectedIndex());
				else
					currentOpen = null;
				notifyCurrentChanged(previous,currentOpen);
			}

		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return opens.iterator();
	}

	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDoc =  new DefaultSingleDocumentModel(null, "");
		newDoc.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				ImageIcon icon = null;
				if(model.isModified())
					icon = getIcon("diskRed.png");
				else
					icon = getIcon("diskGreen.png");
				setIconAt(opens.indexOf(model),icon);
				
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				String title = null;
				if(model.getFilePath() == null)
					title = "unnamed";
				else
					model.getFilePath().getFileName().toString();
				setTitleAt(opens.indexOf(model),title);
				setToolTipTextAt(opens.indexOf(model), model.getFilePath().toString());
				notifyCurrentChanged(model,model);
				
			}
		});
		opens.add(newDoc);
		JScrollPane scrollPane = new JScrollPane(newDoc.getTextComponent());
		this.addTab("unnamed", getIcon("diskRed.png"),scrollPane,"");
		notifyAdded(newDoc);
		return newDoc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.currentOpen;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		StringBuilder sb = new StringBuilder();
		if(checkPath(path)) {
			try {
				List<String> lines = Files.readAllLines(path);
				lines.forEach(line -> sb.append(line + "\n"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			SingleDocumentModel loadedDoc = new DefaultSingleDocumentModel(path, sb.toString());
			opens.add(loadedDoc);
			JScrollPane scrollPane = new JScrollPane(loadedDoc.getTextComponent());
			this.addTab(path.getFileName().toString(), getIcon("diskGreen.png"), scrollPane,path.toString());
			this.setSelectedIndex(opens.indexOf(loadedDoc));
			currentOpen = loadedDoc;
			currentOpen.addSingleDocumentListener(new SingleDocumentListener() {

				@Override
				public void documentModifyStatusUpdated(SingleDocumentModel model) {
					ImageIcon icon = null;
					if(model.isModified())
						icon = getIcon("diskRed.png");
					else
						icon = getIcon("diskGreen.png");
					setIconAt(opens.indexOf(model),icon);
				}

				@Override
				public void documentFilePathUpdated(SingleDocumentModel model) {
					setToolTipTextAt(opens.indexOf(model), model.getFilePath().toString());
					setTitleAt(opens.indexOf(model), model.getFilePath().getFileName().toString());

				}
			});
			currentOpen.setModified(false);
			notifyAdded(loadedDoc);
			return loadedDoc;
		}else {
			throw new IllegalArgumentException("Path to file is not valid!");
		}

	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(newPath == null) {
			newPath = model.getFilePath();
		}

		if(!model.getFilePath().equals(newPath)) {
			model.setFilePath(newPath);
			notifyCurrentChanged(model,model);
		}

		byte[] textToSave = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);		
		try(OutputStream os = new BufferedOutputStream(Files.newOutputStream(model.getFilePath()))){
			os.write(textToSave);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {		
		int index = opens.indexOf(model);
		opens.remove(index);
		remove(index);
		setSelectedIndex(0);
		notifyRemoved(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
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

	@Override
	public int getNumberOfDocuments() {
		return opens.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return opens.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		if(path == null) 
			throw new NullPointerException("Path of file cannot be null!");
		for(SingleDocumentModel model : opens) {
			if(model.getFilePath().equals(path))
				return model;
		}
		return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		return opens.indexOf(doc);
	}

	/**
	 * Auxiliary class used to notify listeners that a document has been removed
	 * @param model the document that was removed
	 */
	private void notifyRemoved(SingleDocumentModel model) {
		listeners.forEach(l -> l.documentRemoved(model));
	}
	
	/**
	 * Auxiliary function that notifies listeners that a new document has been added
	 * @param model the document added
	 */
	private void notifyAdded(SingleDocumentModel model) {
		listeners.forEach(l -> l.documentAdded(model));
	}

	/**
	 * Auxiliary function that notifies listeners that a currently opened document has been changed
	 * @param previous the previous version of the document
	 * @param current the current version of the document
	 */
	private void notifyCurrentChanged(SingleDocumentModel previous,SingleDocumentModel current) {
		listeners.forEach(l -> l.currentDocumentChanged(previous,current));
	}

	/**
	 * Auxiliary function used to check the path to the document
	 * @param path the path that needs to be checked
	 * @return true if the path leads to a readable file, false otherwise
	 */
	private boolean checkPath(Path path) {
		if(path == null)
			return false;
		if(Files.exists(path)) {
			if(Files.isDirectory(path))
				return false;
			if(Files.isReadable(path))
				return true;
		}
		return false;
	}

	/**
	 * Auxiliary function used to get a specified image from the resource folder
	 * @param name the name of the image that contains the icon
	 * @return a new ImageIcon representing the image given in the name parameter
	 */
	private ImageIcon getIcon(String name) {
		byte[] iconBytes = null;
		try(InputStream is = this.getClass().getResourceAsStream("icons/" + name);){
			if(is == null)
				throw new NullPointerException("Icon file is not there");
			iconBytes = is.readAllBytes();

		} catch (IOException e) {
			System.out.println("Icon file not found!");
		}
		return new ImageIcon(iconBytes);
	}

}
