package ihm.swing.table.transaction_details;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.2
 *
 */
public class ButtonRemoveAddDetails extends JButton implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1811345590896499063L;
	
	public ButtonRemoveAddDetails() {
		super("Remove");
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4,
			int arg5) {
		if(arg1 instanceof JButton) {
			this.setText(((JButton) arg1).getText());
		}
		return this;
	}

}
