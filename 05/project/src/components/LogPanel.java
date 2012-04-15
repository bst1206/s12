package components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.stackbaek.rnsarith.event.LogEvent;
import com.stackbaek.rnsarith.eventListener.LogEventListener;

public class LogPanel extends JPanel implements ActionListener,LogEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5298200218458419744L;
	
	public static final String FLUSH_LOG = "logPanelFlush";

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "on")
		{
			logMsgArea.setVisible(true);
		}
		else if(e.getActionCommand() == "off")
		{
			logMsgArea.setVisible(false);
		}
	}
	
	// ------- Children -------
	JTextArea logMsgArea;
	JScrollPane logPane; 
	JRadioButton onButton;
	JRadioButton offButton;
	ButtonGroup txtSwitch;
	
	public LogPanel()
	{
		_init();
	}
	
	// ------- private functions -------
	private void _createChildren()
	{
		logMsgArea = new JTextArea();
		logMsgArea.setEditable(false);
		
		logPane = new JScrollPane(logMsgArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		onButton = new JRadioButton("on");
		onButton.setActionCommand("on");
		onButton.setSelected(true);
		onButton.addActionListener(this);
		
		offButton = new JRadioButton("off");
		offButton.setActionCommand("off");
		offButton.addActionListener(this);
		
		txtSwitch = new ButtonGroup();
		txtSwitch.add(onButton);
		txtSwitch.add(offButton);
	}
	
	private void _init()
	{
		_createChildren();
		setLayout(null);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), new TitledBorder("Log Messages")));
		
		onButton.setBounds(10, 25, 40, 20);
		offButton.setBounds(50, 25, 50, 20);
		logPane.setBounds(10, 50, 330, 440);
		
		
		add(onButton);
		add(offButton);
		add(logPane);
	}
	
	// ------- event handlers -------
	public void handleLogEvent(LogEvent e)
	{
		if(e.getLogMessage() == FLUSH_LOG)
		{
			logMsgArea.setText("");
		}
		else
		{
			if(logMsgArea.getText().equals(""))
			{
				logMsgArea.setText(e.getLogMessage());
			}
			else
			{
				logMsgArea.setText(logMsgArea.getText()+"\n"+e.getLogMessage());
			}
		}
	}

}
