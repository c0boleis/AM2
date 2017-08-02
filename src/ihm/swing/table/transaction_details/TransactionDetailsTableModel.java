package ihm.swing.table.transaction_details;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controller.User;
import ihm.swing.Window;
import ihm.swing.table.account.AccountTableSwing;
import model.AmountException;
import model.Quantity;
import model.Transaction;
import model.TransactionDetail;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.2
 *
 */
public class TransactionDetailsTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1336069244492049269L;

	private static final String[] columnNames = {"Category","Comment","Amount","Quantity","Unit","Action"};

	private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {String.class, String.class,Double.class,Double.class,String.class,JButton.class};

	private Transaction transaction;

	private EmptyTransactionDetail emptyTransactionDetail;
	
	public TransactionDetailsTableModel(Transaction transaction) {
		this.transaction = transaction;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		if(transaction==null) {
			return 0;
		}
		return transaction.getTransactionDetails().length +1;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(transaction==null) {
			return null;
		}
		TransactionDetail transactionDetail = null;
		if(rowIndex==0) {
			transactionDetail = getEmptyTransactionDetail();
		}else {
			transactionDetail = transaction.getTransactionDetails()[rowIndex-1];
		}
		if(transactionDetail == null) {
			return null;
		}
		Quantity quantity = transactionDetail.getQuantity();
		
		switch (columnIndex) {
		case 0:
			return transactionDetail.getCategory();
		case 1:
			return transactionDetail.getComment();
		case 2:
			return transactionDetail.getAmount();
		case 3:
			if(quantity!=null) {
				return quantity.getQuantity();
			}
			return null;
		case 4:
			if(quantity!=null) {
				return quantity.getUnit();
			}
			return null;
		case 5:
			String buttonName = "Remove";
			if(rowIndex==0) {
				buttonName = "Add";
			}
			final JButton button = new JButton(buttonName);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(rowIndex==0) {
						try {
							transaction.addTransactionDetail(getEmptyTransactionDetail().getNewTransactionDetail());
						} catch (AmountException e) {
							JOptionPane.showMessageDialog(Window.get(),
									e.getMessage(), "ERROR", 
									JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					}else {
						transaction.removeTransactionDetail(transaction.getTransactionDetails()[rowIndex-1]);
					}
				}
			});
			return button;
		default:
			break;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	/**
	 * @return the emptyTransactionDetail
	 */
	public EmptyTransactionDetail getEmptyTransactionDetail() {
		if(emptyTransactionDetail == null) {
			try {
				emptyTransactionDetail = new EmptyTransactionDetail();
			} catch (AmountException e) {
				e.printStackTrace();
			}
		}
		return emptyTransactionDetail;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return true;
		case 1:
			return true;
		case 2:
			return true;
		case 3:
			return true;
		case 4:
			return true;
		case 5:
			return false;
		default:
			break;
		}
		return super.isCellEditable(rowIndex, columnIndex);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object obj, int rowIndex, int columnIndex) {
		TransactionDetail transactionDetail = null;
		if(rowIndex==0) {
			transactionDetail = getEmptyTransactionDetail();
		}else {
			transactionDetail = this.transaction.getTransactionDetails()[rowIndex-1];
		}
		switch (columnIndex) {
		case 0:
			String text = (String)obj;
			if(text.equals(AccountTableSwing.NEW_CATEGORY_BOX)){
				String cat = JOptionPane.showInputDialog(Window.get(), 
						"Enter the new category:",
						"Add category", JOptionPane.QUESTION_MESSAGE);
				if(cat!=null) {
					cat = cat.trim();
					if(cat.length()>0) {
						transactionDetail.setCategory(cat);
						User.get().addCategory(cat);
					}
				}
			}
			else {
				transactionDetail.setCategory((String) obj);
			}
			break;
		case 1:
			transactionDetail.setComment((String)obj);
			break;
		case 2:
			try {
				transactionDetail.setAmount((double) obj);
			}catch (AmountException e) {
				JOptionPane.showMessageDialog(Window.get(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case 3:
			Quantity quantity = transactionDetail.getQuantity();
			quantity.setQuantity((double)obj);
			break;
		case 4:
			quantity = transactionDetail.getQuantity();
			quantity.setUnit((String) obj);
			break;
		default:
			break;
		}
		return;
	}


}
