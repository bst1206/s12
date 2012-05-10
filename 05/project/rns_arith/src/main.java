import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import com.stackbaek.rnsarith.model.Model;
import components.InputPanel;
import components.LogPanel;
import components.OutputPanel;


public class main extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
		try{
			SwingUtilities.invokeAndWait(new Runnable() {
				
				public void run() {
					setSize(800, 600);
					_createGUI();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void _createGUI() {
		InputPanel inputPanel = new InputPanel();
		LogPanel logPanel = new LogPanel();
		OutputPanel outputPanel = new OutputPanel();
		
		Model model = Model.getInstance();
		model.addLogEventListener(logPanel);
		model.addRNSArithEvnetListenerList(outputPanel);
		
		setLayout(null);
		
		inputPanel.setBounds(0, 0, 400, 300);
		logPanel.setBounds(400, 50, 350, 500);
		outputPanel.setBounds(0, 300, 400, 200);
		
		add(inputPanel);
		add(logPanel);
		add(outputPanel);
	}
}
