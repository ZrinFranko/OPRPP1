package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationJLabel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

/**
 * Code showing the implementation of the menubar of the app
 * @author zrin
 *
 */
public class NotepadMenus extends JMenuBar{

	private static final long serialVersionUID = 1L;
	private JNotepadPP appFrame;
	private ILocalizationProvider provider;
	private MultipleDocumentModel model;
	
	/**
	 * Constructor of the NotepadMenus app
	 * @param appFrame the frame on which the menus will be active
	 * @param provider the language provider of the app
	 * @param model the tabs shown in the centerpiece of the app
	 */
	public NotepadMenus(JNotepadPP appFrame, ILocalizationProvider provider, MultipleDocumentModel model) {
		super();
		if(appFrame == null) throw new NullPointerException("Given frame cannot be null");
		this.appFrame = appFrame;
		this.provider = provider;
		this.model = model;
		initializeMenus();
	}
	
	/**
	 * Auxiliary function that initializes all of the menus and submenus for the app
	 */
	private void initializeMenus() {
		JNotepadPP frame = this.getFrame();
		NotepadActions actions = new NotepadActions(frame, provider, model);
		JMenu fileMenu = new JMenu((new LocalizationJLabel("file", provider)).getKey());
		fileMenu.add(new JMenuItem(actions.newA));
		fileMenu.add(new JMenuItem(actions.open));
		fileMenu.add(new JMenuItem(actions.save));
		fileMenu.add(new JMenuItem(actions.saveAs));
		fileMenu.add(new JMenuItem(actions.stats));
		fileMenu.add(new JMenuItem(actions.close));
		fileMenu.add(new JMenuItem(actions.exit));
		this.add(fileMenu);
		
		JMenu editMenu = new JMenu((new LocalizationJLabel("edit", provider)).getKey());
		editMenu.add(new JMenuItem(actions.copy));
		editMenu.add(new JMenuItem(actions.cut));
		editMenu.add(new JMenuItem(actions.paste));
		this.add(editMenu);
		
		JMenu toolsMenu = new JMenu(provider.getString("tools"));
		JMenu caseSubmenu = new JMenu(provider.getString("case"));
		JMenu sortSubmenu = new JMenu(provider.getString("sort"));
		caseSubmenu.add(new JMenuItem(actions.upper));
		caseSubmenu.add(new JMenuItem(actions.lower));
		toolsMenu.add(caseSubmenu);
		toolsMenu.add(new JMenuItem(actions.invert));
		sortSubmenu.add(new JMenuItem(actions.asc));
		sortSubmenu.add(new JMenuItem(actions.desc));
		toolsMenu.add(new JMenuItem(actions.unique));
		toolsMenu.add(sortSubmenu);
		this.add(toolsMenu);
		
		JMenu languagesMenu = new JMenu((new LocalizationJLabel("languages", provider)).getKey());
		languagesMenu.add(new JMenuItem(actions.hr));
		languagesMenu.add(new JMenuItem(actions.en));
		languagesMenu.add(new JMenuItem(actions.de));
		languagesMenu.add(new JMenuItem(actions.it));
		this.add(languagesMenu);
		appFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				actions.exit.actionPerformed(null);
			}
		});
	}
	/**
	 * Getter of the appFrame variable
	 * @return
	 */
	public JNotepadPP getFrame() {
		return this.appFrame;
	}
	
	
}
