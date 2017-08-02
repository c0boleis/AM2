package ihm.swing.table.account;

import java.awt.Dimension;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.AccountListener;
import controller.TransactionListener;
import controller.User;
import controller.UserListener;
import ihm.swing.Window;
import model.Account;
import model.Transaction;
import model.TransactionDetail;

/**
 * 
 * @author C.B.
 * @version 1.0
 *
 */
public class AccountTableSwing extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2645615808393445161L;

	private AccountTableModelSwing accountTableModel;

	private Account account;

	private static JComboBox<String> comboBoxCategories;

	public static final String NEW_CATEGORY_BOX = "New Cat.";
	
	private static DefaultCellEditor cellCotegoryEditor;

	private UserListener userListener;

	private AccountListener accountListener;

	private ListSelectionListener listSelectionListener;
	
	private JScrollPane scrollPane;
	
	private boolean addTransactionProcess = false;
	
	private TransactionListener transactionListener;
	
	/**
	 * 
	 * @param account
	 */
	public AccountTableSwing(Account account) {
		super();
		this.account = account;
		this.setMinimumSize(new Dimension(200, 200));
		refresh();
		this.addMouseListener(new JTableButtonMouseListener(this));
		conectListeners();
	}
	
	private void conectListeners() {
		Transaction[] transactions = this.account.getTransactions();
		for(Transaction transaction : transactions) {
			transaction.addListener(getTransactionListener());
		}
		this.account.addListener(getAccountListener());
		this.getSelectionModel().addListSelectionListener(getListSelectionListener());
	}

	public void refresh() {
		accountTableModel = null;
		cellCotegoryEditor = null;
		comboBoxCategories = null;
		this.setModel(getAccountTableModel());
		this.getColumnModel().getColumn(1).setCellEditor(getCellCotegoryEditor());
		this.getColumnModel().getColumn(5).setCellRenderer(new ButtonRemoveAdd());

		this.getColumnModel().getColumn(0).setMinWidth(40);
		this.getColumnModel().getColumn(1).setMinWidth(40);
		this.getColumnModel().getColumn(2).setMinWidth(40);
		this.getColumnModel().getColumn(3).setMinWidth(40);
		this.getColumnModel().getColumn(4).setMinWidth(40);
		this.getColumnModel().getColumn(5).setMinWidth(40);
		repaint();
		if(this.getScrollPane()!=null) {
			this.getScrollPane().getVerticalScrollBar().setValue(0);
		}
	}

	/**
	 * @return the accountTableModel
	 */
	public AccountTableModelSwing getAccountTableModel() {
		if(accountTableModel == null) {
			accountTableModel = new AccountTableModelSwing(this.account,this);
		}
		return accountTableModel;
	}

	/**
	 * @return the cellCotegoryEditor
	 */
	public static DefaultCellEditor getCellCotegoryEditor() {
		if(cellCotegoryEditor == null) {
			cellCotegoryEditor = new DefaultCellEditor(getComboBoxCategories());
		}
		return cellCotegoryEditor;
	}

	/**
	 * @return the userListener
	 */
	public UserListener getUserListener() {
		if(userListener == null) {
			userListener = new UserListener() {

				@Override
				public void categoryAdd(String category) {
					getComboBoxCategories().addItem(category);
					refresh();
				}

				@Override
				public void accountAdd(Account account) {
				}

				@Override
				public void accountRemove(Account account) {
				}

				@Override
				public void needSaveChange() {}
			};
		}
		return userListener;
	}

	/**
	 * @return the comboBoxCategories
	 */
	private static JComboBox<String> getComboBoxCategories() {
		if(comboBoxCategories == null) {
			comboBoxCategories = new JComboBox<String>();
			String[] categories = User.get().getCategories();
			comboBoxCategories.addItem(NEW_CATEGORY_BOX);
			for(String st : categories) {
				comboBoxCategories.addItem(st);
			}
		}
		return comboBoxCategories;
	}

	/**
	 * @return the accountListener
	 */
	private AccountListener getAccountListener() {
		if(accountListener == null) {
			accountListener = new AccountListener() {

				@Override
				public void transactionRemove(Transaction transaction) {
					refresh();
					transaction.removeListener(getTransactionListener());
				}

				@Override
				public void transactionAdd(Transaction transaction) {
					refresh();
					transaction.addListener(getTransactionListener());
				}
			};
		}
		return accountListener;
	}

	public Account getAccount() {
		return this.account;
	}

	/**
	 * @return the listSelectionListener
	 */
	private ListSelectionListener getListSelectionListener() {
		if(listSelectionListener == null) {
			listSelectionListener = new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					if(isAddTransactionProcess()) {
						return;
					}
					int lastIndex = getSelectedRow();
					Transaction transaction = null;
					if(lastIndex==0) {
						transaction = null;
					}else {
						transaction = account.geTransactionAt(lastIndex-1);
					}
					Window.get().getTabInfo()
					.getPanelTransactionDetails()
					.setTransaction(transaction);
				}
			};
		}
		return listSelectionListener;
	}

	/**
	 * @return the scrollPane
	 */
	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	/**
	 * @param scrollPane the scrollPane to set
	 */
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	/**
	 * @return the addTransactionProcess
	 */
	public boolean isAddTransactionProcess() {
		return addTransactionProcess;
	}

	/**
	 * @param addTransactionProcess the addTransactionProcess to set
	 */
	public void setAddTransactionProcess(boolean addTransactionProcess) {
		this.addTransactionProcess = addTransactionProcess;
	}

	/**
	 * @return the transactionListener
	 */
	private TransactionListener getTransactionListener() {
		if(transactionListener == null) {
			transactionListener = new TransactionListener() {
				
				@Override
				public void transactionDetailRemove(TransactionDetail transactionDetail) {}
				
				@Override
				public void transactionDetailAdd(TransactionDetail transactionDetail) {}
				
				@Override
				public void doneChange(boolean oldDone, boolean newDone) {
					repaint();
				}
				
				@Override
				public void dateChange(Date oldDate, Date newDate) {
					repaint();
				}
				
				@Override
				public void commentChange(String oldComment, String newComment) {
					repaint();
				}
				
				@Override
				public void categoryChange(String oldCategory, String newCategory) {
					repaint();
				}
				
				@Override
				public void amountChange(double oldAmount, double newAmount) {
					repaint();
				}
			};
		}
		return transactionListener;
	}

}
