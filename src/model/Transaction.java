package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import controller.listener.TransactionListener;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.0
 *
 */
public class Transaction {
	
	private static final Logger LOGGER = Logger.getLogger(Transaction.class);

	public static final char SEPARATOR = ';';

	private Date date;

	private String category;

	private String comment;

	private double amount;

	private boolean done = false;

	private List<TransactionDetail> transactionDetails = new ArrayList<TransactionDetail>();

	private List<TransactionListener> listeners = new ArrayList<TransactionListener>();

	public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");

	/**
	 * 
	 * @param date
	 * @param category
	 * @param comment
	 * @param amount
	 * @throws AmountException 
	 */
	public Transaction(Date date, String category, String comment, double amount,boolean done) throws AmountException {
		super();
		this.setDate(date);
		this.setCategory(category);
		this.setComment(comment);
		this.setAmount(amount);
		this.setDone(done);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
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
		Transaction other = (Transaction) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		Date oldDate = this.date;
		this.date = date;
		if(this.date==null  && oldDate!=null) {
			fireDateChange(oldDate, this.date);
			return;
		}
		if(this.date!=null  && oldDate==null) {
			fireDateChange(oldDate, this.date);
			return;
		}
		if(this.date==null  && oldDate==null) {
			return;
		}
		if(this.date.compareTo(oldDate)==0) {
			return;
		}
		fireDateChange(oldDate, this.date);
	}

	/**
	 * @return the categorie
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		if(category==null) {
			category = "";
		}
		String oldCategory = this.category;
		this.category = category;
		if(this.category.equals(oldCategory)) {
			return;
		}
		LOGGER.debug(toString()+" categorySet");
		fireCategoryChange(oldCategory, this.category);
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		if(comment == null) {
			comment = "";
		}
		String oldComment = this.comment;
		this.comment = comment;
		if(this.comment.equals(oldComment)) {
			return;
		}
		LOGGER.debug(toString()+" commentSet");
		fireCommentChange(oldComment, this.comment);
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 * @throws AmountException 
	 */
	public void setAmount(double amount) throws AmountException {
		if(Math.abs(amount)<0.00001) {
			throw new AmountException("The amount can't be null");
		}
		double oldAmount = this.amount;
		this.amount = amount;
		if(Double.compare(this.amount, oldAmount)==0) {
			return;
		}
		LOGGER.debug(toString()+" amountSet");
		fireAmountChange(oldAmount, this.amount);
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String outSt =  dateFormat.format(date) + Transaction.SEPARATOR +
				category + Transaction.SEPARATOR +
				comment + Transaction.SEPARATOR +
				amount+Transaction.SEPARATOR+String.valueOf(isDone());
		TransactionDetail[] listTmp = this.getTransactionDetails();
		for(TransactionDetail transactionDetail : listTmp) {
			outSt+=Transaction.SEPARATOR+transactionDetail.toString();
		}
		return outSt;
	}

	public static Transaction parse(String line) throws AmountException {
		if(line==null) {
			return null;
		}
		String info[] = line.split(";");
		if(info.length<5) {
			return null;
		}
		Date date = null;
		try {
			date = dateFormat.parse(info[0]);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		String category = info[1];
		String comment = info[2];
		double amount = Double.parseDouble(info[3]);
		boolean done = Boolean.parseBoolean(info[4]);
		Transaction transaction = new Transaction(date, category, comment, amount,done);
		if(info.length<=5) {
			return transaction;
		}
		for(int index = 5;index<info.length;index++){
			TransactionDetail transactionDetail = TransactionDetail.parse(info[index]);
			transaction.addTransactionDetail(transactionDetail);
		}
		return transaction;
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		boolean oldDone = this.done;
		this.done = done;
		if(Boolean.compare(done, oldDone)!=0) {
			fireDoneChange(oldDone, this.done);
		}
	}

	public TransactionListener[] getListeners() {
		return this.listeners.toArray(new TransactionListener[0]);
	}

	public void addListener(TransactionListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(TransactionListener listener) {
		this.listeners.remove(listener);
	}

	private void fireDateChange(Date oldDate,Date newDate) {
		TransactionListener[] listenersTmp = getListeners();
		for(TransactionListener listener : listenersTmp) {
			listener.dateChange(oldDate, newDate);
		}
	}

	private void fireCategoryChange(String oldCategory,String newCategory) {
		TransactionListener[] listenersTmp = getListeners();
		for(TransactionListener listener : listenersTmp) {
			listener.categoryChange(oldCategory, newCategory);
		}
	}

	private void fireCommentChange(String oldComment,String newComment) {
		TransactionListener[] listenersTmp = getListeners();
		for(TransactionListener listener : listenersTmp) {
			listener.commentChange(oldComment, newComment);
		}
	}

	private void fireAmountChange(double oldAmount,double newAmmount) {
		TransactionListener[] listenersTmp = getListeners();
		for(TransactionListener listener : listenersTmp) {
			listener.amountChange(oldAmount, newAmmount);
		}
	}
	
	private void fireTransactionDetailAdd(TransactionDetail transactionDetail) {
		TransactionListener[] listenersTmp = getListeners();
		for(TransactionListener listener : listenersTmp) {
			listener.transactionDetailAdd(transactionDetail);
		}
	}
	
	private void fireTransactionDetailRemove(TransactionDetail transactionDetail) {
		TransactionListener[] listenersTmp = getListeners();
		for(TransactionListener listener : listenersTmp) {
			listener.transactionDetailRemove(transactionDetail);
		}
	}
	
	private void fireDoneChange(boolean oldDone,boolean newDone) {
		TransactionListener[] listenersTmp = getListeners();
		for(TransactionListener listener : listenersTmp) {
			listener.doneChange(oldDone, newDone);
		}
	}

	/**
	 * @since 1.2
	 * @return the transactionDetails
	 */
	public TransactionDetail[] getTransactionDetails() {
		return transactionDetails.toArray(new TransactionDetail[0]);
	}

	/**
	 * @since 1.2
	 * @param transactionDetail to add
	 * @return true if {@link TransactionDetail} was add.
	 */
	public boolean addTransactionDetail(TransactionDetail transactionDetail) {
		if(transactionDetail == null) {
			return false;
		}
		if(transactionDetails.contains(transactionDetail)) {
			return false;
		}
		transactionDetails.add(transactionDetail);
		fireTransactionDetailAdd(transactionDetail);
		return true;
	}

	/**
	 * @since 1.2
	 * @param transactionDetail to remove
	 * @return true if the {@link TransactionDetail} was removed
	 */
	public boolean removeTransactionDetail(TransactionDetail transactionDetail) {
		if(transactionDetail == null) {
			return false;
		}
		boolean b =  this.transactionDetails.remove(transactionDetail);
		if(!b) {
			return false;
		}
		this.fireTransactionDetailRemove(transactionDetail);
		return true;
	}
}
