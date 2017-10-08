package ihm.swing.info;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import ihm.PanelOCR;
import ihm.swing.table.all_accounts.TableAllAccounts;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.1
 *
 */
public class TabInfo extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2981679288696824579L;

	private TableAllAccounts tableAllAccounts;
	
	private PanelCategories panelCategories;
	
	private PanelAccountTransfert panelAccountTransfert;
	
	private PanelTransactionDetails panelTransactionDetails;

	public TabInfo() {
		super();
		JScrollPane scrollPane = new JScrollPane(getTableAllAccounts());
		this.add("Accounts", scrollPane);
		this.addTab("Categories", getPanelCategories());
		this.addTab("Transfert", getPanelAccountTransfert());
		this.addTab("Transaction details", getPanelTransactionDetails());
		this.addTab("OCR", PanelOCR.get());
	}


	/**
	 * @return the tableCategorie
	 */
	public PanelCategories getPanelCategories() {
		if(panelCategories == null) {
			panelCategories =new PanelCategories();
		}
		return panelCategories;
	}


	/**
	 * @return the tableAllAccount
	 */
	public TableAllAccounts getTableAllAccounts() {
		if(tableAllAccounts == null) {
			tableAllAccounts = new TableAllAccounts();
		}
		return tableAllAccounts;
	}


	/**
	 * @return the panelAccountTransfert
	 */
	public PanelAccountTransfert getPanelAccountTransfert() {
		if(panelAccountTransfert == null) {
			panelAccountTransfert = new PanelAccountTransfert();
		}
		return panelAccountTransfert;
	}


	/**
	 * @return the panelTransactionDetails
	 */
	public PanelTransactionDetails getPanelTransactionDetails() {
		if(panelTransactionDetails == null) {
			panelTransactionDetails = new PanelTransactionDetails();
		}
		return panelTransactionDetails;
	}
}
