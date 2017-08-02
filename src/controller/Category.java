package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Transaction;

/**
 * 
 * @author C.B.
 * @version 1.1
 * @since 1.1
 *
 */
public class Category {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [name=" + name + ", transactions=" + transactions + "]";
	}

	private String name;

	private List<Transaction> transactions = new ArrayList<Transaction>();

	public Category(String name) {
		this.setName(name);
		transactions = User.get().getAllTransactionsByCategorie(getName());
	}

	public Category(String name,Date start,Date end) {
		this(name);
		transactions.clear();
		List<Transaction> transactionsTmp = User.get().getAllTransactionsByCategorie(getName());
		for(Transaction transaction : transactionsTmp) {
			Date dateTransaction = transaction.getDate();
			if(start.compareTo(dateTransaction)>0) {
				continue;
			}
			if(end.compareTo(dateTransaction)<0) {
				continue;
			}
			transactions.add(transaction);
		}
	}



	/**
	 * @return the transactions
	 */
	public List<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	private void setName(String name) {
		this.name = name.trim();
	}

	/**
	 * 
	 * @return the amount of all transactions
	 */
	public double getAmount() {
		double amoutOut = 0.0;
		for(Transaction transaction : transactions) {
			amoutOut += transaction.getAmount();
		}
		return amoutOut;
	}

	/**
	 * 
	 * @return true if the list of transaction is empty
	 */
	public boolean isEmpty() {
		return transactions.isEmpty();
	}

}
