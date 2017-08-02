package ihm.swing.table.all_accounts;

import java.util.Date;

import javax.swing.JTable;

import controller.AccountListener;
import controller.TransactionListener;
import controller.User;
import controller.UserListener;
import model.Account;
import model.Transaction;
import model.TransactionDetail;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.1
 *
 */
public class TableAllAccounts extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4842658196011867668L;
	
	private UserListener userListener;
	
	private AccountListener accountListener;
	
	private TableModelAllAccounts tableModelAllAccounts;
	
	private TransactionListener transactionListener;
	
	public TableAllAccounts() {
		this.setModel(getTableModelAllAccounts());
		conectListener();
	}
	
	private void conectListener() {
		Account[] accounts = User.get().getAccounts();
		User.get().addListener(getUserListener());
		for(Account account : accounts) {
			account.addListener(getAccountListener());
			Transaction[] transactions = account.getTransactions();
			for(Transaction transaction : transactions) {
				transaction.addListener(getTransactionListener());
			}
		}
	}
	
	private void refresh() {
		this.tableModelAllAccounts = null;
		this.setModel(getTableModelAllAccounts());
		repaint();
	}
	
	/**
	 * @return the userListener
	 */
	public UserListener getUserListener() {
		if(userListener == null) {
			userListener = new UserListener() {
				
				/*
				 * (non-Javadoc)
				 * @see controller.UserListener#categoryAdd(java.lang.String)
				 */
				@Override
				public void categoryAdd(String category) {}
				
				/*
				 * (non-Javadoc)
				 * @see controller.UserListener#accountRemove(model.Account)
				 */
				@Override
				public void accountRemove(Account account) {
					account.removeListener(getAccountListener());
					Transaction[] transactions = account.getTransactions();
					for(Transaction transaction : transactions) {
						transaction.removeListener(getTransactionListener());
					}
					refresh();
				}
				
				/*
				 * (non-Javadoc)
				 * @see controller.UserListener#accountAdd(model.Account)
				 */
				@Override
				public void accountAdd(Account account) {
					account.addListener(getAccountListener());
					Transaction[] transactions = account.getTransactions();
					for(Transaction transaction : transactions) {
						transaction.addListener(getTransactionListener());
					}
					refresh();
				}

				/*
				 * (non-Javadoc)
				 * @see controller.UserListener#needSaveChange()
				 */
				@Override
				public void needSaveChange() {}
			};
		}
		return userListener;
	}

	/**
	 * @return the accountListener
	 */
	public AccountListener getAccountListener() {
		if(accountListener == null) {
			accountListener= new AccountListener() {
				
				/*
				 * (non-Javadoc)
				 * @see controller.AccountListener#transactionRemove(model.Transaction)
				 */
				@Override
				public void transactionRemove(Transaction transaction) {
					transaction.removeListener(getTransactionListener());
					repaint();
				}
				
				/*
				 * (non-Javadoc)
				 * @see controller.AccountListener#transactionAdd(model.Transaction)
				 */
				@Override
				public void transactionAdd(Transaction transaction) {
					transaction.addListener(getTransactionListener());
					repaint();
				}
			};
		}
		return accountListener;
	}

	/**
	 * @return the tableModelAllAccounts
	 */
	public TableModelAllAccounts getTableModelAllAccounts() {
		if(tableModelAllAccounts == null) {
			tableModelAllAccounts = new TableModelAllAccounts();
		}
		return tableModelAllAccounts;
	}

	/**
	 * @return the transactionListener
	 */
	public TransactionListener getTransactionListener() {
		if(transactionListener == null) {
			transactionListener = new TransactionListener() {
				
				@Override
				public void transactionDetailRemove(TransactionDetail transactionDetail) {	}
				
				@Override
				public void transactionDetailAdd(TransactionDetail transactionDetail) {}
				
				@Override
				public void doneChange(boolean oldDone, boolean newDone) {
					repaint();
				}
				
				@Override
				public void dateChange(Date oldDate, Date newDate) {}
				
				@Override
				public void commentChange(String oldComment, String newComment) {}
				
				@Override
				public void categoryChange(String oldCategory, String newCategory) {}
				
				@Override
				public void amountChange(double oldAmount, double newAmount) {
					repaint();
				}
			};
		}
		return transactionListener;
	}

}
