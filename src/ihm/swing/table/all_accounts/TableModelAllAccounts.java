package ihm.swing.table.all_accounts;

import javax.swing.table.DefaultTableModel;

import controller.User;
import model.Account;

/**
 * 
 * @author C.B.
 * @version 1.1
 * @since 1.1
 *
 */
public class TableModelAllAccounts extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6661039766996575150L;

	private static final String[] HEADERS = {"Account Number","Amount done","Amount not done"};

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 0) {
			return String.class;	
		}
		return Double.class;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return HEADERS.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		return HEADERS[columnIndex];
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return User.get().getAccounts().length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Account account = User.get().getAccounts()[rowIndex];
		switch (columnIndex) {
		case 0:
			return account.getAccountNumber();
		case 1:
			return account.getAmountDone();
		case 2:
			return account.getAmount();
		default:
			break;
		}
		return null;
		
	}

}
