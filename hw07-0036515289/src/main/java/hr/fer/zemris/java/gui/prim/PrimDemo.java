package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class PrimDemo extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public PrimDemo() {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Prim number lists");
		initGUI();
		pack();
	}
	
	private void initGUI() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		PrimListModel demoModel = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(demoModel);
		JList<Integer> list2 = new JList<>(demoModel);
		
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.add(list1);
		panel.add(list2);
		JScrollPane s=new JScrollPane(panel);
		cp.add(s,BorderLayout.CENTER);
		JButton nextButton = new JButton("sljedeci");
		nextButton.addActionListener(a ->{
			demoModel.next();
		});
		cp.add(nextButton,BorderLayout.PAGE_END);
		
		
	}

	public static void main(String[] args) {
		PrimDemo demo = new PrimDemo();
		
		SwingUtilities.invokeLater(()->{
			demo.setVisible(true);
		});
	}

}
