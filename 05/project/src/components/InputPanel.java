package components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5492065052494858779L;

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	// ------ Public Children -------
	JLabel inputModuliMsg;
	JTextField inputModuliTxtFld;
	
	JLabel inputValuesMsg;
	JLabel inputXMsg;
	JTextField inputXTxtFld;
	JLabel inputYMsg;
	JTextField inputYTxtFld;
	
	JLabel selectOperationMsg;
	
	public InputPanel()
	{
		_init();
	}
	
	// ------- private functions -------
	private void _createChildren()
	{
		//for moduli
		inputModuliMsg = new JLabel("Input up to six(6) comma-separated moduli");
		inputModuliTxtFld = new JTextField();
		
		//for each number
		inputValuesMsg = new JLabel("Input decimal values");
		inputXMsg = new JLabel("X = ");
		inputXTxtFld = new JTextField();	
		inputYMsg = new JLabel("Y = ");
		inputYTxtFld = new JTextField();	
	}
	
	private void _init()
	{
		_createChildren();
		add(inputModuliMsg);
		inputModuliTxtFld.
		add(inputModuliTxtFld);
	}
}
