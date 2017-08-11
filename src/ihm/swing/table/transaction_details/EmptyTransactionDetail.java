package ihm.swing.table.transaction_details;

import model.AmountException;
import model.Quantity;
import model.TransactionDetail;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.2
 *
 */
public class EmptyTransactionDetail extends TransactionDetail {
	
	private double amountDetail = 0.0;

	public EmptyTransactionDetail() throws AmountException {
		super( "", "", 0.0,new Quantity("pcs", 0.0));
	}

	public TransactionDetail getNewTransactionDetail() throws AmountException {
		return new TransactionDetail(getCategory(), getComment(), getAmount(), getQuantity());
	}

	/* (non-Javadoc)
	 * @see model.Transaction#getAmount()
	 */
	@Override
	public double getAmount() {
		return this.amountDetail;
	}

	/* (non-Javadoc)
	 * @see model.Transaction#setAmount(double)
	 */
	@Override
	public void setAmount(double amount) throws AmountException {
		this.amountDetail = amount;
	}

	
}
