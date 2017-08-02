package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import controller.AccountListener;
import controller.User;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.0
 *
 */
public class Account {
	
	private static final Logger LOGGER = Logger.getLogger(Account.class);
	
	private String accountNumber;
	
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	private List<AccountListener> listeners = new ArrayList<AccountListener>();
	
	private Comparator<Transaction> dateTransactionComparator;
	
	public Account(String number) {
		this.accountNumber = number.trim();
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return the transactions
	 */
	public Transaction[] getTransactions() {
		return transactions.toArray(new Transaction[0]);
	}
	
	/**
	 * 
	 * @param index of the transaction
	 * @return a {@link Transaction}
	 */
	public Transaction geTransactionAt(int index) {
		return this.transactions.get(index);
	}
	
	/**
	 * 
	 * @param transaction to add
	 * @return true if the transaction was add.
	 */
	public boolean addTransaction(Transaction transaction) {
		if(transaction==null) {
			LOGGER.debug("transaction wasn't add because it's null");
			return false;
		}
		if(transactions.contains(transaction)) {
			LOGGER.debug("transaction wasn't add because it already in the account");
			return false;
		}
		this.transactions.add(transaction);
		if(User.get()!=null) {
			User.get().addCategory(transaction.getCategory());
		}
		/*
		 * sort transaction
		 */
		Collections.sort(this.transactions, getDateTransactionComparator());
		fireTransactionAdd(transaction);
		LOGGER.info(transaction.toString()+" add");
		return true;
	}

	/**
	 * 
	 * @param transaction to remove
	 * @return true if the transaction was remove
	 */
	public boolean removeTransaction(Transaction transaction) {
		if(this.transactions.remove(transaction)) {
			fireTransactionRemove(transaction);
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return accountNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		return true;
	}

	public void save(File file) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(accountNumber+"\n");
		Transaction[] transactionsTmp = getTransactions();
		for(Transaction transaction : transactionsTmp) {
			writer.write(transaction.toString()+"\n");
		}
		writer.flush();
		writer.close();
	}
	
	public double getAmount() {
		Transaction[] transactionsTmp = getTransactions();
		double amount = 0.0;
		for(Transaction transaction : transactionsTmp) {
			amount+=transaction.getAmount();
		}
		return amount;
	}
	
	/**
	 * 
	 * @return the number of transaction
	 */
	public int getNumberOfTransaction() {
		return transactions.size();
	}
	
	public AccountListener[] getListeners() {
		return this.listeners.toArray(new AccountListener[0]);
	}
	
	public void addListener(AccountListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(AccountListener listener) {
		this.listeners.remove(listener);
	}
	
	private void fireTransactionAdd(Transaction transaction) {
		AccountListener[] listenersTmp = getListeners();
		for(AccountListener listener : listenersTmp) {
			listener.transactionAdd(transaction);
		}
	}
	
	private void fireTransactionRemove(Transaction transaction) {
		AccountListener[] listenersTmp = getListeners();
		for(AccountListener listener : listenersTmp) {
			listener.transactionRemove(transaction);
		}
	}

	/**
	 * @return the dateTransactionComparator
	 */
	public Comparator<Transaction> getDateTransactionComparator() {
		if(dateTransactionComparator == null) {
			dateTransactionComparator = new Comparator<Transaction>() {

				@Override
				public int compare(Transaction arg0, Transaction arg1) {
					return arg1.getDate().compareTo(arg0.getDate());
				}
			};
		}
		return dateTransactionComparator;
	}

	/**
	 * @since 1.2
	 * @return
	 */
	public double getAmountDone() {
		Transaction[] transactionsTmp = getTransactions();
		double amount = 0.0;
		for(Transaction transaction : transactionsTmp) {
			if(!transaction.isDone()) {
				continue;
			}
			amount+=transaction.getAmount();
		}
		return amount;
	}

}
