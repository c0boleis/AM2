package ihm.swing.table.account;

import java.util.Date;

import model.AmountException;
import model.Transaction;

/**
 * 
 * @author C.B.
 * @version 1.0
 * @since 1.0
 */
public class EmptyTransaction extends Transaction {

	public EmptyTransaction() throws AmountException {
		super(new Date(System.currentTimeMillis()), "", "", 10.0,false);
	}
	
	public Transaction getNewTransaction() throws AmountException {
		if(this.getAmount()>0.0) {
			return new Transaction(new Date(System.currentTimeMillis()), getCategory(), getComment(), getAmount(),isDone());
		}else if(this.getAmount()<0.0){
			return new Transaction(new Date(System.currentTimeMillis()), getCategory(), getComment(), getAmount(),isDone());
		}
		return null;
	}

}
