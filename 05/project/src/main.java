import javax.swing.JApplet;
import javax.swing.SwingUtilities;


public class main extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
		try{
			SwingUtilities.invokeAndWait(new Runnable() {
				
				public void run() {
					_createGUI();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void _createGUI() {
		
	}
}
