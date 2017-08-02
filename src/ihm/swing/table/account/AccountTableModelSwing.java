package ihm.swing.table.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import controller.User;
import ihm.swing.Window;
import model.Account;
import model.AmountException;
import model.Transaction;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.0
 *
 */
public class AccountTableModelSwing extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8377135616334724508L;

	private static final String[] columnNames = {"Date","Category","Comment","Amount","valide","Action"};
	
	private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {String.class, String.class, String.class,Double.class,Boolean.class,  JButton.class};

	private EmptyTransaction emptyTransaction;
	
	private AccountTableSwing accountTableSwing;
	
	private Account account;

	public AccountTableModelSwing(Account account,AccountTableSwing accountTableSwing) {
		super();
		this.account = account;
		this.accountTableSwing = accountTableSwing;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}


	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return this.account.getNumberOfTransaction()+1;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Transaction transaction = null;
		if(rowIndex==0) {
			transaction = getEmptyTransaction();
		}else {
			transaction = this.account.geTransactionAt(rowIndex-1);
		}
		switch (columnIndex) {
		case 0:
			return Transaction.dateFormat.format(transaction.getDate());
		case 1:
			return transaction.getCategory();
		case 2:
			return transaction.getComment();
		case 3:
			return transaction.getAmount();
		case 4:
			return transaction.isDone();
		case 5:
			String buttonName = "Remove";
			if(rowIndex==0) {
				buttonName = "Add";
			}
			final JButton button = new JButton(buttonName);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if(rowIndex==0) {
                    	/*
                    	 * when we add or remove a transaction we disable
                    	 * the selection listener with seting addTransactionProcess
                    	 * at true
                    	 */
                    	try {
                    		accountTableSwing.setAddTransactionProcess(true);
							account.addTransaction(getEmptyTransaction().getNewTransaction());
							accountTableSwing.setAddTransactionProcess(false);
                    	} catch (AmountException e) {
							JOptionPane.showMessageDialog(Window.get(),
									e.getMessage(), "ERROR", 
									JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
                    }else {
                    	accountTableSwing.setAddTransactionProcess(true);
                    	account.removeTransaction(account.geTransactionAt(rowIndex-1));
                    	accountTableSwing.setAddTransactionProcess(false);
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
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return false;
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
		Transaction transaction = null;
		if(rowIndex==0) {
			transaction = getEmptyTransaction();
		}else {
			transaction = this.account.geTransactionAt(rowIndex-1);
		}
		switch (columnIndex) {
		case 0:
			break;
		case 1:
			String text = (String)obj;
			if(text.equals(AccountTableSwing.NEW_CATEGORY_BOX)){
				String cat = JOptionPane.showInputDialog(Window.get(), 
						"Enter the new category:",
						"Add category", JOptionPane.QUESTION_MESSAGE);
				if(cat!=null) {
					cat = cat.trim();
					if(cat.length()>0) {
						transaction.setCategory(cat);
						User.get().addCategory(cat);
					}
				}
			}
			else {
				transaction.setCategory((String) obj);
			}
			break;
		case 2:
			transaction.setComment((String)obj);
			break;
		case 3:
			try {
				transaction.setAmount((double) obj);
			}catch (AmountException e) {
				JOptionPane.showMessageDialog(Window.get(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case 4:
			transaction.setDone((Boolean) obj);
			break;
		case 5:
			break;
		default:
			break;
		}
		super.setValueAt(obj, rowIndex, columnIndex);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	/**
	 * @return the emptyTransaction
	 */
	public EmptyTransaction getEmptyTransaction() {
		if(emptyTransaction == null) {
			try {
				emptyTransaction = new EmptyTransaction();
			} catch (AmountException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return emptyTransaction;
	}

}
