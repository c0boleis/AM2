package controller;

import java.util.Date;

import model.TransactionDetail;

/**
 * 
 * @author C.B.
 * @version 1.1
 * @since 1.1
 *
 */
public interface TransactionListener {
	
	public void dateChange(Date oldDate,Date newDate);
	
	public void categoryChange(String oldCategory,String newCategory);
	
	public void commentChange(String oldComment,String newComment);
	
	public void amountChange(double oldAmount,double newAmount);
	
	public void doneChange(boolean oldDone,boolean newDone);
	
	public void transactionDetailAdd(TransactionDetail transactionDetail);
	
	public void transactionDetailRemove(TransactionDetail transactionDetail);

}
