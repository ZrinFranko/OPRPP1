package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * Code showing the implementation of the JNotepad app
 * @author zrin
 *
 */
public class JNotepadPP extends JFrame{
	
	private static final long serialVersionUID = -3738166483318524943L;
	private FormLocalizationProvider formProvider;
	
	/**
	 * Constructor for the JNotepad app 
	 */
	public JNotepadPP() {
		super();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		formProvider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		setTitle("JNotepad++");
		setLocation(100,100);
		setSize(1200,800);
		initGUI();
	}
	/**
	 * Initialization of GUI for the app
	 */
	private void initGUI() {
		Container cp  = getContentPane();
		cp.setLayout(new BorderLayout());
		MultipleDocumentModel model = new DefaultMultipleDocumentModel();
		NotepadStatusBar statusBar = new NotepadStatusBar(model,formProvider);
		NotepadMenus menus = new NotepadMenus(this,formProvider,model);
		this.setJMenuBar(menus);
		cp.add(model.getVisualComponent(),BorderLayout.CENTER);
		cp.add(statusBar.getVisualComponent(),BorderLayout.PAGE_END);
		model.createNewDocument();
	}
	
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);				
			}	
		});
	}

}
