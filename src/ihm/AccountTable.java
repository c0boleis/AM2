package ihm;

import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Account;
import model.Transaction;

/**
 * 
 * @author C.B.
 * @version 1.0
 *
 */
public class AccountTable extends TableView<Transaction> {

	private TableColumn<Transaction, Date> columnDate;

	private TableColumn<Transaction, String> columnCategory;

	private TableColumn<Transaction, String> columnComment;

	private TableColumn<Transaction, Double> columnAmount;

	private Account account;
	
	private final ObservableList<Transaction> data = FXCollections.observableArrayList(
//		    new Person("Jacob", "Smith", "jacob.smith@example.com"),
//		    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
//		    new Person("Ethan", "Williams", "ethan.williams@example.com"),
//		    new Person("Emma", "Jones", "emma.jones@example.com"),
//		    new Person("Michael", "Brown", "michael.brown@example.com")
		);

	@SuppressWarnings("unchecked")
	public AccountTable(Account account) {
		this.account = account;
		this.getColumns().addAll(
				this.getColumnDate(),
				this.getColumnCategory(),
				this.getColumnComment(),
				this.getColumnAmount());
		Transaction[] transactions = account.getTransactions();
		for(Transaction transaction : transactions) {
			data.add(transaction);
		}
		this.setItems(data);
	}

	/**
	 * @return the columnDate
	 */
	public TableColumn<Transaction, Date> getColumnDate() {
		if(columnDate == null) {
			columnDate = new TableColumn<Transaction,Date>("Date");
			columnDate.setCellValueFactory(
					new PropertyValueFactory<Transaction,Date>("date")
					);
		}
		return columnDate;
	}

	/**
	 * @return the columnCategory
	 */
	public TableColumn<Transaction, String> getColumnCategory() {
		if(columnCategory == null) {
			columnCategory = new TableColumn<Transaction,String>("Category");
			columnCategory.setCellValueFactory(
					new PropertyValueFactory<Transaction,String>("category")
					);
		}
		return columnCategory;
	}

	/**
	 * @return the columnComment
	 */
	public TableColumn<Transaction, String> getColumnComment() {
		if(columnComment == null) {
			columnComment = new TableColumn<Transaction,String>("Comment");
			columnComment.setCellValueFactory(
					new PropertyValueFactory<Transaction,String>("comment")
					);
		}
		return columnComment;
	}

	/**
	 * @return the columnAmount
	 */
	public TableColumn<Transaction, Double> getColumnAmount() {
		if(columnAmount == null) {
			columnAmount = new TableColumn<Transaction,Double>("Amount");
			columnAmount.setCellValueFactory(
					new PropertyValueFactory<Transaction,Double>("amount")
					);
		}
		return columnAmount;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

}
