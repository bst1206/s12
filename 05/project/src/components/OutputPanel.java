package components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.stackbaek.rnsarith.domain.MRSValue;
import com.stackbaek.rnsarith.domain.RNSValue;
import com.stackbaek.rnsarith.event.RNSArithEvent;
import com.stackbaek.rnsarith.eventListener.RNSArithEventListener;
import com.stackbaek.rnsarith.util.RNSUtil;

public class OutputPanel extends JPanel implements ActionListener,RNSArithEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6946002636237879629L;

	public void actionPerformed(ActionEvent e) {
		
	}

	public void handleRNSARithEvent(RNSArithEvent rae) {
		String operationString = "X ";
		if(rae.getOperation().equals(RNSUtil.RNS_OPERATION_ADD))
		{
			operationString += "+ ";
		}
		else if(rae.getOperation().equals(RNSUtil.RNS_OPERATION_MULTIPLY))
		{
			operationString += "* ";
		}
		else if(rae.getOperation().equals(RNSUtil.RNS_OPERATION_SUBTRACT))
		{
			operationString += "- ";
		}
		else 
		{
			operationString = "no Operation Selected";
		}
		operationString += "Y = Z";
		outputValuePanel.setBorder(new TitledBorder(operationString));
		
		RNSValue z = RNSUtil.RNS_operation(rae.getX(), rae.getY(), rae.getMods(), rae.getOperation());
		outputRNSTxtFld.setText(" " + Arrays.toString(z.getResidues()));
		
		MRSValue MRS_z = RNSUtil.RNStoMRS(z);
		outputValueTxtFld.setText(""+MRS_z.getDecValue());
	
		
	}
	
	
	// ------- Public Children -------
	JPanel outputValuePanel;
	JLabel outputValueMsg;
	JTextField outputValueTxtFld;
	JTextField outputRNSTxtFld;
	
	public OutputPanel()
	{
		_init();
	}
	
	// ------- private functions -------
	private void _init()
	{
		_createChildren();
		
		setLayout(null);
		outputValueMsg.setBounds(15, 20, 30, 20);
		outputValueTxtFld.setBounds(50, 20, 100, 20);
		outputRNSTxtFld.setBounds(160, 20, 100, 20);
		
		outputValuePanel.add(outputValueMsg);
		outputValuePanel.add(outputValueTxtFld);
		outputValuePanel.add(outputRNSTxtFld);
		
		outputValuePanel.setBounds(50, 50, 300, 50);
		
		add(outputValuePanel);
	}
	
	private void _createChildren()
	{
		outputValuePanel = new JPanel();
		outputValuePanel.setLayout(null);
		outputValuePanel.setBorder(new TitledBorder("Arithmetic Result"));
		
		outputValueMsg = new JLabel("Z = ");
		outputValueTxtFld = new JTextField();
		outputValueTxtFld.setEditable(false);
		outputRNSTxtFld = new JTextField();
		outputRNSTxtFld.setEditable(false);
	}
}
