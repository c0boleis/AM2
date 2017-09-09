package ihm.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import controller.User;
import controller.listener.UserListener;
import model.Account;

/**
 * 
 * @author C.B.
 * @version 1.0
 *
 */
public class MenuBarAccount extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 643422353556331318L;

	private JMenu menuFile;

	private JMenu menuOption;

	private JMenuItem menuItemAddAccount;

	private JMenuItem menuItemRemoveAccount;

	private JMenuItem menuItemSave;

	private JMenuItem menuItemClose;

	private JMenuItem menuItemAbout;
	
	private JCheckBoxMenuItem menuItemDisplayCalcul;
	
	private UserListener userListener;

	public MenuBarAccount() {
		super();
		this.add(getMenuFile());
		this.add(getMenuOption());
		User.get().addListener(getUserListener());
	}

	/**
	 * @return the menuFile
	 */
	private JMenu getMenuFile() {
		if(menuFile == null) {
			menuFile = new JMenu("File");
			menuFile.add(getMenuItemAddAccount());
			menuFile.add(getMenuItemRemoveAccount());
			menuFile.add(new JSeparator());
			menuFile.add(getMenuItemSave());
			menuFile.add(getMenuItemClose());
		}
		return menuFile;
	}

	/**
	 * @return the menuOption
	 */
	private JMenu getMenuOption() {
		if(menuOption == null) {
			menuOption = new JMenu("Options");
			menuOption.add(getMenuItemDisplayCalcul());
			menuOption.add(getMenuItemAbout());
		}
		return menuOption;
	}

	/**
	 * @return the menuItemSave
	 */
	private JMenuItem getMenuItemSave() {
		if(menuItemSave == null) {
			menuItemSave = new JMenuItem("Save");
			KeyStroke f5 = KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK);
			menuItemSave.setAccelerator(f5);
			menuItemSave.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					User.get().save();
				}
			});
		}
		return menuItemSave;
	}

	/**
	 * @return the menuItemClose
	 */
	private JMenuItem getMenuItemClose() {
		if(menuItemClose == null) {
			menuItemClose = new JMenuItem("Close");
			KeyStroke f5 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,KeyEvent.ALT_MASK);
			menuItemClose.setAccelerator(f5);
			menuItemClose.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Window.get().close();

				}
			});
		}
		return menuItemClose;
	}

	/**
	 * @return the menuItemAbout
	 */
	private JMenuItem getMenuItemAbout() {
		if(menuItemAbout == null) {
			menuItemAbout = new JMenuItem("About");
			KeyStroke f1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1,0);
			menuItemAbout.setAccelerator(f1);
			menuItemAbout.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(Window.get(), "Account management version: "+User.VERSION);
					
				}
			});
		}
		return menuItemAbout;
	}

	/**
	 * @return the menuItemRemoveAccount
	 */
	public JMenuItem getMenuItemRemoveAccount() {
		if(menuItemRemoveAccount == null) {
			menuItemRemoveAccount = new JMenuItem("Remove account");
			menuItemRemoveAccount.setEnabled(User.get().getAccounts().length>0);
			menuItemRemoveAccount.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					int nbrAccount = User.get().getAccounts().length;
					Account accountRemove = null;
					switch (nbrAccount) {
					case 0:
						return;
					case 1:
						int rep = JOptionPane.showConfirmDialog(Window.get(),
								"Voulez-vous supprimer le compte: "+User.get().getAccounts()[0].getAccountNumber(),
								"Supprimer Compte",JOptionPane.YES_NO_OPTION);
						if(rep == JOptionPane.YES_OPTION) {
							accountRemove = User.get().getAccounts()[0];
						}
						break;
					default:
						accountRemove = (Account) JOptionPane.showInputDialog(Window.get(),
								"Choose the account",
								"remove account",
								JOptionPane.QUESTION_MESSAGE,
								null, User.get().getAccounts(),
								User.get().getAccounts()[0]);
						break;
					}
					if(accountRemove!=null) {
						User.get().removeAccount(accountRemove);
					}
				}
			});
		}
		return menuItemRemoveAccount;
	}

	/**
	 * @return the menuItemAddAccount
	 */
	public JMenuItem getMenuItemAddAccount() {
		if(menuItemAddAccount == null) {
			menuItemAddAccount = new JMenuItem("Add Account");
			KeyStroke f1 = KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK);
			menuItemAddAccount.setAccelerator(f1);
			menuItemAddAccount.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String accountNumber = JOptionPane.showInputDialog(
							Window.get(),
							"Enter the account Number",
							"New Account");
					if(accountNumber ==null) {
						return;
					}
					User.get().addAccount(new Account(accountNumber));
				}
			});
		}
		return menuItemAddAccount;
	}

	/**
	 * @return the userListener
	 */
	private UserListener getUserListener() {
		if(userListener == null) {
			userListener = new UserListener() {
				
				@Override
				public void categoryAdd(String category) {
				}
				
				@Override
				public void accountRemove(Account account) {
					getMenuItemRemoveAccount().setEnabled(User.get().getAccounts().length>0);
				}
				
				@Override
				public void accountAdd(Account account) {
					getMenuItemRemoveAccount().setEnabled(User.get().getAccounts().length>0);
				}

				@Override
				public void needSaveChange() {
					// TODO Auto-generated method stub
					
				}
			};
		}
		return userListener;
	}

	/**
	 * @return the menuItemDisplayCalcul
	 */
	private JCheckBoxMenuItem getMenuItemDisplayCalcul() {
		if(menuItemDisplayCalcul == null) {
			menuItemDisplayCalcul = new JCheckBoxMenuItem("Calcule");
			menuItemDisplayCalcul.setSelected(false);
			menuItemDisplayCalcul.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Window.get().setDisplayCalcul(menuItemDisplayCalcul.isSelected());
				}
			});
		}
		return menuItemDisplayCalcul;
	}

}
