package controller;

import model.Transaction;

/**
 * 
 * @author C.B.
 * @version 1.0
 *
 */
public interface AccountListener {
	
	/**
	 * 
	 * @param transaction add
	 */
	public void transactionAdd(Transaction transaction);
	
	/**
	 * 
	 * @param transaction remove
	 */
	public void transactionRemove(Transaction transaction);

}
