package controller;

import model.Account;

/**
 * 
 * @author C.B.
 * @version 1.0
 *
 */
public interface UserListener {
	
	public void accountAdd(Account account);
	
	public void accountRemove(Account account);
	
	public void categoryAdd(String category);
	
	public void needSaveChange();

}
