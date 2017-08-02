package model;

import java.util.Date;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.2
 *
 */
public class TransactionDetail extends Transaction{
	
	private Quantity quantity;
	
	public static final char SEPARATOR = '/';

	public TransactionDetail(String category, String comment, double amount,Quantity quantity)
			throws AmountException {
		super(new Date(System.currentTimeMillis()), category, comment, amount, false);
		this.setQuantity(quantity);
	}

	/**
	 * @return the quantity
	 */
	public Quantity getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(getQuantity()==null) {
			return  getCategory() + TransactionDetail.SEPARATOR +
					getComment() + TransactionDetail.SEPARATOR +
					getAmount()+TransactionDetail.SEPARATOR+
					"null";
		}else {
			return  getCategory() + TransactionDetail.SEPARATOR +
					getComment() + TransactionDetail.SEPARATOR +
					getAmount()+TransactionDetail.SEPARATOR+
					getQuantity().toString();
		}
		
	}
	
	/**
	 * 
	 * @param line
	 * @return {@link TransactionDetail}
	 * @throws AmountException 
	 */
	public static TransactionDetail parse(String line) throws AmountException {
		if(line==null) {
			return null;
		}
		String info[] = line.split(String.valueOf(TransactionDetail.SEPARATOR));
		if(info.length<4) {
			return null;
		}
		String category = info[0];
		String comment = info[1];
		double amount = Double.parseDouble(info[2]);
		Quantity qty = Quantity.parse(info[3]);
		return new TransactionDetail(category, comment, amount, qty);
	}

}
