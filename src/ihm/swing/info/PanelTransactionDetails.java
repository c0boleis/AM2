package ihm.swing.info;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import controller.TransactionListener;
import controller.User;
import ihm.swing.table.transaction_details.TransactionDetailsTable;
import model.AmountException;
import model.Transaction;
import model.TransactionDetail;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.2
 *
 */
public class PanelTransactionDetails extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1882493280537107155L;

	private JTextField textFieldDate;

	private JTextField textFieldComment;

	private TransactionDetailsTable transactionDetailsTable;

	private Transaction transaction;

	private JComboBox<String> comboBoxCategory;

	private JFormattedTextField textFieldAmount;

	private JCheckBox checkBoxDone;

	private TransactionListener transactionListener;

	private boolean modifyFieldAllowed = true;

	private boolean processInit = false;

	public PanelTransactionDetails() {
		super();
		processInit = true;
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(getTransactionDetailsTable());
		add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.WEST);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(80dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
				new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));

		JLabel lblDate = new JLabel("Date :");
		panel.add(lblDate, "2, 2, right, default");

		textFieldDate = new JTextField();
		textFieldDate.setEnabled(false);
		panel.add(textFieldDate, "4, 2, fill, default");
		textFieldDate.setColumns(10);

		JLabel lblCategory = new JLabel("Category :");
		panel.add(lblCategory, "2, 4, right, default");

		comboBoxCategory = new JComboBox<String>();
		comboBoxCategory.setEnabled(false);
		comboBoxCategory.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(transaction==null|| processInit) {
					return;
				}
				modifyFieldAllowed = false;
				transaction.setCategory((String) comboBoxCategory.getSelectedItem());
				modifyFieldAllowed = true;
			}
		});
		panel.add(comboBoxCategory, "4, 4, fill, default");

		JLabel lblComment = new JLabel("Comment :");
		panel.add(lblComment, "2, 6, right, default");

		textFieldComment = new JTextField();
		textFieldComment.setEnabled(false);
		textFieldComment.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(transaction==null|| processInit) {
					return;
				}
				modifyFieldAllowed = false;
				transaction.setComment(textFieldComment.getText());
				modifyFieldAllowed = true;
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if(transaction==null|| processInit) {
					return;
				}
				modifyFieldAllowed = false;
				transaction.setComment(textFieldComment.getText());
				modifyFieldAllowed = true;

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if(transaction==null|| processInit) {
					return;
				}
				modifyFieldAllowed = false;
				transaction.setComment(textFieldComment.getText());
				modifyFieldAllowed = true;

			}
		});
		panel.add(textFieldComment, "4, 6, fill, default");
		textFieldComment.setColumns(10);

		JLabel lblAmount = new JLabel("Amount :");
		panel.add(lblAmount, "2, 8, right, default");

		textFieldAmount = new JFormattedTextField();
		textFieldAmount.setEnabled(false);
		textFieldAmount.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(transaction==null || processInit) {
					return;
				}
				modifyFieldAllowed = false;
				double d = transaction.getAmount();
				try {
					d = Double.parseDouble(textFieldAmount.getText());
					transaction.setAmount(d);
				}catch(NumberFormatException exp) {

				} catch (AmountException e1) {
					e1.printStackTrace();
				}
				modifyFieldAllowed = true;
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if(transaction==null || processInit) {
					return;
				}
				modifyFieldAllowed = false;
				double d = transaction.getAmount();
				try {
					d = Double.parseDouble(textFieldAmount.getText());
					transaction.setAmount(d);
				}catch(NumberFormatException exp) {

				} catch (AmountException e1) {
					e1.printStackTrace();
				}
				modifyFieldAllowed = true;

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if(transaction==null || processInit) {
					return;
				}
				modifyFieldAllowed = false;
				double d = transaction.getAmount();
				try {
					d = Double.parseDouble(textFieldAmount.getText());
					transaction.setAmount(d);
				}catch(NumberFormatException exp) {

				} catch (AmountException e1) {
					e1.printStackTrace();
				}
				modifyFieldAllowed = true;

			}
		});
		panel.add(textFieldAmount, "4, 8, fill, default");

		JLabel lblDone = new JLabel("Done :");
		panel.add(lblDone, "2, 10, right, default");

		checkBoxDone = new JCheckBox("");
		checkBoxDone.setEnabled(false);
		checkBoxDone.setSelected(false);
		checkBoxDone.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if(transaction==null || processInit) {
					return;
				}
				modifyFieldAllowed = false;
				transaction.setDone(checkBoxDone.isSelected());
				modifyFieldAllowed = true;
			}
		});
		panel.add(checkBoxDone, "4, 10");
		processInit = false;
	}

	/**
	 * @return the transactionDetailsTable
	 */
	public TransactionDetailsTable getTransactionDetailsTable() {
		if(transactionDetailsTable == null) {
			transactionDetailsTable = new TransactionDetailsTable(this.transaction);
		}
		return transactionDetailsTable;
	}
	/**
	 * @return the transaction
	 */
	public Transaction getTransaction() {
		return transaction;
	}
	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(Transaction transaction) {
		if(this.transaction!=null) {
			this.transaction.removeListener(getTransactionListener());
		}
		this.transaction = transaction;
		if(this.transaction==null) {
			this.textFieldDate.setText("");
			this.textFieldDate.setEnabled(false);

			this.comboBoxCategory.removeAllItems();
			this.comboBoxCategory.setEnabled(false);

			this.textFieldComment.setText("");
			this.textFieldComment.setEnabled(false);

			this.textFieldAmount.setText("0,00");
			this.textFieldAmount.setEnabled(false);

			this.checkBoxDone.setSelected(false);
			this.checkBoxDone.setEnabled(false);
		}else {
			this.transaction.addListener(getTransactionListener());
			processInit = true;
			this.textFieldDate.setText(Transaction.dateFormat.format(this.transaction.getDate()));
			this.textFieldDate.setEnabled(false);

			fillComboBox();
			this.comboBoxCategory.setSelectedItem(this.transaction.getCategory());
			this.comboBoxCategory.setEnabled(true);

			this.textFieldComment.setText(this.transaction.getComment());
			this.textFieldComment.setEnabled(true);

			this.textFieldAmount.setText(String.valueOf(this.transaction.getAmount()));
			this.textFieldAmount.setEnabled(true);

			this.checkBoxDone.setSelected(this.transaction.isDone());
			this.checkBoxDone.setEnabled(true);
			processInit = false;
		}
		getTransactionDetailsTable().setTransaction(this.transaction);
	}

	private void fillComboBox() {
		String[] info = User.get().getCategories();
		this.comboBoxCategory.removeAllItems();
		for(String st : info) {
			this.comboBoxCategory.addItem(st);
		}
	}

	/**
	 * @return the transactionListener
	 */
	public TransactionListener getTransactionListener() {
		if(transactionListener == null) {
			transactionListener = new TransactionListener() {

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#transactionDetailRemove(model.TransactionDetail)
				 */
				@Override
				public void transactionDetailRemove(TransactionDetail transactionDetail) {
					transactionDetailsTable.refresh();
				}

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#transactionDetailAdd(model.TransactionDetail)
				 */
				@Override
				public void transactionDetailAdd(TransactionDetail transactionDetail) {
					transactionDetailsTable.refresh();
				}

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#dateChange(java.util.Date, java.util.Date)
				 */
				@Override
				public void dateChange(Date oldDate, Date newDate) {
					if(modifyFieldAllowed && transaction!=null) {
						textFieldDate.setText(Transaction.dateFormat.format(newDate));
					}
				}

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#commentChange(java.lang.String, java.lang.String)
				 */
				@Override
				public void commentChange(String oldComment, String newComment) {
					if(modifyFieldAllowed && transaction!=null) {
						textFieldComment.setText(newComment);
					}
				}

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#categoryChange(java.lang.String, java.lang.String)
				 */
				@Override
				public void categoryChange(String oldCategory, String newCategory) {
					if(modifyFieldAllowed && transaction!=null) {
						comboBoxCategory.setSelectedItem(newCategory);
						//TODO check modify
					}

				}

				/*
				 * (non-Javadoc)
				 * @see controller.TransactionListener#amountChange(double, double)
				 */
				@Override
				public void amountChange(double oldAmount, double newAmount) {
					if(modifyFieldAllowed && transaction!=null) {
						textFieldAmount.setText(String.valueOf(newAmount).replaceAll(".", ","));
					}
				}

				@Override
				public void doneChange(boolean oldDone, boolean newDone) {
					if(modifyFieldAllowed && transaction!=null) {
						checkBoxDone.setSelected(newDone);
					}
				}
			};
		}
		return transactionListener;
	}

}
