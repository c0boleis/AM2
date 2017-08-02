package ihm.swing.table.transaction_details;

import javax.swing.JTable;

import ihm.swing.table.account.AccountTableSwing;
import model.Transaction;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.2
 *
 */
public class TransactionDetailsTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 781611138171983764L;
	
	private Transaction transaction;
	
	private TransactionDetailsTableModel transactionDetailsTableModel;
	
	public TransactionDetailsTable(Transaction transaction) {
		this.transaction = transaction;
		this.addMouseListener(new JTableDetailsButtonMouseListener(this));
		refresh();
	}

	/**
	 * @return the transactionDetailsTableModel
	 */
	public TransactionDetailsTableModel getTransactionDetailsTableModel() {
		if(transactionDetailsTableModel == null) {
			transactionDetailsTableModel = new TransactionDetailsTableModel(this.transaction);
		}
		return transactionDetailsTableModel;
	}
	
	public void refresh() {
		transactionDetailsTableModel = null;
		this.setModel(getTransactionDetailsTableModel());
		this.getColumnModel().getColumn(0).setCellEditor(AccountTableSwing.getCellCotegoryEditor());
		this.getColumnModel().getColumn(5).setCellRenderer(new ButtonRemoveAddDetails());
		
		this.getColumnModel().getColumn(0).setMinWidth(40);
		this.getColumnModel().getColumn(1).setMinWidth(40);
		this.getColumnModel().getColumn(2).setMinWidth(40);
		this.getColumnModel().getColumn(3).setMinWidth(40);
		
		repaint();
	}
	
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
		refresh();
	}

}
