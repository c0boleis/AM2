package ihm;

import java.text.ParseException;
import java.util.Date;

import controller.User;
import model.Account;
import model.AmountException;
import model.Transaction;

/**
 * 
 * @author C.B.
 * @version 1.0
 *
 */
public class Test {

	public static void main(String[] args) {
		Date date = null;
		try {
			date = Transaction.dateFormat.parse("01/07/2017");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Account account = null;
		account = User.get().getAccounts()[0];
//		account = new Account("000667345");
		int nbrOfAccount = User.get().getAccounts().length;
		System.out.println("there is "+nbrOfAccount+" account(s)");
		if(User.get().addAccount(account)) {
			System.out.println(account.getAccountNumber()+" was add");
		}else {
			System.out.println(account.getAccountNumber()+" wasn't add");
		}
		try {
			account.addTransaction(new Transaction(date, "test_6", "nothing", 450.0,false));
			account.addTransaction(new Transaction(date, "test", "nothing", -950.0,false));
		} catch (AmountException e) {
			e.printStackTrace();
		}
		System.out.println(account.getAccountNumber()+": "+account.getAmount()+" euros");
		User.get().save();
	}

}
