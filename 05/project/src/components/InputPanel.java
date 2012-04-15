package components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.stackbaek.rnsarith.domain.RNSValue;
import com.stackbaek.rnsarith.event.LogEvent;
import com.stackbaek.rnsarith.event.RNSArithEvent;
import com.stackbaek.rnsarith.eventListener.LogEventListener;
import com.stackbaek.rnsarith.eventListener.RNSArithEventListener;
import com.stackbaek.rnsarith.model.Model;
import com.stackbaek.rnsarith.util.RNSUtil;

public class InputPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5492065052494858779L;

	public void actionPerformed(ActionEvent e) {
		
		int[] moduli;
		int x;
		int y;
		RNSValue RNS_x = new RNSValue();
		RNSValue RNS_y = new RNSValue();
		try {
			moduli = RNSUtil.parseModuliString(inputModuliTxtFld.getText());
			x = Integer.parseInt(inputXTxtFld.getText());
			y = Integer.parseInt(inputYTxtFld.getText());
			fireLogEvent(LogPanel.FLUSH_LOG);
			if(RNSUtil.isValidModuli(moduli, true))
			{
				RNS_x.init(x, moduli, true);
				RNS_y.init(y, moduli, true);
				//go on to calculation
				RNSXTxtArea.setText(" " + Arrays.toString(RNS_x.getResidues()));
				RNSYTxtArea.setText(" " + Arrays.toString(RNS_y.getResidues()));
				fireRNSArithEvent(RNS_x, RNS_y, (String) selectOperationCmbx.getSelectedItem(), moduli);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "incorrect moduli detected, please fix it");
			}
		} 
		catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "incorrect value " + nfe.getMessage());
		}	
		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			e1.printStackTrace();
		}
	}

	// ------ Public Children -------
	JPanel inputModuliPanel;
	JTextField inputModuliTxtFld;
	
	JPanel inputValuesPanel;
	JLabel inputXMsg;
	JTextField inputXTxtFld;
	JTextField RNSXTxtArea;
	JLabel inputYMsg;
	JTextField inputYTxtFld;
	JTextField RNSYTxtArea;
	
	JPanel selectOpertaionPanel;
	JComboBox selectOperationCmbx;
	
	JButton startOperationBtn;
	
	public InputPanel()
	{
		_init();
	}
	// ------- private functions -------
	private void _createChildren()
	{
		//for moduli
		inputModuliTxtFld = new JTextField();
		
		inputModuliPanel = new JPanel();
		inputModuliPanel.setLayout(new BorderLayout());
		inputModuliPanel.setBorder(new TitledBorder("Input up to six(6) comma-separated moduli"));
		
		
		//for each number
		inputXMsg = new JLabel("X = ");
		inputXTxtFld = new JTextField();	
		RNSXTxtArea = new JTextField();
		RNSXTxtArea.setEditable(false);
		inputYMsg = new JLabel("Y = ");
		inputYTxtFld = new JTextField();
		RNSYTxtArea = new JTextField();
		RNSYTxtArea.setEditable(false);
		
		inputValuesPanel = new JPanel();
		inputValuesPanel.setLayout(null);
		inputValuesPanel.setBorder(new TitledBorder("Input decimal values"));
		
		
		//combobox for operation selection
		String[] operationList = new String[3];
		operationList[0] = RNSUtil.RNS_OPERATION_ADD;
		operationList[1] = RNSUtil.RNS_OPERATION_MULTIPLY;
		operationList[2] = RNSUtil.RNS_OPERATION_SUBTRACT;
		selectOperationCmbx = new JComboBox(operationList);
		
		selectOpertaionPanel = new JPanel();
		selectOpertaionPanel.setLayout(null);
		selectOpertaionPanel.setBorder(new TitledBorder("Select the operation:"));
		
		//start button
		startOperationBtn = new JButton("Start!");
		startOperationBtn.addActionListener(this);
	}
	
	private void _init()
	{
		_createChildren();
		
		setLayout(null);
		inputXMsg.setBounds(15, 20, 30, 20);
		inputXTxtFld.setBounds(50, 20, 100, 20);
		RNSXTxtArea.setBounds(160, 20, 100, 20);
		inputYMsg.setBounds(15, 50, 30, 20);
		inputYTxtFld.setBounds(50, 50, 100, 20);
		RNSYTxtArea.setBounds(160, 50, 100, 20);
		selectOperationCmbx.setBounds(30, 20, 80, 30);
		startOperationBtn.setBounds(180, 22, 70, 25);
		
		inputModuliPanel.add(inputModuliTxtFld);
		inputValuesPanel.add(inputXMsg);
		inputValuesPanel.add(inputXTxtFld);
		inputValuesPanel.add(RNSXTxtArea);
		inputValuesPanel.add(inputYMsg);
		inputValuesPanel.add(inputYTxtFld);
		inputValuesPanel.add(RNSYTxtArea);
		selectOpertaionPanel.add(selectOperationCmbx);
		selectOpertaionPanel.add(startOperationBtn);
		
		inputModuliPanel.setBounds(50, 50, 300, 50);
		inputValuesPanel.setBounds(50, 120, 300, 80);
		selectOpertaionPanel.setBounds(50, 220, 300, 60);
		

		add(inputModuliPanel);
		add(inputValuesPanel);
		add(selectOpertaionPanel);

		
//		add(inputModuliMsg);
//		add(inputModuliTxtFld);
//		add(inputValuesMsg);
//		add(inputXMsg);
//		add(inputXTxtFld);
//		add(inputYMsg);
//		add(inputYTxtFld);
//		add(selectOperationMsg);
//		add(selectOperationCmbx);
		
	}
	
	// ------- fire events -------
	private void fireRNSArithEvent(RNSValue x, RNSValue y, String operation, int[] mods)
	{
		Iterator<RNSArithEventListener> iterator = Model.getInstance().getRNSArithEvnetListenerList().iterator();
		while(iterator.hasNext())
		{
			iterator.next().handleRNSARithEvent(new RNSArithEvent(this, x, y, operation, mods));
		}
	}
	
	private void fireLogEvent(String msg)
	{
		Iterator<LogEventListener> listeners = Model.getInstance().getLogEventListenerList().iterator();
		while(listeners.hasNext())
		{
				listeners.next().handleLogEvent(new LogEvent(new Object(), msg));
		}
	}
	
}
