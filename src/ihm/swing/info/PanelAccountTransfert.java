package ihm.swing.info;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controller.User;
import controller.listener.UserListener;
import model.Account;
import model.AmountException;
import model.Transaction;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.2
 *
 */
public class PanelAccountTransfert extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1882493280537107155L;
	private JComboBox<Account> comboBoxAccountFrom;
	private JComboBox<Account> comboBoxAccountTo;
	private JFormattedTextField textFieldAmount;
	private JButton btnTransfert;
	private UserListener userListener;
	
	public PanelAccountTransfert() {
		super();
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("60dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("76dlu"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblAccount = new JLabel("From account :");
		add(lblAccount, "2, 2, right, default");
		
		comboBoxAccountFrom = new JComboBox<Account>();
		add(comboBoxAccountFrom, "4, 2, fill, default");
		
		JLabel lblToAccount = new JLabel("to Account :");
		add(lblToAccount, "6, 2");
		
		comboBoxAccountTo = new JComboBox<Account>();
		add(comboBoxAccountTo, "8, 2, fill, default");
		
		JLabel lblAmount = new JLabel("Amount :");
		add(lblAmount, "2, 4, right, default");
		
		textFieldAmount = new JFormattedTextField();
		add(textFieldAmount, "4, 4, 5, 1, fill, default");
		
		btnTransfert = new JButton("Transfert");
		btnTransfert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				transfert();
			}
		});
		add(btnTransfert, "2, 6, 7, 1");
		
		fillComboBoxAccount();
		User.get().addListener(getUserListener());
	}
	
	private void transfert() {
		Account accountFrom = (Account) comboBoxAccountFrom.getSelectedItem();
		Account accountTo = (Account) comboBoxAccountTo.getSelectedItem();
		if(accountFrom == null || accountTo == null) {
			return;
		}
		if(accountFrom.equals(accountTo)) {
			return;
		}
		double amount = 0.0;
		try {
			amount = Double.parseDouble(textFieldAmount.getText().replaceAll(",", "."));
		}catch(NumberFormatException e) {
			e.printStackTrace();
			return;
		}
		if(Double.compare(amount, 0.0)==0) {
			return;
		}
		if(amount <0.0) {
			return;
		}
		Date date = new Date(System.currentTimeMillis());
		try {
			accountFrom.addTransaction(new Transaction(date, "Transfert", "to "+accountTo.getAccountNumber(), -amount, true));
			accountTo.addTransaction(new Transaction(date, "Transfert", "from "+accountFrom.getAccountNumber(), amount, true));
			textFieldAmount.setText("0,0");
		} catch (AmountException e) {
			e.printStackTrace();
		}
	}
	
	private void fillComboBoxAccount() {
		Account[] accounts = User.get().getAccounts();
		comboBoxAccountFrom.removeAllItems();
		comboBoxAccountTo.removeAllItems();
		for(Account account : accounts) {
			comboBoxAccountFrom.addItem(account);
			comboBoxAccountTo.addItem(account);
		}
	}

	/**
	 * @return the userListener
	 */
	private UserListener getUserListener() {
		if(userListener == null) {
			userListener = new UserListener() {
				
				@Override
				public void needSaveChange() {}
				
				@Override
				public void categoryAdd(String category) {}
				
				@Override
				public void accountRemove(Account account) {
					fillComboBoxAccount();
				}
				
				@Override
				public void accountAdd(Account account) {
					fillComboBoxAccount();
				}
			};
		}
		return userListener;
	}

}
