import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import components.InputPanel;


public class main extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
		try{
			SwingUtilities.invokeAndWait(new Runnable() {
				
				public void run() {
					setSize(600, 800);
					_createGUI();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void _createGUI() {
		InputPanel inputPanel = new InputPanel();
		add(inputPanel);
	}
}
