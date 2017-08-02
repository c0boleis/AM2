package ihm.swing.table.categories;

import java.util.Date;

import javax.swing.JTable;

import controller.AccountListener;
import controller.User;
import controller.UserListener;
import model.Account;
import model.Transaction;

/**
 * 
 * @author C.B.
 * @version 1.1
 * @since 1.1
 *
 */
public class TableCategories extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TableModelCategories tableModelCategories;
	
	private UserListener userListener;
	
	private AccountListener accountListener;
	
	public TableCategories() {
		this.setModel(getTableModelCategories());
		conectListener();
	}
	
	private void conectListener() {
		Account[] accounts = User.get().getAccounts();
		User.get().addListener(getUserListener());
		for(Account account : accounts) {
			account.addListener(getAccountListener());
		}
	}
	
	public void refresh() {
		this.tableModelCategories = null;
		this.setModel(getTableModelCategories());
		repaint();
	}
	
	public void refresh(Date dateStart,Date dateEnd) {
		this.tableModelCategories = new TableModelCategories(dateStart, dateEnd);
		this.setModel(getTableModelCategories());
		repaint();
	}

	/**
	 * @return the tableModelCategories
	 */
	public TableModelCategories getTableModelCategories() {
		if(tableModelCategories == null) {
			tableModelCategories = new TableModelCategories();
		}
		return tableModelCategories;
	}

	/**
	 * @return the userListener
	 */
	public UserListener getUserListener() {
		if(userListener == null) {
			userListener = new UserListener() {
				
				@Override
				public void categoryAdd(String category) {}
				
				@Override
				public void accountRemove(Account account) {}
				
				@Override
				public void accountAdd(Account account) {
					account.addListener(getAccountListener());
				}

				@Override
				public void needSaveChange() {
					// TODO Auto-generated method stub
					
				}
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
				
				@Override
				public void transactionRemove(Transaction transaction) {
					refresh();
				}
				
				@Override
				public void transactionAdd(Transaction transaction) {
					refresh();
				}
			};
		}
		return accountListener;
	}

}
