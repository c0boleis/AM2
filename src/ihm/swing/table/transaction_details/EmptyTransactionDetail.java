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

	public EmptyTransactionDetail() throws AmountException {
		super( "", "", 10.0,new Quantity("pcs", 0.0));
	}

	public TransactionDetail getNewTransactionDetail() throws AmountException {
		return new TransactionDetail(getCategory(), getComment(), getAmount(), getQuantity());
	}

}
